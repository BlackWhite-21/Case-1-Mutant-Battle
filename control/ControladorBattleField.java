package mutantbattle.control;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import mutantbattle.game.BattleField;
import mutantbattle.model.Cordenada;
import mutantbattle.model.Mutante;
import mutantbattle.model.PoderAtaque;
import mutantbattle.model.PoderCura;
import mutantbattle.model.PoderDefensa;
import mutantbattle.model.PoderInvisibilidad;
import mutantbattle.model.PoderMutante;
import mutantbattle.model.PoderVelocidad;
import mutantbattle.observer.IObserver;
import mutantbattle.observer.Observable;
import mutantbattle.util.Constantes;

/**
 * Funcionalidad del BattleField. Implementa Runnable (ciclo principal)
 * e IObserver (recibe los movimientos de los mutantes).
 */
public class ControladorBattleField implements Runnable, IObserver {

    private final BattleField battleField;
    private final Vector<MutanteThread> hilosMutantes = new Vector<>();
    private final Set<String> paresEnRadio = ConcurrentHashMap.newKeySet();
    private volatile boolean activo = true;

    public ControladorBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    // ---------------- Creación ----------------

    public void crearEquipos(int tamEquipo) {
        for (int i = 0; i < tamEquipo; i++) {
            Mutante a = crearMutanteAleatorio();
            Mutante b = crearMutanteAleatorio();
            a.addObserver(this);
            b.addObserver(this);
            battleField.agregarMutanteA(a);
            battleField.agregarMutanteB(b);
        }
    }

    private Mutante crearMutanteAleatorio() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Cordenada borde = battleField.getConfig().getBorde();

        int defensa = random.nextInt(Constantes.DEFENSA_MIN, Constantes.DEFENSA_MAX + 1);
        int ataque = random.nextInt(Constantes.DANIO_MIN, Constantes.DANIO_INICIAL_MAX + 1);
        float velocidad = (float) random.nextDouble(Constantes.VELOCIDAD_MIN, Constantes.VELOCIDAD_MAX);
        Cordenada posicion = new Cordenada(random.nextInt(borde.getX()), random.nextInt(borde.getY()));

        return new Mutante(defensa, ataque, velocidad, posicion, crearPoderAleatorio());
    }

    private PoderMutante crearPoderAleatorio() {
        switch (ThreadLocalRandom.current().nextInt(Constantes.CANTIDAD_PODERES)) {
            case 0:
                return new PoderDefensa();
            case 1:
                return new PoderAtaque();
            case 2:
                return new PoderInvisibilidad();
            case 3:
                return new PoderVelocidad();
            default:
                return new PoderCura();
        }
    }

    // ---------------- Hilos ----------------

    public void iniciarMovimiento() {
        for (Mutante mutante : todosLosMutantes()) {
            MutanteThread hilo = new MutanteThread(mutante, battleField.getConfig());
            hilosMutantes.add(hilo);
            hilo.start();
        }
    }

    @Override
    public void run() {
        while (activo && !battleField.hayGanador()) {
            try {
                Thread.sleep(Constantes.TICK_MOVIMIENTO_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        detener();
    }

    public void detener() {
        activo = false;
        for (MutanteThread hilo : hilosMutantes) {
            hilo.detener();
            hilo.interrupt();
        }
    }

    // ---------------- Observer ----------------

    @Override
    public void update(Object source, Observable ob) {
        if (ob instanceof Mutante && Constantes.EVENTO_MOVIMIENTO.equals(source)) {
            verificarRadio((Mutante) ob);
        }
    }

    // ---------------- Combate ----------------

    public void verificarRadio(Mutante mutante) {
        if (!activo || !mutante.estaVivo()) {
            return;
        }
        double radio = battleField.getConfig().getRadio();

        for (Mutante enemigo : enemigosDe(mutante)) {
            if (!enemigo.estaVivo()) {
                continue;
            }
            String par = clavePar(mutante, enemigo);
            double distancia = mutante.getCordenadas().distancia(enemigo.getCordenadas());

            if (distancia <= radio) {
                // add() es atómico: solo el primer hilo que registra el par resuelve el encuentro
                if (paresEnRadio.add(par)) {
                    resolverEncuentro(mutante, enemigo);
                }
            } else {
                // salieron del radio: pueden volver a enfrentarse cuando se acerquen otra vez
                paresEnRadio.remove(par);
            }
        }
    }

    private void resolverEncuentro(Mutante a, Mutante b) {
        // Siempre se bloquea primero al de menor id para evitar deadlocks
        Mutante primero = a.getId() < b.getId() ? a : b;
        Mutante segundo = (primero == a) ? b : a;

        synchronized (primero) {
            synchronized (segundo) {
                if (!a.estaVivo() || !b.estaVivo()) {
                    return;
                }
                // Los dos deciden en el mismo instante
                boolean aAtaca = decideAtacar();
                boolean bAtaca = decideAtacar();

                if (aAtaca) {
                    aplicarAtaque(a, b, !bAtaca);
                }
                if (bAtaca) {
                    aplicarAtaque(b, a, !aAtaca);
                }
            }
        }
    }

    private void aplicarAtaque(Mutante atacante, Mutante objetivo, boolean objetivoDefiende) {
        int danio = atacante.getAtaque();
        if (objetivoDefiende) {
            danio = (int) Math.ceil((double) danio / objetivo.getDefensa());
        }
        if (danio > 0) {
            objetivo.addEnergia(-danio);
            atacante.addAtaque(Constantes.INCREMENTO_DANIO); // tope en DANIO_MAX
        }
    }

    private boolean decideAtacar() {
        return ThreadLocalRandom.current().nextDouble() < Constantes.PROBABILIDAD_ATAQUE;
    }

    // ---------------- Auxiliares ----------------

    private Vector<Mutante> enemigosDe(Mutante mutante) {
        return battleField.getMutantesA().contains(mutante)
                ? battleField.getMutantesB()
                : battleField.getMutantesA();
    }

    private Vector<Mutante> todosLosMutantes() {
        Vector<Mutante> todos = new Vector<>(battleField.getMutantesA());
        todos.addAll(battleField.getMutantesB());
        return todos;
    }

    private String clavePar(Mutante a, Mutante b) {
        int menor = Math.min(a.getId(), b.getId());
        int mayor = Math.max(a.getId(), b.getId());
        return menor + "-" + mayor;
    }

    public BattleField getBattleField() {
        return battleField;
    }
}
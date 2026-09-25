package game;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import model.Mutante;
import util.IObserver;
import util.Observable;
import util.Constantes;

/**
 * Estado del BattleField: los dos equipos.
 * Observa a los mutantes y avisa a la UI cuando algo cambia.
 */
public class BattleField extends Observable implements IObserver {

    private final ConfiguracionBattleField config;
    private final Vector<Mutante> mutantesA = new Vector<>();
    private final Vector<Mutante> mutantesB = new Vector<>();
    private final AtomicBoolean terminado = new AtomicBoolean(false);

    public BattleField(ConfiguracionBattleField config) {
        this.config = config;
    }

    public void agregarMutanteA(Mutante mutante) {
        mutantesA.add(mutante);
        mutante.addObserver(this);
    }

    public void agregarMutanteB(Mutante mutante) {
        mutantesB.add(mutante);
        mutante.addObserver(this);
    }

    public boolean hayGanador() {
        return contarVivos(mutantesA) == 0 || contarVivos(mutantesB) == 0;
    }

    public String getGanador() {
        if (contarVivos(mutantesB) == 0) {
            return Constantes.NOMBRE_EQUIPO_A;
        }
        if (contarVivos(mutantesA) == 0) {
            return Constantes.NOMBRE_EQUIPO_B;
        }
        return null; // la batalla sigue
    }

    @Override
    public void update(Object source, Observable ob) {
        if (!Constantes.EVENTO_MUERTE.equals(source)) {
            return; // los movimientos los maneja el ControladorBattleField
        }
        notifyObservers(Constantes.EVENTO_MUERTE);

        // compareAndSet: aunque mueran dos mutantes a la vez, el fin se avisa una sola vez
        if (hayGanador() && terminado.compareAndSet(false, true)) {
            notifyObservers(Constantes.EVENTO_FIN);
        }
    }

    // ---- Marcador ----

    public int getVivosA() {
        return contarVivos(mutantesA);
    }

    public int getVivosB() {
        return contarVivos(mutantesB);
    }

    public int getMuertosA() {
        return mutantesA.size() - getVivosA();
    }

    public int getMuertosB() {
        return mutantesB.size() - getVivosB();
    }

    private int contarVivos(Vector<Mutante> equipo) {
        int vivos = 0;
        for (Mutante mutante : new Vector<>(equipo)) {
            if (mutante.estaVivo()) {
                vivos++;
            }
        }
        return vivos;
    }

    // ---- Getters ----

    public ConfiguracionBattleField getConfig() {
        return config;
    }

    public Vector<Mutante> getMutantesA() {
        return mutantesA;
    }

    public Vector<Mutante> getMutantesB() {
        return mutantesB;
    }
}
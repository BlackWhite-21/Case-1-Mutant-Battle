package game;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import model.IPoderMutante;
import model.Mutante;
import model.PoderAtaque;
import model.PoderDefensa;
import model.PoderInvisibilidad;
import model.PoderRecarga;
import model.PoderVelocidad;

import util.Constantes;

/**
 * Prueba de la Game Layer por sí sola.
 */
public class MainGame {

    public static void main(String[] args) {
        // 1. Configuración (características del campo)
        ConfiguracionBattleField config = new ConfiguracionBattleField();
        System.out.println("Tamaño de equipo: " + config.getTamEquipo());
        System.out.println("Borde: " + config.getBorde().getX() + " x " + config.getBorde().getY());
        System.out.println("Radio: " + config.getRadio());

        // 2. BattleField con los dos equipos armados a mano
        BattleField battleField = new BattleField(config);
        for (int i = 0; i < config.getTamEquipo(); i++) {
            battleField.agregarMutanteA(crearMutanteAleatorio(i));
            battleField.agregarMutanteB(crearMutanteAleatorio(i+1));
        }
        System.out.println("Mutantes equipo A: " + battleField.getMutantesA().size());
        System.out.println("Mutantes equipo B: " + battleField.getMutantesB().size());

        // 3. Al inicio nadie ha ganado
        System.out.println("¿Hay ganador al inicio? " + battleField.hayGanador() + " (esperado: false)");

        // 4. Matar a un solo mutante de B: todavía no hay ganador
        battleField.getMutantesB().get(0).addEnergia(-Constantes.ENERGIA_INICIAL);
        System.out.println("¿Hay ganador con un muerto? " + battleField.hayGanador() + " (esperado: false)");

        // 5. Matar a todo el equipo B: ahora sí hay ganador
        for (Mutante mutante : battleField.getMutantesB()) {
            mutante.addEnergia(-Constantes.ENERGIA_INICIAL);
        }
        System.out.println("¿Hay ganador sin equipo B? " + battleField.hayGanador() + " (esperado: true)");
    }

	private static Mutante crearMutanteAleatorio(int id) {
        Random random = new Random();
		ConfiguracionBattleField configuracionBattleField = new ConfiguracionBattleField();
		BattleField battleField = new BattleField(configuracionBattleField);


        Point borde = battleField.getConfig().getBorde();

        int defensa = random.nextInt(Constantes.DEFENSA_MIN, Constantes.DEFENSA_MAX + 1);
        int ataque = random.nextInt(Constantes.DANIO_MIN, Constantes.DANIO_INICIAL_MAX + 1);
        float velocidad = (float) random.nextDouble(Constantes.VELOCIDAD_MIN, Constantes.VELOCIDAD_MAX);
        Point posicion = new Point(random.nextInt((int)(borde.getX())), random.nextInt((int)(borde.getY())));

        return new Mutante(id, defensa, ataque, velocidad, posicion, crearPoderAleatorio());
    }

    private static Vector<IPoderMutante> crearPoderAleatorio() {
		IPoderMutante poderesDisponibles[] = {new PoderDefensa(), new PoderAtaque(), new PoderRecarga(), new PoderVelocidad(), new PoderInvisibilidad()};

		Vector<IPoderMutante> poderesM = new Vector<>();
		int cantidadPoderes = (int)(Math.random()*poderesDisponibles.length);
		for (int i = 0; i < cantidadPoderes; i++) {
			poderesM.add(poderesDisponibles[ThreadLocalRandom.current().nextInt(Constantes.CANTIDAD_PODERES)]);
		}
		return poderesM;
    }
}
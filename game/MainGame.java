package game;

import mutantbattle.model.Cordenada;
import mutantbattle.model.Mutante;
import mutantbattle.model.PoderAtaque;
import mutantbattle.model.PoderDefensa;
import mutantbattle.util.Constantes;

/**
 * Prueba de la Game Layer por sí sola.
 */
public class MainGame {

    public static void main(String[] args) {
        // 1. Configuración (características del campo)
        ConfiguracionBattleField config = new ConfiguracionBattleField(Constantes.TAM_EQUIPO_MIN);
        System.out.println("Tamaño de equipo: " + config.getTamEquipo());
        System.out.println("Borde: " + config.getBorde().getX() + " x " + config.getBorde().getY());
        System.out.println("Radio: " + config.getRadio());

        // 2. BattleField con los dos equipos armados a mano
        BattleField battleField = new BattleField(config);
        for (int i = 0; i < config.getTamEquipo(); i++) {
            battleField.agregarMutanteA(new Mutante(Constantes.DEFENSA_MIN, Constantes.DANIO_MIN,
                    (float) Constantes.VELOCIDAD_MIN, new Cordenada(0, 0), new PoderAtaque()));
            battleField.agregarMutanteB(new Mutante(Constantes.DEFENSA_MAX, Constantes.DANIO_MIN,
                    (float) Constantes.VELOCIDAD_MIN, new Cordenada(0, 0), new PoderDefensa()));
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
}
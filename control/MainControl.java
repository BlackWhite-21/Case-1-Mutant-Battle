package control;

import game.BattleField;
import game.ConfiguracionBattleField;
import model.Mutante;
import util.Constantes;

/**
 * Prueba de la Control Layer por sí sola (batalla en consola, sin UI).
 */
public class MainControl {

    private static final int TAM_EQUIPO_PRUEBA = 5;
    private static final long INTERVALO_MARCADOR_MS = 2000;

    public static void main(String[] args) throws InterruptedException {
        // 1. Armar el campo y el controlador
        ConfiguracionBattleField config = new ConfiguracionBattleField(TAM_EQUIPO_PRUEBA);
        BattleField battleField = new BattleField(config);
        ControladorBattleField controlador = new ControladorBattleField(battleField);

        // 2. Crear los equipos y mostrar cómo empiezan
        controlador.crearEquipos(TAM_EQUIPO_PRUEBA);

        System.out.println("=== Equipos iniciales ===");
        imprimirEquipo(Constantes.NOMBRE_EQUIPO_A, battleField, true);
        imprimirEquipo(Constantes.NOMBRE_EQUIPO_B, battleField, false);

        // 3. Arrancar los hilos: un MutanteThread por mutante + el ciclo principal
        controlador.iniciarMovimiento();
        Thread hiloBatalla = new Thread(controlador);
        hiloBatalla.start();

        // 4. Mostrar el marcador cada cierto tiempo mientras dura la batalla
        System.out.println("\n=== Batalla en curso ===");
        while (hiloBatalla.isAlive()) {
            System.out.println("Vivos A: " + battleField.getVivosA() + " | Muertos A: " + battleField.getMuertosA()
                    + "   ||   Vivos B: " + battleField.getVivosB() + " | Muertos B: " + battleField.getMuertosB());
            hiloBatalla.join(INTERVALO_MARCADOR_MS);
        }

        // 5. Resultado final
        System.out.println("\n=== Resultado ===");
        imprimirEquipo(Constantes.NOMBRE_EQUIPO_A, battleField, true);
        imprimirEquipo(Constantes.NOMBRE_EQUIPO_B, battleField, false);
        System.out.println("Ganador: equipo " + battleField.getGanador());
    }

    private static void imprimirEquipo(String nombre, BattleField battleField, boolean esEquipoA) {
        System.out.println("Equipo " + nombre + ":");
        for (Mutante m : esEquipoA ? battleField.getMutantesA() : battleField.getMutantesB()) {
            System.out.println("  Mutante " + m.getId()
                    + " | energía " + m.getEnergia()
                    + " | ataque " + m.getAtaque()
                    + " | defensa " + m.getDefensa()
                    + " | poder " + m.getPoderMutante().getClass().getSimpleName()
                    + (m.estaVivo() ? "" : " (muerto)"));
        }
    }
}
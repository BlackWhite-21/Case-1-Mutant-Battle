package iu;

import java.awt.Point;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.SwingUtilities;

import game.BattleField;
import game.ConfigBattleField;
import iu.Controller.BattleController;
import iu.View.Interfase;
import model.IPoderMutante;
import model.Mutante;
import model.PoderRecarga;
import util.Constantes;

/**
 * Prueba de la UI Layer por sí sola: dibuja un campo estático, sin hilos ni combates.
 * ESC cierra la ventana.
 */
public class MainIU {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleController controller = new BattleController();
            Interfase interfase = new Interfase(controller);
            controller.setInterfase(interfase);
            interfase.mostrarPantallaCompleta();

            ConfigBattleField config = new ConfigBattleField(
                    Constantes.TAM_EQUIPO_PRUEBA, interfase.getAnchoCampo(), interfase.getAltoCampo());
            BattleField battleField = new BattleField(config);

            for (int i = 0; i < config.getTamEquipo(); i++) {
                battleField.agregarMutanteA(crearMutanteDePrueba(2 * i, config));
                battleField.agregarMutanteB(crearMutanteDePrueba(2 * i + 1, config));
            }
            // Uno con menos energía, para ver la barra, y uno muerto, para ver el marcador
            battleField.getMutantesA().get(0).addEnergia(-Constantes.ENERGIA_INICIAL / 2);
            battleField.getMutantesB().get(0).addEnergia(-Constantes.ENERGIA_INICIAL);

            controller.mostrar(battleField);
        });
    }

    private static Mutante crearMutanteDePrueba(int id, ConfigBattleField config) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Point borde = config.getBorde();
        Point posicion = new Point(random.nextInt(borde.x), random.nextInt(borde.y));

        Vector<IPoderMutante> poderes = new Vector<>();
        poderes.add(new PoderRecarga());

        return new Mutante(id, Constantes.DEFENSA_MIN, Constantes.DANIO_MIN,
                Constantes.VELOCIDAD_MIN, posicion, poderes);
    }
}
package mutantbattle;

import javax.swing.SwingUtilities;

import mutantbattle.ui.controller.BattleController;
import mutantbattle.ui.view.Interfase;

/**
 * Programa principal: corre el juego completo.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleController controller = new BattleController();
            Interfase interfase = new Interfase(controller);
            controller.setInterfase(interfase);

            interfase.setVisible(true);
            controller.iniciarBatalla(interfase.pedirTamEquipo());
        });
    }
}
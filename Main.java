import javax.swing.SwingUtilities;

import iu.Controller.BattleController;
import iu.View.Interfase;

/**
 * Programa principal: corre el juego completo.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleController controller = new BattleController();
            Interfase interfase = new Interfase(controller);
            controller.setInterfase(interfase);

            interfase.mostrarPantallaCompleta();
            controller.iniciarBatalla(interfase.pedirTamEquipo());
        });
    }
}
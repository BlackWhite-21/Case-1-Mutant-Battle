package iu.View;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import iu.Controller.BattleController;
import util.Constantes;

/**
 * Ventana principal en pantalla completa (View del MVC).
 * Solo muestra; no tiene lógica de juego.
 */
public class Interfase extends JFrame {

    private final BattleController controller;
    private final PanelBattleField panel;
    private final int anchoPantalla;
    private final int altoPantalla;

    public Interfase(BattleController controller) {
        this.controller = controller;
        this.panel = new PanelBattleField(controller);

        // Resolución de la pantalla
        Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
        this.anchoPantalla = (int) resolucion.getWidth();
        this.altoPantalla = (int) resolucion.getHeight();
        System.out.println("Resolución de la pantalla: " + anchoPantalla + " x " + altoPantalla);

        setTitle(Constantes.TITULO_VENTANA);
        setUndecorated(true);                      // sin barra de título ni bordes
        setSize(anchoPantalla, altoPantalla);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);

        // En pantalla completa no hay botón de cerrar: ESC cierra el juego
        getRootPane().registerKeyboardAction(e -> System.exit(0),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /** Muestra la ventana en pantalla completa. */
    public void mostrarPantallaCompleta() {
        GraphicsDevice pantalla = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (pantalla.isFullScreenSupported()) {
            pantalla.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        }
    }

    /** El campo de batalla mide lo mismo que la pantalla. */
    public int getAnchoCampo() {
        return anchoPantalla;
    }

    public int getAltoCampo() {
        return altoPantalla;
    }

    public int pedirTamEquipo() {
        while (true) {
            String entrada = JOptionPane.showInputDialog(this,
                    "Tamaño de los equipos (" + Constantes.TAM_EQUIPO_MIN
                    + " a " + Constantes.TAM_EQUIPO_MAX + "):");
            if (entrada == null) {
                System.exit(0);
            }
            try {
                int tam = Integer.parseInt(entrada.trim());
                if (tam >= Constantes.TAM_EQUIPO_MIN && tam <= Constantes.TAM_EQUIPO_MAX) {
                    return tam;
                }
            } catch (NumberFormatException e) {
                // no era un número: se vuelve a preguntar
            }
            JOptionPane.showMessageDialog(this, "Valor inválido, intentá de nuevo.");
        }
    }

    public void refrescar() {
        panel.repaint();
    }

    /** Anuncia al ganador y pregunta si se juega otra batalla. */
    public void mostrarGanador(String equipoGanador) {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¡Ganó el equipo " + equipoGanador + "!\n¿Jugar una nueva batalla?",
                Constantes.TITULO_VENTANA, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controller.nuevaBatalla();
        } else {
            System.exit(0);
        }
    }
}

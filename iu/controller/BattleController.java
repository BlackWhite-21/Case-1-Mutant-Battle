package iu.Controller;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import control.ControladorBattleField;
import game.BattleField;
import game.ConfigBattleField;
import iu.View.Interfase;
import util.Constantes;
import util.IObserver;
import util.Observable;

/**
 * Controller del MVC. Arranca la batalla, refresca la vista y anuncia al ganador.
 * Observa al BattleField para enterarse de cuándo termina.
 */
public class BattleController implements IObserver {

    private ControladorBattleField controlador;
    private BattleField battleField;
    private Interfase interfase;
    private Timer refresco;

    public void setInterfase(Interfase interfase) {
        this.interfase = interfase;
    }

    public void iniciarBatalla(int tamEquipo) {
        // El campo mide lo mismo que la pantalla
        ConfigBattleField config = new ConfigBattleField(
                tamEquipo, interfase.getAnchoCampo(), interfase.getAltoCampo());
        battleField = new BattleField(config);
        battleField.addObserver(this);

        controlador = new ControladorBattleField(battleField);
        controlador.crearEquipos(tamEquipo);
        controlador.iniciarMovimiento();
        new Thread(controlador).start();   // la batalla corre en su propio hilo

        // Tasa de refresco configurable: redibuja cada REFRESCO_UI_MS
        refresco = new Timer((int) Constantes.REFRESCO_UI_MS, e -> interfase.refrescar());
        refresco.start();
    }

    public void nuevaBatalla() {
        if (controlador != null) {
            controlador.detener();
        }
        if (refresco != null) {
            refresco.stop();
        }
        iniciarBatalla(interfase.pedirTamEquipo());
    }

    @Override
    public void update(Object source, Observable ob) {
        if (Constantes.EVENTO_FIN.equals(source)) {
            BattleField terminado = (BattleField) ob;
            // El aviso llega desde un hilo de la batalla: Swing se actualiza en su propio hilo
            SwingUtilities.invokeLater(() -> {
                refresco.stop();
                interfase.refrescar();
                interfase.mostrarGanador(terminado.getGanador());
            });
        }
    }

    /** Muestra un BattleField sin arrancar la batalla (para probar la UI sola). */
    public void mostrar(BattleField battleField) {
        this.battleField = battleField;
        interfase.refrescar();
    }

    public BattleField getBattleField() {
        return battleField;
    }
}
package mutantbattle.control;

import java.util.Vector;

import mutantbattle.game.BattleField;
import mutantbattle.model.Mutante;
import mutantbattle.observer.IObserver;
import mutantbattle.observer.Observable;

/**
 * Funcionalidad del BattleField. Implementa Runnable (ciclo principal)
 * e IObserver (recibe los movimientos de los mutantes).
 */
public class ControladorBattleField implements Runnable, IObserver {

    private final BattleField battleField;
    private final Vector<MutanteThread> hilosMutantes = new Vector<>();

    public ControladorBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public void crearEquipos(int tamEquipo) {
        // TODO: crear tamEquipo mutantes aleatorios por equipo,
        //       agregarlos al battleField y suscribirse a cada uno
    }

    public void iniciarMovimiento() {
        // TODO: crear un MutanteThread por mutante y llamar start()
    }

    public void verificarRadio(Mutante mutante) {
        // TODO: si un enemigo está dentro del radio, decidir atacar o defenderse
    }

    @Override
    public void run() {
        // TODO: mientras no haya ganador, mantener la batalla; al terminar, detener()
    }

    public void detener() {
        // TODO: detener todos los MutanteThread
    }

    @Override
    public void update(Object source, Observable ob) {
        // TODO: un mutante se movió → verificarRadio((Mutante) ob)
    }

    public BattleField getBattleField() {
        return battleField;
    }
}
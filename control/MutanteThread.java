package mutantbattle.control;

import mutantbattle.model.Mutante;

/**
 * Hilo propio de cada mutante: lo mueve mientras esté vivo.
 */
public class MutanteThread extends Thread {

    private final Mutante mutante;
    private volatile boolean activo = true;

    public MutanteThread(Mutante mutante) {
        this.mutante = mutante;
    }

    @Override
    public void run() {
        // TODO: mientras activo y mutante.estaVivo():
        //       calcular la nueva posición, mutante.moverse(...),
        //       Thread.sleep(Constantes.TICK_MOVIMIENTO_MS)
    }

    public void detener() {
        activo = false;
    }
}
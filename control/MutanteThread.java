package control;

import game.ConfigBattleField;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import model.IPoderMutante;
import model.Mutante;
import util.Constantes;

public class MutanteThread extends Thread {

    private final Mutante mutante;
    private final ConfigBattleField config;
    private volatile boolean activo = true;
    private double dirX;
    private double dirY;
    private long proximoPoder;   // cuándo puede usar el siguiente poder
    private long finPoder;       // cuándo se acaba el poder activo

    public MutanteThread(Mutante mutante, ConfigBattleField config) {
        this.mutante = mutante;
        this.config = config;
        this.proximoPoder = System.currentTimeMillis() + Constantes.COOLDOWN_PODER_MS;
        double angulo = ThreadLocalRandom.current().nextDouble(2 * Math.PI);
        this.dirX = Math.cos(angulo);
        this.dirY = Math.sin(angulo);
    }

    @Override
    public void run() {
        while (activo && mutante.estaVivo()) {
            mover();
            controlarPoderes();
            try {
                Thread.sleep(Constantes.TICK_MOVIMIENTO_MS);
            } catch (InterruptedException e) {
                break; // detener() interrumpe el sleep
            }
        }
    }

    /**
     * Ciclo de los poderes: esperar el cooldown -> activar uno -> dura DURACION -> se desactiva.
     */
    private void controlarPoderes() {
        long ahora = System.currentTimeMillis();

        if (mutante.getPoderActivo() != null) {
            // Hay un poder en curso: ¿ya se le acabó el tiempo?
            if (ahora >= finPoder) {
                mutante.terminarPoder();
                proximoPoder = ahora + Constantes.COOLDOWN_PODER_MS;
            }
        } else if (ahora >= proximoPoder) {
            // Terminó el cooldown: intenta usar un poder
            IPoderMutante poder = mutante.usarPoderMutante();
            if (poder != null) {
                finPoder = ahora + poder.getDuracionMs();
            }
            proximoPoder = ahora + Constantes.COOLDOWN_PODER_MS;
        }
    }

    private void mover() {
        Point actual = mutante.getPos();
        Point borde = config.getBorde();
        double paso = mutante.getVelocidad() * Constantes.PASO_MOVIMIENTO;

        double x = actual.getX() + dirX * paso;
        double y = actual.getY() + dirY * paso;

        if (x < 0 || x > borde.getX()) {
            dirX = -dirX;
            x = Math.max(0, Math.min(x, borde.getX()));
        }
        if (y < 0 || y > borde.getY()) {
            dirY = -dirY;
            y = Math.max(0, Math.min(y, borde.getY()));
        }
        mutante.mover(new Point((int) Math.round(x), (int) Math.round(y)));
    }

    public void detener() {
        activo = false;
    }
}
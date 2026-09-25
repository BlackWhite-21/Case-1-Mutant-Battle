package control;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;

import game.ConfiguracionBattleField;
import model.Mutante;
import util.Constantes;

public class MutanteThread extends Thread {

    private final Mutante mutante;
    private final ConfiguracionBattleField config;
	private final int cooldownTicks;
    private volatile boolean activo = true;
    private double dirX;
    private double dirY;

    public MutanteThread(Mutante mutante, ConfiguracionBattleField config) {
        this.mutante = mutante;
        this.config = config;
		this.cooldownTicks = 0;
        double angulo = ThreadLocalRandom.current().nextDouble(2 * Math.PI);
        this.dirX = Math.cos(angulo);
        this.dirY = Math.sin(angulo);
    }

    @Override
    public void run() {
        while (activo && mutante.estaVivo()) {
            mover();
            try {
                Thread.sleep(Constantes.TICK_MOVIMIENTO_MS);
            } catch (InterruptedException e) {
                break; // detener() interrumpe el sleep
            }
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
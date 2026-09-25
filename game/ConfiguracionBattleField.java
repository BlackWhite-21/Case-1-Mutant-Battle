package game;

import java.awt.Point;
import java.util.Random;

import util.Constantes;

/**
 * ConfiguracionBattleField solo guarda datos y los valida. No mueve mutantes ni decide combates.
 */
public class ConfiguracionBattleField {
	
    private final Point borde;
    private final int tamEquipo;
    private final double radio;

    public ConfiguracionBattleField() {
		Random random = new Random();
        this.tamEquipo = random.nextInt(Constantes.TAM_EQUIPO_MIN, Constantes.TAM_EQUIPO_MAX + 1);
        this.borde = new Point(Constantes.BORDE_X, Constantes.BORDE_Y);
        this.radio = Constantes.RADIO;
    }

    public Point getBorde() {
        return borde;
    }

    public int getTamEquipo() {
        return tamEquipo;
    }

    public double getRadio() {
        return radio;
    }
}
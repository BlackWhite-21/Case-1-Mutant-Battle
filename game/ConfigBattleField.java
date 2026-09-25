package game;

import java.awt.Point;
import java.util.Random;

import util.Constantes;

/**
 * ConfigBattleField solo guarda datos y los valida. No mueve mutantes ni decide combates.
 */
public class ConfigBattleField {
	
    private final Point borde;
    private final int tamEquipo;
    private final double radio;

    public ConfigBattleField() {
		Random random = new Random();
        this.tamEquipo = random.nextInt(Constantes.TAM_EQUIPO_MIN, Constantes.TAM_EQUIPO_MAX + 1);
        this.borde = new Point(Constantes.BORDE_X, Constantes.BORDE_Y);
        this.radio = Constantes.RADIO;
    }

     /** Para el juego con ventana: el usuario elige el tamaño y el campo mide lo que la pantalla. */
    public ConfigBattleField(int tamEquipo, int ancho, int alto) {
        if (tamEquipo < Constantes.TAM_EQUIPO_MIN || tamEquipo > Constantes.TAM_EQUIPO_MAX) {
            throw new IllegalArgumentException("El tamaño del equipo debe estar entre "
                    + Constantes.TAM_EQUIPO_MIN + " y " + Constantes.TAM_EQUIPO_MAX);
        }
        this.tamEquipo = tamEquipo;
        this.borde = new Point(ancho, alto);
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
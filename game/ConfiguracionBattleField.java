package game;

import mutantbattle.model.Cordenada;
import mutantbattle.util.Constantes;

/**
 * ConfiguracionBattleField solo guarda datos y los valida. No mueve mutantes ni decide combates.
 */
public class ConfiguracionBattleField {

    private final Cordenada borde;
    private final int tamEquipo;
    private final double radio;

    public ConfiguracionBattleField(int tamEquipo) {
        if (tamEquipo < Constantes.TAM_EQUIPO_MIN || tamEquipo > Constantes.TAM_EQUIPO_MAX) {
            throw new IllegalArgumentException(
                    "El tamaño del equipo debe estar entre " + Constantes.TAM_EQUIPO_MIN
                    + " y " + Constantes.TAM_EQUIPO_MAX + ", se recibió: " + tamEquipo);
        }
        this.tamEquipo = tamEquipo;
        this.borde = new Cordenada(Constantes.BORDE_X, Constantes.BORDE_Y);
        this.radio = Constantes.RADIO;
    }

    public Cordenada getBorde() {
        return borde;
    }

    public int getTamEquipo() {
        return tamEquipo;
    }

    public double getRadio() {
        return radio;
    }
}
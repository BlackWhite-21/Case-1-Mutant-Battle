package model;

import util.Constantes;

/**
 * Poder instantáneo: recupera energía de una vez, así que no tiene duración ni se desactiva.
 */
public class PoderRecarga implements IPoderMutante {

    public PoderRecarga() {
    }

    @Override
    public boolean ActivarPoder(Mutante mutante_n) {
        if (mutante_n.getEnergia() + Constantes.AUMENTO_RECARGA <= Constantes.ENERGIA_INICIAL) {
            mutante_n.addEnergia(Constantes.AUMENTO_RECARGA);
            return true;
        }
        return false;
    }

    @Override
    public long getDuracionMs() {
        return Constantes.DURACION_INSTANTANEA;
    }
}
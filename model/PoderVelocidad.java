package model;

import util.Constantes;

public class PoderVelocidad implements IPoderMutante {

    public PoderVelocidad() {
    }

    @Override
    public boolean ActivarPoder(Mutante mutante_n) {
        if (mutante_n.getVelocidad() == Constantes.VELOCIDAD_NORMAL) {
            mutante_n.setVelocidad(Constantes.VELOCIDAD_AUMENTADA);
            return true;
        }
        return false;
    }

    @Override
    public void DesactivarPoder(Mutante mutante_n) {
        mutante_n.setVelocidad(Constantes.VELOCIDAD_NORMAL);
    }
}

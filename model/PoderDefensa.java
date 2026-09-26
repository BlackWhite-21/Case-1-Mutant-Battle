package model;

import util.Constantes;

public class PoderDefensa implements IPoderMutante {

    public PoderDefensa() {
    }

    @Override
    public boolean ActivarPoder(Mutante mutante_n) {
        if (mutante_n.getDefensa() + Constantes.AUMENTO_DEFENSA <= Constantes.DEFENSA_MAX) {
            mutante_n.addDefensa(Constantes.AUMENTO_DEFENSA);
            return true;
        }
        return false;
    }

    @Override
    public void DesactivarPoder(Mutante mutante_n) {
        mutante_n.addDefensa(-Constantes.AUMENTO_DEFENSA);
    }
}
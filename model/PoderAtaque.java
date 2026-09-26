package model;

import util.Constantes;

public class PoderAtaque implements IPoderMutante {

    public PoderAtaque() {
    }

    @Override
    public boolean ActivarPoder(Mutante mutante_n) {
        if (mutante_n.getAtaque() + Constantes.AUMENTO_ATAQUE <= Constantes.DANIO_MAX) {
            mutante_n.addAtaque(Constantes.AUMENTO_ATAQUE);
            return true;
        }
        return false;
    }

    @Override
    public void DesactivarPoder(Mutante mutante_n) {
        mutante_n.addAtaque(-Constantes.AUMENTO_ATAQUE);
    }
}
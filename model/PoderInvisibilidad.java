package model;

public class PoderInvisibilidad implements IPoderMutante {

    public PoderInvisibilidad() {
    }

    @Override
    public boolean ActivarPoder(Mutante mutante_n) {
        if (mutante_n.getVisibilidad()) {
            mutante_n.changeVisibilidad();   // se vuelve invisible
            return true;
        }
        return false;
    }

    @Override
    public void DesactivarPoder(Mutante mutante_n) {
        if (!mutante_n.getVisibilidad()) {
            mutante_n.changeVisibilidad();   // vuelve a ser visible
        }
    }
}
package model;

public class PoderInvisibilidad implements IPoderMutante{
    public PoderInvisibilidad(){

    }

    @Override 
    public void ActivarPoder(Mutante mutante_n){
        mutante_n.changeVisibilidad();
    }
}
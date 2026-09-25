package model;

public class PoderInvisibilidad implements IPoderMutante{
    public PoderInvisibilidad(){

    }

    @Override 
    public void DispararPoder(Mutante mutante_n){
        mutante_n.changeVisibilidad();
    }
}
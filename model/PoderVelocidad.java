package model;
public class PoderVelocidad implements IPoderMutante{
    public PoderVelocidad(){

    }

    @Override 
    public void ActivarPoder(Mutante mutante_n){
        mutante_n.setVelocidad(1.5);
    }
}
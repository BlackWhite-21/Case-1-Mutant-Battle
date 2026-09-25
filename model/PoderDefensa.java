package model;

public class PoderDefensa implements IPoderMutante{
    public PoderDefensa(){

    }

    @Override 
    public void ActivarPoder(Mutante mutante_n){
        mutante_n.addDefensa(0);
    }
}

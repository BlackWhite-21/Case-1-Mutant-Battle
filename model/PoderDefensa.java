package model;

public class PoderDefensa implements IPoderMutante{
    public PoderDefensa(){

    }

    @Override 
    public void DispararPoder(Mutante mutante_n){
        mutante_n.addDefensa(0);
    }
}

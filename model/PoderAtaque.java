package model;
public class PoderAtaque implements IPoderMutante{
    public PoderAtaque(){

    }

    @Override 
    public void DispararPoder(Mutante mutante_n){
        mutante_n.addAtaque(1);
    }
}
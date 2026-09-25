package model;
public class PoderRecarga implements IPoderMutante{
    public PoderRecarga(){

    }

    @Override 
    public void ActivarPoder(Mutante mutante_n){
		mutante_n.addEnergia(0);
    }
}
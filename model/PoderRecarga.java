package model;
public class PoderRecarga implements IPoderMutante{
    public PoderRecarga(){

    }

    @Override 
    public boolean ActivarPoder(Mutante mutante_n){
		if (mutante_n.getEnergia() + 15 <= 100) {
			mutante_n.addDefensa(15);
			return true;
		}
		return false;
    }
}
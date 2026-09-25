package model;
public class PoderVelocidad implements IPoderMutante{
    public PoderVelocidad(){

    }

    @Override 
    public boolean ActivarPoder(Mutante mutante_n){
		if (mutante_n.getVelocidad() == 1) {
			mutante_n.setVelocidad(1.5);
			return true;
		}
		return false;
       
    }
}
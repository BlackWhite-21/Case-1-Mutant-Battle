package model;

public class PoderInvisibilidad implements IPoderMutante{
    public PoderInvisibilidad(){

    }

    @Override 
    public boolean ActivarPoder(Mutante mutante_n){
		if (mutante_n.getVisibilidad()) {
			mutante_n.changeVisibilidad();
			return true;
		}
		return false;
	}

}
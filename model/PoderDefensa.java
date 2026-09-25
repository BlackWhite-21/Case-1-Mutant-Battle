package model;

public class PoderDefensa implements IPoderMutante{
    public PoderDefensa(){

    }

    @Override 
    public boolean ActivarPoder(Mutante mutante_n){
		if (mutante_n.getDefensa() + 1 <= 3) {
			mutante_n.addDefensa(1);
			return true;
		}
		return false;
    }
}

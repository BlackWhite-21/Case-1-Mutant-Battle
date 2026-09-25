package model;
public class PoderAtaque implements IPoderMutante{
    public PoderAtaque(){

    }

    @Override 
    public boolean ActivarPoder(Mutante mutante_n){
		if (mutante_n.getAtaque() + 1 <= 3) {
			mutante_n.addAtaque(1);
			return true;
		}
		return false;
    }
}
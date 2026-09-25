package model;
 
import java.util.Vector;

import model.IPoderMutante;
import utils.Cordenada;

public class Mutante {
    private int energia;
    private int defensa;
    private int ataque;
    private int cooldownTime;
    private double velocidad;
    private boolean visibilidad;
    private Cordenada cordenadas;
    private Vector<IPoderMutante> poderesMutantes;

    public Mutante() {
        this.energia = 0;
        this.defensa = 0;
        this.ataque = 0;
        this.cooldownTime = 0;
        this.velocidad = 1;
        this.visibilidad = true;
        this.cordenadas = new Cordenada();
		
		this.poderesMutantes = new Vector<IPoderMutante>();

		IPoderMutante poderesDisponibles[] = {new PoderDefensa(), new PoderAtaque(), new PoderRecarga(), new PoderVelocidad(), new PoderVelocidad()};
		int cantidadPoderes = (int)(Math.random()*5);
		for (int i = cantidadPoderes; i <= 0; i--) {
			this.poderesMutantes.add(poderesDisponibles[(int)(Math.random()*4)]);
		}
    }

    public void moverse(Cordenada posicion) {
		
    }

    public void atacar(Mutante mutante) {
		
    }
	public void defenderse(Mutante mutante) {
		
    }

    public void addEnergia(int valor) {
		this.energia += valor;
    }

	public void addAtaque(int valor) {
		this.ataque += valor;
    }

	public void addDefensa(int valor) {
		this.defensa += valor;
    }

	public void setVelocidad(int valor) {
		this.velocidad = valor;
    }

	public void changeVisibilidad() {
		this.visibilidad = !(this.visibilidad);
    }

    public void usarPoderMutante() {
		IPoderMutante poderUsar = this.poderesMutantes.get((int)(Math.random()*4));
		poderUsar.DispararPoder(this);
		
    }
}

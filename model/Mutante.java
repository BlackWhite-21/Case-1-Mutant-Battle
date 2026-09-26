package model;
 
import java.awt.Point;
import java.util.Vector;
import util.Constantes;
import util.Observable;


public class Mutante extends Observable{
	private int id;
    private int energia;
    private int defensa;
    private int ataque;
    private double velocidad;
    private boolean visibilidad;
    private Point Coordenada;
    private Vector<IPoderMutante> poderesMutantes;
    private volatile IPoderMutante poderActivo;   // poder con efecto en curso (null si no hay)
	
    public Mutante(int pId, int pDefensa, int pAtaque, double pVelocidad, Point pCoordenada, Vector<IPoderMutante> pPoderesAleatorios) {
		this.id = pId;
        this.energia = 100;
        this.defensa = pDefensa;
        this.ataque = pAtaque;
        this.velocidad = 1;
        this.visibilidad = true;
        this.Coordenada = pCoordenada;
		
		this.poderesMutantes = pPoderesAleatorios;
    }

    public void mover(Point nPosition) {
        this.Coordenada.setLocation(nPosition);
		notifyObservers(Constantes.EVENTO_MOVIMIENTO);
    }

	public Point getPos(){
		return this.Coordenada;
	}

    public void addEnergia(int valor) {
        boolean estabaVivo = estaVivo();   
        if ((valor < 0) || (valor > 0 && this.energia + valor <= 100)) {
            this.energia += valor;
        }
        if (estabaVivo && !estaVivo()) {
            notifyObservers(Constantes.EVENTO_MUERTE); // avisa al BattleField
        }
    }

	public int getEnergia(){
		return this.energia;
	}

	public void addAtaque(int valor) {
		if ((valor < 0 && this.ataque + valor > 0) || (valor > 0 && this.ataque + valor <= Constantes.DANIO_MAX)) {
			this.ataque += valor;
		} 
    }
	public int getAtaque(){
		return this.ataque;
	}

	public void addDefensa(int valor) {
		if ((valor < 0 && this.defensa + valor > 0) || (valor > 0 && this.defensa + valor <= Constantes.DEFENSA_MAX)) {
			this.defensa += valor;
		}
    }
	public int getDefensa(){
		return this.defensa;
	}

	public void setVelocidad(double valor) {
		this.velocidad = valor;
    }
	public double getVelocidad(){
		return this.velocidad;
	}

	public void changeVisibilidad() {
		this.visibilidad = !(this.visibilidad);
    }

	public boolean getVisibilidad(){
		return this.visibilidad;
	}

    /**
     * Activa uno de sus poderes al azar
     * Si el poder tiene duración, queda como poder activo hasta que se llame terminarPoder().
     * Devuelve el poder activado, o null si no se pudo activar.
     */
    public synchronized IPoderMutante usarPoderMutante() {
		if (poderActivo != null || poderesMutantes.isEmpty()) {
			return null;   // ya tiene un poder en curso
		}
		IPoderMutante poderUsar = this.poderesMutantes.get((int)(Math.random()*(this.poderesMutantes.size())));
		if (!poderUsar.ActivarPoder(this)) {
			return null;
		}
		if (poderUsar.getDuracionMs() > 0) {
			this.poderActivo = poderUsar;
		}
		return poderUsar;
    }

    /** Se acabó el tiempo del poder: se quita su efecto. */
    public synchronized void terminarPoder() {
		if (poderActivo != null) {
			poderActivo.DesactivarPoder(this);
			poderActivo = null;
		}
    }

	public IPoderMutante getPoderActivo() {
		return this.poderActivo;
	}

	public boolean estaVivo(){
		return this.energia > 0;
	}
	
	public Vector<IPoderMutante> getPoderesMutantes(){
		return this.poderesMutantes;
	}

	public int getId(){
		return this.id;
	}

}

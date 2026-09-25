package model;


import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Point;

import util.Constantes;

import game.BattleField;
import game.ConfiguracionBattleField;

public class MainModel {
	public static void main(String[] args) {

		Random random = new Random();
		ConfiguracionBattleField configuracionBattleField = new ConfiguracionBattleField();
		BattleField battleField = new BattleField(configuracionBattleField);

		// Pruebas 
		
		Point borde = battleField.getConfig().getBorde();
	
		int defensa = random.nextInt(Constantes.DEFENSA_MIN, Constantes.DEFENSA_MAX + 1);
		int ataque = random.nextInt(Constantes.DANIO_MIN, Constantes.DANIO_INICIAL_MAX + 1);
		float velocidad = (float) random.nextDouble(Constantes.VELOCIDAD_MIN, Constantes.VELOCIDAD_MAX);
		Point posicion = new Point(random.nextInt((int)(borde.getX())), random.nextInt((int)(borde.getY())));
	
		Mutante mutantePrueba = new Mutante(1, defensa, ataque, velocidad, posicion, crearPoderAleatorio());
	
		imprimirMutante(mutantePrueba);

		mutantePrueba.mover(new Point(5,5));
		mutantePrueba.usarPoderMutante();

	}
	private static Vector<IPoderMutante> crearPoderAleatorio() {
		IPoderMutante poderesDisponibles[] = {new PoderDefensa(), new PoderAtaque(), new PoderRecarga(), new PoderVelocidad(), new PoderVelocidad()};

		Vector<IPoderMutante> poderesM = new Vector<>();
		int cantidadPoderes = (int)(Math.random()*5);
		for (int i = 0; i < cantidadPoderes; i++){
			poderesM.add(poderesDisponibles[ThreadLocalRandom.current().nextInt(Constantes.CANTIDAD_PODERES)]);
		};
		return poderesM;
	};

	private static void imprimirMutante(Mutante m) {
		String poderes = "";

		for (IPoderMutante poder : m.getPoderesMutantes()) {
			poderes += poder.getClass().getSimpleName() + ", ";
		}

		// Eliminar la última coma y espacio
		if (!poderes.isEmpty()) {
			poderes = poderes.substring(0, poderes.length() - 2);
		} else {
			poderes = "Ninguno";
		}

		System.out.println("  Mutante " + m.getId()
				+ " | energía " + m.getEnergia()
				+ " | ataque " + m.getAtaque()
				+ " | defensa " + m.getDefensa()
				+ " | poderes " + poderes
				+ (m.estaVivo() ? "" : " (muerto)"));
	}
	
}

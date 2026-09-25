package model;


import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Point;

import util.Constantes;

import game.BattleField;
import game.ConfiguracionBattleField;

public abstract class MainModel {
	private Vector<IPoderMutante> crearPoderAleatorio() {
		IPoderMutante poderesDisponibles[] = {new PoderDefensa(), new PoderAtaque(), new PoderRecarga(), new PoderVelocidad(), new PoderVelocidad()};

		Vector<IPoderMutante> poderesM = new Vector<>();
		int cantidadPoderes = (int)(Math.random()*5);
		for (int i = cantidadPoderes; i <= 0; i--) {
			poderesM.add(poderesDisponibles[ThreadLocalRandom.current().nextInt(Constantes.CANTIDAD_PODERES)]);
		};
		return poderesM;
    };

	Random random = new Random();
	ConfiguracionBattleField configuracionBattleField = new ConfiguracionBattleField();
	BattleField battleField = new BattleField(configuracionBattleField);
	// Pruebas 
	{
		Point borde = battleField.getConfig().getBorde();
	
		int defensa = random.nextInt(Constantes.DEFENSA_MIN, Constantes.DEFENSA_MAX + 1);
		int ataque = random.nextInt(Constantes.DANIO_MIN, Constantes.DANIO_INICIAL_MAX + 1);
		float velocidad = (float) random.nextDouble(Constantes.VELOCIDAD_MIN, Constantes.VELOCIDAD_MAX);
		Point posicion = new Point(random.nextInt((int)(borde.getX())), random.nextInt((int)(borde.getY())));
	
		Mutante mutantePrueba = new Mutante(1, defensa, ataque, velocidad, posicion, crearPoderAleatorio());
	
		mutantePrueba.mover(new Point(5,5));
	}
	
}

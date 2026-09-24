package mutantbattle.observer;

import java.util.Vector;

/**
 * Objeto observado: guarda una lista de observadores y les avisa cuando cambia.
 */
public abstract class Observable {

    private final Vector<IObserver> observers = new Vector<>();

    public void addObserver(IObserver ob) {
        // TODO: agregar el observador a la lista (evitar duplicados)
    }

    public void removeObserver(IObserver ob) {
        // TODO: quitar el observador de la lista
    }

    public void notifyObservers(Object source) {
        // TODO: recorrer la lista y llamar ob.update(source, this) en cada uno
    }
}
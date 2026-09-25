package utils;

import java.util.Vector;

/**
 * Objeto observado: guarda una lista de observadores y les avisa cuando cambia.
 */
public abstract class Observable {

    private final Vector<IObserver> observers = new Vector<>();

    public void addObserver(IObserver ob) {
        if (ob != null && !observers.contains(ob)) {
            observers.add(ob);
        }
    }

    public void removeObserver(IObserver ob) {
        observers.remove(ob);
    }

    public void notifyObservers(Object source) {
        // Se recorre una copia para que, si alguien se agrega o se quita
        // mientras se notifica (desde otro hilo), no se rompa el recorrido.
        Vector<IObserver> copia = new Vector<>(observers);
        for (IObserver ob : copia) {
            ob.update(source, this);
        }
    }
}
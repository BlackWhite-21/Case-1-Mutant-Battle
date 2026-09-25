package utils;

/**
 * Contrato que debe cumplir todo objeto que quiera ser notificado
 * cuando un Observable cambia.
 */
public interface IObserver {

    void update(Object source, Observable ob);
}

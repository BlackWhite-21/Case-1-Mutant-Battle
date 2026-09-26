package model;

import util.Constantes;

/**
 * Poder de un mutante. Se activa, dura un tiempo y luego se desactiva.
 */
public interface IPoderMutante {

    /** Aplica el efecto. Devuelve true si se pudo activar. */
    public boolean ActivarPoder(Mutante a);

    /** Quita el efecto cuando se acaba el tiempo. Por defecto no hace nada (poderes instantáneos). */
    public default void DesactivarPoder(Mutante a) {
    }

    /** Cuánto dura el efecto, en milisegundos. */
    public default long getDuracionMs() {
        return Constantes.DURACION_PODER_MS;
    }
}

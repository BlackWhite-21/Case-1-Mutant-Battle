package util;
import java.awt.Color;

/**
 * Librería de constantes: ningún valor debe quedar quemado en el código.
 */
public final class Constantes {

    // ---- Mutante ----
    public static final int ENERGIA_INICIAL = 100;
    public static final int DEFENSA_MIN = 1;
    public static final int DEFENSA_MAX = 3;
    public static final int DANIO_MIN = 1;
    public static final int DANIO_INICIAL_MAX = 3;
    public static final int DANIO_MAX = 7;
    public static final int INCREMENTO_DANIO = 1;

    // ---- Poderes ----
    public static final int CANTIDAD_PODERES = 5;
    public static final int AUMENTO_DEFENSA = 1;
    public static final int AUMENTO_ATAQUE = 1;
    public static final float AUMENTO_VELOCIDAD = 1.5f;
    public static final int AUMENTO_CURA = 10;
    public static final float DURACION_PODER = 3.0f;

    // ---- BattleField ----
    public static final int TAM_EQUIPO_MIN = 3;
    public static final int TAM_EQUIPO_MAX = 11;
    public static final int BORDE_X = 800;
    public static final int BORDE_Y = 600;
    public static final double RADIO = 60.0;

    // ---- Equipos ----
    public static final String NOMBRE_EQUIPO_A = "A";
    public static final String NOMBRE_EQUIPO_B = "B";
    public static final Color COLOR_EQUIPO_A = new Color(30, 136, 229);  // azul
    public static final Color COLOR_EQUIPO_B = new Color(229, 57, 53);   // rojo
    public static final String SIMBOLO_EQUIPO_A = "X";
    public static final String SIMBOLO_EQUIPO_B = "M";

    // ---- Movimiento y combate ----
    public static final double VELOCIDAD_MIN = 1.0;
    public static final double VELOCIDAD_MAX = 3.0;
    public static final double PASO_MOVIMIENTO = 5.0;
    public static final double PROBABILIDAD_ATAQUE = 0.5;

    // ---- Eventos del Observer ----
    public static final String EVENTO_MOVIMIENTO = "MOVIMIENTO";
    public static final String EVENTO_MUERTE = "MUERTE";
    public static final String EVENTO_FIN = "FIN";

    // ---- Hilos y UI ----
    public static final long TICK_MOVIMIENTO_MS = 1;
	public static final int COOLDOWN_TICKS = 165;
    public static final long REFRESCO_UI_MS = 33;   // ~30 cuadros por segundo
    public static final String TITULO_VENTANA = "Mutant Battle";
    public static final String TEXTO_BOTON_NUEVA_BATALLA = "Nueva batalla";

    // ---- UI: dibujo ----
    public static final Color COLOR_FONDO = new Color(245, 245, 245);
    public static final Color COLOR_TEXTO = Color.WHITE;
    public static final Color COLOR_ENERGIA = new Color(67, 160, 71);
    public static final Color COLOR_ENERGIA_FONDO = Color.DARK_GRAY;
    public static final double PROPORCION_TAM_MUTANTE = 0.03;  // respecto al lado menor de la pantalla
    public static final int TAM_MUTANTE_MIN = 10;
    public static final double PROPORCION_FUENTE = 0.6;
    public static final int ALTO_BARRA_ENERGIA = 4;
    public static final int SEPARACION_BARRA = 3;
    public static final int MARGEN_MARCADOR = 20;
    public static final int TAM_FUENTE_MARCADOR = 18;
    public static final int TAM_EQUIPO_PRUEBA = 5;

    private Constantes() {
        // No se instancia
    }
}
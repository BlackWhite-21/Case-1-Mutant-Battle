package iu.View;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JPanel;

import game.BattleField;
import iu.Controller.BattleController;
import model.IPoderMutante;
import model.Mutante;
import util.Constantes;

/**
 * Dibuja el campo de batalla consultando al controller.
 * Todos los mutantes son círculos del mismo tamaño, calculado según la pantalla.
 */
public class PanelBattleField extends JPanel {

    private final BattleController controller;

    public PanelBattleField(BattleController controller) {
        this.controller = controller;
        setBackground(Constantes.COLOR_FONDO);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BattleField battleField = controller.getBattleField();
        if (battleField == null) {
            return; // todavía no empieza la batalla
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tam = calcularTamMutante();
        dibujarEquipo(g2, battleField.getMutantesA(), Constantes.COLOR_EQUIPO_A, Constantes.SIMBOLO_EQUIPO_A, tam);
        dibujarEquipo(g2, battleField.getMutantesB(), Constantes.COLOR_EQUIPO_B, Constantes.SIMBOLO_EQUIPO_B, tam);
        dibujarMarcador(g2, battleField);
    }

    /** Todos los mutantes miden lo mismo, en proporción a la pantalla. */
    private int calcularTamMutante() {
        int ladoMenor = Math.min(getWidth(), getHeight());
        return Math.max(Constantes.TAM_MUTANTE_MIN,
                (int) Math.round(ladoMenor * Constantes.PROPORCION_TAM_MUTANTE));
    }

    private void dibujarEquipo(Graphics2D g2, Vector<Mutante> equipo, Color color, String simbolo, int tam) {
        for (Mutante mutante : new Vector<>(equipo)) {
            if (!mutante.estaVivo()) {
                continue;
            }
            Point pos = mutante.getPos();
            int x = pos.x - tam / 2;   // la posición es el centro del mutante
            int y = pos.y - tam / 2;

            // Invisible: se dibuja semitransparente (sigue en pantalla, como pide el enunciado)
            Composite original = g2.getComposite();
            if (!mutante.getVisibilidad()) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Constantes.OPACIDAD_INVISIBLE));
            }

            // Cuerpo: círculo del color del equipo
            g2.setColor(color);
            g2.fillOval(x, y, tam, tam);

            // Poder activo: anillo dorado alrededor
            if (mutante.getPoderActivo() != null) {
                int grosor = (int) Constantes.GROSOR_ANILLO;
                g2.setColor(Constantes.COLOR_PODER_ACTIVO);
                g2.setStroke(new BasicStroke(Constantes.GROSOR_ANILLO));
                g2.drawOval(x - grosor, y - grosor, tam + 2 * grosor, tam + 2 * grosor);
            }

            // Símbolo del equipo centrado
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int) (tam * Constantes.PROPORCION_FUENTE)));
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(Constantes.COLOR_TEXTO);
            g2.drawString(simbolo,
                    x + (tam - fm.stringWidth(simbolo)) / 2,
                    y + (tam - fm.getHeight()) / 2 + fm.getAscent());

            // Barra de energía encima
            int energia = Math.max(0, mutante.getEnergia());
            int yBarra = y - Constantes.SEPARACION_BARRA - Constantes.ALTO_BARRA_ENERGIA;
            int anchoEnergia = tam * energia / Constantes.ENERGIA_INICIAL;
            g2.setColor(Constantes.COLOR_ENERGIA_FONDO);
            g2.fillRect(x, yBarra, tam, Constantes.ALTO_BARRA_ENERGIA);
            g2.setColor(Constantes.COLOR_ENERGIA);
            g2.fillRect(x, yBarra, anchoEnergia, Constantes.ALTO_BARRA_ENERGIA);

            // Poderes debajo: la inicial de cada uno
            dibujarPoderes(g2, mutante, x, y + tam, tam);

            g2.setComposite(original);
        }
    }

    /** Escribe debajo del mutante las iniciales de sus poderes, por ejemplo "A D R". */
    private void dibujarPoderes(Graphics2D g2, Mutante mutante, int x, int yAbajo, int tam) {
        String texto = inicialesPoderes(mutante);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int) (tam * Constantes.PROPORCION_FUENTE_PODERES)));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Constantes.COLOR_PODERES);
        g2.drawString(texto, x + (tam - fm.stringWidth(texto)) / 2, yAbajo + fm.getAscent());
    }

    private String inicialesPoderes(Mutante mutante) {
        Set<Character> iniciales = new LinkedHashSet<>();   // sin repetidos
        for (IPoderMutante poder : new Vector<>(mutante.getPoderesMutantes())) {
            String nombre = poder.getClass().getSimpleName().replace(Constantes.PREFIJO_PODER, "");
            iniciales.add(nombre.charAt(0));
        }
        StringBuilder texto = new StringBuilder();
        for (char inicial : iniciales) {
            texto.append(inicial).append(' ');
        }
        return texto.toString().trim();
    }

    private void dibujarMarcador(Graphics2D g2, BattleField bf) {
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Constantes.TAM_FUENTE_MARCADOR));
        int margen = Constantes.MARGEN_MARCADOR;
        int linea = g2.getFontMetrics().getHeight();

        g2.setColor(Constantes.COLOR_EQUIPO_A);
        g2.drawString("Equipo " + Constantes.NOMBRE_EQUIPO_A + " (" + Constantes.SIMBOLO_EQUIPO_A + ")  vivos: "
                + bf.getVivosA() + "  muertos: " + bf.getMuertosA(), margen, margen + linea);

        g2.setColor(Constantes.COLOR_EQUIPO_B);
        g2.drawString("Equipo " + Constantes.NOMBRE_EQUIPO_B + " (" + Constantes.SIMBOLO_EQUIPO_B + ")  vivos: "
                + bf.getVivosB() + "  muertos: " + bf.getMuertosB(), margen, margen + 2 * linea);

        g2.setColor(Constantes.COLOR_PODERES);
        g2.drawString(Constantes.LEYENDA_PODERES, margen, margen + 3 * linea);
        g2.drawString(Constantes.LEYENDA_EFECTOS, margen, margen + 4 * linea);
    }
}
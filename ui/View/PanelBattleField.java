package iu.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JPanel;

import game.BattleField;
import iu.Controller.BattleController;
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
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int) (tam * Constantes.PROPORCION_FUENTE)));
        FontMetrics fm = g2.getFontMetrics();

        for (Mutante mutante : new Vector<>(equipo)) {
            if (!mutante.estaVivo()) {
                continue;
            }
            Point pos = mutante.getPos();
            int x = pos.x - tam / 2;   // la posición es el centro del mutante
            int y = pos.y - tam / 2;

            // Cuerpo: círculo del color del equipo
            g2.setColor(color);
            g2.fillOval(x, y, tam, tam);

            // Símbolo del equipo centrado
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
        }
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
    }
}
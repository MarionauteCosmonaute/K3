package View;

import Model.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PDJPyramideCentrale extends JComponent implements Observateur {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;

    JPanel parent;

    PDJPyramideCentrale(Jeu jeu, JPanel parent) {
        this.jeu = jeu;
        this.parent = parent;
        setOpaque(false);
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        System.out.println("PaintComponent de PDJPyramideCentrale");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        System.out.println("width_fenetre " + width_fenetre);
        System.out.println("height_fenetre " + height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPrincipale());
    }
}

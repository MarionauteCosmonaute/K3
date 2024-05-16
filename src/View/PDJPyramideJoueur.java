package View;

import Model.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PDJPyramideJoueur extends JComponent implements Observateur {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    JFrame frame;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;

    JPanel parent;

    PDJPyramideJoueur(Jeu jeu, JPanel parent,int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur=nj;
        setOpaque(false);
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {

        while (jeu.draw()) {
        }
        Jeu.build(jeu.getPlayer(joueur));
        System.out.println("PaintComponent de PDJPyramideJoueur");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        System.out.println("width_fenetre " + width_fenetre);
        System.out.println("height_fenetre " + height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid());
    }
}

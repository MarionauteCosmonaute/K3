package View;

import Model.Jeu;
import Patterns.Observateur;

import javax.swing.*;

import Controller.ControleurMediateur;

import java.awt.*;
import java.util.ArrayList;

public class PDJPyramideCentrale extends JComponent implements Observateur {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne;
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

    public void DessineAccessible(int x, int y)
    {
        int x_haut, y_haut;
        int taille_pyramide = jeu.getPrincipale().getSize();
        int taille_cube = Math.min(80 * height_fenetre / (100 * taille_pyramide), 80 * width_fenetre / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        ArrayList<Point> Listeaccessible = jeu.CubeAccessibleDestinations(x,y);
         for(Point p : Listeaccessible){
            x_haut = height_fenetre / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (5-p.x)
                    - (espace * taille_pyramide) / 2;
            y_haut = width_fenetre / 2 - (taille_cube / 2) * ((5-p.x) + 1) + taille_cube * p.y - (espace * (5-p.x)) / 2;

            // drawable.setColor(Color.BLACK);

            drawable.drawRect(y_haut, x_haut, taille_cube, taille_cube);
            drawable.drawRect(y_haut + 1, x_haut + 1, taille_cube - 2, taille_cube - 2);
            drawable.drawRect(y_haut + 2, x_haut + 2, taille_cube - 4, taille_cube - 4);
            drawable.drawRect(y_haut + 3, x_haut + 3, taille_cube - 6, taille_cube - 6);
        //     // drawable.setColor(Color.YELLOW);
        //     drawable.drawRect(y_haut + 4, x_haut + 4, taille_cube - 8, taille_cube - 8);
        //     drawable.drawRect(y_haut + 5, x_haut + 5, taille_cube - 10, taille_cube - 10);
        }
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
        if (ControleurMediateur.GetClic())
        {
            DessineAccessible(ControleurMediateur.GetAbscisse(),ControleurMediateur.GetOrdonnee());
            ControleurMediateur.SetClic(false);
        }
    }
}

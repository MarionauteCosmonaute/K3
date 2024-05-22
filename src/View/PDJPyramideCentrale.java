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

    public PDJPyramideCentrale(Jeu jeu, JPanel parent) {
        this.jeu = jeu;
        this.parent = parent;
        setOpaque(false);
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public int GetJoueurCourant()
    {
        return jeu.get_player();
    }

    public int GetTaillePyramide(){
        return jeu.getPricipale().getSize();
    }

    public int GetTailleCubePyramideCentrale()
    {
        return Math.min(80 * height_fenetre / (100 * GetTaillePyramide()), 80 * width_fenetre / (100 * GetTaillePyramide()));
    }

    public Point[][] GetPointPyramideCentrale()
    {
        return StructurePainter.PointPyramideCentrale();
    }

    public void DessineAccessible(int x, int y)
    {
        int x_haut, y_haut;
        int taille_pyramide = jeu.getPrincipale().getSize();
        int taille_cube = Math.min(80 * height_fenetre / (100 * taille_pyramide), 80 * width_fenetre / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        ArrayList<Point> Listeaccessible = jeu.CubeAccessibleDestinations(x,y);
         for(Point p : Listeaccessible){
            x_haut = height_fenetre / 2 - (taille_cube / 2) * (taille_pyramide - 1) + taille_cube * (taille_pyramide - 1-p.x) - (espace * (taille_pyramide)) / 2;
            y_haut = width_fenetre / 2 - (taille_cube / 2) * ((taille_pyramide - 1 -p.x) + 1) + taille_cube * p.y - (espace * (taille_pyramide-p.x)) / 2;

            drawable.drawRect(y_haut + espace * p.y, x_haut + espace * p.x, taille_cube, taille_cube);
            drawable.drawRect(y_haut + espace * p.y + 1, x_haut + espace * p.x + 1, taille_cube - 2, taille_cube - 2);
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
        if (ControleurMediateur.GetClic() && jeu.accessible(ControleurMediateur.GetLigne(), ControleurMediateur.GetColonne()))
        {
            DessineAccessible(ControleurMediateur.GetLigne(),ControleurMediateur.GetColonne());
            // Mettre le booléen à false quand on clique sur la pyramide centrale et quand on clique sur un cube non accessible
        }
        else
        {
            ControleurMediateur.SetClic(false);
        }
    }
}

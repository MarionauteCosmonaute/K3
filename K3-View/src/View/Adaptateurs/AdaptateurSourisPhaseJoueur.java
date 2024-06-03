package View.Adaptateurs;

import View.CollecteurEvenements;
import View.PDJPyramideCentrale;
import View.PDJPyramideJoueur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisPhaseJoueur extends MouseAdapter {
    CollecteurEvenements controle;
    int nbJoueur;
    int taille_base_pyramide;
    PDJPyramideJoueur pdj;
    PDJPyramideCentrale pdjCentrale;

    public AdaptateurSourisPhaseJoueur(CollecteurEvenements c, PDJPyramideJoueur pdj, PDJPyramideCentrale pdjCentrale) {
        controle = c;
        this.pdj = pdj;
        nbJoueur = pdj.NombreDeJoueur();
        taille_base_pyramide = 8 - nbJoueur;
        this.pdjCentrale = pdjCentrale;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (pdj.NumeroJoueur() != pdjCentrale.GetJoueurCourant()) {
            return;
        }

        // On clique dans la pyramide
        PDJPyramideJoueur.SetCube_Select_Static(false);
        int taille_cube_joueur = pdj.TailleCubeJoueur();
        Point points_pyramide_joueurs[][] = pdj.PointPyramideJoueurs(pdj.NumeroJoueur());
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++) {
                if ((e.getY() >= points_pyramide_joueurs[x][y].getY())
                        && (e.getY() <= (points_pyramide_joueurs[x][y].getY() + taille_cube_joueur))
                        && (e.getX() >= points_pyramide_joueurs[x][y].getX())
                        && (e.getX() <= (points_pyramide_joueurs[x][y].getX() + taille_cube_joueur))){
                    PDJPyramideJoueur.SetCube_Select_Static(true);
                    pdj.SetX_Select(taille_base_pyramide - 1 - x);
                    pdj.SetY_Select(y);
                    controle.clicJoueurPyramide(taille_base_pyramide - 1 - x, y);
                    pdjCentrale.repaint();
                    pdj.repaint();
                }
            }
        }

        // On clique dans le side
        Point points_side[] = pdj.PointSide();
        for(int x = 0; x < pdj.tailleSide(); x++){
            if ((e.getY() >= points_side[x].getY())
                        && (e.getY() <= (points_side[x].getY() + taille_cube_joueur))
                        && (e.getX() >= points_side[x].getX())
                        && (e.getX() <= (points_side[x].getX() + taille_cube_joueur))) {
                    PDJPyramideJoueur.SetCube_Select_Static(true);
                    pdj.SetX_Select(x);
                    pdj.SetY_Select(-1);
                    
                    controle.clicJoueurSide(x);
                    pdjCentrale.repaint();
                    pdj.repaint();
            }
        }
    }
}
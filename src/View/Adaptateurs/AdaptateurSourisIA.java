package View.Adaptateurs;

import View.CollecteurEvenements;
import View.Curseur;
import View.PDJPyramideCentrale;
import View.PDJPyramideIA;
import View.PDJPyramideJoueur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisIA extends MouseAdapter {
    CollecteurEvenements controle;
    int nbJoueur;
    int taille_base_pyramide;
    PDJPyramideIA pdj;
    PDJPyramideCentrale pdjCentrale;
    PDJPyramideJoueur pdjJoueur;

    public AdaptateurSourisIA(CollecteurEvenements c, PDJPyramideIA pdj, PDJPyramideJoueur pdjJoueur, PDJPyramideCentrale pdjCentrale) {
        controle = c;
        this.pdj = pdj;
        nbJoueur = pdj.NombreDeJoueur();
        taille_base_pyramide = 8 - nbJoueur;
        this.pdjCentrale = pdjCentrale;
        this.pdjJoueur = pdjJoueur;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // pdj.setCursor(Cursor.getDefaultCursor());
        pdj.setCursor(Curseur.Gerer_Curseur_main());
        // pdj2.setCursor(Cursor.getDefaultCursor());
        pdjJoueur.setCursor(Curseur.Gerer_Curseur_main());
        // pdjCentrale.setCursor(Cursor.getDefaultCursor());
        pdjCentrale.setCursor(Curseur.Gerer_Curseur_main());
        pdjCentrale.GetAccessible(false);

        // System.out.println("joueur courant : "+ pdjCentrale.GetJoueurCourant() + ",
        // joueur adaptateur : " + pdj.NumeroJoueur());
        if (pdj.NumeroJoueur() != pdjCentrale.GetJoueurCourant()) {
            return;
        }
        int taille_cube_joueur = pdj.TailleCubeJoueur();
        Point points_pyramide_joueurs[][] = pdj.PointPyramideJoueurs(pdj.NumeroJoueur());
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++) {
                if ((e.getY() >= points_pyramide_joueurs[x][y].getY())
                        && (e.getY() <= (points_pyramide_joueurs[x][y].getY() + taille_cube_joueur))
                        && (e.getX() >= points_pyramide_joueurs[x][y].getX())
                        && (e.getX() <= (points_pyramide_joueurs[x][y].getX() + taille_cube_joueur))) {
                    controle.clicJoueurPyramide(taille_base_pyramide - 1 - x, y);
                    // pdjCentrale.repaint();
                }
            }
        }

        Point points_side[] = pdj.PointSide();
        for(int x = 0; x < pdj.tailleSide(); x++){
            if ((e.getY() >= points_side[x].getY())
                        && (e.getY() <= (points_side[x].getY() + taille_cube_joueur))
                        && (e.getX() >= points_side[x].getX())
                        && (e.getX() <= (points_side[x].getX() + taille_cube_joueur))) {
                    controle.clicJoueurSide(x);
                    // pdjCentrale.repaint();
            }
        }
    }
}
package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisBasGauche extends MouseAdapter {
	CollecteurEvenements controle;
	int nbJoueur;
	int taille_base_pyramide;
    PDJPyramideJoueur pdj;
    PDJPyramideCentrale pdjCentrale;

	AdaptateurSourisBasGauche(CollecteurEvenements c, PDJPyramideJoueur pdj, PDJPyramideCentrale pdjCentrale) {
		controle = c;
		this.pdj = pdj;
        nbJoueur = pdj.NombreDeJoueur();
		taille_base_pyramide = 8 - nbJoueur;
        this.pdjCentrale = pdjCentrale;
	}

	@Override
	public void mousePressed(MouseEvent e) {

        int taille_cube_joueur = pdj.TailleCubeJoueur();
        Point points_pyramide_joueurs[][] = pdj.PointPyramideJoueurs();
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++) {
                if((e.getY() >= points_pyramide_joueurs[x][y].getY()) && (e.getY() <= (points_pyramide_joueurs[x][y].getY() + taille_cube_joueur))
                && (e.getX() >= points_pyramide_joueurs[x][y].getX()) && (e.getX() <= (points_pyramide_joueurs[x][y].getX() + taille_cube_joueur)))
                {
                    controle.clicSouris(taille_base_pyramide - 1 - x, y);
                    pdjCentrale.repaint();
                }
            }
        }
    }
}
package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisBasGauche extends MouseAdapter {
	CollecteurEvenements controle;
	int nbJoueur;
	int taille_base_pyramide;
    PDJPyramideJoueur pdj;

	AdaptateurSourisBasGauche(CollecteurEvenements c, PDJPyramideJoueur pdj) {
		controle = c;
		this.pdj = pdj;
        nbJoueur = pdj.NombreDeJoueur();
		taille_base_pyramide = 8 - nbJoueur;
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
                    System.out.print("joueur" + (pdj.NumeroJoueur() + 1) + " : ");
                    controle.clicSouris(taille_base_pyramide - 1 - x, y);
                }
            }
        }
    }
}
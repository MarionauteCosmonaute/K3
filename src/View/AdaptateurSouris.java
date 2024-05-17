package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSouris extends MouseAdapter {
	CollecteurEvenements controle;
	AffichagePhaseConstruction nivGraph;
	int nbJoueur;
	int taille_base_pyramide;

	AdaptateurSouris(CollecteurEvenements c, AffichagePhaseConstruction nivGraph) {
		controle = c;
		this.nivGraph = nivGraph;
		nbJoueur = nivGraph.nbJoueur();
		taille_base_pyramide = 8 - nbJoueur;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getY() < nivGraph.Hauteur_Fenetre() * 7 / 10) {
			int x2, y2;
			if (!nivGraph.peut_cliquer_pyramide()) {
				int taille_cube = nivGraph.tailleCube();
				Point pts[][] = nivGraph.pointsPyr();
				for (int x = 0; x < taille_base_pyramide; x++) {
					for (int y = 0; y < (taille_base_pyramide - x); y++) {
						if (e.getX() >= pts[x][y].getX() && e.getX() <= pts[x][y].getX() + taille_cube) {
							if (e.getY() >= pts[x][y].getY() && e.getY() <= pts[x][y].getY() + taille_cube) {
								if (nivGraph.getEchange() % 2 == 0) {
									nivGraph.setX1(x);
									nivGraph.setY1(y);
									nivGraph.echange();
								} else {
									x2 = x;
									y2 = y;
									controle.clicSourisEchange(nivGraph.getX1(), nivGraph.getY1(), x2, y2);
									nivGraph.echange();
								}
								nivGraph.setEchange(nivGraph.getEchange() + 1);
								return;

							}
						}
					}
				}
			}
			int taille_cube = nivGraph.tailleCube();
			Point pts[][] = nivGraph.pointsPyr();
			Point p = new Point(-1, -1);
			for (int x = 0; x < taille_base_pyramide; x++) {
				for (int y = 0; y < (taille_base_pyramide - x); y++) {
					if (e.getX() >= pts[x][y].getX() && e.getX() <= pts[x][y].getX() + taille_cube) {
						if (e.getY() >= pts[x][y].getY() && e.getY() <= pts[x][y].getY() + taille_cube) {
							controle.clicSourisPyr(x, y);
							nivGraph.setPoint(p);
						}
					}
				}
			}
		} else if (e.getY() >= nivGraph.Hauteur_Fenetre() * 7 / 10) {
			int taille_cube = nivGraph.tailleCube();
			Point pts[] = nivGraph.pointsPioche2();
			int emp = nivGraph.getEmplacement();
			if (nivGraph.peut_cliquer_pyramide()) { // un cube a été selectionné dans la pioche
				if (e.getX() >= pts[emp].getX() && e.getX() <= pts[emp].getX() + taille_cube) {
					if (e.getY() >= pts[emp].getY() && e.getY() <= pts[emp].getY() + taille_cube) {
						nivGraph.doubleClic();
						return;
					}
				}
			}

			int couleurs[] = nivGraph.couleurs();
			int couleur;
			int somme = 0;
			for (int i = 0; i < 7; i++) {
				somme += couleurs[i];
			}
			for (int i = 0; i < somme; i++) {
				if (e.getX() >= pts[i].getX() && e.getX() <= pts[i].getX() + taille_cube) {
					if (e.getY() >= pts[i].getY() && e.getY() <= pts[i].getY() + taille_cube) {
						nivGraph.setEchange(0);
						couleur = nivGraph.couleur_case(i + 1, couleurs);
						controle.clicSourisPioche(couleur);
						nivGraph.modifierPioche(i);
					}
				}
			}
		}

	}
}
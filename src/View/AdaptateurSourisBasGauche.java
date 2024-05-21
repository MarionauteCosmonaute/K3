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
        // System.out.println("taille des cubes ADAPTATEUR: "+ taille_cube_joueur);
        Point points_pyramide_joueurs[][] = pdj.PointPyramideJoueurs();
        
        // for (int i = 8 - 2 - 1; i >= 0; i--) {
        //     for(int j = 0; j<=i; j++){
        //         System.out.print("(" + points_pyramide_joueurs[i][j].x + ", " + points_pyramide_joueurs[i][j].y+")");
        //     }
        //     System.out.println();
        // }
        // System.out.println("------------------------------------------> Nouveau click");
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++){
                
                // System.out.println("e.getY: "+ e.getY());
                // System.out.println("(" + points_pyramide_joueurs[x][y].x + ", " + (points_pyramide_joueurs[x][y].x + taille_cube_joueur)+")");
                // System.out.println("e.getX: "+ e.getX());
                // System.out.println("(" + points_pyramide_joueurs[x][y].y + ", " + (points_pyramide_joueurs[x][y].y + taille_cube_joueur)+")");

                if((e.getY() >= points_pyramide_joueurs[x][y].getY()) && (e.getY() <= (points_pyramide_joueurs[x][y].getY() + taille_cube_joueur))
                && (e.getX() >= points_pyramide_joueurs[x][y].getX()) && (e.getX() <= (points_pyramide_joueurs[x][y].getX() + taille_cube_joueur)))
                {
                    controle.clicSouris(taille_base_pyramide - 1 - x, y);
                }
            }
        }
    }
}
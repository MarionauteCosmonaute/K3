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
        
        for (int i = 8 - 2 - 1; i >= 0; i--) {
            for(int j = 0; j<=i; j++){
                System.out.print("(" + points_pyramide_joueurs[i][j].x + ", " + points_pyramide_joueurs[i][j].y+")");
            }
            System.out.println();
        }

        // System.out.println("coordonnees : x :" + points_pyramide_joueurs[8 - nbJoueur - 1][0].x + ", y : " + points_pyramide_joueurs[8 - nbJoueur - 1][0].y);
        // System.out.println("coordonnees bas droite: x :" + (points_pyramide_joueurs[8 - nbJoueur - 1][0].x + taille_cube_joueur)+ ", y : " + (points_pyramide_joueurs[8 - nbJoueur - 1][0].y + taille_cube_joueur));
        // System.out.println("clic : x :" + e.getX() + ", y : " + e.getY());
        
        for (int x = 8 - nbJoueur - 1; x >= 0; x--) {
        // for(int x = 0; x < 8 - nbJoueur; x++){
            // for (int y = 0; y <= x; y++) {
            for (int y = 0; y <= x; y++){
                // System.out.println("---> x"+ x);
                // System.out.println("---> y"+ y);
                // System.out.println(" 8 - nbJoueur ... : "+ (8 - nbJoueur - 1 - x));
                
                if((e.getY() >= points_pyramide_joueurs[x][y].x) && (e.getY() <= points_pyramide_joueurs[x][y].x + taille_cube_joueur)){
                    System.out.println("---> 8");
                    if((e.getX() >= points_pyramide_joueurs[x][y].y) && (e.getX() <= points_pyramide_joueurs[x][y].y + taille_cube_joueur)){
                        System.out.println("---> 9");
                        controle.clicSouris(8 - nbJoueur - 1, y);
                    }
                }
                System.out.println("---> 12");
            }
        }
    }
}
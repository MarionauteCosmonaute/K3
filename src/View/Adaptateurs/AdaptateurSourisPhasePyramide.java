package View.Adaptateurs;

import View.CollecteurEvenements;
import View.Curseur;
import View.PDJPyramideCentrale;
import View.PDJPyramideJoueur;

import Model.Jeu;
import Model.Cube;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisPhasePyramide extends MouseAdapter {
    CollecteurEvenements controle;
    int taille_base_pyramide;
    PDJPyramideCentrale pdjCentrale;
    PDJPyramideJoueur pdj1, pdj2;
    Jeu jeu;

    public AdaptateurSourisPhasePyramide(CollecteurEvenements c, PDJPyramideCentrale pdjCentrale, PDJPyramideJoueur pdj1, PDJPyramideJoueur pdj2 ) {
        controle = c;
        // nbJoueur = pdjCentrale.NombreDeJoueur();
        taille_base_pyramide = pdjCentrale.GetTaillePyramide();
        this.pdjCentrale = pdjCentrale;
        this.pdj1 = pdj1;
        this.pdj2 = pdj2;
        jeu = pdjCentrale.getJeu();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int taille_cube_pyramide_centrale = pdjCentrale.GetTailleCubePyramideCentrale();
        Point points_pyramide_centrale[][] = pdjCentrale.GetPointPyramideCentrale();

        if(!PDJPyramideJoueur.getCube_Select_Static()){
			System.out.println("return");
			return;
		}

        // on récupère le cube selectionné pour pouvoir ensuite tester que le coup est valide
        Cube cube;
        if(jeu.get_player() == 0){
            cube = jeu.getPlayer().get(pdj1.getX_Select(), pdj1.getY_Select());
        }
        else{
            cube = jeu.getPlayer().get(pdj2.getX_Select(), pdj2.getY_Select());
        }
        
        // Le clique a lieu on remet le curseur normal
        // 4) Si dans la pyramide centrale, on clique sur un emplacement ACCESSIBLE on remet le curseur normal et on continue

        // pdjCentrale.setCursor(Cursor.getDefaultCursor());
        pdjCentrale.setCursor(Curseur.Gerer_Curseur_main());
        pdjCentrale.GetAccessible(false);
        pdjCentrale.repaint();

        // pdj1.setCursor(Cursor.getDefaultCursor());
        pdj1.setCursor(Curseur.Gerer_Curseur_main());
        pdj1.SetDessineMoins1(false);
        pdj1.repaint();

        // pdj2.setCursor(Cursor.getDefaultCursor());
        pdj2.setCursor(Curseur.Gerer_Curseur_main());
        pdj2.SetDessineMoins1(false);
        pdj2.repaint();


        // On gère le clique du blanc
        if((e.getY() >= pdjCentrale.GetBlancAccessible().x)
        && (e.getY() <= pdjCentrale.GetBlancAccessible().x + taille_cube_pyramide_centrale)
        && (e.getX() >= pdjCentrale.GetBlancAccessible().y)
        && (e.getX() <= (pdjCentrale.GetBlancAccessible().y + taille_cube_pyramide_centrale)))
        {
            System.out.println("Dans le if");
            if(jeu.move_validity(cube, taille_base_pyramide - 1, -1) != 0){
                pdjCentrale.setDernierCoup(false);
                controle.clicBlanc(taille_base_pyramide - 1 , -1);
                pdjCentrale.repaint();
            }
            else{
                PDJPyramideJoueur.SetCube_Select_Static(false);
                pdjCentrale.repaint();
            }
            return;
        }

        // Le clique a lieu dans la pyramide
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++) {
                if ((e.getY() >= points_pyramide_centrale[x][y].getY())
                        && (e.getY() <= (points_pyramide_centrale[x][y].getY() + taille_cube_pyramide_centrale))
                        && (e.getX() >= points_pyramide_centrale[x][y].getX())
                        && (e.getX() <= (points_pyramide_centrale[x][y].getX() + taille_cube_pyramide_centrale))) {
                            if(jeu.move_validity(cube, taille_base_pyramide - 1 - x, y) != 0){
                                System.out.println("repaint cube posé!");
                                pdjCentrale.setDernierCoup(false);
                                controle.clicPyramideCentrale(taille_base_pyramide - 1 - x, y);
                                pdjCentrale.repaint();
                            }
                            else{
                                PDJPyramideJoueur.SetCube_Select_Static(false);
                                pdjCentrale.repaint();
                            }
                            return;
                }
            }
        }
    }
}

package View.Adaptateurs;

import View.CollecteurEvenements;
import View.Curseur;
import View.PDJPyramideCentrale;
import View.PDJPyramideJoueur;
import View.PDJPyramideIA;

import Model.Jeu;
import Model.Cube;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisPhasePyramideVSia extends MouseAdapter {
    CollecteurEvenements controle;
    int taille_base_pyramide;
    PDJPyramideCentrale pdjCentrale;
    PDJPyramideJoueur pdj;
    PDJPyramideIA pdjIA;
    Jeu jeu;

    public AdaptateurSourisPhasePyramideVSia(CollecteurEvenements c, PDJPyramideCentrale pdjCentrale, PDJPyramideJoueur pdj, PDJPyramideIA pdjIA) {
        controle = c;
        // nbJoueur = pdjCentrale.NombreDeJoueur();
        taille_base_pyramide = pdjCentrale.GetTaillePyramide();
        this.pdjCentrale = pdjCentrale;
        this.pdj = pdj;
        this.pdjIA = pdjIA;
        jeu = pdjCentrale.getJeu();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int taille_cube_pyramide_centrale = pdjCentrale.GetTailleCubePyramideCentrale();
        Point points_pyramide_centrale[][] = pdjCentrale.GetPointPyramideCentrale();

        if(!PDJPyramideJoueur.getCube_Select_Static() || jeu.get_player() == 1){
			System.out.println("return");
			return;
		}

        // on récupère le cube selectionné pour pouvoir ensuite tester que le coup est valide
        Cube cube = jeu.getPlayer().get(pdj.getX_Select(), pdj.getY_Select());

        // Le clique a lieu on remet le curseur normal
        // 4) Si dans la pyramide centrale, on clique sur un emplacement ACCESSIBLE on remet le curseur normal et on continue
        pdjCentrale.setCursor(Curseur.Gerer_Curseur_main());
        pdjCentrale.GetAccessible(false);
        pdjCentrale.repaint();

        pdj.setCursor(Curseur.Gerer_Curseur_main());
        pdj.SetDessineMoins1(false);
        pdj.repaint();

        pdjIA.setCursor(Curseur.Gerer_Curseur_main());
        pdjIA.repaint();


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
            else
            {
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

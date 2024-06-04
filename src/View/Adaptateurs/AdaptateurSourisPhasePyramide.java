package View.Adaptateurs;

import View.CollecteurEvenements;
import View.Curseur;
import View.PDJPyramideCentrale;
import View.PDJPyramideJoueur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class AdaptateurSourisPhasePyramide extends MouseAdapter {
    CollecteurEvenements controle;
    int taille_base_pyramide;
    PDJPyramideCentrale pdjCentrale;
    PDJPyramideJoueur pdj1, pdj2;

    public AdaptateurSourisPhasePyramide(CollecteurEvenements c, PDJPyramideCentrale pdjCentrale, PDJPyramideJoueur pdj1, PDJPyramideJoueur pdj2 ) {
        controle = c;
        // nbJoueur = pdjCentrale.NombreDeJoueur();
        taille_base_pyramide = pdjCentrale.GetTaillePyramide();
        this.pdjCentrale = pdjCentrale;
        this.pdj1 = pdj1;
        this.pdj2 = pdj2;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int taille_cube_pyramide_centrale = pdjCentrale.GetTailleCubePyramideCentrale();
        Point points_pyramide_centrale[][] = pdjCentrale.GetPointPyramideCentrale();

        if(!PDJPyramideJoueur.getCube_Select_Static()){
			System.out.println("return");
			return;
		}
        // Le clique a lieu on remet le curseur normal
        // 4) Si dans la pyramide centrale, on clique sur un emplacement ACCESSIBLE on remet le curseur normal et on continue

        // pdjCentrale.setCursor(Cursor.getDefaultCursor());
        pdjCentrale.setCursor(Curseur.Gerer_Curseur_main());
        pdjCentrale.GetAccessible(false);

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
            controle.clicBlanc(taille_base_pyramide - 1 , -1);
            return;
        }

        // Le clique a lieu dans la pyramide
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y <= x; y++) {
                if ((e.getY() >= points_pyramide_centrale[x][y].getY())
                        && (e.getY() <= (points_pyramide_centrale[x][y].getY() + taille_cube_pyramide_centrale))
                        && (e.getX() >= points_pyramide_centrale[x][y].getX())
                        && (e.getX() <= (points_pyramide_centrale[x][y].getX() + taille_cube_pyramide_centrale))) {
                    controle.clicPyramideCentrale(taille_base_pyramide - 1 - x, y);
                    // pdjCentrale.repaint();
                }
            }
        }
    }
}

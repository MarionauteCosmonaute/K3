package View.Adaptateurs;

import View.CollecteurEvenements;
import View.Curseur;
import View.PDJPyramideCentrale;
import View.PDJPyramideIA;
import View.PDJPyramideJoueur;
import Model.Cube;


import Global.FileLoader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.*;

public class AdaptateurSourisPhaseJoueurVSia extends MouseAdapter {
    CollecteurEvenements controle;
    int nbJoueur;
    int taille_base_pyramide;
    PDJPyramideJoueur pdj;
    PDJPyramideIA pdjIA;
    PDJPyramideCentrale pdjCentrale;

    public AdaptateurSourisPhaseJoueurVSia(CollecteurEvenements c, PDJPyramideJoueur pdj, PDJPyramideIA pdjIA, PDJPyramideCentrale pdjCentrale) {
        controle = c;
        this.pdj = pdj;
        this.pdjIA = pdjIA;
        nbJoueur = pdj.NombreDeJoueur();
        taille_base_pyramide = 8 - nbJoueur;
        this.pdjCentrale = pdjCentrale;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pdj.SetDessineMoins1(false);
        // pdj.setCursor(Cursor.getDefaultCursor());
        // pdjIA.setCursor(Cursor.getDefaultCursor());
        // pdjCentrale.setCursor(Cursor.getDefaultCursor());
        pdj.setCursor(Curseur.Gerer_Curseur_main());
        pdj.repaint();
        pdjIA.setCursor(Curseur.Gerer_Curseur_main());
        pdjIA.repaint();
        pdjCentrale.setCursor(Curseur.Gerer_Curseur_main());
        pdjCentrale.GetAccessible(false);
        pdjCentrale.repaint();

        if (pdj.NumeroJoueur() != pdjCentrale.GetJoueurCourant()) {
            // Voir si les deux repaint sont utiles
            pdj.SetDessineMoins1(false);
            pdj.repaint();
            pdjIA.repaint();
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
                        && (e.getX() <= (points_pyramide_joueurs[x][y].getX() + taille_cube_joueur)))
                        {

                    // ArrayList<Point> liste_accessible = pdj.GetAccessible();
                    ArrayList<Point> liste_accessible;
                    if(pdj.GetPenality()){
                        liste_accessible = pdj.GetAccessible();
                    }
                    else{
                        liste_accessible = pdj.accessiblesJouables();
                    }
                    for (Point p: liste_accessible)
                    {
                        if ((p.x == taille_base_pyramide - 1 - x) && (p.y==y))
                        {
                            // Un cube a été sélectionné on le highlight le contour        
                            PDJPyramideJoueur.SetCube_Select_Static(true);
                            pdj.SetX_Select(taille_base_pyramide - 1 - x);
                            pdj.SetY_Select(y);

                            // On informe le controleur médiateur
                            controle.clicJoueurPyramide(taille_base_pyramide - 1 - x, y);

                            Gerer_Curseur(x, y, false);

                            pdjCentrale.repaint();
                            pdj.repaint();
                        }
                    }
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

                    Gerer_Curseur(x, -1, true);

                    pdjCentrale.repaint();
                    pdj.repaint();
            }
        }
    }

    public void Gerer_Curseur(int x, int y, boolean side)
    {
        // 2) On supprime le cube de la pyramide
        // 3) On supprime tous les highlight
        // 4) Si dans la pyramide centrale, on clique sur un emplacement ACCESSIBLE on remet le curseur normal et on continue
        // 5) Si dans la pyramide centrale, on clique sur un emplacement NON accessible remet le curseur normal, on remet le cube dans la pyramide du joueur et on remet les highlight accessible et on continue

        try
        {
            // Charger l'image de la banane
            String curseur = Cube_Chope(x, y, side);
            if (curseur != "Erreur")
            {
                // Redimensionner l'image de la banane à 50x50 pixels
                int taille_cube_pyramide_centrale = pdjCentrale.GetTailleCubePyramideCentrale();
                Image scaledBananaImage = FileLoader.getImage(curseur).getScaledInstance(taille_cube_pyramide_centrale, taille_cube_pyramide_centrale, Image.SCALE_SMOOTH);
                // Convertir l'image redimensionnée de la banane en curseur
                Cursor bananaCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaledBananaImage, new Point(taille_cube_pyramide_centrale/2,taille_cube_pyramide_centrale/2), "banana cursor");

                pdj.setCursor(bananaCursor);
                pdjIA.setCursor(bananaCursor);
                pdjCentrale.setCursor(bananaCursor);
                pdjCentrale.GetAccessible(true);
                pdj.SetDessineMoins1(true);
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String Cube_Chope(int x, int y, boolean side)
    {
       Cube cube = pdj.GetCubeChope(x, y, side);
       switch (cube) {
            case Noir:

                return "res/violet.png";

            case Neutre:

                return "res/neutre2.png";

            case Blanc:

                return "res/ange.png";

            case Vert:

                return "res/vert.png";

            case Jaune:

                return "res/jaune.png";

            case Rouge:

                return "res/rouge.png";

            case Bleu:

                return "res/bleu.png";

            default:
                return "Erreur";
        }

    }
}
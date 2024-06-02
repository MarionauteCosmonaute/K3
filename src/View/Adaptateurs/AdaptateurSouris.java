package View.Adaptateurs;
import View.CollecteurEvenements;
import View.Curseur;
import View.AffichagePhaseConstruction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.*;

public class AdaptateurSouris extends MouseAdapter {
	CollecteurEvenements controle;
	AffichagePhaseConstruction nivGraph;
	int nbJoueur;
	int taille_base_pyramide;

	public AdaptateurSouris(CollecteurEvenements c, AffichagePhaseConstruction nivGraph) {
		controle = c;
		this.nivGraph = nivGraph;
		nbJoueur = nivGraph.nbJoueur();
		taille_base_pyramide = 8 - nbJoueur;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// Clique dans la pyramide
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
									System.out.println("-------------------> Lisa = premier clique dans la pyramide");
									// Gerer_Curseur(); réussir à déterminer la couleur sur laquelle on a cliquée
									nivGraph.setX1(x);
									nivGraph.setY1(y);
									nivGraph.echange();
								} else {
									System.out.println("-------------------> Askel: Arrivée de Lisa! On a cliqué dans la pyramide et le deuxième clique est aussi dans la pyramide!");
									x2 = x;
									y2 = y;
									controle.clicSourisEchange(nivGraph.getX1(), nivGraph.getY1(), x2, y2);
									// nivGraph.setCursor(Cursor.getDefaultCursor());
									nivGraph.setCursor(Curseur.Gerer_Curseur_main());
									// nivGraph.GetAccessible(false
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
							System.out.println("-------------------> Judi: On a sélecctionné un cube dans la pioche et le prochain clique est dans la pyramide!");
							// nivGraph.setCursor(Cursor.getDefaultCursor());
							// nivGraph.GetAccessible(false);
							controle.clicSourisPyr(x, y);
							nivGraph.setPoint(p);
						}
					}
				}
			}
			// nivGraph.setCursor(Cursor.getDefaultCursor());
			nivGraph.setCursor(Curseur.Gerer_Curseur_main());
			// nivGraph.GetAccessible(false);
		} 
		else if (e.getY() >= nivGraph.Hauteur_Fenetre() * 7 / 10) 	// Clique dans la pioche
		{	
			int taille_cube = nivGraph.tailleCube();
			Point pts[] = nivGraph.pointsPioche2();
			int emp = nivGraph.getEmplacement();
			if (nivGraph.peut_cliquer_pyramide()) { // un cube a été selectionné dans la pioche
				if (e.getX() >= pts[emp].getX() && e.getX() <= pts[emp].getX() + taille_cube) {
					if (e.getY() >= pts[emp].getY() && e.getY() <= pts[emp].getY() + taille_cube) {
						System.out.println("-------------------> Natacha:");
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
						System.out.println("-------------------> Ryan: Premier clique dans la pioche");
						nivGraph.setEchange(0);
						couleur = nivGraph.couleur_case(i + 1, couleurs);

						Gerer_Curseur(couleur);

						controle.clicSourisPioche(couleur);
						nivGraph.modifierPioche(i);
					}
				}
			}
		}
		else
		{
			System.out.println("-------------------> Louis: ");
			// nivGraph.setCursor(Cursor.getDefaultCursor());
			nivGraph.setCursor(Curseur.Gerer_Curseur_main());
			// nivGraph.GetAccessible(false);
		}
	}

	public void Gerer_Curseur(int couleur)
    {
        try
        {
            System.out.println("Changment de curseur!");
            // Charger l'image de la banane
            String curseur = Cube_Chope(couleur);
            if (curseur != "Erreur")
            {
                ImageIcon bananaIcon = new ImageIcon(curseur); // Remplacez "banana_cursor.png" par le chemin de votre image de curseur
                // Redimensionner l'image de la banane à 50x50 pixels
                int taille_cube_pyramide_centrale = nivGraph.tailleCube();
                Image scaledBananaImage = bananaIcon.getImage().getScaledInstance(taille_cube_pyramide_centrale, taille_cube_pyramide_centrale, Image.SCALE_SMOOTH);
                // Convertir l'image redimensionnée de la banane en curseur
                Cursor bananaCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaledBananaImage, new Point(taille_cube_pyramide_centrale/2,taille_cube_pyramide_centrale/2), "banana cursor");

                nivGraph.setCursor(bananaCursor);
                // nivGraph.GetAccessible(true);
                // nivGraph.SetDessineMoins1(true);
            }
        }
        catch (Exception execption) 
        {
            System.out.println(execption);
        }
    }

    public String Cube_Chope(int couleur)
    {
       switch (couleur) {
            case 1:
                // System.out.println("cube noir");
                return "res/curseur_main_fermee_noir.png";

            case 6:
                // System.out.println("cube neutre");
                return "res/curseur_main_bois.png";

            case 0:
                // System.out.println("cube blanc");
                return "res/curseur_main_fermee_blanc.png";

            case 3:
                // System.out.println("cube vert");
                return "res/curseur_main_fermee_vert.png";

            case 5:
                // System.out.println("cube jaune");

                return "res/curseur_main_jaune.png";
            case 4:
                // System.out.println("cube rouge");

                return "res/curseur_main_fermee_rouge.png";
            case 2:
                // System.out.println("cube bleu");
                return "res/curseur_main_bleu.png";

            default:
                // System.out.println("default");
                return "Erreur";
        }

    }
}
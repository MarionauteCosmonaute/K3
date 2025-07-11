package View.Adaptateurs;
import View.CollecteurEvenements;
import View.Curseur;
import View.AffichagePhaseConstruction;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Global.FileLoader;

import java.awt.*;

public class AdaptateurSouris extends MouseAdapter {
	CollecteurEvenements controle;
	AffichagePhaseConstruction nivGraph;
	int nbJoueur;
	int taille_base_pyramide;
	boolean cube_selectionne = false;
	boolean clic_dans_pyramide = false;

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
									// Gerer_Curseur(); réussir à déterminer la couleur sur laquelle on a cliquée
									clic_dans_pyramide = true;
									int cube = nivGraph.getCubePyramideJoueur(x, y);
									if(cube != 7){
										Gerer_Curseur(cube);
										nivGraph.setX1(x);
										nivGraph.setY1(y);
										nivGraph.echange();
										nivGraph.setEchange(nivGraph.getEchange() + 1);
										nivGraph.SetMoinsUnPyramide(true);
										nivGraph.repaint();
									}
								} else {

									x2 = x;
									y2 = y;
									controle.clicSourisEchange(nivGraph.getX1(), nivGraph.getY1(), x2, y2);
									nivGraph.SetMoinsUnPyramide(false);
									nivGraph.setCursor(Curseur.Gerer_Curseur_main());
									nivGraph.echange();
									nivGraph.setEchange(nivGraph.getEchange() + 1);
									nivGraph.repaint();
									
								}
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
							nivGraph.SetMoinsUnPyramide(false);
							System.out.println("pyramide");
							// nivGraph.setCursor(Cursor.getDefaultCursor());
							// nivGraph.GetAccessible(false);
							if(cube_selectionne){
								controle.clicSourisPyr(x, y);
								nivGraph.setPoint(p);
							}
							nivGraph.repaint();
							
						}
					}
				}
			}
			reset();
			nivGraph.repaint();
			
		} 
		else if (e.getY() >= nivGraph.Hauteur_Fenetre() * 7 / 10) 	// Clique dans la pioche
		{	
			if(nivGraph.peut_cliquer_pyramide()){
				reset();
				nivGraph.repaint();
				return;
			}
			Point pts[] = nivGraph.pointsPioche2();
			int taille_cube = nivGraph.tailleCube();
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
						nivGraph.SetMoinsUnPioche(true);
						nivGraph.SetMoinsUnPyramide(false);
						Gerer_Curseur(couleur);
						nivGraph.repaint();

						controle.clicSourisPioche(couleur);
						cube_selectionne = true;
						nivGraph.modifierPioche(i);
						nivGraph.repaint();

						// nivGraph.setCubeSel(false);
					}
				}
			}
			if(clic_dans_pyramide){ // un cube a été selectionné dans la pyramide, on le remet dedans car on a cliqué dans la pioche
				reset();
				nivGraph.repaint();
			}
		}
	}

	public void Gerer_Curseur(int couleur)
    {
        try
        {
            // Charger l'image de la banane
            String curseur = Cube_Chope(couleur);
            if (curseur != "Erreur")
            {
                // Redimensionner l'image de la banane à 50x50 pixels
                int taille_cube_pyramide_centrale = nivGraph.tailleCube();
                Image scaledBananaImage = FileLoader.getImage(curseur).getScaledInstance(taille_cube_pyramide_centrale, 
																	taille_cube_pyramide_centrale, Image.SCALE_SMOOTH);
                // Convertir l'image redimensionnée de la banane en curseur
                Cursor bananaCursor = Toolkit.getDefaultToolkit().createCustomCursor(scaledBananaImage, 
										new Point(taille_cube_pyramide_centrale/2,taille_cube_pyramide_centrale/2), "banana cursor");
                nivGraph.setCursor(bananaCursor);
                // nivGraph.GetAccessible(true);
                // nivGraph.SetDessineMoins1(true);
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

	public void reset(){
		nivGraph.setEchange(0);
		nivGraph.SetMoinsUnPioche(false);
		nivGraph.SetMoinsUnPyramide(false);
		cube_selectionne = false;
		nivGraph.setDessinVideFalse();
		nivGraph.setCubeSel(false);
		nivGraph.setCursor(Curseur.Gerer_Curseur_main());
		nivGraph.repaint();
		clic_dans_pyramide = false;
	}

    public String Cube_Chope(int couleur)
    {
       switch (couleur) {
            case 1:
                // System.out.println("cube noir");
                return "res/violet.png";

            case 6:
                // System.out.println("cube neutre");
                return "res/neutre2.png";

            case 0:
                // System.out.println("cube blanc");
                return "res/ange.png";

            case 3:
                // System.out.println("cube vert");
                return "res/vert.png";

            case 5:
                // System.out.println("cube jaune");
                return "res/jaune.png";
				
            case 4:
                // System.out.println("cube rouge");
                return "res/rouge.png";

            case 2:
                // System.out.println("cube bleu");
                return "res/bleu.png";

            default:
                // System.out.println("default");
                return "Erreur";
        }

    }
}
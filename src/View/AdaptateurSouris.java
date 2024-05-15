package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;


public class AdaptateurSouris extends MouseAdapter 
{
	CollecteurEvenements controle;
	NiveauGraphique nivGraph;

	AdaptateurSouris(CollecteurEvenements c, NiveauGraphique nivGraph)
	{
		controle = c;
		this.nivGraph = nivGraph;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getY() < nivGraph.Hauteur_Fenetre()*7/10){
			if(!nivGraph.peut_cliquer_pyramide()){
				return;
			}
			int taille_cube = nivGraph.tailleCube();
			Point pts[][] = nivGraph.pointsPyr();
			Point p = new Point(-1, -1);
			for(int x = 0; x<6; x++){
				for(int y = 0; y<(6-x); y++){
					if(e.getX() >= pts[x][y].getX() && e.getX() <= pts[x][y].getX() + taille_cube){
						if(e.getY() >= pts[x][y].getY() && e.getY() <= pts[x][y].getY() + taille_cube){
						controle.clicSourisPyr(x, y);
						nivGraph.setPoint(p);
						}
					}
				}
			}
		}
		else if(e.getY() >= nivGraph.Hauteur_Fenetre()*7/10){
			int taille_cube = nivGraph.tailleCube();
			Point pts[] = nivGraph.pointsPioche2();
			int couleurs[] = nivGraph.couleurs();
			int couleur;
			int somme = 0;
			for(int i=0; i<7; i++){
				somme += couleurs[i];
			}
			for(int i = 0; i < somme; i++){
				if(e.getX() >= pts[i].getX() && e.getX() <= pts[i].getX() + taille_cube){
					if(e.getY() >= pts[i].getY() && e.getY() <= pts[i].getY() + taille_cube){
						couleur = nivGraph.couleur_case(i+1, couleurs);
						controle.clicSourisPioche(couleur);
						nivGraph.modifierPioche(i);
					}
				}
			}
		}
		
	}
}
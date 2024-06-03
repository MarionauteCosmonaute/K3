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
		if((e.getX() <= nivGraph.Largeur_Fenetre()*3/4) && e.getY() <= nivGraph.Hauteur_Fenetre()*7/10){
			
			if(!nivGraph.peut_cliquer_pyramide()){
				return;
			}
			System.out.println("pyramide");
			int taille_cube = nivGraph.tailleCubePyramide();
			Point pts[][] = nivGraph.pointsPyr();
			int l, c;
			Point p;
			for(int i=0; i<6; i++){
				for(int j=0; j<=i; j++){
					if(e.getX() >= pts[i][j].getX() && e.getX() <= pts[i][j].getX() + taille_cube){
						if(e.getY() >= pts[i][j].getY() && e.getY() <= pts[i][j].getY() + taille_cube){
							l = 5-i;
							c = i-j;
							p = new Point(l, c);
							nivGraph.setPoint(p);
							controle.clicSourisPyr(l, c);
						}
						
					}
				}
			}
		}
		else if(e.getX() >= nivGraph.Largeur_Fenetre()*3/4){
			System.out.println("cote");
			int taille_cube = nivGraph.tailleCubePioche();
			Point pts[][] = nivGraph.pointsPioche();
			int l, c;
			int couleurs[] = nivGraph.couleurs();
			Point p;
			for(int i=0; i<7; i++){
				for(int j=0; j<couleurs[i]; j++){
					if(e.getX() >= pts[i][j].getX() && e.getX() <= pts[i][j].getX() + taille_cube){
						if(e.getY() >= pts[i][j].getY() && e.getY() <= pts[i][j].getY() + taille_cube){
							l = i;
							c = couleurs[i] - j - 1;
							p = new Point(l, couleurs[i] - j - 1);
							nivGraph.modifierLignePioche(p);
							controle.clicSourisPioche(l,  c);
						}
					}
				}
			}
		}
		
	}
}
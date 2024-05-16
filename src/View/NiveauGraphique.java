package View;

import javax.swing.*;
import java.awt.*;

import Model.Jeu;
import Patterns.Observateur;

public class NiveauGraphique extends JComponent implements Observateur{
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    JFrame frame;
    Graphics2D drawable;
    Jeu jeu;

    PhaseConstruction cons;

    NiveauGraphique(Jeu jeu, PhaseConstruction c)
    {
        this.jeu = jeu;
        cons = c;
    }

    @Override
	public void miseAJour()
    {
		repaint();
	}

    public int Largeur_Fenetre()
    {
        return width_fenetre;
    }

    public int Hauteur_Fenetre()
    {
        return height_fenetre;
    }

    public int Largeur_case()
    {
        return largeur_case;
    }

    public int Hauteur_case()
    {
        return hauteur_case;
    }
    

    public void Dessiner_plateau()
    {
  
    }


    public int tailleCubePyramide(){
        return cons.tailleCubePyramide();
    }

    public Point[][] pointsPyr(){
        return cons.points_pyr();
    }

    public int tailleCubePioche(){
        return cons.tailleCubePioche();
    }

    public Point[][] pointsPioche(){
        return cons.points_pioche();
    }

    public int[] couleurs(){
        return cons.couleurs();
    }

    public void modifierLignePioche(Point p){
        cons.modifierLignePioche(p);
        cons.pioche = cons.pioche = true;
        System.out.println("ligne piooooche");
        cons.set_cube_sel(true);
        // cons.set_cube2_sel(false); ////////////
        // cons.setClicCentre(true);
        // cons.pioche = !cons.pioche;
        repaint();
    }

    public void setPoint(Point p){
        cons.setPoint(p);
        cons.set_cube_sel(false);
        cons.pioche = cons.pioche = false;

        // cons.set_cube1_sel(true);
        // cons.pioche = !cons.pioche;
        repaint();
    }

    public boolean peut_cliquer_pyramide(){
        return cons.peut_cliquer_pyramide();
    }

    /*
    public boolean cube1_selectionne(){
        return cons.cube1_selectionne();
    }

    public void setCube1(Point p){
        cons.setCube1(p);
        cons.set_cube1_sel(false);
        
    }

    public void setCube2(Point p){
        cons.setCube2(p);
        // cons.set_cube1_sel(false);
        // cons.set_cube2_sel(true);
    }
    */

    // public boolean getClicCentre(){
    //     return cons.clic_centre;
    // }
    // public void setClicCentre(boolean clic){
    //     cons.setClicCentre(clic);
    // }

    public void paintComponent(Graphics g)
    {
        // System.out.println("PaintComponent de NiveauGraphique");
       
       
        // Initialisation de la fenêtre graphique
	    drawable = (Graphics2D) g;
		width_fenetre = getSize().width;
		height_fenetre = getSize().height;
		drawable.clearRect(0, 0, width_fenetre, height_fenetre);
        
        //drawable.drawLine(0, 0, 50, 50);
        // cons.set_cube_sel(false);
        cons.fonction_globale(jeu, g, width_fenetre, height_fenetre);
    }
}

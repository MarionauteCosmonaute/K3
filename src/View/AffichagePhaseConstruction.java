package View;

import javax.swing.*;
import java.awt.*;
import Model.Jeu;
import Patterns.Observateur;

public class AffichagePhaseConstruction extends JComponent implements Observateur{
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    JFrame frame;
    Graphics2D drawable;
    Jeu jeu;

    PhaseConstruction cons;

    AffichagePhaseConstruction(Jeu jeu, PhaseConstruction c)
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

    public int tailleCube(){
        return cons.tailleCube();
    }

    public Point[][] pointsPyr(){
        return cons.points_pyr();
    }

    public Point[] pointsPioche2(){
        return cons.pointsPioche2();
    }

    public int[] couleurs(){
        return cons.couleurs();
    }

    public int couleur_case(int emplacement, int[] couleurs){
        return cons.couleur_case(emplacement, couleurs);
    }

    public void modifierPioche(int emplacement){
        cons.set_cube_sel(true);
        cons.modifierPioche(emplacement);
        repaint();
    }

    public void setPoint(Point p){
        cons.set_cube_sel(false);
        cons.setDessinVideFalse();
        repaint();
    }

    public void echange(){
        repaint();
    }

    public boolean peut_cliquer_pyramide(){
        return cons.peut_cliquer_pyramide();
    }

    public int getEchange(){
        return cons.getEchange();
    }

    public void setEchange(int val){
        cons.setEchange(val);
    }

    public int getX1(){
        return cons.getX1();
    }

    public int getY1(){
        return cons.getY1();
    }

    public void setX1(int x){
        cons.setX1(x);
    }

    public void setY1(int y){
        cons.setY1(y);
    }

    public int nbJoueur(){
        return jeu.nbJoueur();
    }
    
    public int getEmplacement(){
        return cons.getEmplacement();
    }

    public void setValider(boolean b){
        cons.setValider(b);
    }

    public void doubleClic(){
        cons.set_cube_sel(false);
        cons.setDessinVideFalse();
        repaint();
    }

    // public void setCubeSel(boolean b){
    //     cons.set_cube_sel(b);
    // }

    public void paintComponent(Graphics g)
    {
        // System.out.println("PaintComponent de NiveauGraphique");
       
       
        // Initialisation de la fenêtre graphique
	    drawable = (Graphics2D) g;
		width_fenetre = getSize().width;
		height_fenetre = getSize().height;
        
        cons.fonction_globale(jeu, g, width_fenetre, height_fenetre);
    }
}

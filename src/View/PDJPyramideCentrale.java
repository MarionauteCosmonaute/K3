package View;

import Model.Jeu;
import Patterns.Observateur;

import javax.swing.*;

import Controller.ControleurMediateur;

import java.awt.*;

public class PDJPyramideCentrale extends JComponent implements Observateur {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne;
    Graphics2D drawable;
    Jeu jeu;
    JPanel parent;
    boolean accessible = true;
    boolean dernier_coup = false;
    boolean suggestion = false;
    Point coup_suggere;

    public PDJPyramideCentrale(Jeu jeu, JPanel parent) {
        this.jeu = jeu;
        this.parent = parent;
        setOpaque(false);
        coup_suggere = new Point();

        
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public Jeu getJeu(){
        return jeu;
    }

    public int GetJoueurCourant()
    {
        return jeu.get_player();
    }

    public int GetTaillePyramide(){
        return jeu.getPricipale().getSize();
    }

    public int GetTailleCubePyramideCentrale()
    {
        return Math.min(80 * height_fenetre / (100 * GetTaillePyramide()), 80 * width_fenetre / (100 * GetTaillePyramide()));
    }

    public Point[][] GetPointPyramideCentrale()
    {
        return StructurePainter.PointPyramideCentrale();
    }

    public Point GetBlancAccessible()
    {
        return StructurePainter.GetBlancAccessible();
    }

    public void GetAccessible(boolean bool)
    {
       accessible = bool;
    }

    public void setDernierCoup(boolean bool){
        dernier_coup = bool;
        repaint();
    }

    public boolean getDernierCoup(){
        return dernier_coup;
    }

    public void setSuggestion(boolean bool){
        suggestion = bool;
        repaint();
    }

    public void setCoupSuggere(Point p){
        System.out.println("centrale : x : "+p.x+", y : "+p.y);
        coup_suggere = p;
        repaint();
    }

    public void paintComponent(Graphics g) {
        //System.out.println("PaintComponent de PDJPyramideCentrale");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPrincipale(), false, -1);
        if(dernier_coup)
        {
            StructurePainter.dessiner_dernier_coup(jeu, drawable, height_fenetre, width_fenetre, false);
        }

        if(suggestion){
            StructurePainter.dessineSuggestionCentrale(g, coup_suggere.x, coup_suggere.y, height_fenetre, width_fenetre, false, jeu);
        }        

        if (ControleurMediateur.GetClic() && (ControleurMediateur.GetColonne() == -1 || jeu.accessible(ControleurMediateur.GetLigne(), ControleurMediateur.GetColonne())))
        {
            if (accessible)
            {
                System.out.println("dans pdj centrale : ligne : "+ ControleurMediateur.GetLigne() + ", colonne : "+ ControleurMediateur.GetColonne());
                StructurePainter.DessineAccessible(g, ControleurMediateur.GetLigne(),ControleurMediateur.GetColonne(), height_fenetre, width_fenetre, jeu);
            }
        }
        else
        {
            ControleurMediateur.SetClic(false);
        }
    }
}

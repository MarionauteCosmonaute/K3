package View;

import javax.swing.*;
import java.awt.*;
import Model.Jeu;
import Patterns.Observateur;

public class AffichagePhaseDeJeu2 extends JComponent implements Observateur
{
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    JFrame frame;
    Graphics2D drawable;
    Jeu jeu;


    AffichagePhaseDeJeu2(Jeu jeu)
    {
        this.jeu = jeu;
    }

    @Override
	public void miseAJour()
    {
		repaint();
	}

    public void paintComponent(Graphics g)
    {
        System.out.println("PaintComponent de AffichagePhaseDeJeu2");
	    drawable = (Graphics2D) g;
		width_fenetre = getSize().width;
		height_fenetre = getSize().height;
		drawable.clearRect(0, 0, width_fenetre, height_fenetre);
    }
}

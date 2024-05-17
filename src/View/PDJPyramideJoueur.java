package View;

import Model.Jeu;
import Model.Player;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class PDJPyramideJoueur extends JComponent implements Observateur {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;

    JPanel parent;

    PDJPyramideJoueur(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {

        System.out.println("PaintComponent de PDJPyramideJoueur");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        System.out.println("width_fenetre " + width_fenetre);
        System.out.println("height_fenetre " + height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid());
    }

    void build(Player player){
        Collections.shuffle(player.getPersonalBag());
        for(int i = player.getSize()-1; i >= 0; i--){
            for(int j = 0; j < player.getSize()-i; j++){
                //System.out.println( i + " " + j + " " + "0" );
                if(!player.bagEmpty()){player.construction(j, i, player.getPersonalBag().get(0));}
            }
        }
    }
}

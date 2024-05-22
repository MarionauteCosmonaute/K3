package View;

import Model.Jeu;
import Model.Player;


import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class PDJPyramideJoueur extends JComponent {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;

    JPanel parent;

    public PDJPyramideJoueur(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
    }
    // Retourne le tableau qui contient les coordonnées des points de la pyramide du joueur
    public Point[][] PointPyramideJoueurs(){
        return StructurePainter.PointPyramideJoueurs();
    }

    // Retourne la taille des cubes des joueurs
    public int TailleCubeJoueur(){
        return StructurePainter.TailleCubeJoueur();
    }

    // Retourne le nombre de joueur présent dans la partie
    public int NombreDeJoueur(){
        return jeu.nbJoueur();
    }
    
    public int NumeroJoueur(){
        return joueur;
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
        switch(joueur){
            case 0:
                drawable.setColor(Color.BLUE);
            break;
            case 1:
                drawable.setColor(Color.RED);
            break;
        }
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre/10,width_fenetre/10)));
        String languageCode = Global.Config.getLanguage();
        switch(languageCode){
            case "FR":
                drawable.drawString("Joueur "+(joueur+1), 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;
            case "EN":
                drawable.drawString("Player "+(joueur+1), 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;
        }
        drawable.setColor(Color.BLACK);
        if(joueur == jeu.get_player()){
            StructurePainter.Contour_Accessible_Joueur(joueur, jeu, g, height_fenetre, width_fenetre);
        }
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

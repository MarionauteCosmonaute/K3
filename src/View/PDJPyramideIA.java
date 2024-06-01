package View;

import Model.Jeu;

import javax.swing.*;
import java.awt.*;

public class PDJPyramideIA extends JComponent {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;

    JPanel parent;

    public PDJPyramideIA(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
    }
    // Retourne le tableau qui contient les coordonnées des points de la pyramide du joueur
    public Point[][] PointPyramideJoueurs(int joueur){
        return StructurePainter.PointPyramideJoueurs(joueur);
    }

    public Point[] PointSide(){
        return StructurePainter.PointSide();
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

    public int tailleSide(){
        return jeu.getPlayer(joueur).getSideSize();
    }

    public void paintComponent(Graphics g) {

        //System.out.println("PaintComponent de PDJPyramideJoueur");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);

        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid(), jeu.getPlayer(joueur).getSideSize() > 0, joueur);
        StructurePainter.dessiner_side(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSide());
       
        drawable.setColor(Color.RED);

        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre/10,width_fenetre/10)));
        String languageCode = Global.Config.getLanguage();
        switch(languageCode){
            case "FR":
                drawable.drawString("IA", 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;
            case "EN":
                drawable.drawString("AI", 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;
        }
        drawable.setColor(Color.BLACK);
        if(jeu.get_player() == 0 && jeu.getPenality()){
            StructurePainter.Contour_Accessible_Joueur(joueur, jeu, g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSideSize() > 0);
        }
    }
}

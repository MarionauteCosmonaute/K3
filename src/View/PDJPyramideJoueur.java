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
    static boolean cube_selec;
    int x_selec;
    int y_selec;

    JPanel parent;

    public PDJPyramideJoueur(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
        cube_selec = false;
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

    // public void SetCube_Select(boolean bool)
    // {
    //     cube_selec = bool;
    // }

    public static void SetCube_Select_Static(boolean bool)
    {
        cube_selec = bool;
    }

    public void SetX_Select(int x)
    {
        x_selec = x;
    }

    public void SetY_Select(int y)
    {
        y_selec = y;
    }

    public void paintComponent(Graphics g) {

        System.out.println("PaintComponent de PDJPyramideJoueur");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid(), jeu.getPlayer(joueur).getSideSize() > 0, joueur);
        StructurePainter.dessiner_side(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSide());
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
            StructurePainter.Contour_Accessible_Joueur(joueur, jeu, g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSideSize() > 0);
            System.out.println("cube_select: "+ cube_selec);
            if (cube_selec)
            {
                System.out.println("Chaton");
                System.out.println("x : "+x_selec+", y : "+y_selec);
                StructurePainter.DessineSelectionne(joueur, jeu, drawable, height_fenetre, width_fenetre, x_selec, y_selec, jeu.getPlayer(joueur).getSideSize() > 0);
            }
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

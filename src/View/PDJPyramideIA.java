package View;

import Model.Jeu;
import Global.FileLoader;

import javax.swing.*;
import java.awt.*;

public class PDJPyramideIA extends PDJPyramideAbstaite {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;
    // int joueur;

    JPanel parent;

    Image gifIcon1, gifIcon2, gifIcon3, gifIcon4;
    // static Timer timer;
    // static boolean bool_sablier = false;
    boolean sablier;
    int imageIndex = 0;
    // int compteur_image = 0;
    Image[] images;

    public PDJPyramideIA(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        // joueur = nj;
        resetImageIndex();
        try
        {
            gifIcon1 = FileLoader.getImage("res/sablier1.png");
            gifIcon2 = FileLoader.getImage("res/sablier2.png");
            gifIcon3 = FileLoader.getImage("res/sablier3.png");
            gifIcon4 = FileLoader.getImage("res/sablier4.png");

            images = new Image[4];
            images[0] = gifIcon1;
            images[1] = gifIcon2;
            images[2] = gifIcon3;
            images[3] = gifIcon4;
            // gifLabel = new JLabel(gifIcon);
        }
        catch (Exception e) 
        {
            System.exit(1);
        }

        sablier = false;
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
        // return joueur;
        return 1;
    }

    public int tailleSide(){
        return jeu.getPlayer(1).getSideSize();
    }
    public void resetImageIndex(){
        imageIndex=0;
    }
    public void stepImageIndex(){
        imageIndex = (imageIndex+1)%4;
    }

    public void setBoolSablier(boolean bool){
        sablier = bool;
    }
    public void paintComponent(Graphics g) {

        System.out.println("PaintComponent de PDJPyramideIA");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(1).getPyramid(), jeu.getPlayer(1).getSideSize() > 0, 1);
        StructurePainter.dessiner_side(g, height_fenetre, width_fenetre, jeu.getPlayer(1).getSide());

        if ((jeu.get_player()) == 1 && sablier)
        {
            int taille_sablier = Math.min(80 * height_fenetre / (100 * 6), 80 * width_fenetre / (100 * 6))/2;
            drawable.drawImage(images[imageIndex], width_fenetre - 2*taille_sablier, taille_sablier, taille_sablier, taille_sablier, null);
        }

        drawable.setColor(Color.GRAY);
        if(jeu.getPenality()){
            if(jeu.get_player() == 0){
                drawable.setColor(Color.RED);
            }
        }
        else if (jeu.get_player() == 1){
            drawable.setColor(Color.RED);
        }
        drawable.drawRect(0, 0, width_fenetre-1, height_fenetre-1);
        drawable.drawRect(1, 1, width_fenetre-3, height_fenetre-3);
        drawable.drawRect(2, 2, width_fenetre-5, height_fenetre-5);
        drawable.drawRect(3, 3, width_fenetre-7, height_fenetre-7);
        drawable.drawRect(4, 4, width_fenetre-9, height_fenetre-9);
        // parent.setBorder(BorderFactory.createLineBorder(drawable.getColor(),5));
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
        
        if(jeu.get_player() == 1){
            StructurePainter.Contour_Accessible_Joueur(1, jeu, g, height_fenetre, width_fenetre, jeu.getPlayer(1).getSideSize() > 0);
        }

    }
}

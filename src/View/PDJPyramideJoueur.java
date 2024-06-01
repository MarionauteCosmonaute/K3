package View;

import Model.Cube;
import Model.Jeu;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class PDJPyramideJoueur extends PDJPyramideAbstaite {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics g;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;
    static boolean cube_selec;
    int x_selec;
    int y_selec;
    static boolean charge;
    JPanel parent;
    Image gifIcon1, gifIcon2, gifIcon3, gifIcon4;
    int imageIndex = 0;
    Image[] images;
    Color playerColor;

    boolean dessineMoins1 = false;

    public PDJPyramideJoueur(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
        cube_selec = false;
    }

    // Retourne le tableau qui contient les coordonnées des points de la pyramide du
    // joueur
    public Point[][] PointPyramideJoueurs(int joueur) {
        return StructurePainter.PointPyramideJoueurs(joueur);
    }

    public Point[] PointSide() {
        return StructurePainter.PointSide();
    }

    // Retourne la taille des cubes des joueurs
    public int TailleCubeJoueur() {
        return StructurePainter.TailleCubeJoueur();
    }

    // Retourne le nombre de joueur présent dans la partie
    public int NombreDeJoueur() {
        return jeu.nbJoueur();
    }

    public int NumeroJoueur() {
        return joueur;
    }

    public int tailleSide() {
        return jeu.getPlayer(joueur).getSideSize();
    }

    public static void SetCube_Select_Static(boolean bool) {
        cube_selec = bool;
    }

    public void SetX_Select(int x) {
        x_selec = x;
    }

    public void SetY_Select(int y) {
        y_selec = y;
    }

    public void setImageIndex(int i) {
        imageIndex = i;
    }

    public void SetDessineMoins1(boolean bool)
    {
        dessineMoins1 = bool;
    }

    public Cube GetCubeChope(int x, int y, boolean side)
    {
        if(side)
        {
            return jeu.getPlayer(NumeroJoueur()).getSide(x);
        }
        else
        {
            return jeu.getPlayer(NumeroJoueur()).getPyramid().get(jeu.getPlayer(joueur).getPyramid().getSize() - 1 - x, y);
        }
    }

    public boolean GetPenality()
    {
        return jeu.getPenality();
    }

    public void paintComponent(Graphics g) {

        // System.out.println("PaintComponent de PDJPyramideJoueur");
        this.g = g;
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        if (!dessineMoins1)
        {
            StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid(),jeu.getPlayer(joueur).getSideSize() > 0, joueur);
            StructurePainter.dessiner_side(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSide());
        }
        else
        {
            System.out.println("--------------> x_select= "+x_selec);
            if( y_selec == -1)
            {
                System.out.println("--------------> y_select= "+y_selec);
                StructurePainter.dessiner_side_Moins1(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSide(), x_selec);
            }
            StructurePainter.dessiner_pyramide_moins1(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid(),jeu.getPlayer(joueur).getSideSize() > 0, joueur, 5 - x_selec, y_selec);
        }


        drawable.setColor(Color.GRAY);
        switch (joueur) {
            case 0:
                if (jeu.getPenality()) {
                    if (jeu.get_player() == 1) {
                        drawable.setColor(Color.BLUE);
                    }
                } else if (jeu.get_player() == 0) {
                    drawable.setColor(Color.BLUE);
                }
                break;
            case 1:
                if (jeu.getPenality()) {
                    if (jeu.get_player() == 0) {
                        drawable.setColor(Color.RED);
                    }
                } else if (jeu.get_player() == 1) {
                    drawable.setColor(Color.RED);
                }
                break;
        }
        // Dessine le contour de chaque joueur
        drawable.drawRect(0, 0, width_fenetre-1, height_fenetre-1);
        drawable.drawRect(1, 1, width_fenetre-3, height_fenetre-3);
        drawable.drawRect(2, 2, width_fenetre-5, height_fenetre-5);
        drawable.drawRect(3, 3, width_fenetre-7, height_fenetre-7);
        drawable.drawRect(4, 4, width_fenetre-9, height_fenetre-9);
        // playerColor=drawable.getColor();
        
        
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre / 10, width_fenetre / 10)));
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                drawable.drawString("Joueur " + (joueur + 1), 5, Math.min(height_fenetre / 10, width_fenetre / 10));
                break;

            case "EN":
                drawable.drawString("Player " + (joueur + 1), 5, Math.min(height_fenetre / 10, width_fenetre / 10));
                break;
        }

        drawable.setColor(Color.WHITE);
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre / 10, width_fenetre / 10) / 3));
        if (jeu.getPenality()) {
            if (jeu.get_player() == joueur) {
                switch (languageCode) {
                    case "FR":
                        drawable.drawString("Pénalité, votre adversaire va vous prendre un cube", 5,
                                Math.min(height_fenetre / 10, width_fenetre / 10) * 2);
                        break;
                    case "EN":
                        drawable.drawString("Penalty, your opponent will take one of your pawn", 5,
                                Math.min(height_fenetre / 10, width_fenetre / 10) * 2);
                        break;
                }
            } else {
                switch (languageCode) {
                    case "FR":
                        drawable.drawString("Pénalité, prenez un cube de votre adversaire", 5,
                                Math.min(height_fenetre / 10, width_fenetre / 10) * 2);
                        break;
                    case "EN":
                        drawable.drawString("Penalty, take one pawn from your opponent", 5,
                                Math.min(height_fenetre / 10, width_fenetre / 10) * 2);
                        break;
                }
            }
        }

        if (joueur == jeu.get_player()) {
            StructurePainter.Contour_Accessible_Joueur(joueur, jeu, g, height_fenetre, width_fenetre,
                    jeu.getPlayer(joueur).getSideSize() > 0);
            if (cube_selec) {
                StructurePainter.DessineSelectionne(joueur, jeu, drawable, height_fenetre, width_fenetre, x_selec,
                        y_selec, jeu.getPlayer(joueur).getSideSize() > 0);
            }
        }
    }

    public Color getPlayerColor(){
        return playerColor;
    }

    void build(Player player) {
        Collections.shuffle(player.getPersonalBag());
        for (int i = player.getSize() - 1; i >= 0; i--) {
            for (int j = 0; j < player.getSize() - i; j++) {
                if (!player.bagEmpty()) {
                    player.construction(j, i, player.getPersonalBag().get(0));
                }
            }
        }
    }
}

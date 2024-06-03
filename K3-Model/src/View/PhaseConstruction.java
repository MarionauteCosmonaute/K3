package View;

import Model.Jeu;
import Model.Pyramid;
import Model.Cube;
import Patterns.Observateur;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Math;
import java.net.Proxy;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PhaseConstruction
{
    JFrame frame;
    JButton Aide;
    CollecteurEvenements controle;
    Graphics2D drawable;
    Image neutre, bleu, vert, jaune, noir, blanc, rouge, vide, carre_noir_vide;
    Jeu jeu;
    int nb_couleurs[];

    Point tab_pts[][];
    int taille_cube_pyr;
    Point case_select;
    // ArrayList<Point> tab_cote[];
    Point tab_cote[][];
    int taille_cube_pioche;

    Point cube_pioche;
    int couleur_selectionnee;
    Cube cube_a_mettre;

    boolean cube_sel;

    public boolean pioche;
    // boolean clic_centre;


    // Point cube_echange1, cube_echange2;
    // boolean cube1_sel, cube2_sel;
    int cpt;
    

    public PhaseConstruction(JFrame frame, CollecteurEvenements controle, Jeu jeu){
        this.frame = frame;
        this.controle = controle;
        this.jeu = jeu;
        try {
			InputStream in = new FileInputStream("res/carre_bois.png");
			neutre = ImageIO.read(in);
            in = new FileInputStream("res/carre_bleu.png");
			bleu = ImageIO.read(in);
            in = new FileInputStream("res/carre_vert.png");
			vert = ImageIO.read(in);
            in = new FileInputStream("res/carre_jaune.png");
			jaune = ImageIO.read(in);
            in = new FileInputStream("res/carre_noir.png");
			noir = ImageIO.read(in);
            in = new FileInputStream("res/carre_blanc.png");
			blanc = ImageIO.read(in);
            in = new FileInputStream("res/carre_rouge.png");
			rouge = ImageIO.read(in);
            in = new FileInputStream("res/carre_vide.png");
			vide = ImageIO.read(in);
            in = new FileInputStream("res/carre_noir_vide.png");
			carre_noir_vide = ImageIO.read(in);

		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : impossible de trouver le fichier du pousseur");
			System.exit(2);
		} catch (IOException e) {
			System.err.println("ERREUR : impossible de charger l'image");
			System.exit(3);
		}

        while(jeu.draw())
        {
        }

        cpt = 0;
        tab_pts = new Point[6][6]; //a adapter selon le nombre de joueurs
        tab_cote = new Point[7][9]; //tout le temps (7 couleurs et 9 de chaque)
        cube_pioche = new Point(-1, -1);
        case_select = new Point(-1, -1);
        couleur_selectionnee = -1;
        pioche = false;
        cube_sel = false;
        // cube1_sel = false;
        // cube2_sel = false;
        // clic_centre = false;
    }

    public Point[][] points_pyr(){
        return tab_pts;
    }

    public int tailleCubePyramide(){
        return taille_cube_pyr;
    }

    public Point[][] points_pioche(){
        return tab_cote;
    }

    public int tailleCubePioche(){
        return taille_cube_pioche;
    }

    public int[] couleurs(){
        return nb_couleurs;
    }

    public void modifierLignePioche(Point p){
        cube_pioche = new Point((int)p.getX(), (int)p.getY());
    }

    public void setPoint(Point p){
        case_select = new Point((int)p.getX(), (int)p.getY());
    }

    public boolean peut_cliquer_pyramide(){
        return cube_sel; // cube_sel || pas premier tour (pour pouvoir echanger deux cases)
    }

    public void set_cube_sel(boolean bool){
        cube_sel = bool;
    }

    /*
    public boolean cube1_selectionne(){
        return cube1_sel;
    }

    public void setCube1(Point p){
        cube_echange1 = p;
    }

    public void setCube2(Point p){
        cube_echange2 = p;
    }

    public void set_cube1_sel(boolean bool){
        cube1_sel = bool;
    }

    public void set_cube2_sel(boolean bool){
        cube2_sel = bool;
    }
    */

    // public void setClicCentre(boolean clic){
    //     setClicCentre(clic);
    // }

    public void fonction_globale(Jeu jeu, Graphics g, int width_fenetre, int height_fenetre){
        Pyramid pyr = jeu.getPlayer(0).getPyramid();
        
        dessiner_pyramide_centrale(g, width_fenetre, height_fenetre);
        dessiner_pyramide_joueur(g, width_fenetre, height_fenetre);
        dessiner_cubes_pioches(g, width_fenetre, height_fenetre);

        
    }

    public int max_nb(int[] tab){
        int max = tab[0];
        for (int i = 0; i<7; i++){
            if (tab[i] > max){
                max = tab[i];
            }
        }
        return max;
    }

    public void dessiner_cubes_pioches(Graphics g, int width_fenetre, int height_fenetre){
        nb_couleurs = jeu.compte_personal_bag();
        drawable = (Graphics2D) g;
        int debut_zone_haut = height_fenetre / 10;

        int fin_zone_gauche = (int)(0.95 * width_fenetre);
        
        
        int nb;

        int taille_cube = 9*width_fenetre / (4*9*10);
        taille_cube_pioche = taille_cube;
        int y=0;
        Point p;
        int x_haut, y_haut;
        int c_x = ((int)cube_pioche.getX());
        int c_y = ((int)cube_pioche.getY());
        int x_cube_vide = 0;
        int y_cube_vide = 0;
        Cube cube = Cube.Vide;
        for (int i=0; i<7; i++){
            nb = nb_couleurs[i];
            if (nb > 0){
                y++;
            }
            for(int x=0; x<nb; x++){
                
                x_haut = fin_zone_gauche - x*(taille_cube + taille_cube/10);
                y_haut = y*(taille_cube + taille_cube/10) + debut_zone_haut;
                p = new Point(x_haut, y_haut);

                tab_cote[i][x] = p;
                
                switch(i){
                    case 0:
                        drawable.drawImage(noir, x_haut, y_haut, taille_cube, taille_cube, null);
                        cube = Cube.Noir;
                        break;
                    case 1:
                        drawable.drawImage(neutre, x_haut, y_haut, taille_cube, taille_cube, null);                    
                        cube = Cube.Neutre;
                        break;
                    case 2:
                        drawable.drawImage(blanc, x_haut, y_haut, taille_cube, taille_cube, null);   
                        cube = Cube.Blanc;
                        break;
                    case 3:
                        drawable.drawImage(vert, x_haut, y_haut, taille_cube, taille_cube, null);                      
                        cube = Cube.Vert;
                        break;
                    case 4:
                        drawable.drawImage(jaune, x_haut, y_haut, taille_cube, taille_cube, null);                     
                        cube = Cube.Jaune;
                        break;
                    case 5:
                        drawable.drawImage(rouge, x_haut, y_haut, taille_cube, taille_cube, null);                   
                        cube = Cube.Rouge;
                        break;
                    case 6:
                        drawable.drawImage(bleu, x_haut, y_haut, taille_cube, taille_cube, null);                    
                        cube = Cube.Bleu;
                        break;
                }
                if (x == c_y && i == c_x){
                    x_cube_vide = fin_zone_gauche - (nb_couleurs[i] -1 -x)*(taille_cube + taille_cube/10);
                    y_cube_vide = y_haut;
                    couleur_selectionnee = i;
                    cube_a_mettre = cube;
                    // System.out.println("i :" + i);
                    
                }
                
            }   
        }
        
        if(pioche){
            System.out.println("pioche " + cpt);
            cpt++;
            drawable.drawImage(carre_noir_vide, x_cube_vide, y_cube_vide, taille_cube, taille_cube, null);
            pioche = false;
        }
        
        
    }

    public void dessiner_pyramide_centrale(Graphics g, int width_fenetre, int height_fenetre){
        drawable = (Graphics2D) g;
        int taille_cube = height_fenetre/12;
        int debut_zone_haut = height_fenetre * 85/100;
        int taille_pyramide = (taille_cube+taille_cube/10)*9;
        int taille_zone = width_fenetre*3/4;
        int debut_zone_gauche = (taille_zone - taille_pyramide) / 2;

        for (int i=0; i<9; i++){
            switch(jeu.principale.get(0, i)){
                case Noir:
                // System.out.println("noir");
                    drawable.drawImage(noir, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Bleu:
                // System.out.println("bleu");
                    drawable.drawImage(bleu, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Blanc:
                // System.out.println("blanc");
                    drawable.drawImage(blanc, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Rouge:
                // System.out.println("rouge");
                    drawable.drawImage(rouge, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Jaune:
                // System.out.println("jaune");
                    drawable.drawImage(jaune, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Vert:
                // System.out.println("vert");
                    drawable.drawImage(vert, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Neutre:
                // System.out.println("neutre");
                    drawable.drawImage(neutre, debut_zone_gauche+(taille_cube+taille_cube/10)*i, debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                default:
                    break;
            }
        }
    }

    public void dessiner_pyramide_joueur(Graphics g, int width_fenetre, int height_fenetre){
        // if(cube1_sel){
        //     System.out.println(cube_echange1.getX() + " " + cube_echange1.getY());
        //     System.out.println(cube_echange2.getX() + " " + cube_echange2.getY());
        // }
        
        drawable = (Graphics2D) g;
        int taille_cube = height_fenetre/12;
        int debut_zone_bas = height_fenetre*7/10;
        int debut_zone_haut = height_fenetre/10;
        int taille_pyramide = (taille_cube+taille_cube/10)*6;
        int taille_zone = width_fenetre*3/4;
        int debut_zone_gauche = (taille_zone - taille_pyramide) / 4;
        int x_haut, y_haut;

        taille_cube_pyr = taille_cube;
        Point p;
        Cube cube;
        int xx,yy;
        if(case_select.getX() >= 0 && case_select.getY() >= 0){
            xx = (int)case_select.getX();
            yy = (int)case_select.getY();
            jeu.construction(xx, yy, cube_a_mettre);
            cube_a_mettre = Cube.Vide;
            case_select.setLocation(-1, -1);
            nb_couleurs = jeu.compte_personal_bag();

        }
        // if (couleur_selectionnee != -1){
        //     System.out.println("----");
        // }
        
        for (int x=0; x<6; x++){
            for (int y = 0; y<=x; y++){
                cube = jeu.getPlayer().get(5-x, x-y);
                
                x_haut = debut_zone_haut + x*(taille_cube + taille_cube/10);
                y_haut = debut_zone_gauche + x*(taille_cube + taille_cube/10)/2 + (taille_cube + taille_cube/10)*(6-y);
                p = new Point(y_haut, x_haut);
                tab_pts[x][y] = p;

                switch (cube) {
                    case Noir:
                        drawable.drawImage(noir, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vide:
                        drawable.drawImage(vide, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    default:
                        break;
                }
                
            }
        }
    }
   
}
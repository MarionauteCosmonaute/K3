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
    Point tab_pioche[];
    int taille_cube_pioche;

    Point cube_pioche;
    int couleur_selectionnee;
    boolean cube_sel;

    boolean pioche;

    int cpt;

    int taille_cube;
    int emplacement;
    boolean dessiner_vide;
    

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
        cube_pioche = new Point(-1, -1);
        couleur_selectionnee = -1;
        pioche = false;
        cube_sel = false;

        dessiner_vide = false;
        tab_pioche = new Point[21];
    }

    public Point[][] points_pyr(){
        return tab_pts;
    }

    public int tailleCube(){
        return taille_cube;
    }

    public Point[] pointsPioche2(){
        return tab_pioche;
    }

    public int[] couleurs(){
        return nb_couleurs;
    }

    public void modifierLignePioche(Point p){
        cube_pioche = new Point((int)p.getX(), (int)p.getY());
    }

    public void modifierPioche(int emplacement){
        this.emplacement = emplacement;
        dessiner_vide = true;
        
    }

    public void setDessinVideFalse(){
        dessiner_vide = false;
    }

    public boolean peut_cliquer_pyramide(){
        return cube_sel; // cube_sel || pas premier tour (pour pouvoir echanger deux cases)
    }

    public void set_cube_sel(boolean bool){
        cube_sel = bool;
    }

    public void fonction_globale(Jeu jeu, Graphics g, int width_fenetre, int height_fenetre){
        
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

    public int couleur_case(int emplacement, int[] couleurs){
        int somme = 0;
        for(int i=0; i<7; i++){
            somme += couleurs[i];
            if(emplacement <= somme){
                return i;
            }
        }
        return somme; //juste parce qu'il faut renvoyer un int dans tous les cas
    }

    public void dessiner_cubes_pioches(Graphics g, int width_fenetre, int height_fenetre){
        nb_couleurs = jeu.compte_personal_bag();
        drawable = (Graphics2D) g;
        int espace = taille_cube/10;
        int debut_zone_haut = height_fenetre *7 / 10;
        int hauteur_utilisee = taille_cube*3 + 2*espace;
        int largeur_utilisee = taille_cube*7 + 6*espace;

        int y_haut = debut_zone_haut + (height_fenetre - debut_zone_haut - hauteur_utilisee)/2;
        int x_gauche = (width_fenetre - largeur_utilisee)/2;
        
        int somme = 0;
        for(int i=0; i<7; i++){
            somme += nb_couleurs[i];
        }

        int x, y;
        Point p;
        int couleur;
        int ligne, col;

        for(int i=0; i<somme; i++){
            ligne = i/7;
            col = i % 7;
            if (somme >= i+1){
                x = x_gauche + col*(taille_cube+espace);
                y = y_haut + ligne*(taille_cube+espace);
                couleur = couleur_case(i+1, nb_couleurs);
                p = new Point(x, y);
                tab_pioche[i] = p;
                switch(couleur){
                    case 0:
                        drawable.drawImage(noir, x, y, taille_cube, taille_cube, null);
                        break;
                    case 1:
                        drawable.drawImage(neutre, x, y, taille_cube, taille_cube, null);                    
                        break;
                    case 2:
                        drawable.drawImage(blanc, x, y, taille_cube, taille_cube, null); 
                        break;
                    case 3:
                        drawable.drawImage(vert, x, y, taille_cube, taille_cube, null);                    
                        break;
                    case 4:
                        drawable.drawImage(jaune, x, y, taille_cube, taille_cube, null);       
                        break;
                    case 5:
                        drawable.drawImage(rouge, x, y, taille_cube, taille_cube, null);                  
                        break;
                    case 6:
                        drawable.drawImage(bleu, x, y, taille_cube, taille_cube, null);           
                        break;
                }
                
            }
        }
        if (dessiner_vide){
           int x_selection = x_gauche + (emplacement % 7)*(taille_cube+espace);
            int y_selection = y_haut + (emplacement / 7)*(taille_cube+espace);
            drawable.drawImage(carre_noir_vide, x_selection, y_selection, taille_cube, taille_cube, null); 
        }
        
    }

   

    public void dessiner_pyramide_centrale(Graphics g, int width_fenetre, int height_fenetre){
        drawable = (Graphics2D) g;
        taille_cube = this.taille_cube*2/3;
        int debut_zone_haut = height_fenetre * 2/10;
        int espace = taille_cube/10;
        int taille_pyramide = taille_cube*9 + espace*8;
        int debut_zone_gauche = width_fenetre - width_fenetre/10 - taille_pyramide;
        Cube c;
        for(int col = 0; col<9; col++){
            
            c = jeu.getCubePrincipale(0, col);
            switch (c) {
                case Noir:
                    drawable.drawImage(bleu, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Neutre:
                    drawable.drawImage(neutre, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Blanc:
                    drawable.drawImage(blanc, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Vert:
                    drawable.drawImage(vert, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Jaune:
                    drawable.drawImage(jaune, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Rouge:
                    drawable.drawImage(rouge, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Bleu:
                    drawable.drawImage(bleu, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                case Vide:
                    drawable.drawImage(vide, debut_zone_gauche + col*(taille_cube+espace), debut_zone_haut, taille_cube, taille_cube, null);
                    break;
                default:
                    break;
            }
        }
    }

    public void dessiner_pyramide_joueur(Graphics g, int width_fenetre, int height_fenetre){
        drawable = (Graphics2D) g;
        taille_cube = height_fenetre/12;
        int debut_zone_haut = height_fenetre/10;
        int debut_zone_gauche = width_fenetre*1/5;
        int x_haut, y_haut;

        Point p;
        Cube cube;
        for(int x = 0; x<6; x++){
            for(int y = 0; y<(6-x); y++){
                cube = jeu.getPlayer().get(x, y);
                
                x_haut = debut_zone_haut + (5-x)*(taille_cube + taille_cube/10);
                y_haut = debut_zone_gauche + x*(taille_cube + taille_cube/10)/2 + (taille_cube + taille_cube/10)*(y);
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
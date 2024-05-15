package View;

import Model.Jeu;
import Model.Pyramid;
import Model.Cube;
import Patterns.Observateur;
import Global.FileLoader;

import javax.swing.*;
import java.awt.*;

public class PDJPyramideCentrale extends JComponent implements Observateur
{
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    JFrame frame;
    Graphics2D drawable;
    Jeu jeu;
    Image neutre, bleu, vert, jaune, noir, blanc, rouge;
    JPanel parent;


    PDJPyramideCentrale(Jeu jeu, JPanel parent)
    {
        this.jeu = jeu;
        this.parent = parent;
        try
        {
            neutre = FileLoader.getImage("res/carre_bois.png");
            bleu = FileLoader.getImage("res/carre_bleu.png");
            vert = FileLoader.getImage("res/carre_vert.png");
            jaune = FileLoader.getImage("res/carre_jaune.png");
            noir = FileLoader.getImage("res/carre_noir.png");
            blanc = FileLoader.getImage("res/carre_blanc.png");
            rouge = FileLoader.getImage("res/carre_rouge.png");
            
        }
        catch(Exception e)
        {
            System.exit(1);
        }
    }

    @Override
	public void miseAJour()
    {
		repaint();
	}

    public void dessiner_pyramide(Graphics g, int height, int width, Pyramid pyramide)
    {
        System.out.println("dessinerPyramide de PDJPyramideCentrale");
        drawable = (Graphics2D) g;
        int taille_pyramide = pyramide.getSize();
        int taille_cube = Math.min(80*height/(100*taille_pyramide), 80*width/(100*taille_pyramide));


        int x_haut, y_haut;

        Cube cube;
        for (int x = 0; x < taille_pyramide; x++){
            x_haut = height*10/100 + taille_cube*x;
            for (int y = 0; y <= x; y++){
                cube = pyramide.get(taille_pyramide-1-x, x-y);
                y_haut = width/2 - (taille_cube/2)*x+taille_cube*y ;
                

                System.out.println("x_haut "+x_haut);
                System.out.println("y_haut "+y_haut);
                System.out.println("taille_cube " + taille_cube);
                System.out.println("height "+height);
                System.out.println("width "+width);

                switch (cube) {
                    case Noir:
                        System.out.println("cube noir");
                        drawable.drawImage(noir, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        System.out.println("cube neutre");
                        drawable.drawImage(neutre, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        System.out.println("cube blanc");
                        drawable.drawImage(blanc, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        System.out.println("cube vert");
                        drawable.drawImage(vert, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        System.out.println("cube jaune");
                        drawable.drawImage(jaune, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        System.out.println("cube rouge");
                        drawable.drawImage(rouge, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        System.out.println("cube bleu");
                        drawable.drawImage(bleu, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    default:
                        System.out.println("default");
                        break;
                }
                
            }
        }
    }


    public void paintComponent(Graphics g)
    {
        int joueur=0;
        
        while(jeu.draw())
        {
        }
        jeu.build(joueur);
        System.out.println("PaintComponent de PDJPyramideCentrale");
	    drawable = (Graphics2D) g;
		width_fenetre = parent.getWidth();;
		height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
		drawable.clearRect(0, 0, width_fenetre, height_fenetre);
        System.out.println("width_fenetre "+width_fenetre);
        System.out.println("height_fenetre "+height_fenetre);
        // System.exit(1);
        drawable.setColor(Color.BLACK);
        // drawable.fillRect(0, 0, width_fenetre, height_fenetre);
        dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid());
    }
}

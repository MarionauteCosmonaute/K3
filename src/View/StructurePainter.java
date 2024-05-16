package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import Global.FileLoader;
import Model.Cube;
import Model.Pyramid;

public class StructurePainter {

    static Image neutre, bleu, vert, jaune, noir, blanc, rouge,access;
    static Boolean inititalised=false;

    public static void init(){
        if (!inititalised){
            try{
                neutre = FileLoader.getImage("res/carre_bois.png");
                bleu = FileLoader.getImage("res/carre_bleu.png");
                vert = FileLoader.getImage("res/carre_vert.png");
                jaune = FileLoader.getImage("res/carre_jaune.png");
                noir = FileLoader.getImage("res/carre_noir.png");
                blanc = FileLoader.getImage("res/carre_blanc.png");
                rouge = FileLoader.getImage("res/carre_rouge.png");
                access = FileLoader.getImage("res/carre_noir_vide.png");
                inititalised=true;
            }catch (Exception e){
                System.exit(1);
            }
        }
    }

    public static void dessiner_pyramide(Graphics g, int height, int width, Pyramid pyramide)
    {
        //System.out.println("dessinerPyramide de PDJPyramideCentrale");
        Graphics2D drawable = (Graphics2D) g;
        int taille_pyramide = pyramide.getSize();
        int taille_cube = Math.min(80*height/(100*taille_pyramide), 80*width/(100*taille_pyramide));
        int espace = taille_cube/10;

        int x_haut, y_haut;

        Cube cube;
        for (int x = taille_pyramide-1; x >= 0; x--){
            x_haut =height/2 -(taille_cube/2)*(taille_pyramide) + taille_cube*x -(espace*taille_pyramide)/2 ;
            for (int y = 0; y <= x; y++){
                cube = pyramide.get(taille_pyramide-1-x, x-y);
                y_haut = width/2 - (taille_cube/2)*(x+1)+taille_cube*y - (espace*x)/2 ;
                

                System.out.println("x_haut "+x_haut);
                System.out.println("y_haut "+y_haut);
                System.out.println("taille_cube " + taille_cube);
                System.out.println("height "+height);
                System.out.println("width "+width);

                switch (cube) {
                    case Noir:
                        System.out.println("cube noir");
                        drawable.drawImage(noir, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        System.out.println("cube neutre");
                        drawable.drawImage(neutre, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        System.out.println("cube blanc");
                        drawable.drawImage(blanc, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        System.out.println("cube vert");
                        drawable.drawImage(vert, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        System.out.println("cube jaune");
                        drawable.drawImage(jaune, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        System.out.println("cube rouge");
                        drawable.drawImage(rouge, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        System.out.println("cube bleu");
                        drawable.drawImage(bleu, y_haut+espace*y, x_haut+espace*x, taille_cube, taille_cube, null);
                        break;
                    default:
                        System.out.println("default");
                        break;
                }
                
            }
        }
        drawable.drawRect(width/2, 0, 0, height);
        drawable.drawRect(0, height/2, width, 0);
    }
    
}

package View;

import java.awt.*;
import java.util.ArrayList;

import Global.FileLoader;
import Model.Cube;
import Model.Pyramid;
import Model.Jeu;

public class StructurePainter {

    static Image neutre, bleu, vert, jaune, noir, blanc, rouge, access, vide;
    static Boolean inititalised = false;
    static Point points_pyramide_joueur1[][];
    static Point points_pyramide_joueur2[][];
    static Point points_pyramide_centrale[][];
    static Point points_side[];
    static int taille_cube_joueur;
    static int taille_cube_pyramide_centrale;
    static Point blanc_accessible = new Point(-10,-10);

    public static void init() {
        if (!inititalised) {
            try {
                neutre = FileLoader.getImage("res/neutre2.png");
                bleu = FileLoader.getImage("res/bleu.png");
                vert = FileLoader.getImage("res/vert.png");
                jaune = FileLoader.getImage("res/jaune.png");
                noir = FileLoader.getImage("res/violet.png");
                blanc = FileLoader.getImage("res/ange.png");
                rouge = FileLoader.getImage("res/rouge.png");
                vide = FileLoader.getImage("res/carre_vide.png");
                inititalised = true;
            } catch (Exception e) {
                System.exit(1);
            }
            points_pyramide_joueur1 = new Point[6][6];
            points_pyramide_joueur2 = new Point[6][6];
            points_pyramide_centrale = new Point[9][9];
            points_side  = new Point[6];
        }
    }

    public static void dessiner_pyramide(Graphics g, int height, int width, Pyramid pyramide, boolean side, int joueur) {

        // System.out.println("dessinerPyramide de PDJPyramideCentrale");
        Graphics2D drawable = (Graphics2D) g;
        int taille_pyramide = pyramide.getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        int x_haut, y_haut;

        x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (taille_pyramide -1) - (espace * taille_pyramide) / 2;
        y_haut = width / 2 - (taille_cube / 2) * ((taille_pyramide-1) + 1) + taille_cube * -2 - (espace * (taille_pyramide -1)) / 2;
        blanc_accessible.x = x_haut + espace * (taille_pyramide -1);
        blanc_accessible.y = y_haut + espace * -2;

        Cube cube;
        for (int x = taille_pyramide - 1; x >= 0; x--) {
            x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * x
                    - (espace * taille_pyramide) / 2;
            for (int y = 0; y <= x; y++) {
                cube = pyramide.get(taille_pyramide - 1 - x, y);
                y_haut = (width / 2 - (taille_cube / 2) * (x + 1) + taille_cube * y - (espace * x) / 2);
                if(side){
                    y_haut -= 2*taille_cube;
                }
                // On complète notre tableau de points uniquement si c'est la pyramide joueur
                if(taille_pyramide < 9){
                    if(joueur == 0){
                        points_pyramide_joueur1[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                    }
                    else{
                        points_pyramide_joueur2[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                    }
                    taille_cube_joueur = taille_cube; 
                }
                else{
                    points_pyramide_centrale[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                    taille_cube_pyramide_centrale = taille_cube; 
                }
                switch (cube) {
                    case Noir:
                        // System.out.println("cube noir");
                        drawable.drawImage(noir, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Neutre:
                        // System.out.println("cube neutre");
                        drawable.drawImage(neutre, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Blanc:
                        // System.out.println("cube blanc");
                        drawable.drawImage(blanc, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Vert:
                        // System.out.println("cube vert");
                        drawable.drawImage(vert, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Jaune:
                        // System.out.println("cube jaune");
                        drawable.drawImage(jaune, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Rouge:
                        // System.out.println("cube rouge");
                        drawable.drawImage(rouge, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Bleu:
                        // System.out.println("cube bleu");
                        drawable.drawImage(bleu, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    default:
                        // System.out.println("default");
                        break;
                }
            }
        }
    }

    public static void dessiner_side(Graphics g, int height, int width,  ArrayList<Cube> side) {
        Graphics2D drawable = (Graphics2D) g;
        int taille_cube = taille_cube_joueur;
        int espace = taille_cube / 10;
        int x_haut = 0, y_haut = 0;
        Cube cube;
        int taille_side = side.size(); //a revoir pour utiliser la fonction getSideSize mais elle est dans player

        
        // y_haut = ((width *5)/6 - (taille_cube / 2) * (x + 1) + taille_cube * y - (espace * x) / 2);
        y_haut = width/2 + 3*taille_cube_joueur;
        for(int x = 0; x < taille_side; x++){
            x_haut = height / 2 - (taille_cube / 2) * 5 + taille_cube * (5-x)
                    - (espace * 6) / 2;
            cube = side.get(x);
            points_side[x] = (new Point(y_haut, x_haut - espace * x));
            switch (cube) {
                case Noir:
                    drawable.drawImage(noir, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Neutre:
                    drawable.drawImage(neutre, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Blanc:
                    drawable.drawImage(blanc, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Vert:
                    drawable.drawImage(vert, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Jaune:
                    drawable.drawImage(jaune, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Rouge:
                    drawable.drawImage(rouge, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                case Bleu:
                    drawable.drawImage(bleu, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                            null);
                    break;
                default:
                    break;
            }
        }
    }

    public static void dessiner_side_Moins1(Graphics g, int height, int width, ArrayList<Cube> side, int x1) {
        Graphics2D drawable = (Graphics2D) g;
        int taille_cube = taille_cube_joueur;
        int espace = taille_cube / 10;
        int x_haut = 0, y_haut = 0;
        Cube cube;
        int taille_side = side.size(); //a revoir pour utiliser la fonction getSideSize mais elle est dans player

        
        // y_haut = ((width *5)/6 - (taille_cube / 2) * (x + 1) + taille_cube * y - (espace * x) / 2);
        y_haut = width/2 + 3*taille_cube_joueur;
        for(int x = 0; x < taille_side; x++){
            x_haut = height / 2 - (taille_cube / 2) * 5 + taille_cube * (5-x)
                    - (espace * 6) / 2;
            cube = side.get(x);
            points_side[x] = (new Point(y_haut, x_haut - espace * x));
            if (x != x1)
            {
                switch (cube) {
                    case Noir:
                        drawable.drawImage(noir, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, y_haut, x_haut - espace * x, taille_cube, taille_cube,
                                null);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void dessiner_pyramide_moins1(Graphics g, int height, int width, Pyramid pyramide, boolean side, int joueur, int x1, int y1) {
        System.out.println("StructurePainter dessiner_pyramide_moins1");
        Graphics2D drawable = (Graphics2D) g;
        int taille_pyramide = pyramide.getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        int x_haut, y_haut;

        Cube cube;
        for (int x = taille_pyramide - 1; x >= 0; x--) {
            x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * x
                    - (espace * taille_pyramide) / 2;
            for (int y = 0; y <= x; y++)
            {
                if (x1 != x || y1 != y)
                {
                    cube = pyramide.get(taille_pyramide - 1 - x, y);
                    y_haut = (width / 2 - (taille_cube / 2) * (x + 1) + taille_cube * y - (espace * x) / 2);
                    if(side){
                        y_haut -= 2*taille_cube;
                    }
                    // On complète notre tableau de points uniquement si c'est la pyramide joueur
                    if(taille_pyramide < 9){
                        if(joueur == 0){
                            points_pyramide_joueur1[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                        }
                        else{
                            points_pyramide_joueur2[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                        }
                        taille_cube_joueur = taille_cube; 
                    }
                    else{
                        points_pyramide_centrale[x][y] = new Point(y_haut + espace * y, x_haut + espace * x);
                        taille_cube_pyramide_centrale = taille_cube; 
                    }
                    switch (cube) {
                        case Noir:
                            // System.out.println("cube noir");
                            drawable.drawImage(noir, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Neutre:
                            // System.out.println("cube neutre");
                            drawable.drawImage(neutre, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Blanc:
                            // System.out.println("cube blanc");
                            drawable.drawImage(blanc, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Vert:
                            // System.out.println("cube vert");
                            drawable.drawImage(vert, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Jaune:
                            // System.out.println("cube jaune");
                            drawable.drawImage(jaune, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Rouge:
                            // System.out.println("cube rouge");
                            drawable.drawImage(rouge, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        case Bleu:
                            // System.out.println("cube bleu");
                            drawable.drawImage(bleu, y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube,
                                    null);
                            break;
                        default:
                            // System.out.println("default");
                            break;
                    }
                }
            }
        }
    }


    public static void DessineAccessible(Graphics g, int ligne, int colonne,int height, int width, Jeu jeu)
    {
        Graphics2D drawable = (Graphics2D) g;
        int x_haut, y_haut;
        int taille_pyramide = jeu.getPrincipale().getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        ArrayList<Point> Listeaccessible = jeu.CubeAccessibleDestinations(ligne,colonne);

        // On gère le cas du cube blanc
        if(Listeaccessible.size() == 0){
            return; //rien à dessiner
        }
        if (Listeaccessible.get(0).x == -1 && Listeaccessible.get(0).y == -1)
        {
            x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (taille_pyramide -1) - (espace * taille_pyramide) / 2;
            y_haut = width / 2 - (taille_cube / 2) * ((taille_pyramide-1) + 1) + taille_cube * -2 - (espace * (taille_pyramide -1)) / 2;
            drawable.setColor(Color.WHITE);
            drawable.drawRect(y_haut + espace * -2, x_haut + espace * (taille_pyramide -1), taille_cube, taille_cube);
            drawable.drawRect(y_haut + espace * -2 + 1, x_haut + espace * (taille_pyramide -1) + 1, taille_cube - 2, taille_cube - 2);
            // blanc_accessible.x = x_haut + espace * (taille_pyramide -1);
            // blanc_accessible.y = y_haut + espace * -2;
            return;
        }
        
        Point p;
        for (int x = taille_pyramide - 1; x >= 0; x--)
        {
            x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * x - (espace * taille_pyramide) / 2;
            for (int y = 0; y <= x; y++)
            {
                y_haut = width / 2 - (taille_cube / 2) * (x + 1) + taille_cube * y - (espace * x) / 2;
                p = new Point(taille_pyramide - 1 - x, y);
                if(Listeaccessible.contains(p))
                {
                    // if (jeu.move_validity(jeu.getPlayer().get(ligne, colonne), taille_pyramide - 1 - x, y) == 3)

                    if (jeu.move_validity(jeu.getPlayer().get(ligne, colonne), taille_pyramide - 1 - x, y) == 2){ //
                        drawable.setColor(Color.RED);
                    }
                    else
                    {
                        drawable.setColor(Color.WHITE);
                    }
                    drawable.drawRect(y_haut + espace * y, x_haut + espace * x, taille_cube, taille_cube);
                    drawable.drawRect(y_haut + espace * y + 1, x_haut + espace * x + 1, taille_cube - 2, taille_cube - 2);
                }
            }
        }

    }

    public static Point[][] PointPyramideJoueurs(int joueur){
        if(joueur == 0){
            return points_pyramide_joueur1;
        }
        return points_pyramide_joueur2;
    }

    public static Point[][] PointPyramideCentrale(){
        return points_pyramide_centrale;
    }

    public static Point[] PointSide(){
        return points_side;
    }

    public static int TailleCubeJoueur(){
        return taille_cube_joueur;
    }

    public static Point GetBlancAccessible()
    {
        return blanc_accessible;
    }

    public static void Contour_Accessible_Joueur(int num_joueur, Jeu jeu, Graphics g, int height, int width, boolean side)
    {
        // On clacul les proportions de la pyramide
        Graphics2D drawable = (Graphics2D) g;
        int taille_pyramide = jeu.getPyrPlayer(num_joueur).getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;
        int x_haut, y_haut;
        ArrayList<Point> ListePoints;
        // On dessine la "sur-brillance"
        int taille_side = jeu.getPlayer().getSideSize();
        if(jeu.getPenality()){ // tester recupération pénalité
            ListePoints = jeu.AccessibleCubesPlayer(num_joueur); //highlight tout le side quand il y a une pénalité
            for(int x = 0; x<taille_side; x++){
                y_haut = width/2 + 3*taille_cube_joueur;
                x_haut = height / 2 - (taille_cube / 2) * 5 + taille_cube * (5-x)
                    - (espace * 6) / 2;

                drawable.setColor(Color.YELLOW);

                drawable.drawRect(y_haut, x_haut - espace * x, taille_cube, taille_cube);
                drawable.drawRect(y_haut+1, x_haut - espace * x + 1, taille_cube - 2, taille_cube - 2);

                
                drawable.drawRect(y_haut+2, x_haut - espace * x + 2, taille_cube - 4, taille_cube - 4);
                drawable.setColor(Color.BLACK);

                drawable.drawRect(y_haut+3, x_haut - espace * x + 3, taille_cube - 6, taille_cube - 6);

                drawable.drawRect(y_haut+4, x_haut - espace * x + 4, taille_cube - 8, taille_cube - 8);
                drawable.drawRect(y_haut+5, x_haut - espace * x + 5, taille_cube - 10, taille_cube - 10);
            }
        }
        else{
            ListePoints = jeu.Accessible_Playable(num_joueur);
        }
        for(Point p : ListePoints){
            // y_haut + espace * p.y, x_haut + espace * p.x

            /*
            //si on veut ne pas avoir à recalculer, mais à vérifier pour les autres points
            x_haut = points_pyramide_joueur1[5-p.x][p.y].y;
            y_haut = points_pyramide_joueur1[5-p.x][p.y].x;
            */
            if(p.y != -1){
                x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (5-p.x)
                        - (espace * taille_pyramide) / 2;
                y_haut = width / 2 - (taille_cube / 2) * ((5-p.x) + 1) + taille_cube * p.y - (espace * (5-p.x)) / 2;  
                if(side){
                    y_haut -= 2*taille_cube;
                }
                
                drawable.setColor(Color.YELLOW);

                drawable.drawRect(y_haut + espace * p.y, x_haut + espace * (5-p.x), taille_cube, taille_cube);
                drawable.drawRect(y_haut + espace * p.y + 1, x_haut + espace * (5-p.x) + 1, taille_cube - 2, taille_cube - 2);

                
                drawable.drawRect(y_haut + espace * p.y + 2, x_haut + espace * (5-p.x) + 2, taille_cube - 4, taille_cube - 4);
                drawable.setColor(Color.BLACK);

                drawable.drawRect(y_haut + espace * p.y + 3, x_haut + espace * (5-p.x) + 3, taille_cube - 6, taille_cube - 6);

                drawable.drawRect(y_haut + espace * p.y + 4, x_haut + espace * (5-p.x) + 4, taille_cube - 8, taille_cube - 8);
                drawable.drawRect(y_haut + espace * p.y + 5, x_haut + espace * (5-p.x) + 5, taille_cube - 10, taille_cube - 10);
            }
            else{
                y_haut = width/2 + 3*taille_cube_joueur;
                x_haut = height / 2 - (taille_cube / 2) * 5 + taille_cube * (5-p.x)
                    - (espace * 6) / 2;

                drawable.setColor(Color.YELLOW);

                drawable.drawRect(y_haut, x_haut - espace * p.x, taille_cube, taille_cube);
                drawable.drawRect(y_haut+1, x_haut - espace * p.x + 1, taille_cube - 2, taille_cube - 2);

                
                drawable.drawRect(y_haut+2, x_haut - espace * p.x + 2, taille_cube - 4, taille_cube - 4);
                drawable.setColor(Color.BLACK);

                drawable.drawRect(y_haut+3, x_haut - espace * p.x + 3, taille_cube - 6, taille_cube - 6);

                drawable.drawRect(y_haut+4, x_haut - espace * p.x + 4, taille_cube - 8, taille_cube - 8);
                drawable.drawRect(y_haut+5, x_haut - espace * p.x + 5, taille_cube - 10, taille_cube - 10);
            }
        }
    }


    public static void DessineSelectionne(int num_joueur, Jeu jeu, Graphics2D drawable, int height, int width, int x, int y, boolean side)
    {
        //System.out.println("StructurePainter de DessineSelectionne");
        int taille_pyramide = jeu.getPyrPlayer(num_joueur).getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        // drawable.setColor(Color.ORANGE);
        // drawable.drawRect(y_haut + espace * y, x_haut + espace * x, taille_cube_joueur, taille_cube_joueur);
        // drawable.drawRect(y_haut + espace * y + 1, x_haut + espace * x + 1, taille_cube_joueur - 2, taille_cube_joueur - 2);

        int x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (5-x)
        - (espace * taille_pyramide) / 2;
        int y_haut = width / 2 - (taille_cube / 2) * ((5-x) + 1) + taille_cube * y - (espace * (5-x)) / 2;  
        if(side){
            y_haut -= 2*taille_cube;
        }

        if(!jeu.getPenality() && jeu.getPlayer().get(x, y) != Cube.Vide)
        {
            drawable.setColor(Color.ORANGE);
            if(y == -1){
                y_haut = width/2 + 3*taille_cube_joueur;
                x_haut = height / 2 - (taille_cube / 2) * 5 + taille_cube * (5-x)
                    - (espace * 6) / 2;

                drawable.drawRect(y_haut, x_haut - espace * x, taille_cube, taille_cube);
                drawable.drawRect(y_haut+1, x_haut - espace * x + 1, taille_cube - 2, taille_cube - 2);
                drawable.drawRect(y_haut+2, x_haut - espace * x + 2, taille_cube - 4, taille_cube - 4);
                drawable.drawRect(y_haut+3, x_haut - espace * x + 3, taille_cube - 6, taille_cube - 6);
                return;

            }
            ArrayList<Point> accessible = jeu.Accessible_Playable();
            for (Point p : accessible)
            {
                if (p.x == x && p.y == y)
                {
                    drawable.drawRect(y_haut + espace * y, x_haut + espace * (5-x), taille_cube, taille_cube);
                    drawable.drawRect(y_haut + espace * y + 1, x_haut + espace * (5-x) + 1, taille_cube - 2, taille_cube - 2);
                    drawable.drawRect(y_haut + espace * y + 2, x_haut + espace * (5-x) + 2, taille_cube - 4, taille_cube - 4);
                    drawable.drawRect(y_haut + espace * y + 3, x_haut + espace * (5-x) + 3, taille_cube - 6, taille_cube - 6);
                    return;
                }
            }
        }
    }

    public static void dessiner_dernier_coup(Jeu jeu, Graphics2D drawable, int height, int width, boolean side)
    {
        Point last = jeu.dernierCoup();
        // if (last == null || (!PDJPyramideJoueur.getCube_Select_Static()))
        if(last == null)
        {
            return;
        }

        int taille_pyramide = jeu.getPrincipale().getSize();
        int taille_cube = Math.min(80 * height / (100 * taille_pyramide), 80 * width / (100 * taille_pyramide));
        int espace = taille_cube / 10;

        if(last.x == -1 && last.y == -1){
            System.out.println("blanc!");
            int x = GetBlancAccessible().x;
            int y = GetBlancAccessible().y;
            System.out.println("x : "+x +", y : "+y);

            drawable.setColor(Color.RED);
            drawable.drawRect(y, x, taille_cube, taille_cube);
            drawable.drawRect(y + 1, x + 1, taille_cube - 2, taille_cube - 2);
            drawable.setColor(Color.WHITE);
            drawable.drawRect(y + 2, x + 2, taille_cube - 4, taille_cube - 4);
            drawable.drawRect(y + 3, x + 3, taille_cube - 6, taille_cube - 6);
            drawable.setColor(Color.RED);
            drawable.drawRect(y + 4, x + 4, taille_cube - 8, taille_cube - 8);
            drawable.drawRect(y + 5, x + 5, taille_cube - 10, taille_cube - 10);
            return;

        }

        int x_haut = height / 2 - (taille_cube / 2) * (taille_pyramide) + taille_cube * (8-last.x) - (espace * taille_pyramide) / 2;
        int y_haut = width / 2 - (taille_cube / 2) * ((8-last.x) + 1) + taille_cube * last.y - (espace * (8-last.x)) / 2;  
        if(side)
        {
            y_haut -= 2*taille_cube;
        }

        drawable.setColor(Color.RED);
        drawable.drawRect(y_haut + espace * (last.y), x_haut + espace * (8-last.x), taille_cube, taille_cube);
        drawable.drawRect(y_haut + espace * (last.y) + 1, x_haut + espace * (8-last.x) + 1, taille_cube - 2, taille_cube - 2);
        drawable.setColor(Color.WHITE);
        drawable.drawRect(y_haut + espace * (last.y) + 2, x_haut + espace * (8-last.x) + 2, taille_cube - 4, taille_cube - 4);
        drawable.drawRect(y_haut + espace * (last.y) + 3, x_haut + espace * (8-last.x) + 3, taille_cube - 6, taille_cube - 6);
        drawable.setColor(Color.RED);
        drawable.drawRect(y_haut + espace * (last.y) + 4, x_haut + espace * (8-last.x) + 4, taille_cube - 8, taille_cube - 8);
        drawable.drawRect(y_haut + espace * (last.y) + 5, x_haut + espace * (8-last.x) + 5, taille_cube - 10, taille_cube - 10);

    }
}

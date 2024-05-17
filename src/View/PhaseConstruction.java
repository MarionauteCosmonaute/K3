package View;

import Model.Jeu;
import Model.Cube;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;

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
    Point tab_pioche[];
    boolean cube_sel;
    int taille_cube;
    int emplacement;
    boolean dessiner_vide;

    int echange;
    int x1,y1;

    int nbJoueur;
    int taille_base_pyramide;
    

    JButton reset, valider, aide;


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
			System.err.println("ERREUR : impossible de trouver le fichier");
			System.exit(2);
		} catch (IOException e) {
			System.err.println("ERREUR : impossible de charger l'image");
			System.exit(3);
		}

        while(jeu.draw())
        {
        }

        nbJoueur = jeu.nbJoueur();
        taille_base_pyramide = 8 - nbJoueur;
        tab_pts = new Point[taille_base_pyramide][taille_base_pyramide]; 
        cube_sel = false;

        dessiner_vide = false;
        tab_pioche = new Point[21];

        echange = 0;
        
        frame.addKeyListener(new AdaptateurClavier(controle));

        Box boiteTexte = Box.createVerticalBox();
        JPanel centrePanel = new JPanel();
        reset = Bouton.creerButton("Reset");
        reset.addActionListener(new AdaptateurReset(controle));
        centrePanel.add(reset);
        boiteTexte.add(centrePanel);

        valider = Bouton.creerButton("Valider");
        valider.setEnabled(false);
        centrePanel.add(valider);
        boiteTexte.add(centrePanel);
        frame.add(boiteTexte, BorderLayout.SOUTH);


        Box boite_aide = Box.createVerticalBox();
        JPanel panel = new JPanel();
        aide = Bouton.creerButton("Aide");
        aide.addActionListener(new AdaptateurAideConstruction(controle));
        panel.add(aide);
        boite_aide.add(panel);
        frame.add(boite_aide, BorderLayout.NORTH);

        
        
        JPanel content = new JPanel(new BorderLayout());   
        // On sépare la partie qui contient les boutons retour/aide/son et la partie du jeu
        JPanel topPanel = new JPanel(new BorderLayout()); //pour les boutons
        JButton UnMute = Bouton.BoutonUnMute(controle);
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(UnMute,BorderLayout.EAST);
        topRightPanel.setOpaque(false);
        topPanel.add(topRightPanel);

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

    public int getEchange(){
        return echange;
    }

    public void setEchange(int val){
        echange = val;
    }

    public int getX1(){
        return x1;
    }

    public int getY1(){
        return y1;
    }

    public void setX1(int x){
        x1 = x;
    }

    public void setY1(int y){
        y1 = y;
    }

    public int getEmplacement(){
        return emplacement;
    }

    public void setValider(boolean b){
        valider.setEnabled(b);
    }


    public void fonction_globale(Jeu jeu, Graphics g, int width_fenetre, int height_fenetre){
        if(pyramidePleine()){
            // System.out.println("true");
            setValider(true);
        }
        taille_cube = Math.min(height_fenetre/12, width_fenetre/18);
        // reset.setPreferredSize(new Dimension(, ));
        dessiner_pyramide_joueur(g, width_fenetre, height_fenetre);
        dessiner_pyramide_centrale(g, width_fenetre, height_fenetre);
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

    public boolean pyramidePleine(){
        for(int x = 0; x<taille_base_pyramide; x++){
            for(int y = 0; y<(taille_base_pyramide-x); y++){
                if(jeu.getPlayer().get(x, y) == Cube.Vide){
                    return false;
                }
            }
        }
        return true;
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

            drawable.setColor(Color.BLACK); 
            drawable.drawRect(x_selection, y_selection, taille_cube, taille_cube);
            drawable.drawRect(x_selection+1, y_selection+1, taille_cube-2, taille_cube-2);
            drawable.drawRect(x_selection+2, y_selection+2, taille_cube-4, taille_cube-4);

            drawable.setColor(Color.WHITE); 

            drawable.drawRect(x_selection+3, y_selection+3, taille_cube-6, taille_cube-6);
            drawable.drawRect(x_selection+4, y_selection+4, taille_cube-8, taille_cube-8);
            drawable.drawRect(x_selection+5, y_selection+5, taille_cube-10, taille_cube-10);
            drawable.setColor(Color.BLACK); 
        }
        
    }

   

    public void dessiner_pyramide_centrale(Graphics g, int width_fenetre, int height_fenetre){
        drawable = (Graphics2D) g;
        int taille_cube = this.taille_cube*2/3;
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
        
        int debut_zone_haut = height_fenetre/10;
        int debut_zone_gauche = width_fenetre*1/5;
        int x_haut, y_haut;

        Point p;
        Cube cube;
        for(int x = 0; x<taille_base_pyramide; x++){
            for(int y = 0; y<(taille_base_pyramide-x); y++){
                cube = jeu.getPlayer().get(x, y);
                
                x_haut = debut_zone_haut + (taille_base_pyramide - 1 -x)*(taille_cube + taille_cube/10);
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
        if(echange % 2 == 1){
            x_haut = debut_zone_haut + (taille_base_pyramide - 1-x1)*(taille_cube + taille_cube/10);
            y_haut = debut_zone_gauche + x1*(taille_cube + taille_cube/10)/2 + (taille_cube + taille_cube/10)*(y1);
            
            // if(jeu.getPlayer().get(x1, y1) == Cube.Blanc){
            //     System.out.println("blanc");
            //     drawable.setColor(Color.RED); 
            // }
            drawable.setColor(Color.RED);
            

            drawable.drawRect(y_haut, x_haut, taille_cube, taille_cube);
            drawable.drawRect(y_haut+1, x_haut+1, taille_cube-2, taille_cube-2);
            drawable.drawRect(y_haut+2, x_haut+2, taille_cube-4, taille_cube-4);

            drawable.setColor(Color.WHITE); 

            drawable.drawRect(y_haut+3, x_haut+3, taille_cube-6, taille_cube-6);
            drawable.drawRect(y_haut+4, x_haut+4, taille_cube-8, taille_cube-8);
            drawable.drawRect(y_haut+5, x_haut+5, taille_cube-10, taille_cube-10);

            drawable.setColor(Color.BLACK); 
        }
    }
   
}
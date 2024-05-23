import Model.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class test{
    static int joueBlanc(Jeu jeu, int x, int y){
        if(jeu.getPlayer().get(x, y) == Cube.Blanc){
            jeu.getPlayer().set(x, y, Cube.Vide);
            return 1;
        }
        return 0;
    }
    static int joueBlanc(Jeu jeu, int x){
        if(jeu.getPlayer().getSide(x) == Cube.Blanc){
            jeu.getPlayer().removeSide(x);
            return 1;
        }
        return 0;
    }

    static void rempli(Jeu jeu){
        for (int i = 0; i < 6; i++){
            jeu.construction(0, i, jeu.getPlayer().getPersonalBag().get(0) );
        }
        System.out.println(jeu.getPlayer());
    }
    static void drawAll(Jeu jeu){
        while(jeu.draw()){}
    }

    static void build(Player player){
        Collections.shuffle(player.getPersonalBag());
        for(int i = player.getSize()-1; i >= 0; i--){
            for(int j = 0; j < player.getSize()-i; j++){
                //System.out.println( i + " " + j + " " + "0" );
                if(!player.bagEmpty()){player.construction(j, i, player.getPersonalBag().get(0));}
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Jeu jeu;
        if(args[0].equals("charge")){
            jeu = new Jeu(args[1]);
            
        }
        else{
            jeu = new Jeu(Integer.parseInt(args[0]));
            jeu.initPrincipale();    
        }
        boolean not = true,start = false;
        Player player;
        int x = 0,y = 0,z = 0,w = 0,validity = 0;

        String[] entree;
        System.out.println("help pour afficher le menue");
        while(true){
            if(jeu.End_Game()){
                System.out.println("Game Done winner is player: " + (jeu.get_player() + 1) );
                break;
            }
            //System.out.println(jeu.check_loss());
            if(start && jeu.check_loss()){jeu.avance();}
            else{
            System.out.println("tour du joueur:" + (jeu.get_player()+1));
            entree = s.nextLine().split("\\s+");
            if(entree[0].equals("start")){System.out.println("game started");start = true;}
            
            
            if(entree[0].equals("save")){jeu.sauvegarde(entree[1]);}
            if(entree[0].equals("Annule")) {
                System.out.println();
                jeu.annule();}
            if(entree[0].equals("Refaire")) {jeu.refais();}
            if(entree[0].equals("colors")){
                HashMap<Cube,Boolean> list = jeu.accessibleColors();
                System.out.print("Colors possible to play: ");
                for (Cube cube : Cube.values()){
                    if(list.get(cube) != null){System.out.print(cube + " ");}
                }
            }
            if(entree[0].equals("playable")){
                if(jeu.noPlay()){System.out.print("Not ");}
                System.out.println("Playable");
            }
            if(entree[0].equals("rempli")){
                rempli(jeu);
            }
            if(entree[0].equals("auto")){
                drawAll(jeu);
                for(int i = 0; i < jeu.nbJoueur(); i++){
                    build(jeu.getPlayer());
                    if(entree.length > 1 ) {jeu.getPlayer().addBag(Cube.Vide);};
                    jeu.avance();
                }
            }
            if(entree[0].equals("build")){
                if(entree.length == 1){build(jeu.getPlayer());}
                else{build(jeu.getPlayer(Integer.parseInt(entree[1])-1));}
            }
            if(entree[0].equals("show")){
                if(entree[1].equals("bag")){
                    System.out.println(jeu.bag());
                }
                else if(entree[1].equals("accessible")) {
                    for (Point e : jeu.AccessibleCubesPlayer()){
                        System.out.print(e.x + "." + e.y + ":" + jeu.getPlayer().get(e.x, e.y) + " ");
                    }
                    System.out.println();
                }
            }

            /***********/

            if(entree[0].equals("joue")){

                if(entree[1].equals("s")){
                    x = Integer.parseInt(entree[2]);
                    y = Integer.parseInt(entree[3]);
                    z = Integer.parseInt(entree[4]);
                    validity = jeu.add_central(x, y,z, -1);
                }
                else{
                    x = Integer.parseInt(entree[1]);
                    y = Integer.parseInt(entree[2]);
                    z = Integer.parseInt(entree[3]);
                    w = Integer.parseInt(entree[4]);
                    validity = jeu.add_central(x,y,z,w);
                }
                switch(validity){
                    case 0:
                        System.err.println("coup non valide");
                        break;
                    case 1:
                        System.out.println("coup valide");
                        jeu.avance();
                        break;
                    case 2:
                        System.out.println("coup valide mais penalitee appliquee\nvoici le jeu du joueur penaliser:");
                        System.out.println(jeu.getPlayer());
                        System.out.println("Joueur " + (jeu.previous_player()+1) +" selectionnez 'p' si vous voulez prendre de sa pyramide ou 's' de son sac" );
                        entree = s.nextLine().split("\\s+");
                        if(entree[0].equals("p")){
                            while(not || !jeu.accessible(jeu.getPlayer().getPyramid(),x, y)){
                                not = false;
                                System.out.println("Selectionnez une case accessible a voler");
                                entree = s.nextLine().split("\\s+");
                                x = Integer.parseInt(entree[0]);
                                y = Integer.parseInt(entree[1]);
                            }
                            not = true;
                            jeu.takePenaltyCubeFromPyramid(x,y);
                        }
                        if(entree[0].equals("s")){
                            while(not || x > jeu.getPlayer().getSideSize()){
                                not = false;
                                System.out.println("Selectionnez l'indice du cube a voler");
                                entree = s.nextLine().split("\\s+");
                                x = Integer.parseInt(entree[0]);
                            }
                            not = true;
                            jeu.takePenaltyCubeFromSide(x);
                        }
                        jeu.avance();

                    default:
                        break;
                }

            }

            if(entree[0].equals("ajoute")){
                x = Integer.parseInt(entree[1]);
                y = Integer.parseInt(entree[2]);
                z = Integer.parseInt(entree[3]);
                jeu.construction(x, y, jeu.getPlayer().getPersonalBag().get(z));
                System.out.println(jeu.getPlayer());

            }

            if(entree[0].equals("draw")){
                if(entree.length == 1){
                    if(!jeu.draw()){
                        System.out.println("plus rien dans le sac");
                    }
                }
                else{
                    drawAll(jeu);
                }
            }

            if(entree[0].equals("accessible")){
                x = Integer.parseInt(entree[1]);
                y = Integer.parseInt(entree[2]);
                if(!jeu.accessible(x, y)){System.out.print("Not ");}
                System.out.println("Accessible");
            }

            if(entree[0].equals("print")){
                if(entree[1].equals("joueur")){
                    if(entree.length == 2){
                        player = jeu.getPlayer();
                        System.out.println(player);
                    }
                    else{
                        player = jeu.getPlayer(Integer.parseInt(entree[2]) - 1);
                        System.out.println(player);
                    }
                }
                if(entree[1].equals("mid")){
                    System.out.println(jeu.getPrincipale());
                }
            }

            if(entree[0].equals("quit")){break;}
            if(entree[0].equals("help")){
                System.out.println("-print <joueur/mid> <n du joueur (optionelle)> #affiche la pyramide");
                System.out.println("-accessible <x> <y> ##affiche si la case du joueur est accessible");
                System.out.println("-draw <all (optionnelle)> ##ajoute au joueur courant les cube piochee, si all ajouter tout les cubes sont distribuer");
                System.out.println("-ajoute <x> <y> <z> #ajoute a la pyramide du joueur au coordonnee x y le cube dans son sac a l'emplacement z");
                System.out.println("-joue <'s'/'blanc' (optionelle si du side ou blanc)> <x> <y> <z> <w> #joue le cube de coordonnee (z,y) a l'emplacement (x,y)");
                System.out.println("-rempli #rempli la premiere ligne de la pyramide du joueur aleatoirement");
                System.out.println("-auto #effectue la fonction draw all puis remplie toute les pyramide de tout les joueur de maniere aleatoire");
                System.out.println("-build <n joueur (optionnelle)> #rempli toute la pyramide du joueur courant ou le nombre indiquee");
                System.out.println("-show <bag/accesible> #affiche le contenue de la pioche ou affiche toute les case accessible du joueur");
                System.out.println("-quit #quite le jeu");
                System.out.println("-Annule");
                System.out.println("-Refaire");
            }
            System.out.println();
            System.out.println();}
            
        }
        s.close();
    }
    
}
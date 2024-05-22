package Model.Runnables;

import Model.*;
import java.awt.Point;
import java.util.HashMap;

public class PlayGameThread implements Runnable{
    Jeu jeu;
    BestPyramide best;
    Pyramid pyramid;
    int index;

    public PlayGameThread(Jeu jeu, BestPyramide best,int index){
        this.jeu = jeu;
        this.index = index;
        this.best = best;
        jeu.constructionAleatoire(jeu.getPlayer(index));
        try{pyramid = jeu.getPlayer(index).getPyramid().clone();}
        catch(CloneNotSupportedException e){System.err.println("Erreur clonnage de la pyramide lors de la simulation du jeu");System.exit(1);}
    }
    
    public void run(){
        IA ia = IA.nouvelle(jeu);           /* pas tres sur de le faire avec une ia facile */
        int nbCoup = 0;
        while(!jeu.End_Game()){
            if(jeu.check_loss()){}
            else{
                if(jeu.get_player() == index){
                    nbCoup++;
                    /*if(nbCoup > 20){
                        System.out.println(nbCoup);
                        System.out.println("plateau:\n" + jeu.getPricipale() + "\njoueur 0:\n" + jeu.getPlayer(0) + "\njoueur 1:\n" + jeu.getPlayer(1));
                        HashMap<Cube,Boolean> list = jeu.accessibleColors(true);
                        System.out.print("cube disponible: ");
                        for(Cube cube : Cube.values()){
                            if(list.containsKey(cube)){System.out.print(cube + " ");}
                        }
                        System.out.println();
                        System.out.println("joueur 0:");
                        for(Point p : jeu.Accessible_Playable(0)){
                            System.out.println("le cube: "+ jeu.getPlayer(0).getSide(p.x) + " en: "+ p);
                        }
                        System.out.println("joueur 1:");
                        for(Point p : jeu.Accessible_Playable(1)){
                            System.out.print("le cube: "+ jeu.getPlayer(1).get(p.x, p.y) + " en: "+ p);
                        }
                        System.out.println();
                    }*/
                }
                if(ia.add_central() == 2){ia.takePenaltyCube();}
            }
                
        }
        try{
            best.set(pyramid, jeu.getPrincipale().clone(),jeu.getPlayer(index).getPyramid().clone(),nbCoup);
        }
        catch(CloneNotSupportedException e){System.err.println("clone pas fonctionner pyramide");System.exit(1);}
    }
}

package Model.Runnables;

import Model.*;
import java.awt.Point;
import java.util.HashMap;

public class SimationIAvsIA implements Runnable{
    Jeu jeu;
    BestPyramide best;
    Pyramid pyramid;
    int index;

    public SimationIAvsIA(Jeu jeu, BestPyramide best,int index){
        this.jeu = jeu;
        this.index = index;
        this.best = best;
        jeu.constructionAleatoire(jeu.getPlayer(index));
        try{pyramid = jeu.getPlayer(index).getPyramid().clone();}
        catch(CloneNotSupportedException e){System.err.println("Erreur clonnage de la pyramide lors de la simulation du jeu");System.exit(1);}
    }
    
    public void run(){
        IA ia = IA.nouvelle(jeu,"Difficile");           /* pas tres sur de le faire avec une ia facile */
        int nbCoup = 0;
        while(!jeu.End_Game()){
            if(jeu.check_loss()){}
            else{
                if(jeu.get_player() == index){
                    nbCoup++;
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

package Model.Runnables;

import Model.*;

public class PlayGameThread implements Runnable{
    Jeu jeu;
    BestPyramide best;
    Pyramid pyramid;

    public PlayGameThread(Jeu jeu, BestPyramide best){
        this.jeu = jeu;
        this.best = best;
        jeu.constructionAleatoire(jeu.getPlayer(1));
        try{pyramid = jeu.getPlayer(1).getPyramid().clone();}
        catch(CloneNotSupportedException e){System.err.println("Erreur clonnage de la pyramide");System.exit(1);}
    }
    
    public void run(){
        IA ia = IA.nouvelle(jeu);           /* pas tres sur de le faire avec une ia facile */
        int nbCoup = 0;
        while(!jeu.End_Game()){
            if(jeu.check_loss()){jeu.avance();}
            else if(ia.add_central() == 2){
                ia.takePenaltyCube();
            }
        }
        best.set(pyramid, nbCoup);
    }
}

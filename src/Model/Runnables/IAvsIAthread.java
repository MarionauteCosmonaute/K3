package Model.Runnables;

import Model.*;
import Model.Info.*;
import Model.IA_pack.*;

public class IAvsIAthread extends IAvsIA implements Runnable {
    Stat stats; 

    public IAvsIAthread(Jeu jeu, int difficulte1, int difficulte2, boolean constructionAleatoire,Stat stats){
        super(jeu,difficulte1,difficulte2,constructionAleatoire);
        this.stats = stats;
    }
    
    public void run(){
        GameInfo infos = play();
        //System.out.println(infos);
        stats.addGame(infos.getWinner() == 0);
    }
}

package Model.Runnables;

import Structure.Fifo;
import  Model.Coup;
import  Model.Jeu;

public class Action implements Runnable{
    Jeu jeu;
    Fifo play;

    public Action(Jeu jeu,Fifo play){
        this.play = play;
        this.jeu = jeu;
    }


    public void run(){
        while(true){
            Coup coup = play.get();
            jeu.playAction(coup);
        }

    }
}

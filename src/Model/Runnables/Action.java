package Model.Runnables;

import Structure.Fifo;
import  Model.Coup;
import Model.JeuOnline;

public class Action implements Runnable{
    JeuOnline jeu;
    Fifo play;

    public Action(JeuOnline jeu,Fifo play){
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

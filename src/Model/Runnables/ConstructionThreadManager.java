package Model.Runnables;

import Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ConstructionThreadManager implements Runnable{
    Jeu jeu;
    PyramideList list;
    int nbThreads = 4, difficulte, indice;
    ArrayList<Cube> potentielCube;
    


    public ConstructionThreadManager(Jeu jeu,PyramideList list, ArrayList<Cube> potentielCube, int difficulte, int indice){
        this.jeu = jeu;
        this.list = list;
        this.potentielCube = potentielCube;
        this.difficulte = difficulte;
        this.indice = indice;
    }

    public ConstructionThreadManager(Jeu jeu, PyramideList list, ArrayList<Cube> potentielCube, int difficulte, int indice, int nbThreads){
        this.jeu = jeu;
        this.list = list;
        this.potentielCube = potentielCube;
        this.difficulte = difficulte;
        this.indice = indice;
        this.nbThreads = nbThreads;
    }

    public void finish(){
        list.finish();
    }

    public Thread doWork(Jeu game){
        Random rand = new Random();
        Cube cube = potentielCube.get(rand.nextInt(potentielCube.size()));
        game.getPlayer().construction(game.getPlayer().getSize()-1, 0, cube);
        Thread thread = new Thread(new ConstructionRunable(game, list, difficulte, indice));
        thread.start();
        return thread;
    }

    public void run(){
        jeu.getPlayer(jeu.next_player(indice)).fusion();
        Thread[] threads = new Thread[nbThreads];

        for(int i = 0; i < nbThreads; i++){
            threads[i] = doWork(jeu.clone());
        }
        
        while(!list.done()){
            
            for(int i = 0; i < nbThreads; i++ ){
                try{
                    threads[i].join(100);
                }

                catch(InterruptedException e){System.err.println(e);System.exit(1);}
                if( !threads[i].isAlive() && !list.done() ){
                    threads[i] = doWork(jeu.clone());
                }
            }
        }

    }
}

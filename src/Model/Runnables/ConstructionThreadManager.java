package Model.Runnables;

import Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ConstructionThreadManager implements Runnable {
    Jeu jeu;
    BestPyramide ZeBest;
    int nbThreads = 3, difficulte, indice;
    ArrayList<Cube> potentielCube;

    public ConstructionThreadManager(Jeu jeu, BestPyramide ZeBest, ArrayList<Cube> potentielCube, int difficulte,
            int indice) {
        this.jeu = jeu;
        this.ZeBest = ZeBest;
        this.potentielCube = potentielCube;
        this.difficulte = difficulte;
        this.indice = indice;
    }

    public ConstructionThreadManager(Jeu jeu, BestPyramide ZeBest, ArrayList<Cube> potentielCube, int difficulte,
            int indice, int nbThreads) {
        this.jeu = jeu;
        this.ZeBest = ZeBest;
        this.potentielCube = potentielCube;
        this.difficulte = difficulte;
        this.indice = indice;
        this.nbThreads = nbThreads;
    }

    public void finish() {
        ZeBest.finish();
    }

    public Thread doWork(Jeu game) {
        Random rand = new Random();
        Cube cube = potentielCube.get(rand.nextInt(potentielCube.size()));
        game.getPlayer(indice).construction(game.getPlayer(indice).getSize() - 1, 0, cube);
        Thread thread = new Thread(new ConstructionRunable(game, ZeBest, 0, indice));
        thread.start();
        //System.out.println("Un nouveau thread est creer");
        return thread;
    }

    public void run() {
        jeu.getPlayer(jeu.next_player(indice)).fusion();
        Thread[] threads = new Thread[nbThreads];

        for (int i = 0; i < nbThreads; i++) {
            threads[i] = doWork(jeu.clone());
        }
        //boolean bool = true;
        while (!ZeBest.done()) {
            /*if (ZeBest.getPyramid() != null && bool) {
                System.out.println();
                System.out.println();
                System.out.println("L'IA a trouver une pyramide");
                System.out.println();
                System.out.println();
                bool = false;
            }*/
            for (int i = 0; i < nbThreads; i++) {
                try {
                    threads[i].join(100);
                }

                catch (InterruptedException e) {
                    System.err.println(e);
                    System.exit(1);
                }
                if (!threads[i].isAlive() && !ZeBest.done()) {
                    threads[i] = doWork(jeu.clone());

                }
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }
}

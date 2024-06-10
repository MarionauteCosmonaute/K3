import Model.*;
import Model.Info.Stat;
import Model.Runnables.IAvsIAthread;

public class testMemePartie {
    
    public static void main(String[] args) {
        /*if(args.length != 4){
            throw new IllegalArgumentException("usage: java IAsimulation <test ammount> <difficulty AI 1> <difficulty AI 2> <premier joueur>");
        }
        int commence = Integer.parseInt(args[3]);
        int difficulte1 = Integer.parseInt(args[1]), difficulte2 = Integer.parseInt(args[2]);
        int nbTest = Integer.parseInt(args[0]);*/
        
        int difficulte1 = 2, difficulte2 = 0,nbTest = 10;

        
        Jeu jeu = new Jeu("src/Jeu.txt");
        
        
        
        //jeu.changeCurrentPlayer(commence);
        System.out.println(jeu.getPlayer(0));
        System.out.println(jeu.getPlayer(1));
        
        /*IA ia1 = IA.nouvelle(jeu,difficulte1,0);
        IA ia2 = IA.nouvelle(jeu, difficulte2, 1);
        //System.out.println("ptdr");
        ia1.construction(true);
        ia2.construction(true);
        try{ia2.thread().join();ia1.thread().join();}
        catch(InterruptedException e){System.err.println(e);}
        ia1 = null;
        ia2 = null;*/
        
        Jeu clone;
        int nb = 0, nbThreads = 1;
        Stat stat = new Stat();
        Thread threads[] = new Thread[nbThreads];
        
        for(int i = 0; i < nbThreads; i++){
            clone = jeu.clone();
            threads[i] = new Thread(new IAvsIAthread(clone,difficulte1,difficulte2,true,stat));
            threads[i].start();
            System.out.println("simulation nb: " + nb);
            nb++;
        }

        boolean stop = false;
        while(!stop){
            for(int i = 0; i < nbThreads; i++ ){
                if(nb >= nbTest) stop = true;
                try{
                    threads[i].join(100);
                }

                catch(InterruptedException e){System.err.println(e);System.exit(1);}
                if( !threads[i].isAlive() && !stop ){
                    System.out.println("simulation nb: " + nb);
                    clone = jeu.clone();
                    threads[i] = new Thread(new IAvsIAthread(clone,difficulte1,difficulte2,true,stat));
                    threads[i].start();
                    nb++;
                }
            }
        }
        for(Thread thread : threads){
            try{thread.join();}
            catch(InterruptedException e){System.err.println(e);System.exit(1);}
        }
        System.out.println("Pour le jeu:\n" + jeu);
        System.out.println("winrate of Player 1: " + stat.winratePlayer1());
        System.out.println("winrate of Player 2: " + stat.winratePlayer2());
        
    }
}

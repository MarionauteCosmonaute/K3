
import Model.Jeu;
import Model.Info.*;
import Model.Runnables.*;
public class IAsimulation {

    public static void main(String[] args) {
        if(args.length != 3){
            throw new IllegalArgumentException("usage: java IAsimulation <test ammount> <difficulty AI 1> <difficulty AI 2>");
        }
        int nbTest = Integer.parseInt(args[0]);
        int difficulte1 = Integer.parseInt(args[1]), difficulte2 = Integer.parseInt(args[2]);
        /*int nbTest = 2;
        int difficulte1 = 0;
        int difficulte2 = 0;*/

        int nb = 0,nbThreads = 5;
        Stat stat = new Stat();
        Thread threads[] = new Thread[nbThreads];
        Jeu jeu;
        for(int i = 0; i < nbThreads; i++){
            jeu = new Jeu(2);
            jeu.initTest();
            threads[i] = new Thread(new IAvsIAthread(jeu,difficulte1,difficulte2,false,stat));
            threads[i].start();
            System.out.println("simulation nb: " + nb);
            nb++;
        }
        boolean stop = false;
        while(!stop){
            for(int i = 0; i < nbThreads; i++ ){
                if(nb > nbTest) stop = true;
                try{
                    threads[i].join(100);
                }

                catch(InterruptedException e){System.err.println(e);System.exit(1);}
                if( !threads[i].isAlive() && !stop ){
                    System.out.println("simulation nb: " + nb);
                    jeu = new Jeu(2);
                    jeu.initTest();
                    threads[i] = new Thread(new IAvsIAthread(jeu,difficulte1,difficulte2,false,stat));
                    threads[i].start();
                    nb++;
                }
            }
        }
        for(Thread thread : threads){
            try{thread.join();}
            catch(InterruptedException e){System.err.println(e);System.exit(1);}
        }
        
        System.out.println("winrate of Player 1: " + stat.winratePlayer1());
        System.out.println("winrate of Player 2: " + stat.winratePlayer2());        

    }
}

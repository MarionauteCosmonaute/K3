import Model.*;
import Model.IA_pack.*;

public class InitGame {

    public static void main(String[] args) {
        Jeu jeu = new Jeu(2);
        jeu.initTest();
        IA ia1 = IA.nouvelle(jeu, 1, 0);
        IA ia2 = IA.nouvelle(jeu, 1, 1);

        ia1.construction(true);
        ia2.construction(true);
        try{ia2.thread().join();ia1.thread().join();}
        catch(InterruptedException e){System.err.println(e);}
        System.out.println(jeu);
        jeu.sauvegarde("Jeu.txt");
    }
}

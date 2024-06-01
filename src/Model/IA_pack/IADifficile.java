package Model.IA_pack;

/*import java.util.*;
import java.awt.Point;*/
import Model.Runnables.StartConstruction;

public class IADifficile extends IA {

    /*@Override
    public int jouer_coup() {
        //long start = System.currentTimeMillis();
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, 2);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        //long finish = System.currentTimeMillis();
        //System.out.println("Temps passer a calculer le coup est de: " + (finish - start));

        return jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(),
                (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }*/

    @Override
    public void construction(boolean aide) {

        constructionThread = new Thread(new StartConstruction(this,aide));
        constructionThread.start();
    }
}
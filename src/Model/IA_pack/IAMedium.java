package Model.IA_pack;

import Model.Runnables.StartConstruction;

import java.util.*;
import java.awt.Point;

public class IAMedium extends IA {

    @Override
    public int jouer_coup() {
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, 1);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        return jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(),
                (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }

    @Override
    public void construction() {

        Thread thread = new Thread(new StartConstruction(this));
        thread.start();
    }
}
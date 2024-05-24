package Model;

import Model.Iterateur.*;

import java.util.*;
import java.awt.Point;

public class IAMedium extends IA {

    @Override
    public int add_central(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player, 1);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        return jeu.add_central((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }

    @Override
    public void construction(){
        Pyramid pyramide = generePyramide(10).getPyramid();
        Iterateur it = pyramide.iterateur("UP");
        Iterateur itIA = jeu.getPlayer(indiceJoueur).getPyramid().iterateur("UP");
        while(it.hasNext()){
            itIA.next();
            itIA.modify(it.next());
        }
    }
}

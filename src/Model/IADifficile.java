package Model;

import Model.Iterateur.*;
import java.util.*;
import java.awt.Point;

public class IADifficile extends IA {

    @Override
    public int jouer_coup(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player, 2);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        return jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
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

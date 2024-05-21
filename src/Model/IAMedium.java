/*package Model;
import java.util.*;
import java.io.*;
import java.awt.Point;

public class IAMedium extends IA {

    @Override
    public void add_central(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player, 1);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.add_central((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }

    @Override
    public void construction(){
        ArrayList<Integer> coups_possibles = pyramideIA(jeu, jeu.current_player);
        Random random = new Random();
        Point x_y_pyra = jeu.findFirstFreeElement();
        int cube_a_placer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.construction((int) x_y_pyra.getX(), (int) x_y_pyra.getY(), jeu.getPlayer().getSide(cube_a_placer));
    }
}
*/
package Model;
import java.util.*;
// import java.io.*;
import java.awt.Point;

public class IAFacile extends IA {

    @Override
    public int add_central(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player, 0);
        if(coups_possibles.size() != 0){
            Random random = new Random();
            ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
            return jeu.add_central((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
        }
        return 0;
    }

    /*@Override
    public void construction(){ //Tout refaire en choisissant les 3 cubes les plus hauts, puis en énumérant des pyramides aléatoires et en calculant a chaque fois qu'on enleve un cube le nombre de cubes de couleur différents accessibles
                                // On prendra a la fin la pyramide où on a le plus de cubes de couleur différente accessibles a chaque coup
        ArrayList<Integer> coups_possibles = pyramideIA(jeu, jeu.current_player);
        Random random = new Random();
        Point x_y_pyra;
        x_y_pyra = jeu.findFirstFreeElement();
        System.out.println(coups_possibles.size());
        int cube_a_placer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.construction((int) x_y_pyra.getX(), (int) x_y_pyra.getY(), jeu.getPlayer().getSide(cube_a_placer));
    }*/
}
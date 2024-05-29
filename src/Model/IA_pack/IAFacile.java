package Model.IA_pack;
import java.util.*;

import Model.Pyramid;

import java.awt.Point;

public class IAFacile extends IA {

    @Override
    public int jouer_coup(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, 0);
        if(coups_possibles.size() != 0){
            Random random = new Random();
            ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
            int value = jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY()); 
            return value;
        }
        return 0;
    }

    @Override
    public void construction(){ //Tout refaire en choisissant les 3 cubes les plus hauts, puis en énumérant des pyramides aléatoires et en calculant a chaque fois qu'on enleve un cube le nombre de cubes de couleur différents accessibles
                                // On prendra a la fin la pyramide où on a le plus de cubes de couleur différente accessibles a chaque coup
        phaseConstruction = true;
        Pyramid pyramide = generePyramide();
        jeu.getPlayer(indiceJoueur).build(pyramide);
        /*int x;
        if(pyramideList.getBest() != null) {pyramide = pyramideList.getBest(); x = -1;}
        else {pyramide = pyramideList.getPyramid(0); x = 0;}
        System.out.println("la meilleur pyramide est: \n" + pyramide + "\nde profondeur: " + pyramideList.getProfondeur(x) );
        */
    }
}
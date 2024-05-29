package Model.IA_pack;

import Model.*;
import java.util.*;
import java.awt.Point;

public class IADifficile extends IA {

    @Override
    public int jouer_coup(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, 2);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        return jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }
    
    @Override
    public void construction(){
        super.phaseConstruction = true;
        Pyramid pyramide = generePyramide();
        jeu.getPlayer(indiceJoueur).build(pyramide);
        // PyramideList pyramideList = generePyramide(15,30);
        // Pyramid pyramide;
        // int x;
        // if(pyramideList.getBest() != null) {pyramide = pyramideList.getBest(); x = -1;}
        // else {pyramide = pyramideList.getPyramid(0); x = 0;}
        // System.out.println("la meilleur pyramide est: \n" + pyramide + "\nde profondeur: " + pyramideList.getProfondeur(x) );
        // jeu.getPlayer(indiceJoueur).build(pyramide);
    }
}
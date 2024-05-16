package Model;
import java.util.*;
import java.io.*;
import java.awt.Point;

public class IAFacile extends IA {

    public ArrayList<ArrayList<Point>> coupIA(Jeu j, int joueur1){
        ArrayList<ArrayList<Point>> resultat_ok = new ArrayList<>();
        ArrayList<ArrayList<Point>> resultat_ko = new ArrayList<>();
        int value_max= -100000;
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        for(Point depart : cubes_access){
            ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),(int) depart.getY());
            for(Point arrivee : coups_jouables){
                ArrayList<Point> pos = new ArrayList<>();
                pos.add(depart);
                pos.add(arrivee);
                Jeu clone = new Jeu(2);
                clone = j.clone();
                clone.add_central_pyramid((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY());
                int value= MinMaxIA(clone, 2, joueur1, -10000, +10000, 0);
                if(value==value_max){
                    resultat_ok.add(pos);
                }
                else if(value>value_max){
                    resultat_ok.clear();
                    resultat_ok.add(pos);
                    value_max = value;
                }
                else if(value==1000){
                    resultat_ok.clear();
                    resultat_ok.add(pos);
                    return resultat_ok;
                }
                else{
                    resultat_ko.add(pos);
                }
            }
        }
        if(resultat_ok.size()==0){
            return resultat_ko;
        }
        return resultat_ok;
    }

    public ArrayList<Integer> pyramideIA(Jeu j, int joueur1){
        ArrayList<Integer> resultat_ok = new ArrayList<>();
        ArrayList<Integer> resultat_ko = new ArrayList<>();
        int value_max= -100000;
        for(int i=0; i<j.getPlayer(joueur1).getBagSize();i++){
            ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations(i, -1);
            for(Point coup : coups_jouables){
                Jeu clone = new Jeu(2);
                clone = j.clone();
                clone.add_central_pyramid((int) coup.getX(),(int) coup.getY(), i, -1);
                int value= MinMaxIA(clone, 2, joueur1, -10000, +10000, -1);
                if(value==value_max){
                    resultat_ok.add(i);
                }
                else if(value>value_max){
                    resultat_ok.clear();
                    resultat_ok.add(i);
                    value_max = value;
                }
                else if(value==1000){
                    resultat_ok.clear();
                    resultat_ok.add(i);
                    return resultat_ok;
                }
                else{
                    resultat_ko.add(i);
                }
            }
        }
        if(resultat_ok.size()==0){
            return resultat_ko;
        }
        return resultat_ok;
    }

    @Override
    public void add_central(){
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, jeu.current_player);
        Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.add_central((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(), (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
    }

    @Override
    public void construction(){
        ArrayList<Integer> coups_possibles = pyramideIA(jeu, jeu.current_player);
        Random random = new Random();
        Point x_y_pyra;
        x_y_pyra = jeu.findFirstFreeElement();
        int cube_a_placer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.construction((int) x_y_pyra.getX(), (int) x_y_pyra.getY(), jeu.getPlayer().getSide(cube_a_placer));
    }
}

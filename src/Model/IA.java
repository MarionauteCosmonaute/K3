package Model;

import java.util.*;
import java.awt.Point;

public abstract class IA {
    Jeu jeu;
    
    public static IA nouvelle(Jeu j) {
        IA resultat = null;
        String type = "Facile";

        switch (type) {
            case "Aleatoire":
                //resultat = new IAAleatoire();
                break;
            case "Facile":
                resultat = new IAFacile();
                break;
            case "Medium":
                resultat = new IAMedium();
                break;
            case "Difficile":
                resultat = new IADifficile();
                break;
        }
        if (resultat != null) {
            resultat.jeu = j;
        }
        return resultat;
    }

    public int MinMaxIA(Jeu j,int depth, int player_max, int alpha, int beta, int IA){
        int value;
        boolean bon_joueur = player_max == j.get_player();
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        if(j.End_Game()){ //Condition de défaite de tous les autres joueurs en même temps à implémenter
                                             //Besoin d'une fonctione auxiliaire qui permet lors de tests de si un joueur a perdu de l'exclure du calcul
                                             //Et appeler l'IA avec les nouveaux paramètres.
            if(j.check_loss()){
                return -1000;
            }else{
                return 1000;
            }
        }
        if (depth == 0){
            int total = 0;
            int total_j1 = 0;
            int total_j2 = 0;
            for(Point compte : cubes_access){ //Compte du nombre de coups jouable du j1
                int current_possibilities = j.CubeAccessibleDestinations((int) compte.getX(),(int) compte.getY()).size();
                total_j1+= current_possibilities;
            }
            ArrayList<Point> cubes_access2 = j.Accessible_Playable(j.next_player()); //Necessaire de pouvoir récupérer les positions accessibles du joueur adverse
            for(Point compte : cubes_access2) { //Compte du nombre de coups jouable du j1
                int current_possibilities = j.CubeAccessibleDestinations((int) compte.getX(),(int) compte.getY()).size();
                total_j2+= current_possibilities;
            }
            switch (IA){
            case -1: //IA specifique à la création de la pyramide
                
                for(Point compte : cubes_access){
                    int current_possibilities = j.CubeAccessibleDestinations((int) compte.getX(),(int) compte.getY()).size();
                    total+= current_possibilities;
                }
                return total;
            case 0: //IA Facile
                if(bon_joueur){
                    return j.getPlayer().totalCube() - j.getPlayer(j.next_player()).totalCube();
                }
                else{
                    return j.getPlayer(j.next_player()).totalCube() - j.getPlayer().totalCube();
                }
            case 1 : //IA Medium
                total = (int)(total_j1 * 0.2) + (int)(j.getPlayer().totalCube()*0.8) - (int)(0.8 * j.getPlayer(j.next_player()).totalCube()) - (int)(total_j2 * 0.2);
                if(bon_joueur){
                    return total; 
                }
                else{
                    return -total;
                }
            case 2 : //IA Difficile
                total = (int)(total_j1 * 0.7) + (int)(j.getPlayer().totalCube()*0.3) - (int)(0.3 * j.getPlayer(j.next_player()).totalCube()) - (int)(total_j2 * 0.7);
                if(bon_joueur){
                    return total; 
                }
                else{
                    return -total;
                }
        }
        if(bon_joueur){
            value = -1000;
            for(Point depart : cubes_access){
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),(int) depart.getY());
                for(Point arrivee : coups_jouables){
                    if(j.add_central((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY()) == 2){ //On joue une pénalité
                        for(Point access : j.Accessible_Playable()){
                            Jeu clone = j.clone();
                            clone.takePenaltyCube((int) access.getX(), (int) access.getY());
                            value = Math.max(value,MinMaxIA(clone,depth-1,player_max, alpha, beta, IA));
                        }
                    }
                    else{
                        //System.out.println( value + " " + depth + " " + alpha + " " + beta );
                        value = Math.max(value,MinMaxIA(j,depth-1,player_max, alpha, beta, IA));
                        /*System.out.println();
                        System.out.println();
                        System.out.println();*/
                    }
                    if(alpha >= value){
                        return value;
                    }
                    beta = Math.min(beta,value);
                }
            }
        }
        else{
            value = 1000;
            for(Point depart : cubes_access){
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),(int) depart.getY());
                for(Point arrivee : coups_jouables){
                    if(j.add_central((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY())==2){
                        for(Point access : j.Accessible_Playable()){
                            Jeu clone = j.clone();
                            clone.takePenaltyCube((int) access.getX(), (int) access.getY());
                            value = Math.min(value,MinMaxIA(clone,depth-1,player_max, alpha, beta, IA));
                        }
                    }
                    else{
                        value = Math.min(value,MinMaxIA(j,depth-1,player_max, alpha, beta, IA));
                    }
                    if(beta <= value){
                        return value;
                    }
                    alpha = Math.max(alpha,value);
                }
            }
        }
        return value;
    }

    public ArrayList<ArrayList<Point>> coupIA(Jeu j, int joueur1, int difficulté){
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
                int value = 0;
                switch(difficulté){
                    case 0:
                    value= MinMaxIA(clone, 2, joueur1, -10000, +10000, 0);
                    break;
                    case 1:
                    value = MinMaxIA(clone, 3, joueur1, -10000, +10000, 1);
                    break;
                    case 2:
                    value = MinMaxIA(clone, 5, joueur1, -10000, +10000, 2);
                    break;
                }
                
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

    public ArrayList<Point> penaltyIA(Jeu j){
        ArrayList<Point> resultat_ok = new ArrayList<>();
        int max = 0;
        Point x_y_to_take = new Point();
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        for(Point cubes : cubes_access){
            int nb = j.CubeAccessibleDestinations((int) cubes.getX(),(int) cubes.getY()).size();
            x_y_to_take.x = (int) cubes.getX();
            x_y_to_take.y = (int) cubes.getY();
            if (max<nb){
                resultat_ok.clear();
                resultat_ok.add(x_y_to_take);
                max = nb;
            }
            else if(nb==max){
                resultat_ok.add(x_y_to_take);
            }
        }
        return resultat_ok;
    }

    public void add_central() {
        throw new UnsupportedOperationException();
    }
    public void construction(){
        throw new UnsupportedOperationException();
    }

    public void takePenaltyCube(){
        ArrayList<Point> coups_possibles = penaltyIA(jeu);
        Random random = new Random();
        Point coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        jeu.takePenaltyCube((int) coup_a_jouer.getX(), (int) coup_a_jouer.getY());
    }
}

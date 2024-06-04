package Model.IA_pack;

import Model.*;

import java.util.*;
import java.awt.Point;
import Model.Runnables.*;

public abstract class IA {
    Jeu jeu;
    int difficulte, indiceJoueur;
    HashMap<Jeu,Integer> heuristique;
    Thread constructionThread;

    public static IA nouvelle(Jeu j, int difficulte, int indiceJoueur) {
        IA resultat = null;
        resultat = new IAFacile();

        switch (difficulte) {
            case -1:
                // resultat = new IAAleatoire();
                break;
            case 0:
                resultat = new IAFacile();
                break;
            case 1:
                resultat = new IAMedium();
                break;
            case 2:
                resultat = new IADifficile();
                break;
            default:
                resultat = null;
        }
        if (resultat != null) {
            resultat.jeu = j;
            resultat.difficulte = difficulte;
            resultat.indiceJoueur = indiceJoueur;
            resultat.heuristique = new HashMap<>();
        }
        return resultat;
    }

    public int saveValue(Jeu jeu,int value){
        heuristique.put(jeu, value);
        return value;
    }
    
    public int MinMaxIA(Jeu j, int depth, int player_max, int alpha, int beta, int IA) {
        if(heuristique.containsKey(j)){
            return heuristique.get(j);
        }
        int value;
        boolean bon_joueur = player_max == j.get_player();
        ArrayList<Point> cubes_access = j.Accessible_Playable();

        if (j.check_loss() || j.End_Game()) { // Condition de défaite de tous les autres joueurs en même temps à
                                              // implémenter
                                              // Besoin d'une fonctione auxiliaire qui permet lors de tests de si un
                                              // joueur a perdu de l'exclure du calcul
                                              // Et appeler l'IA avec les nouveaux paramètres.
            if (j.getPlayer(player_max).lost()) {
                return saveValue(j,-10000000);
            } else {
                return saveValue(j,10000000);
            }
        }
        if (depth == 0) {
            int total = 0;
            int total_j1 = 0;
            int total_j2 = 0;
            int total_access = cubes_access.size();
            int total_access2;
            ArrayList<Point> cubes_access2 = j.Accessible_Playable(j.next_player());
            if(IA!=0){
                for (Point compte : cubes_access) { // Compte du nombre de coups jouable du j1
                    int current_possibilities = j.destinationSansPen(compte);
                    total_j1 += current_possibilities;
                }
                for (Point compte : cubes_access2) { 
                    int current_possibilities = j.destinationSansPen(j.getPlayer(j.next_player()),
                            compte);
                    total_j2 += current_possibilities;
                }
            }

            switch (IA) {
                case 0: // IA Facile
                    if (bon_joueur) {
                        return saveValue(j,j.getPlayer().totalCube() - j.getPlayer(j.next_player()).totalCube());
                    } else {
                        return saveValue(j,j.getPlayer(j.next_player()).totalCube() - j.getPlayer().totalCube());
                    }
                case 1: // IA Medium

                    total_access2 = cubes_access2.size();

                    total = total_access + (int) (j.getPlayer().totalCube() * 1000)
                    - (int) (j.getPlayer(j.next_player()).totalCube() * 1000) - total_access2;
                    
                    
                    if (bon_joueur) {
                        return saveValue(j,total);
                    } else {
                        return saveValue(j,-total);
                    }
                case 2: // IA Difficile
                    
                    total_access2 = cubes_access2.size();
                    total = (int) (j.getPlayer().totalCube() * 10000) + total_access * 100 + total_j1
                        - ((int) (j.getPlayer(j.next_player()).totalCube() * 10000)
                                + (total_access2 * 100) + total_j2);

                   
                    if (bon_joueur) {
                    return saveValue(j,total);
                    } else {
                    return saveValue(j,-total);
                    }
            }
        }
        if (bon_joueur) {
            value = -10000000;
            for (Point depart : cubes_access) {
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),
                        (int) depart.getY());
                for (Point arrivee : coups_jouables) {
                    Jeu clone = j.clone();
                    if (clone.jouer_coup((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),
                            (int) depart.getY()) == 2) { // On joue une pénalité
                            this.penaltyIA(clone);
                    }
                    value = Math.max(value, MinMaxIA(clone, depth - 1, player_max, alpha, beta, IA));
                    if (beta <= value) {
                        return saveValue(j,value);
                    }
                    alpha = Math.max(alpha, value);
                }
            }
        } else {
            value = 10000000;
            for (Point depart : cubes_access) {
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),
                        (int) depart.getY());
                for (Point arrivee : coups_jouables) {
                    Jeu clone = j.clone();
                    if (clone.jouer_coup((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),
                            (int) depart.getY()) == 2) {
                                this.penaltyIA(clone);
                    }
                    value = Math.min(value, MinMaxIA(clone, depth - 1, player_max, alpha, beta, IA));
                    if (alpha >= value) {
                        return saveValue(j,value);
                    }
                    beta = Math.min(beta, value);
                }
            }
        }

        return saveValue(j,value);
    }
    

    public ArrayList<ArrayList<Point>> coupIA(Jeu j, int joueur1, int difficulté) {
        ArrayList<ArrayList<Point>> resultat_ok = new ArrayList<>();
        int value_max = -1000000000/* (int) Double.NEGATIVE_INFINITY */;
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        for (Point depart : cubes_access) {
            ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(), (int) depart.getY());
            for (Point arrivee : coups_jouables) {
                ArrayList<Point> pos = new ArrayList<>();
                pos.add(depart);
                pos.add(arrivee);
                Jeu clone = j.clone();
                clone.jouer_coup(arrivee.x, arrivee.y, depart.x, depart.y);
                int value = 0;
                switch (difficulté) {
                    case 0:
                        value = MinMaxIA(clone, 2, joueur1, -100000000, +100000000, 0);
                        break;
                    case 1:
                        value = MinMaxIA(clone, 4, joueur1, -100000000, +100000000, 1);
                        break;
                    case 2:
                        value = MinMaxIA(clone, 6, joueur1, -100000000, +100000000, 2);
                        break;
                }

                if (value == value_max) {
                    resultat_ok.add(pos);
                } else if (value > value_max) {
                    resultat_ok.clear();
                    resultat_ok.add(pos);
                    value_max = value;
                }
            }
        }
        return resultat_ok;
    }

    public ArrayList<Point> penaltyIA(Jeu j) {      /* la penalitee doit aussi prendre en compte que l'ia n'ouvre pas a l'autre personne plus de coup */
        ArrayList<Point> resultat_ok = new ArrayList<>();   /* Potentiellement en faisant la difference entre le nombre de coup possible a jouer entre l'ia et le joueur */
        int max = -1;
        Point x_y_to_take = new Point();
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        for (Point cubes : cubes_access) {
            if(j.getPlayer().get(cubes.x,cubes.y) == Cube.Blanc) {              /* A corriger quel cube blanc a prendre */
                resultat_ok.clear();
                resultat_ok.add(cubes);
                return resultat_ok;
            }
            int nb = j.CubeAccessibleDestinations((int) cubes.getX(), (int) cubes.getY()).size();
            x_y_to_take.x = (int) cubes.getX();
            x_y_to_take.y = (int) cubes.getY();
            if (max < nb) {
                resultat_ok.clear();
                resultat_ok.add(x_y_to_take);
                max = nb;
            } else if (nb == max) {
                resultat_ok.add(x_y_to_take);
            }
        }
        return resultat_ok;
    }

    public ArrayList<Cube> cubePotentiel() {
        Player player = jeu.getPlayer(indiceJoueur);
        ArrayList<Cube> list = new ArrayList<>();
        for (Cube cube : Cube.values()) {
            if ((cube != Cube.Blanc && cube != Cube.Neutre) && player.getPersonalBag().contains(cube)
                    && (jeu.destination(cube).size() > 1)) {
                list.add(cube);
            }
        }
        return list;
    }

    public Pyramid generePyramide() {
        return generePyramide(false);
    }

    public Pyramid generePyramide(boolean aide) {
        Player player;
        ArrayList<Cube> list = cubePotentiel();
        Jeu clone = jeu.clone();
        player = clone.getPlayer(indiceJoueur);
        player.resetBag();
        
        BestPyramide ZeBest = new BestPyramide();
        Thread manager = new Thread(new ConstructionThreadManager(clone, ZeBest, list, indiceJoueur));
        manager.start();

        // phaseConstruction = jeu.endConstruction((indiceJoueur+1)%2);

        if (aide) {
            try {
                Thread.sleep(5000);
                while (true) {
                    Thread.sleep(100);
                    if (ZeBest.getPyramid() != null) {
                        ZeBest.finish();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("interuption caught in IA in construction");
                System.exit(1);
            }
        } else {
            try {
                Thread.sleep(5000);
                while (true) {
                    Thread.sleep(100);
                    if (jeu.gameStarted() && ZeBest.getPyramid() != null) { /*
                                                                              * a decommenter lorsqu'on integre a l'ihm
                                                                              */
                        ZeBest.finish();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("interuption caught in IA in construction");
                System.exit(1);
            }
        }

        try {
            manager.join();
        } catch (InterruptedException e) {
            System.err.println("Interuption catched for the construction manager");
            System.exit(1);
        }

        // System.out.println(ZeBest);

        return ZeBest.getPyramid();
    }


    public int jouer_coup() {
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, difficulte);
        if(coups_possibles.size()!=0){Random random = new Random();
        ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
        return  jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(),
                (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());            }
        return 0;
    }

    public void construction(){
        construction(false);
    }
    public void construction(boolean aide) {
        throw new UnsupportedOperationException();
    }

    public void constructionAleatoire() {
        jeu.constructionAleatoire(indiceJoueur);
    }

    public void takePenaltyCube() {
        ArrayList<Point> coups_possibles = penaltyIA(jeu);
        if (coups_possibles.size() != 0) {
            Random random = new Random();
            Point coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
            jeu.takePenaltyCube((int) coup_a_jouer.getX(), (int) coup_a_jouer.getY());
        }
    }

    public void generationPyramide(){
        generationPyramide(false);
    }
    public void generationPyramide(boolean aide) {
        Pyramid pyramide = generePyramide(aide);
        jeu.getPlayer(indiceJoueur).build(pyramide);
        heuristique = new HashMap<>();
    }

    public Thread thread(){
        return constructionThread;
    }
    // public void endConstruction(){
    // phaseConstruction = false;
    // }

}
package Model.IA_pack;
import Model.*;

import java.util.*;
import java.awt.Point;
import Model.Runnables.*;

public abstract class IA {
    Jeu jeu;
    int difficulte, indiceJoueur;
    
    public static IA nouvelle(Jeu j,int difficulte,int indiceJoueur) {
        IA resultat = null;
        resultat = new IAFacile();

        switch (difficulte) {
            case -1:
                //resultat = new IAAleatoire();
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
        }
        return resultat;
    }

    public int MinMaxIA(Jeu j,int depth, int player_max, int alpha, int beta, int IA){
        int value;
        boolean bon_joueur = player_max == j.get_player();
        ArrayList<Point> cubes_access = j.Accessible_Playable();
        
        if(j.check_loss() || j.End_Game()){ //Condition de défaite de tous les autres joueurs en même temps à implémenter
                                             //Besoin d'une fonctione auxiliaire qui permet lors de tests de si un joueur a perdu de l'exclure du calcul
                                             //Et appeler l'IA avec les nouveaux paramètres.
            if(j.getPlayer(player_max).lost()){
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
            ArrayList<Point> cubes_access2 = new ArrayList<>();
            
            switch (IA){
            case 0: //IA Facile
                if(bon_joueur){
                    return j.getPlayer().totalCube() - j.getPlayer(j.next_player()).totalCube();
                }
                else{
                    return j.getPlayer(j.next_player()).totalCube() - j.getPlayer().totalCube();
                }
            case 1 : //IA Medium
                cubes_access2 = j.Accessible_Playable(j.next_player()); //Necessaire de pouvoir récupérer les positions accessibles du joueur adverse
                for(Point compte : cubes_access2) { //Compte du nombre de coups jouable du j1
                    int current_possibilities = j.CubeAccessibleDestinations(j.getPlayer(j.next_player()),(int) compte.getX(),(int) compte.getY()).size();
                    total_j2+= current_possibilities;
                }

                total = (int)(total_j1) + (int)(j.getPlayer().totalCube()) - (int)(j.getPlayer(j.next_player()).totalCube()) - (int)(total_j2);
                if(bon_joueur){
                    return total;
                }
                else{
                    return -total;
                }
            case 2 : //IA Difficile
                cubes_access2 = j.Accessible_Playable(j.next_player()); //Necessaire de pouvoir récupérer les positions accessibles du joueur adverse
                for(Point compte : cubes_access2) { //Compte du nombre de coups jouable du j1
                    int current_possibilities = j.CubeAccessibleDestinations(j.getPlayer(j.next_player()),(int) compte.getX(),(int) compte.getY()).size();
                    total_j2+= current_possibilities;
                }
                total = (int)(total_j1 ) + (int)(j.getPlayer().totalCube()*2) - (int)(j.getPlayer(j.next_player()).totalCube()*2) - (int)(total_j2);
                if(bon_joueur){
                    return total; 
                }
                else{
                    return -total;
                }
            }
        }
        if(bon_joueur){
            value = -1000;
            for(Point depart : cubes_access){
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),(int) depart.getY());
                for(Point arrivee : coups_jouables){
                    Jeu clone = j.clone();
                    if(clone.jouer_coup((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY()) == 2){ //On joue une pénalité
                        for(Point access : clone.Accessible_Playable()){
                            Jeu clone_pen = clone.clone();
                            clone_pen.takePenaltyCube((int) access.getX(), (int) access.getY());
                            value = Math.max(value,MinMaxIA(clone_pen,depth-1,player_max, alpha, beta, IA));
                        }
                    }
                    else{
                        //System.out.println( value + " " + depth + " " + alpha + " " + beta );
                        value = Math.max(value,MinMaxIA(clone,depth-1,player_max, alpha, beta, IA));
                        /*System.out.println();
                        System.out.println();
                        System.out.println();*/
                    }
                    if(beta <= value){
                        return value;
                    }
                    alpha = Math.max(alpha,value);
                }
            }
        }
        else{
            value = 1000;
            for(Point depart : cubes_access){
                ArrayList<Point> coups_jouables = j.CubeAccessibleDestinations((int) depart.getX(),(int) depart.getY());
                for(Point arrivee : coups_jouables){
                    Jeu clone = j.clone();
                    if(clone.jouer_coup((int) arrivee.getX(), (int) arrivee.getY(), (int) depart.getX(),(int) depart.getY())==2){
                        for(Point access : clone.Accessible_Playable()){
                            Jeu clone_pen = clone.clone();
                            clone_pen.takePenaltyCube((int) access.getX(), (int) access.getY());
                            value = Math.min(value,MinMaxIA(clone_pen,depth-1,player_max, alpha, beta, IA));
                        }
                    }
                    else{
                        value = Math.min(value,MinMaxIA(clone,depth-1,player_max, alpha, beta, IA));
                    }
                    if(alpha >= value){
                        return value;
                    }
                    beta = Math.min(beta,value);
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

    public ArrayList<Cube> cubePotentiel(){
        Player player = jeu.getPlayer(indiceJoueur);
        ArrayList<Cube> list = new ArrayList<>();
        for(Cube cube : Cube.values()){
            if((cube != Cube.Blanc && cube != Cube.Neutre) && player.getPersonalBag().contains(cube) && (jeu.destination(cube).size() > 1)){
                list.add(cube);
            }
        }
        return list;
    }

    public Pyramid generePyramide(){
        return generePyramide(false);
    }

    public Pyramid generePyramide(boolean aide){
        Player player;
        ArrayList<Cube> list = cubePotentiel();
        Jeu clone = jeu.clone();
        player = clone.getPlayer(indiceJoueur);
        player.resetBag();

        BestPyramide ZeBest = new BestPyramide();
        Thread manager = new Thread(new ConstructionThreadManager(clone,ZeBest,list,difficulte,indiceJoueur));
        manager.start();

        //phaseConstruction = jeu.endConstruction((indiceJoueur+1)%2);
        
        if(aide){
            try{Thread.sleep(3000);        
                while(true){
                Thread.sleep(100);
                    if(ZeBest.getPyramid() != null){ /* a decommenter lorsqu'on integre a l'ihm */
                        ZeBest.finish();
                        break;
                    }
                }
            }catch(InterruptedException e){System.err.println("interuption caught in IA in construction");System.exit(1);}
        }
        else{
            try{Thread.sleep(10000);        
                while(true){
                Thread.sleep(100);
                    if(!jeu.gameStarted() && ZeBest.getPyramid() != null){ /* a decommenter lorsqu'on integre a l'ihm */
                        ZeBest.finish();
                        break;
                    }
                }
            }catch(InterruptedException e){System.err.println("interuption caught in IA in construction");System.exit(1);}
        }
        
        
        
        try{manager.join();}
        catch(InterruptedException e){System.err.println("Interuption catched for the construction manager");System.exit(1);}
        
        //System.out.println(ZeBest);
        
        return ZeBest.getPyramid();
    }
    
    public int jouer_coup() {  /* Modifier pour savoir si il y a une penalitee */
        throw new UnsupportedOperationException();
    }
    
    public void construction(){
        throw new UnsupportedOperationException();
    }
    public void constructionAleatoire(){
        jeu.constructionAleatoire(jeu.getPlayer(indiceJoueur));
    }

    public void takePenaltyCube(){
        ArrayList<Point> coups_possibles = penaltyIA(jeu);
        if(coups_possibles.size() != 0){
            Random random = new Random();
            Point coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
            jeu.takePenaltyCube((int) coup_a_jouer.getX(), (int) coup_a_jouer.getY());
        }
    }

    //public void endConstruction(){
    //    phaseConstruction = false;
    //}
    
}
package Model;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import Model.Historique.Historique;

public class Jeu implements Cloneable{
    Player[] players;
    int nbJoueur;
    Pyramid principale;
    PawnsBag bag;
    int current_player, size;
    boolean End;
    //Model.Historique.Historique hist;

        /*****************************/
        /* Fonction creation du jeu */
        /***************************/

    public Jeu(int nb){             /* creation de l'objet jeu ainsi que les joueurs */
        reset(nb);
    }

    public Jeu(String fileName){
        reset(fileName);
    }

    public void reset(int nb){
        nbJoueur = nb;
        End = false;
        players = new Player[nb];
        //hist = new Historique();

        bag = new PawnsBag(nb);
        principale = new Pyramid(9);
        for(int i = 0;i < nb; i++ ){
            size = 8-nb;
            players[i] = new Player(size);
            if(nb!=4){
                for(int j = 0; j < 4/nb; j++){
                    players[i].addBag(Cube.Blanc);
                    players[i].addBag(Cube.Neutre);
                }
                if(nb == 3){players[i].addBag(Cube.Neutre);}
            }
            else{
                players[i].addSide(Cube.Blanc);
                players[i].addBag(Cube.Neutre);
            }
        }
        Random r = new Random();
        current_player = r.nextInt(nb);
    }

    public void reset(String fileName){
        try{
            Scanner s = new Scanner(new FileInputStream(fileName));
            String[] chaine = s.nextLine().split(" ");
            //hist = new Historique();
            nbJoueur = Integer.parseInt(chaine[0]);
            bag = new PawnsBag(nbJoueur);
            size = 8-nbJoueur;
            current_player = Integer.parseInt(chaine[1]);
            principale = new Pyramid(s.nextLine());
            players = new Player[nbJoueur];
            String[] playerString = new String[4];
            for(int i = 0; i < nbJoueur; i++){
                for(int j = 0; j < 4; j++){
                    playerString[j] = s.nextLine();
                }
                players[i] = new Player(playerString);
            }
            End = false;
            s.close();
        }
        catch(FileNotFoundException e){System.err.println(e);System.exit(2);}
    }

    //Tirage de la base de la pyramide central 
    public void initPrincipale(){       /* base central aleatoire */
        int y = 0;
        for( Cube cube : bag.init_center()){
            principale.set(0, y, cube);
            y++;
        }
    }

    public void initPrincipale(ArrayList<Cube> bag){        /*base central deja instancier */
        int y = 0;
        for( Cube cube : bag ){
            principale.set(0, y, cube);
            y++;
        }
    }

    public void sauvegarde(String fileName){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            String sauvegarde = "";
            sauvegarde += nbJoueur + " " + current_player + "\n";
            sauvegarde+= principale.sauvegarde();
            for(int i = 0; i < nbJoueur; i++){
                sauvegarde += players[i].sauvegarde();
            }
            writer.write(sauvegarde);
            writer.close();
        }
        catch(IOException e){System.err.println(e);System.exit(2);}
    }

    public Pyramid getPricipale(){
        return principale;
    }

    public Cube getCubePrincipale(int x,int y){
        return principale.get(x,y);
    }


    public void constructionAleatoire(Player player){
        for(int i = player.getSize()-1; i >= 0; i--){
            for(int j = 0; j < player.getSize()-i; j++){
                if(player.getPyramid().get(j, i) == Cube.Vide && !player.bagEmpty()){player.construction(i, j, player.getPersonalBag().get(0));}
            }
        }    
    }
        /************************************ */
        /* Fonction lier a une action de jeu */
        /********************************** */

    /* ordre de jeu */

    public void avance(){           /* le bon joueur est envoyer */
        current_player = next_player();
    }


    /** Coup **/
    /** Debut de partie **/

    public boolean draw(){
        if(bag.getSize() > 2){
            for(Cube c : bag.draw()){
                players[current_player].addBag(c);
            }
            avance();
            return true;
        }
        return false;
    }

    public void construction(int x, int y, Cube cube){
        getPlayer().construction(x, y, cube);
    }

    /*Swaps two cubes positions*/
    public void permutation(int x, int y, int x_p, int y_p){
        getPlayer().permutation(x, y, x_p, y_p);
    }

    public void resetBag(){
        getPlayer().resetBag();
    }


    /** Jouer **/
    public void setCubePlayer(int x, int y, Cube cube){     /* Ajoute le cube au coordonnee x y de la pyramide du joueur courant  */
        getPlayer().set(x, y, cube);
        avance();
    }

    /* joue un coup dans la pyramide central, si l'y du cube a jouer est egale a -1 le cube sera piochee du side */
    // 0 -> NOT VALID
    // 1 -> VALID
    // 2 -> VALID WITH PENALITY
    // 3 -> PLAY WHITE
    public int add_central(int x_central, int y_central, int x_player, int y_player){
        if (y_player==-1){
            return add_central_side(x_central, y_central, x_player);
        } else {
            return add_central_pyramid(x_central, y_central, x_player, y_player);
        }
    }

    public int add_central_pyramid(int x_central, int y_central, int x_player, int y_player){   /* meme valeur renvoyer */
        if(accessible(x_player, y_player)){
            Cube cube = players[current_player].get(x_player, y_player);
            int valid = move_validity(cube, x_central, y_central);
            if(valid == 3){joueBlancPyramide(x_player, y_player);}
            else if(valid != 0){
                players[current_player].set(x_player, y_player, Cube.Vide);
                principale.set(x_central, y_central, cube);
                if(x_central == 9){
                    principale.extend();
                }
            }
            //hist.action(1,new Point(x_player,y_player), new Point(x_central,y_central));
            return valid;
        }
        return 0;
    }

    public int add_central_side(int x_central, int y_central, int x_player){        /* meme valeur renvoyer */
        Cube cube = players[current_player].getSide(x_player);
        int valid = move_validity(cube, x_central, y_central);
        if(valid == 3) {
            joueBlancSide(x_player);
        }
        else if(valid != 0){
            players[current_player].removeSide(x_player);
            principale.set(x_central, y_central, cube);
            //hist.action(2, new Point(cube.getInt(),-1),new Point(x_central,y_central));
        }
        return valid;
    }

    public boolean joueBlancPyramide(int x, int y){
        if(getPlayer().get(x,y) == Cube.Blanc){
            getPlayer().remove(x, y);
            //hist.action(5,new Point(x,y), null);
            return true;
        }
        return false;
    }

    public boolean joueBlancSide(int x){
        if(getPlayer().getSide(x) == Cube.Blanc){
            getPlayer().removeSide(x);
            //hist.action(6, null, null);
            return true;
        }
        return false;
    }

        /* Penalitee */
    
    public void takePenaltyCube(int x,int y){
        if (y==-1){
            takePenaltyCubeFromSide(x);
        }else{
            takePenaltyCubeFromPyramid(x,y);
        }
    }

    public void takePenaltyCubeFromPyramid(int x,int y) {               /*Recupere le cube de la position x y du joueur courant et l'ajoute au side du joueur precedent */
        Cube cube  = players[current_player].get(x,y);
        players[next_player()].addSide(cube);
        getPlayer().remove(x,y);
        //hist.action(3,new Point(x,y), new Point(cube.getInt(),-1));
    }

    public void takePenaltyCubeFromSide(int x) {            /* Recupere le cube de la position x dans la liste de cotee du joueur courant et l'ajoute au side du joueur precedent */
        Cube cube = players[current_player].getSide(x);
        players[next_player()].addSide(cube);
        getPlayer().removeSide(x);
        //hist.action(4, new Point(cube.getInt(),-1), null);
    }

    

    /*************/

        /*************************** */
        /* Fonction de verification */
        /************************* */

    /* Penalitee */

    public boolean check_penality(int x, int y) {  
        return principale.get(x-1, y) == principale.get(x-1, y+1);
    }

    public boolean check_under(int x, int y){
        return !sameColor(principale.get(x-1, y),Cube.Vide) && !sameColor(principale.get(x-1, y+1),Cube.Vide);
    }

    public boolean sameColor(Cube c1,Cube c2){
        return (c1 == c2) || (c1 == Cube.Neutre) || (c2 == Cube.Neutre);
    }


    //MOVE VALIDITY :
    // 0 -> NOT VALID
    // 1 -> VALID
    // 2 -> VALID WITH PENALITY
    // 3 -> PLAY WHITE
    public int move_validity(Cube cube, int x, int y){          /* bonne validitee renvoyee */
        if(cube == Cube.Blanc) return 3;
        if ( sameColor(principale.get(x, y), Cube.Vide) && check_under(x,y) && (sameColor(principale.get(x-1, y),cube) || ( sameColor(principale.get(x-1, y+1),cube))) ){
            if (check_penality(x, y)){
                return 2;
            }
            return 1;
        }
        else {return 0;}
    }


    /* Accessibilitee */
    public boolean accessible(int x, int y){
        return accessible(x,y,current_player);
    }

    public boolean accessible(int x, int y, int joueur){

        Pyramid pyramid = getPlayer(joueur).getPyramid();
        return accessible(pyramid , x, y);
    }

    public boolean accessible(Pyramid pyramid , int x, int y){
        return (pyramid.get(x, y) != Cube.Vide) && (( x == size-1 && y == 0 ) || (( y == size-x-1 || pyramid.get(x+1, y) == Cube.Vide) && (y == 0 || pyramid.get(x+1, y-1)== Cube.Vide)));
    }


    public boolean case_dessus_possible(int x, int y){          /* renvoie vrai si l'on peu poser un cube sur un cube de la pyramide central */
        if( (principale.get(x, y) != Cube.Vide) && ( !caseAdjacenteVide(x, y) ) && ( principale.get(x+1, y) == Cube.Vide || ( y != 0 && principale.get(x+1, y-1) == Cube.Vide ))) {return true;}
        return false;
    }

    public boolean caseAdjacenteVide(int x, int y){         /* renvoie vrai si les cases adjacente sont vide */
        return (( y == 0 || principale.get(x, y-1) == Cube.Vide ) && (y == (principale.getSize()-1-x) || principale.get(x, y+1) == Cube.Vide) );
    }

    /* Fin de partie */
    public boolean check_loss(){            /* Verifie si le joueur courrant n'a aucun coup possible, s'il ne peut rien jouer le joueur courant est le prochain joueur */
        if(noPlay() || getPlayer().totalCube() == 0){
            getPlayer().playerLost();
            int next = next_player();
            if(next == next_player(next)){End = true;}          /* si un joueur est eliminer et que le prochain est le meme que le prochain du prochain, le joueur est donc seul et est le vainqueur */
            return true;
        }
        return false;
    }

    public boolean noPlay(){
        return Accessible_Playable().size()==0;
    }

        /**************************************** */
        /* Fonction lier aux informations du jeu */
        /************************************* */

    public Pyramid getPrincipale(){
        return principale;
    }

    /* Recuperation de l'indice d'un joueur */
    public int next_player(){               /* Fonctionne */
        return next_player(current_player);
    }

    public int next_player(int player){     /* renvoie le l'indice du prochain joueur */
        int next_player = (player+1)%nbJoueur;
        while ( players[next_player].lost() == true ){
            next_player = (next_player+1)%nbJoueur;
        }
        return next_player;
    }
    //Determine the previous player out of those still in the game
    public int previous_player(){
        return previous_player(current_player);
    }

    public int previous_player(int current_player){        /*renvoie l'indice du joueur precedent */

        int previous_player = (current_player + nbJoueur - 1) % nbJoueur;
        while(players[previous_player].lost() == true){
            previous_player = (previous_player + nbJoueur - 1) % nbJoueur;
        }
        return previous_player;
    }

    /* Information pratique joueur courant */


    //Ammount of cubes in a player's hand
    public int TotCubesHand (int i){
        return getPlayer(i).totalCube();
    }

    //Ammount of a colour in the current player's hand
    public int ColourAmmount (Cube cube){
        return getPlayer().ColourAmmount(cube);
    }
    public int[] compte_personal_bag(){
        return getPlayer().compte_personal_bag();
    }

    public ArrayList<Point> AccessibleCubesPlayer(){
        return AccessibleCubesPlayer(current_player);
    }

    public ArrayList<Point> AccessibleCubesPlayer(int joueur){            /* Fonctionne mais crash?(tres rarement)*/
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i=getPlayer(joueur).getSize()-1; i>=0; i--){
            for (int j=0; j<getPlayer(joueur).getSize()-i; j++){
                if (accessible(i,j, joueur)){
                    Point p = new Point(i, j);
                    list.add(p);
                }
            }
        }
        return list;
    }

    public ArrayList<Point> destination(Cube cube){
        ArrayList<Point> list = new ArrayList<>();
        if(cube == Cube.Blanc){
            list.add(new Point(-1,-1));
            return list;
        }
        for(int i = principale.getSize()-1; i >= 0; i--){
            for(int j = 0; j < principale.getSize()-i; j++){
                if( move_validity(cube,i,j)!=0){
                    Point p = new Point(i,j);
                    list.add(p);
                }
            }
        }
        return list;
    }
    public ArrayList<Point> CubeAccessibleDestinationBag(int index){        
        return destination(getPlayer().getPersonalBag().get(index));
}

    //COORD POSITION POSSIBLES POUR UN CUBE DONNEE
    public ArrayList<Point> CubeAccessibleDestinations(int x, int y){
        if (y==-1){
            return destination(getPlayer().getSide(x));
        }
        else{
            return destination(getPlayer().get(x, y));
        }
    }
    

    public ArrayList<Point> Accessible_Playable(){
        return Accessible_Playable(current_player);
    }

    public ArrayList<Point> Accessible_Playable(int i){          /* parmis les cube accessible les quel sont possible d'etre jouer, renvoie une liste de coordonee */
        HashMap<Cube,Boolean> list = accessibleColors();
        ArrayList<Point> Aksel = new ArrayList<Point>();

        for(Point e : AccessibleCubesPlayer(i)){
            Cube cube = getPlayer(i).get(e.x, e.y);
            if(cube == Cube.Blanc || cube == Cube.Neutre || list.get(cube) != null){
                Aksel.add(e);
            }
        }
        int x = 0;
        for(Cube c : getPlayer(i).getSide()){
            if(c == Cube.Blanc || c == Cube.Neutre || list.get(c) != null){
                Point p = new Point(x, -1);
                Aksel.add(p);
            }
            x++;
        }
        return Aksel;
    }

    public HashMap<Cube,Boolean> accessibleColors(){    /* renvoie un dictionnaire avec comme clef une couleur de cube et un booleen en fonction de si la couleur est accessible sur la pyramide du milieu */
        HashMap<Cube,Boolean> list = new HashMap<>();
        for(int i = principale.getSize()-1; i >= 0; i--){
            for(int j = 0; j < principale.getSize()-i; j++){
                if(case_dessus_possible(i, j)){
                    list.put(principale.get(i, j), true);
                }
            }
        }
        return list;
    }


    public Point findFirstFreeElement() {   //return first free or (-1,-1)
        for (int i = getPlayer().getSize()-1; i >= 0; i--) {
            for (int j = 0; j < getPlayer().getSize()-i; j++) {
                if (getPlayer().get(i,j)==Cube.Vide) {
                    return (new Point(i,j));
                }
            }
        }
        return (new Point(-1,-1));
    }


    public String bag(){     /* le tostring du bag */
        return bag.toString();
    }

    /* Fin de partie */

    public boolean End_Game(){
        return End;
    }


    /************************* */
    /* Recuperation de donnee */
    /*********************** */

    public int get_player(){
        return current_player;
    }

    public int nbJoueur(){
        return nbJoueur;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    public Player getPlayer(){
        return getPlayer(current_player);
    }

    public Pyramid getPyrPlayer(int i){
        return getPlayer(i).getPyramid();
    }

    public ArrayList<Cube> getPlayerBag(){
        return getPlayer().personalBag;
    }

    public ArrayList<Cube> getPlayerBag(int i){
        return getPlayer(i).personalBag;
    }

    public Jeu clone() {
        try{
            Jeu clone = (Jeu) super.clone();  // Clone the basic object structure

            clone.players = new Player[nbJoueur];
            for (int i = 0; i < nbJoueur; i++) {
                clone.players[i] = players[i].clone();
            }   
            clone.principale = principale.clone();
            clone.bag = bag.clone();

            return clone;
        }catch(Exception e){System.err.println("Error Clone");System.exit(1);}
        return new Jeu(2);
    }
}
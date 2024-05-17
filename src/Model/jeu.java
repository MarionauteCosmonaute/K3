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

public class jeu {
    Player[] players;
    int nbJoueur;
    Pyramid principale;
    PawnsBag bag;
    int current_player, size;
    boolean End;

    public jeu(int nb){             /* creation de l'objet jeu ainsi que les joueurs */
        reset(nb);
    }

    public jeu(String fileName){
        reset(fileName);
    }

    public void reset(int nb){
        nbJoueur = nb;
        End = false;
        players = new Player[nb];

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
                if(player.getPyramid().get(j, i) == Cube.Vide && !player.bagEmpty()){player.construction(j, i, player.getPersonalBag().get(0));}
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
    


}

import java.util.Scanner;

import Model.*;

public class Host {
    public static void main(String[] args) {
        JeuOnline jeu = new JeuOnline("localhost:" + args[0], true);
        //System.out.println(jeu);
        jeu.constructionAleatoire(0);
        jeu.doneConstruction();
        jeu.gameStart();
        System.out.println("jeu a commencer\npremier joueur est: " + jeu.get_player());
        Scanner s = new Scanner(System.in);
        String[] entree;
        int x_c,y_c,x_j,y_j;
        while(!jeu.End_Game()){
            //System.out.println("joueur courant est: " + jeu.get_player());
            if(jeu.get_player() == 0){
                System.out.println(jeu);
                entree = s.nextLine().split("\\s+");
                x_c = Integer.parseInt(entree[0]);
                y_c = Integer.parseInt(entree[1]);
                x_j = Integer.parseInt(entree[2]);
                y_j = Integer.parseInt(entree[3]);
                jeu.jouer_coup(x_c,y_c,x_j,y_j);
            }
            /*else{
                try{Thread.sleep(3000);}
                catch(Exception e){}
            }*/
        }
        s.close();
    }
}

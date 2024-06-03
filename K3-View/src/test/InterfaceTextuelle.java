package test;
import java.util.Scanner;

import Model.*;

public class InterfaceTextuelle {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int input;
        Jeu jeu;
        if(args[0].equals("charge")){
            jeu = new Jeu(args[1]);
        }
        else{
            System.out.print("How many players do you want: ");
            input = s.nextInt();
            jeu = new Jeu(Integer.parseInt(args[0]));
            jeu.initPrincipale();    
        }
        boolean not = true, start = false;
        Player player;
        int x = 0, y = 0, z = 0, w = 0, validity = 0;

        String[] entree;
        System.out.println("help pour afficher le menue");


        while(true){
            if(jeu.End_Game()){
                System.out.println("Game Done winner is player: " + (jeu.get_player() + 1) );
                break;
            }
        }
    }
}

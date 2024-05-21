import java.util.Scanner;

import Model.*;

public class InterfaceTextuelle {
    static void action(String[] string){
        boolean not = true;
        switch (string[0]) {
            case " ":
                
                break;
        
            default:
                break;
        }
    }


    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int input;
        Jeu jeu;
        if((args.length == 2 ) && args[0].equals("charge")){
            jeu = new Jeu(args[1]);
        }
        else if(args.length == 0)  {
            System.out.print("How many players do you want: ");
            input = s.nextInt();
            jeu = new Jeu(input);
            jeu.initPrincipale();    
        }
        else{System.out.println("invalide arguments");System.exit(1);}

        boolean start = false;
        int x = 0, y = 0, z = 0, w = 0, validity = 0;

        String[] entree;
        System.out.println("help pour afficher le menue");


        while(true){
            if(jeu.End_Game()){
                System.out.println("Game Done winner is the player: " + (jeu.get_player() + 1) );
                break;
            }
            if(start && jeu.check_loss()){jeu.avance();}
            else{
                entree = s.nextLine().split("\\s+");
                action(entree);
            }
        }
        s.close();
    }
}

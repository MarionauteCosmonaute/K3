package Runnables;
import Model.*;
import java.awt.*;
import java.util.Random;

public class IAvsIA{

    public static void main(String[] args){
        Jeu jeu = new Jeu(2);
        int nb_games = Integer.parseInt(args[0]);
        int difficulte1 = Integer.parseInt(args[1]);
        int difficulte2 = Integer.parseInt(args[2]);
        IA ia1 = IA.nouvelle(jeu, difficulte1);
        IA ia2 = IA.nouvelle(jeu, difficulte2);         /* pas tres sur de le faire avec une ia facile */
        int win_IA1;
        win_IA1 = 0;

        for(int i=0; i<nb_games; i++){
            jeu = new Jeu(2);
            while(!jeu.End_Game()){
                if(jeu.check_loss()){
                    if(jeu.getPlayer(1).lost()){
                        win_IA1++;
                    }
                }
                else{
                    if(jeu.get_player() == 0){
                        if(ia1.add_central()==2){
                            ia2.takePenaltyCube();
                        }
                    }
                    else{
                        if(ia2.add_central()==2){
                            ia1.takePenaltyCube();
                        }
                    }
                }
            }
        }
        System.out.println("winrate IA 1 = " + win_IA1/nb_games*100);
        System.out.println("winrate IA 2 = " + (100-win_IA1/nb_games*100));
    }
}


import Model.*;
import Model.IA.IA;

public class IAvsIA{

    public static void main(String[] args){
        Jeu jeu = new Jeu(2);
        int nb_games = Integer.parseInt(args[0]);
        int difficulte1 = Integer.parseInt(args[1]);
        int difficulte2 = Integer.parseInt(args[2]);
        IA ia1 = IA.nouvelle(jeu, difficulte1,0);
        IA ia2 = IA.nouvelle(jeu, difficulte2,1);         /* pas tres sur de le faire avec une ia facile */
        int win_IA1,nbCoup,game_done = nb_games;
        win_IA1 = 0;


        
        
        for(int i=0; i<game_done; i++){
            nbCoup = 0;
            jeu.reset(2);
            jeu.initPrincipale();
            while(jeu.draw()){}
            ia1.construction();
            ia2.construction();
            while(!jeu.End_Game()){
                if(jeu.check_loss()){
                    if(jeu.getPlayer(1).lost()){
                        if(nbCoup > 5){win_IA1++;}
                        else{nb_games--;}
                        //System.out.println("elle a perdu apres " + nbCoup + "on est a " + win_IA1);
                    }
                }
                else{
                    nbCoup++;
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

        float winrate1 = (float)((float)win_IA1/(float)nb_games)*(float)100;
        float winrate2 = (float)(100 - winrate1);
        System.out.println("winrate IA 1 = " + winrate1 + "%");
        System.out.println("winrate IA 2 = " + winrate2 + "%");
    }
}

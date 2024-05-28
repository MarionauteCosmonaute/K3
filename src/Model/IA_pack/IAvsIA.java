package Model.IA_pack;

import Model.Jeu;
import Model.Info.GameInfo;

public class IAvsIA {
    Jeu jeu;
    int difficulte1, difficulte2;
    boolean construction;

    public IAvsIA(Jeu jeu, int difficulte1, int difficulte2, boolean constructionAleatoire){
        this.jeu = jeu;
        this.difficulte1 = difficulte1;
        this.difficulte2 = difficulte2;
        this.construction = constructionAleatoire;
    }
    
    public GameInfo play(){
        int nbCoup0 = 0,nbCoup1 = 0;
        IA ia0 = IA.nouvelle(jeu,difficulte1,0);
        IA ia1 = IA.nouvelle(jeu, difficulte2, 1);
        construction(ia0,ia1);
        
        while(!jeu.End_Game()){
            if(jeu.check_loss()){}
            else{
                if(jeu.get_player() == 0){
                    nbCoup0++;
                    if(ia0.jouer_coup() == 2){ia1.takePenaltyCube();}
                }
                else{
                    nbCoup1++;
                    if(ia1.jouer_coup() == 2){
                        ia0.takePenaltyCube();
                    }
                }
            }
        }
        
        int winner;
        if(jeu.getPlayer(0).lost()) winner = 1; 
        else winner = 0;

        int[] coup = new int[2];
        coup[0] = nbCoup0;
        coup[1] = nbCoup1;

        return new GameInfo(winner,coup);
    }

    private void construction(IA ia0, IA ia1){
        if(construction){
            jeu.constructionAleatoire(jeu.getPlayer(0));
            jeu.constructionAleatoire(jeu.getPlayer(1));
        }
        else{
            ia0.construction();
            ia1.construction();
        }
    }
    
}

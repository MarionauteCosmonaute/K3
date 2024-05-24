package Model.Runnables;

import Model.*;

public class ConstructionRunable implements Runnable{
    Jeu jeu;
    BestPyramide best;
    Pyramid pyramid;
    int index, difficulte1, difficulte2;

    public ConstructionRunable(Jeu jeu, BestPyramide best,int index,int difficulte1){
        this.jeu = jeu;
        this.index = index;
        this.best = best;
        this.difficulte1 = difficulte1;
        this.difficulte2 = difficulte1;
        jeu.constructionAleatoire(jeu.getPlayer(index));
        try{pyramid = jeu.getPlayer(index).getPyramid().clone();}
        catch(CloneNotSupportedException e){System.err.println("Clone Pyramide defectueux dans 'ConstructionRunnable'");System.exit(1);}
    }

    public ConstructionRunable(Jeu jeu, int difficulte1, int difficulte2){
        this.jeu = jeu;
        index = 0;
        this.best = null;
        this.difficulte1 = difficulte1;
        this.difficulte2 = difficulte2;
        pyramid = null;
    }
    
    public void run(){
        IA ia1 = IA.nouvelle(jeu, difficulte1,index), ia2 = IA.nouvelle(jeu, difficulte2,jeu.next_player(index)) ;           /* pas tres sur de le faire avec une ia facile */
        int nbCoup = 0;
        if(best == null){
            ia1.construction();
            ia2.construction();
        }
        while(!jeu.End_Game()){
            if(jeu.check_loss()){}
            else{
                if(jeu.get_player() == index){
                    nbCoup++;
                    if(ia1.add_central() == 2){ia2.takePenaltyCube();}
                }
                else if(ia1.add_central() == 2){ia2.takePenaltyCube();}
                
            }
        }
        if(best != null){
            try{
                best.set(pyramid, jeu.getPrincipale().clone(),jeu.getPlayer(index).getPyramid().clone(),nbCoup);
                
            }
            catch(CloneNotSupportedException e){System.err.println("clone pas fonctionner pyramide");System.exit(1);}
        }
        
    }
}

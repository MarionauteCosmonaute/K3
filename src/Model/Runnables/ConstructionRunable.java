package Model.Runnables;

import Model.*;
import Model.IA.IA;

public class ConstructionRunable implements Runnable{
    Jeu jeu;
    PyramideList list;
    int difficulte, indice;

    ConstructionRunable(Jeu jeu, PyramideList list, int difficulte, int indice){
        this.jeu = jeu;
        this.list = list;
        this.difficulte = difficulte;
        this.indice = indice;
    }

    public void run(){
        IA ia1 = IA.nouvelle(jeu, difficulte,indice),ia2 = IA.nouvelle(jeu, difficulte, jeu.next_player(indice));
        ia1.constructionAleatoire();
        Pyramid pyramide = null;
        try{pyramide = jeu.getPlayer(indice).getPyramid().clone();}
        catch(CloneNotSupportedException e){System.err.println("Exception catched when creating clone for the 'ConstructionRunable'");System.exit(1);}
        int nbCoup = 0;
        while( !list.done() && !jeu.End_Game() ){
            if(jeu.check_loss()){}
            else {
                if(jeu.get_player() == indice){
                    nbCoup++;
                    if(ia1.add_central() == 2){ia2.takePenaltyCube();} 
                }else if(ia2.add_central() == 2){ia1.takePenaltyCube();}
            }
        }
        if(jeu.End_Game()){list.add(pyramide, nbCoup);}
    }
}

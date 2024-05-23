import Model.*;

public class testConstruction {
    
    public static void main(String[] args){
        Jeu jeu = new Jeu(2);
        jeu.initPrincipale();
        while(jeu.draw()){}
        IA ia = IA.nouvelle(jeu, Integer.parseInt(args[0]), 1);
        ia.construction();
        System.out.println(jeu.getPlayer(1).getPyramid());
    }
}

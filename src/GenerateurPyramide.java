import Model.*;

public class GenerateurPyramide {
    static void drawAll(Jeu jeu){
        while(jeu.draw()){}
    }
    public static void main(String[] args) {
        Jeu jeu = new Jeu(2);
        jeu.initPrincipale();
        drawAll(jeu);
        IA ia = IA.nouvelle(jeu);
        Pyramid pyramid = ia.generePyramide(0,2);
        System.out.println(jeu.getPlayer(0).getPyramid());
        System.out.println(pyramid);
    }
}

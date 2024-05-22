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
        BestPyramide pyramid = ia.generePyramide(1,10);
        System.out.println(pyramid.getPrincipal());
        //System.out.println(jeu.getPlayer(0).getPyramid());
        System.out.println("Pyramide de profondeur " + pyramid.getProfondeur() +" generer:\n" + pyramid.getPyramid());
        System.out.println("Pyramide a la fin de la partie:\n" + pyramid.getFin());
    }
}

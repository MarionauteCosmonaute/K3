import Model.*;
import Model.IA.IA;

public class GenerateurPyramide {
    static void drawAll(Jeu jeu){
        while(jeu.draw()){}
    }
    public static void main(String[] args) {
        Jeu jeu = new Jeu(2);
        jeu.initPrincipale();
        drawAll(jeu);
        IA ia = IA.nouvelle(jeu,1,1);
        ia.construction();
    }
}

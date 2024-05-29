import Model.*;
import Model.IA_pack.IA;

public class GenerateurPyramide {
    static void drawAll(Jeu jeu){
        while(jeu.draw()){}
    }
    public static void main(String[] args) {
        Jeu jeu = new Jeu(2);
        jeu.initTest();
        IA ia = IA.nouvelle(jeu,1,1);
        ia.construction();
        System.out.println(jeu.getPlayer(1).getPyramid());
    }
}

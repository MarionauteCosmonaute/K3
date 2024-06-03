package Controleur;
import java.util.Stack;

import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceUtilisateur;
import IA.IA.Direction;

public class ControleurClavierSouris implements CollecteurEvenements {
    Jeu J;
    Stack<Direction> out=new Stack<>();
    InterfaceUtilisateur IU;
    public ControleurClavierSouris(Jeu j,InterfaceUtilisateur v){
        J=j;
        IU=v;

    }

    public void clicSouris(int x,int y){
        
    }
    public void toucheClavier(String touche){
        switch (touche) {
            case "fs":
                IU.toggleFullscreen();
                break;
            case "up":
                J.move_Pousseur(Direction.UP,
                                true);

                break;
            case "down":
                J.move_Pousseur(Direction.DOWN,
                                true);
                break;
            case "left":
                J.move_Pousseur(Direction.LEFT,
                                true);

                break;
            case "right":
                J.move_Pousseur(Direction.RIGHT,
                                true);

                break;
            case "r":
                J.reset_Level();
                break;

            case "next":
                out=new Stack<>();
                if(!J.prochainNiveau()){
                    System.out.println("FIN DES NIVEAUX");
                    System.exit(0);
                }
                break;
                
            case "quit":
                System.exit(0);
                break;
            case "IA":
                out=J.run();
                System.out.println("Done");
                break;
            case "Run":
                if (!out.isEmpty()){
                    J.move_Pousseur(out.pop(),true);
                }
                break;
            default:
                break;
        }
        
    }

    
}

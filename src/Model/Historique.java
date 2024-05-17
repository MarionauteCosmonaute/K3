package Model;


import java.util.Stack;
import java.awt.Point;

public class Historique{
    public class Coup{
        int type;
        Point[] coordonnee;
        
        Coup(int type, Point[] coordonnee){
            this.type = type;
            this.coordonnee = coordonnee;
        }
    }

    Stack<Coup> coup_jouer;
    Stack<Coup> coup_retablie;

    public Historique(){
        coup_jouer = new Stack<>();
        coup_retablie = new Stack<>();
    }

    public void action(int type, Point[] coordonee){
        coup_jouer.add(new Coup(type, coordonee));
    }

    
}
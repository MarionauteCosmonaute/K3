package Model;


import java.util.Stack;
import java.awt.Point;

public class Historique{
    public class Coup{
        int type;
        Point[] coordonnee; 
        
        Coup(int type){
            this.type = type;
        }
    
    }

    Stack<Coup> coup_jouer;
    Stack<Coup> coup_annuler;

    public Historique(){
        coup_jouer = new Stack<>();
        coup_annuler = new Stack<>();

    }

    
}
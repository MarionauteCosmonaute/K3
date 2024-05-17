package Model;


import java.util.Stack;
import java.awt.Point;

public class Historique{
    

    Stack<Coup> coup_jouer;
    Stack<Coup> coup_annule;

    public Historique(){
        coup_jouer = new Stack<>();
        coup_annule = new Stack<>();
    }

    public void action(int type, Point[] coordonee){
        coup_jouer.add(new Coup(type, coordonee));
    }

    public Coup annule(){
        if(annulePossible()){
            Coup coup = coup_jouer.pop();
            coup_annule.add(coup);
            return coup;
        }
        return new Coup(-1, null);
    }

    public Coup refais(){
        if(refaisPossible()){
            
        }
        return new Coup(-1,null);
    }

    private boolean annulePossible(){
        return !coup_jouer.empty();
    }

    private boolean refaisPossible(){
        return !coup_annule.empty();
    }

    
}
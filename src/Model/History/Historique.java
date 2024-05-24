package Model.History;


import java.util.Stack;
import java.awt.Point;
import Model.Coup;

public class Historique{

    Stack<Coup> coup_jouer;
    Stack<Coup> coup_annule;

    public Historique(){
        coup_jouer = new Stack<>();
        coup_annule = new Stack<>();
    }

    public void action(int type, Point s, Point d){
        coup_jouer.add(new Coup(type, s, d));
        coup_annule = new Stack<Coup>();
    }

    public Coup annule(){
        if(isEmptyAnnule()){
            Coup coup = coup_jouer.pop();
            coup_annule.add(coup);
            return coup;
        }
        return null;
    }

    public Coup refais(){
        if(isEmptyRefaire()){
            Coup coup = coup_annule.pop();
            coup_jouer.add(coup);
            return coup;
        }
        return null;
    }

    private boolean isEmptyAnnule(){
        return !coup_jouer.empty();
    }

    private boolean isEmptyRefaire(){
        return !coup_annule.empty();
    }
}   

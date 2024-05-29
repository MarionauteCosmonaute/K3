package Model;

//import Model.*;

public class BestPyramide {
    Pyramid best = null;
    int profondeur = 0;
    boolean done = false;

    public BestPyramide(){}

    public synchronized void add(Pyramid pyramide, int prof){
        if(prof > profondeur){
            best = pyramide;
            profondeur = prof;
        }
    }

    public void finish(){
        done = true;
    }

    public boolean done(){
        return done;
    }

    public Pyramid getPyramid(){
        return best;
    }

    public String toString(){
        String chaine = "";
        chaine += "la Pyramide:\n" + best + "\nde profondeur: " + profondeur; 
        return chaine;
    }
}
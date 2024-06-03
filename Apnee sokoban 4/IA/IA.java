package IA;
import Modele.Jeu;
import Modele.Niveau;

import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Vector;

public class IA {
    
    public static enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    Jeu J;
    PriorityQueue<State> FAP=new PriorityQueue<>();

    Hashtable<State,Boolean> visited=new Hashtable<>();
    int [][] Heur;

    public IA(Jeu j){
        J=j;
    }

    int [][] initHeur(State S,Niveau N){
        int [][] out=new int[N.max_l()][N.max_c()];
        Vector<Couple> Buts=S.Buts;
        for(int i=0;i<N.max_l();i++){
            for(int j=0;j<N.max_c();j++){
                out[i][j]=N.max_l()+N.max_c();//j'initialise avec la plus grande taille possible
                for (Couple c : Buts) {
                    out[i][j]=Math.min(out[i][j],dist(new Couple(i, j),c));//on fait la distance entre (i,j) et chaque but et on prend la plus petite
                }
            }
        }
        return out;
    }

    int dist(Couple a, Couple b){
        return (int)(Math.sqrt(Math.pow((double)(a.x-b.x),2))+ Math.pow((double)(a.y-b.y),2));
    }

    public Stack<Direction> runIA(){
        J.reset_Level();
        State S=new State(J);
        visited.put(S, false);
        Stack<Direction> out=new Stack<>();
        State Stemp;
        Heur=initHeur(S,J.niveau());
        FAP.add(S);
        while (!FAP.isEmpty()) {
            S=FAP.poll();
            J.load_State(S,false);
            visited.replace(S, true);
            if(J.Win()){
                while (S.prevState!=null){
                    out.add(S.dir);
                    S=S.prevState;
                }
                //un peu de menage
                FAP=new PriorityQueue<>();
                visited=new Hashtable<>();
                return out;
            }else{
                Stemp=S.next(Direction.UP,Heur);
                if(!(visited.containsKey(Stemp))){
                //if (!Stemp.equals(S)){
                    FAP.add(Stemp);
                    visited.put(Stemp, false);
                }
                Stemp=S.next(Direction.DOWN,Heur);
                if(!(visited.containsKey(Stemp))){
                //if (!Stemp.equals(S)){
                    FAP.add(Stemp);
                    visited.put(Stemp, false);
                }
                Stemp=S.next(Direction.LEFT,Heur);
                if(!(visited.containsKey(Stemp))){
                //if (!Stemp.equals(S)){
                    FAP.add(Stemp);
                    visited.put(Stemp, false);
                }
                Stemp=S.next(Direction.RIGHT,Heur);
                if(!(visited.containsKey(Stemp))){
                //if (!Stemp.equals(S)){
                    FAP.add(Stemp);
                    visited.put(Stemp, false);
                }
            }
        }
        return out;
    }

}

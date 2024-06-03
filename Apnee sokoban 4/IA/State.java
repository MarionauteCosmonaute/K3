package IA;

import java.util.Vector;

import javax.naming.NoPermissionException;

import IA.IA.Direction;
import Modele.Jeu;
public class State implements Comparable<State>{
    public Vector<Couple> Caisses;
    public Vector<Couple> Buts;
    public Couple Pousseur;

    int prio=-1;
    Jeu J;

    State prevState=null;
    Direction dir;

    State(Jeu jeu){
        J=jeu;
        Caisses=new Vector<>();
        Buts= new Vector<>();
        Pousseur=new Couple(jeu.niveau().getPousseur().x,jeu.niveau().getPousseur().y);
        int max_c=jeu.niveau().max_c();
        int max_l=jeu.niveau().max_l();
        for (int i=0;i<max_l;i++){
            for(int j=0;j<max_c;j++){
                if(jeu.niveau().aCaisse(i, j)){
                    Caisses.add(new Couple(i, j));
                }
                if(jeu.niveau().aBut(i, j)){
                    Buts.add(new Couple(i, j));
                }
                if(jeu.niveau().aPousseur(i, j)){
                    Pousseur=new Couple(i,j);
                }
            }
        }
    }
    
    void AddPrev(State s){
        prevState=s;
    }
    int getPrio(int [][]Heur) throws NoPermissionException{
        if (Heur==null){throw new NoPermissionException("Heuristique non fourni, impossible d'obtenir la priorite");}
        if (prio==-1){
            prio=0;
            for(Couple c :Caisses){
                prio+=Heur[c.x][c.y];
            }
        }
        //if (prevState!=null){
         //   if (Caisses_diff(prevState)){
        //        prio=prio*(97/100);
        //    }
        //}
        return prio;
    }
    int getPrio()throws NoPermissionException{
        if (prio==-1){
            throw new NoPermissionException("priorite non calculee");
        }
        return prio;
    }

    State next(Direction d,int [][]Heurr){
        J.load_State(this,false);
        J.move_Pousseur(d,false);
        State out=new State(J);
        out.AddPrev(this);
        out.dir=d;
        try{
        out.getPrio(Heurr);
        } catch (NoPermissionException e) {
            throw new NullPointerException();
        }
        return out;
    }

    boolean Caisses_diff(State s){
        if(Caisses.size()==s.Caisses.size()){
            for (Couple c: Caisses){

                if (!s.Caisses.contains(c)){
                    return true;//si une caisse n'est pas au meme endroit dans l'autre etat -> false
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(State S) throws NullPointerException{
        try {
            int pa=this.getPrio();
            int pb=S.getPrio();
            return pa-pb;
        } catch (NoPermissionException e) {
            throw new NullPointerException();
        }
        
    }
    @Override
    public boolean equals(Object o) {
        if(this==o){
            return true;
        }
        if (!(o instanceof State)){
        }
        State s= (State) o;
        if (this.Pousseur.equals(s.Pousseur)){
            return !(this.Caisses_diff(s));
        }
        return false;
        
    }
    @Override
    public int hashCode(){
        Vector<Integer> hash=new Vector<>();
        for(Couple c:Caisses){
            hash.add(c.x);
            hash.add(c.y);
        }
        hash.add(Pousseur.x);
        hash.add(Pousseur.y);
        return hash.hashCode();
    }
}

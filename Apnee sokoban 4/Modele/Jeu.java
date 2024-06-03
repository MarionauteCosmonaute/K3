package Modele;
import java.awt.Point;
import java.util.Stack;

import Patterns.MyObservable;
import IA.State;
import IA.Couple;
import IA.IA.Direction;
import IA.IA;

public class Jeu extends MyObservable {
    Niveau CurrentConfig;
    Niveau OriginalConfig;
    LecteurNiveau ln;
    IA bigbrain;
    
    public Jeu(String s){
        ln=new LecteurNiveau(s);
        CurrentConfig =null;
        bigbrain=new IA(this);
    }

    public Niveau niveau(){
        if (CurrentConfig == null){
            throw new NoLoadedLevelException() ;
        }else{
            return CurrentConfig;
        }
    }
    public void move_Pousseur(Direction d,Boolean update){
        Point pou=niveau().getPousseur();
        switch (d) {
            case UP:
                move_Pousseur(pou.x, pou.y, pou.x-1, pou.y,update);
                break;
            case DOWN:
                move_Pousseur(pou.x,pou.y,pou.x+1,pou.y,update);
                break;
            case LEFT:
                move_Pousseur(pou.x, pou.y, pou.x, pou.y-1,update);
                break;
            case RIGHT:
                move_Pousseur(pou.x, pou.y,pou.x, pou.y+1,update);
            default:
                break;
        }

    }
    public void move_Pousseur(int x1, int y1,int x2, int y2,Boolean update){
        if(adjacent(x1, y1, x2, y2)){//On se teleporte pas
            if (!CurrentConfig.aMur(x2,y2)){//On ne grimpe pas sur les murs
                if (CurrentConfig.aCaisse(x2, y2)){//Si une caisse est ou on veut aller, on la pousse
                    move_Caisse(x2, y2, x2+(x2-x1), y2+(y2-y1));
                }
                if (!CurrentConfig.aCaisse(x2,y2)){//S'il y a toujours une caisse, c'est que celle ci ne peut etre deplacee, et donc notre pousseur non plus
                    if(CurrentConfig.aBut(x1, y1)){//s'il y a un but sur la case de depart, on le conserve
                        CurrentConfig.ajouteBut(x1, y1);
                    }else{
                        CurrentConfig.videCase(x1, y1);
                    }
                    if(CurrentConfig.aBut(x2, y2)){//s'il y a un but sur la case d'arrivee, on le conserve
                        CurrentConfig.ajoutePousseurSurBut(x2, y2);
                    }else{
                        CurrentConfig.ajoutePousseur(x2, y2);
                    }
                }
            }
        }
        if(update){
            MAJ();
        }
        
    }
    void move_Caisse(int x1, int y1, int x2, int y2){
        if(adjacent(x1, y1, x2, y2)){//On ne lance pas les caisses
            if (CurrentConfig.aCaisse(x1, y1)){//On verifie que la case de depart contient bien une caisse
                if(!CurrentConfig.aMur(x2, y2) && !CurrentConfig.aCaisse(x2, y2)){//On verifie que l'on essaye pas de pousser notre caisse sur un mur ou une caisse
                    if(CurrentConfig.aBut(x1, y1)){//s'il y a un but sur la case de depart, on le conserve
                        CurrentConfig.ajouteBut(x1, y1);
                    }else{
                        CurrentConfig.videCase(x1, y1);
                    }
                    if(CurrentConfig.aBut(x2, y2)){//s'il y a un but sur la case d'arrivee, on le conserve
                        CurrentConfig.ajouteCaisseSurBut(x2, y2);
                    }else{
                        CurrentConfig.ajouteCaisse(x2, y2);
                    }
                }
            }
        }
    }
    
    public boolean Win(){
        for(int i=0;i<CurrentConfig.max_l;i++){
            for(int j=0;j<CurrentConfig.max_c;j++){
                if(CurrentConfig.aCaisse(i, j) && !CurrentConfig.aBut(i, j)){return false;}//Si une caisse n'est pas sur un but
            }
        }
        return true;
    }

    boolean adjacent(int x1, int y1, int x2, int y2){
        if ((x2==x1+1 && y2==y1) ||
            (x2==x1 && y2==y1+1) ||
            (x2==x1-1 && y2==y1) ||
            (x2==x1 && y2==y1-1)   ){
                return true;
            }else{
                return false;}
    }

    public boolean prochainNiveau(){
        Niveau n = ln.lisProchainNiveau();
        if (n==null){
            return false;
        }else{
            OriginalConfig=n;
            CurrentConfig=OriginalConfig.copy();
            MAJ();
            return true;
        }
    }

    public void reset_Level(){
        CurrentConfig=OriginalConfig.copy();
        MAJ();
    }

    public void load_State(State s,Boolean update){
        for(int i=0;i<CurrentConfig.max_l;i++){
            for(int j=0;j<CurrentConfig.max_c;j++){
                if(CurrentConfig.aCaisse(i, j)||CurrentConfig.aPousseur(i, j)){
                    if(CurrentConfig.aBut(i, j)){
                        CurrentConfig.ajouteBut(i, j);
                    }else{
                        CurrentConfig.videCase(i, j);}
                }
            }
        }//J'efface toutes les caisses et le pousseur
        for(Couple c:s.Caisses){
            CurrentConfig.ajouteCaisse(c.getX(), c.getY());//replace les caisses
        }
        CurrentConfig.ajoutePousseur(s.Pousseur.getX(), s.Pousseur.getY());//replace le pousseur
        if(update){
            MAJ();
        }
        
    }

    void playStack(Stack<Direction> s){
        Direction D;
        reset_Level();
        while (!s.empty()) {
            D=s.pop();
            move_Pousseur(D,true);
        }
    }
    public Stack<Direction> run(){
        reset_Level();
        Stack<Direction> out=bigbrain.runIA();
        reset_Level();
        return out;
    }
}

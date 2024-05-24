package Model;

public class BestPyramide {
    Pyramid pyramide,principal,fin;
    int profondeur,min,max;
    boolean done;
    
    public BestPyramide(int taille,int min, int max){
        pyramide = null;
        profondeur = 0;
        principal = null;
        fin = null;
        this.min = min;
        this.max = max;
        done = false;
    }
    
    public synchronized void set(Pyramid pyramid, Pyramid principal, Pyramid fin ,int profondeur){
        if(profondeur == 0){
            
        }
        if(min <= profondeur && max <= max){
            this.pyramide = pyramid;
            this.profondeur = profondeur;
            this.principal = principal;
            this.fin = fin;
            done = true;
        }
    }

    public Pyramid getFin(){
        return fin;
    }
    public Pyramid getPyramid(){
        return pyramide;
    }

    public Pyramid getPrincipal(){
        return principal;
    }
    
    public synchronized int getProfondeur(){
        return profondeur;
    }
    
}
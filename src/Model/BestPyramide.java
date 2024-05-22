package Model;

public class BestPyramide {
    Pyramid pyramide,principal,fin;
    int profondeur;
    
    BestPyramide(int taille){
        pyramide = new Pyramid(taille);
        profondeur = 0;
        principal = null;
        fin = null;
    }
    
    public synchronized void set(Pyramid pyramid, Pyramid principal, Pyramid fin ,int profondeur){
        if(this.profondeur < profondeur){
            this.pyramide = pyramid;
            this.profondeur = profondeur;
            this.principal = principal;
            this.fin = fin;
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
    
    public int getProfondeur(){
        return profondeur;
    }
    
}
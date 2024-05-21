package Model;

public class BestPyramide {
    Pyramid pyramide;
    int profondeur;
    
    BestPyramide(int taille){
        pyramide = new Pyramid(taille);
        profondeur = 0;
    }
    
    synchronized public void set(Pyramid pyramid, int profondeur){
        if(this.profondeur < profondeur){
            this.pyramide = pyramid;
            this.profondeur = profondeur;
        }
    }

    public Pyramid getPyramid(){
        return pyramide;
    }
    
    public int getProfondeur(){
        return profondeur;
    }
    
}
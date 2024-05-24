package Model;

public class PyramideList {
    int[] profondeur;
    Pyramid[] pyramides;
    int nb,min,max,perfectProfondeur;
    Pyramid perfect = null;
    boolean done = false;

    public PyramideList(int min, int max){
        profondeur = new int[3];
        pyramides = new Pyramid[3];
        nb = 3;
        this.min = min;
        this.max = max;
    }

    public PyramideList(int min, int max,int nombre){
        profondeur = new int[nombre];
        pyramides = new Pyramid[nombre];
        nb = nombre;
        this.min = min;
        this.max = max;
    }
    
    public synchronized void add(Pyramid pyramide, int profondeur){
        if( (profondeur >= min) && (profondeur <= max) ){
            perfect = pyramide;
            perfectProfondeur = profondeur;
            done = true;
        }
        else{
            for(int i = 0; i < nb; i++){
                if(profondeur > this.profondeur[i]){
                    Pyramid tmp = pyramides[i];
                    pyramides[i] = pyramide;
                    pyramide = tmp;
                    int tmpint = this.profondeur[i];
                    this.profondeur[i] = profondeur;
                    profondeur = tmpint;

                }
            }
        }
    }

    public boolean done(){
        return done;
    }

    public void finish(){
        done = true;
    }

    public Pyramid getBest(){
        return perfect;
    }

    public synchronized Pyramid getPyramid(int i){
        return pyramides[i];
    }

    public synchronized int getProfondeur(int i){
        if (i == -1) return perfectProfondeur;
        else return profondeur[i];
    }
}

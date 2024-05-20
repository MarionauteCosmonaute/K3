package Model;

import java.util.NoSuchElementException;

public class IterateurUP implements Iterateur{
    Pyramid pyramide;
    int x,y;
    boolean bool;

    IterateurUP(Pyramid pyramide, boolean bool){
        this.pyramide = pyramide;
        this.bool = bool;
        y = 0;
        if(bool){x = 0;}
        else{x = pyramide.getSize();}
    }

    public boolean aProchain(){
        if(bool){return x < pyramide.getSize();}
        else{return x > 0 && y < pyramide.getSize();}
    }

    public Cube prochain() throws NoSuchElementException{
        if(aProchain()){
            int x_cor = x,y_cor;
            if((y_cor = y++) >= pyramide.getSize()-x ){
                if(bool){x_cor = ++x;}
                else{x_cor = x--;}
                y_cor = (y = 0);
            }
            return pyramide.get(x_cor, y_cor);
        }
        else{
            throw new NoSuchElementException();
        }
    }

    public void modifie(Cube cube){
        pyramide.set(x, y, cube);
    }
}

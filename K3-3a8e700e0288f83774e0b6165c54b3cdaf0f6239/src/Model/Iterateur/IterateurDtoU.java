package Model.Iterateur;

import java.util.NoSuchElementException;

import Model.Cube;
import Model.Pyramid;

public class IterateurDtoU implements Iterateur{
    Pyramid pyramide;
    int x,y;
    boolean next;

    public IterateurDtoU(Pyramid pyramide){
        this.pyramide = pyramide;
        x = 0;
        y = -1;
        next = false;
    }

    public boolean hasNext(){
        return x < pyramide.getSize();
    }

    public Cube next() throws NoSuchElementException{
        if(hasNext()){
            if(y >= pyramide.getSize()-x-1 ){
                y = -1;
                x++;
            }
            next = true;
            return pyramide.get(x, ++y);
        }
        else{
            throw new NoSuchElementException();
        }
    }

    public void modify(Cube cube){
        if(next) pyramide.set(x, y, cube);
        else throw new NullPointerException();
    }
}

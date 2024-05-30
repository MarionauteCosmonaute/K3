

import Model.*;
import Model.Iterateur.*;

public class testIterateur {
    public static void main(String[] args) {
        Pyramid pyramid = new Pyramid(5);
        for(int i = 0; i < 5; i++){
            pyramid.set(0,i,Cube.Bleu);
        }
        for(int i = 0; i < 4; i++){
            pyramid.set(1,i,Cube.Rouge);
        }
        for(int i = 0; i < 3; i++){
            pyramid.set(2,i,Cube.Vert);
        }
        for(int i = 0; i < 2; i++){
            pyramid.set(3,i,Cube.Jaune);
        }
        pyramid.set(4, 0, Cube.Noir);
        Iterateur it = pyramid.iterateur("DOWN");
        //it.modify(Cube.Rouge);
        try{
            while(true){
                System.out.println(it.next());
                /*if(it.next() == Cube.Bleu){
                    it.modify(Cube.Vide);
                }*/
                
            }
        }catch(Exception e){}

        System.out.println(pyramid);
    }   
}

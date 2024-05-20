

import Model.*;

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
        IterateurPyramide it = pyramid.iterateur();
        for(int i = 0; i < 19; i++){
            System.out.println(it.prochain());
        }

        System.out.println(pyramid);
    }   
}

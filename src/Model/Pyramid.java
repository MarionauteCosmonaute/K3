package Model;

public class Pyramid {
    Cube[][] pyramid;
    int size;

    Pyramid(int i){
        pyramid = new Cube[i][i];
        size = i;
        for (int x=0; x<i; x++ ){
            for (int y=0; y<i; y++ ){
                pyramid[x][y] = Cube.Vide;
            }
        }
    }


    //Cloning of a Pyramid object

    public Pyramid clone() throws CloneNotSupportedException {
        Pyramid clone = (Pyramid) super.clone();  // Clone the basic object structure

        clone.pyramid = new Cube[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(pyramid[i], 0, clone.pyramid[i], 0, size);
        }

        return clone;
    }


    //Size Pyramid
    public int getSize(){
        return size;
    }


    //Get an element at x y position

    public Cube get(int x, int y){
        return pyramid[x][y];
    }



    //Put a cube of a color on the pyramid at x y

    public void set(int x, int y, Cube c){
        pyramid[x][y] = c;
    }




    public String tmp(int i){
        String chaine = "";
        for(int j = 0; j < i; j++){
            chaine += "   ";
        }
        return chaine;
    }
    public String milieu(Cube c){
        String chaine = "";
        switch (c) {
            case Noir:
            case Bleu:
            case Vert:
            case Vide:
                chaine = " " + c + " ";
                break;
            case Blanc:
            case Rouge:
            case Jaune:
                chaine = " " + c;
                break;
            default:
                chaine = "" + c;
                break;
        }
        return chaine;
    }
    
    @Override
    public String toString(){
        String chaine = "";
        for(int i = size-1; i >= 0; i--){
            chaine += tmp(i);
            for (int j = 0; j <= size - 1 - i; j++){
                chaine += milieu(pyramid[i][j]) + " ";
            }
            chaine+="\n";
        }
        return chaine;
    }

}


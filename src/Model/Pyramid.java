package Model;

public class Pyramid {
    Cube[][] pyramid;
    int size;

    public Pyramid(int i){
        pyramid = new Cube[i][i];
        size = i;
        for (int x=0; x<i; x++ ){
            for (int y=0; y<i; y++ ){
                pyramid[x][y] = Cube.Vide;
            }
        }
    }
    
    Pyramid(String string){
        String[] charge = string.split(" ");
        size = Integer.parseInt(charge[0]);
        int index = 1;
        pyramid = new Cube[size][size];
        for(int i = size-1; i >= 0; i--){
            for(int j = 0; j <= size-1-i; j++){
                pyramid[i][j] = Cube.conversion(charge[index++]);
            }
        }
    }
    
    public String sauvegarde(){
        String sauvegarde = size + " ";
        for(int i = size-1; i >= 0; i--){
            for (int j = 0; j <= size-i-1; j++){
                sauvegarde += Cube.conversionString(get(i, j));
            }
        }
        sauvegarde += "\n";
        return sauvegarde;
    }

    //Size Pyramid
    public int getSize(){
        return size;
    }

    public void extend(){
        Cube cop_pyramid[][] = new Cube[size+2][size+2];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                cop_pyramid[i][j+1] = pyramid[i][j];
            }
        }
        pyramid = cop_pyramid;
        size += 2;
    }

    //Get an element at x y position
    public Cube get(int x, int y){
        return pyramid[x][y];
    }

    //Put a cube of a color on the pyramid at x y
    public void set(int x, int y, Cube c){
        pyramid[x][y] = c;
    }

    public Iterateur iterateur(){
        return new IterateurUP(this,true);
    }


    public String centrer(int i){
        String chaine = "";
        for(int j = 0; j < i; j++){
            chaine += "   ";
        }
        return chaine;
    }
    public String centrerCube(Cube c){
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
            chaine += centrer(i);
            for (int j = 0; j <= size - 1 - i; j++){
                chaine += centrerCube(pyramid[i][j]) + " ";
            }
            chaine+="\n";
        }
        return chaine;
    }

    //Fonction Clone 
    public Pyramid clone() throws CloneNotSupportedException {
        Pyramid clone = new Pyramid(size);  // Clone the basic object structure
        for (int i = 0; i < size; i++) {
            System.arraycopy(pyramid[i], 0, clone.pyramid[i], 0, size);
        }
        return clone;
    }
     
}



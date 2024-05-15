package Model;

import java.util.ArrayList;

public class Player implements Cloneable{
    Pyramid pyramid;

    ArrayList<Cube> side, personalBag;

    int[] totalCube;

    int noir, bleu, blanc, rouge, jaune, vert, neutre;
    int size;
    boolean loss;

    Player(int i){
        pyramid = new Pyramid(i);
        size = i;

        totalCube = new int[7];

        side = new ArrayList<>();
        personalBag = new ArrayList<>();
    }
    //CLONING METHOD

    public Player clone() throws CloneNotSupportedException {
        Player clone = (Player) super.clone();  // Clone the basic object structure

        clone.pyramid = pyramid.clone();
        clone.side = new ArrayList<>(side.size());
        for (Cube cube : side) {
          clone.side.add(cube);  // Add existing cube references
        }
        clone.personalBag = new ArrayList<>(personalBag.size());
        for (Cube cube : personalBag) {
          clone.personalBag.add(cube);  // Add existing cube references
        }
        return clone;
    }

     /*loss Setting/Checking */
    public boolean lost(){
        return loss;
    }

    public void playerLost(){
        loss = true;
    }

    public ArrayList<Cube> getPersonalBag(){
        return personalBag;
    }
    /*Cubes stats */
    public int totalCube(){
        //CUBE VIDE => Total of all colours
        return ColourAmmount(Cube.Vide);
    }

    public int ColourAmmount (Cube cube){
        int total = 0;
        switch(cube){
            case Noir:
                return totalCube[0];
            case Neutre:
                return totalCube[1];
            case Blanc:
                return totalCube[2];
            case Vert:
                return totalCube[3];
            case Jaune:
                return totalCube[4];
            case Rouge:
                return totalCube[5];
            case Bleu:
                return totalCube[6];
            default:
                for(int i = 0; i < 7; i++){
                    total+=totalCube[i];
                }
                return total;
        }
    }

    public void increment(Cube c){
        switch (c) {
            case Noir:
                totalCube[0]++;
                break;
            case Neutre:
                totalCube[1]++;
                break;
            case Blanc:
                totalCube[2]++;
                break;
            case Vert:
                totalCube[3]++;
                break;
            case Jaune:
                totalCube[4]++;
                break;
            case Rouge:
                totalCube[5]++;
                break;
            case Bleu:
                totalCube[6]++;
                break;
            default:
                break;
        }
    }

    public void decrement(Cube c){
        switch (c) {
            case Noir:
                totalCube[0]--;
                break;
            case Neutre:
                totalCube[1]--;
                break;
            case Blanc:
                totalCube[2]--;
                break;
            case Vert:
                totalCube[3]--;
                break;
            case Jaune:
                totalCube[4]--;
                break;
            case Rouge:
                totalCube[5]--;
                break;
            case Bleu:
                totalCube[6]--;
                break;
            default:
                break;
        }
    }


    public int[] compte_personal_bag(){
        int nb[] = new int[7];
        for(Cube cube : personalBag){
            switch (cube) {
                case Noir:
                    nb[0]++;
                    break;
                case Neutre:
                    nb[1]++;
                    break;
                case Blanc:
                    nb[2]++;
                    break;
                case Vert:
                    nb[3]++;
                    break;
                case Jaune:
                    nb[4]++;
                    break;
                case Rouge:
                    nb[5]++;
                    break;
                case Bleu:
                    nb[6]++;
                    break;
                default:
                    break;
            }
        }
        return nb;
    }

    /*Side access methods */
    public ArrayList<Cube> getSide(){
        return side;
    }

    public Cube getSide(int x){
        return side.get(x);

    }

    public void addSide(Cube c){
        side.add(c);
        increment(c);
    }

    public void removeSide(int x){
        decrement(side.remove(x));
    }

    public int getSideSize(){
        return side.size();
    }

    /*Pyramid access methods */
    public Pyramid getPyramid(){
        return pyramid;
    }

    public Cube get(int x, int y){
        return pyramid.get(x, y);
    }

    public void set(int x, int y, Cube c){
        Cube cube = get(x, y);
        decrement(cube);
        pyramid.set(x,y,c);
        increment(c);
    }

    public int getSize(){
        return size;
    }


    /* FONCTION AJOUTER */
    /*Pers Bag access methods */
    public boolean bagEmpty(){
        return personalBag.isEmpty();
    }

    
    public int getBagSize(){
        return personalBag.size();
    }


    public void addBag(Cube cube){
        increment(cube);
        personalBag.add(cube);
    }

    /*Construct method -> Puts a cube in a position after checking its content:       */
   /*If cube already existing in position -> Puts it back in the bag and replaces it */
    public void construction(int x, int y,Cube cube){
        if(!(get(x, y) == Cube.Vide)){
            personalBag.add(get(x, y));
        }
        personalBag.remove(cube);
        pyramid.set(x,y,cube);
    }


    /*Puts back a pawn of the pyramid in the bag */
    public void remise(int x, int y){
        addBag(get(x,y));
        set(x,y,Cube.Vide);
    }

    /*Swaps two cubes positions*/
    public void permutation(int x, int y, int x_p, int y_p){
        Cube cube = get(x,y);
        set(x,y,get(x_p,y_p));
        set(x_p,y_p,cube);
    }


    @Override
    public String toString(){
        String chaine = "Noir: "+ noir + "     Bleu: " + bleu + "     Blanc: "+ blanc + "     Rouge: " + rouge +"\nJaune: " + jaune +"     Vert: " + vert + "      Neutre: " + neutre + "\n";
        chaine +="Bag: ";
        int nb = 0;
        for(Cube cube : personalBag){
            chaine+= nb + ":" + cube + " ";
            nb++;
        }
        chaine +="\nSide: ";
        nb = 0;
        for( Cube cube : side ){
            chaine+= nb + ":" + cube + " ";
            nb++;
        }
        chaine+="\nPyramide:\n" + pyramid;

        return chaine;
    }
}
package Model;
import java.util.ArrayList;

public class Player {
    Pyramid pyramid;

    ArrayList<Cube> side, personalBag;

    int[] nbCube;
    int[] nbCubeBag;

    int noir, bleu, blanc, rouge, jaune, vert, neutre;
    int size;
    boolean loss;

    Player(int i){
        size = i;
        pyramid = new Pyramid(size);

        nbCube = new int[7];
        nbCubeBag = new int[7];

        loss = false;
        side = new ArrayList<>();
        personalBag = new ArrayList<>();
    }
    
    Player(String[] string){
        personalBag = new ArrayList<>();
        nbCube = new int[7];
        nbCubeBag = new int[7];
        String[] charge = string[0].split(" ");
        for(int i = 0; i < charge.length; i++){
            if(!charge[i].equals("")){
                personalBag.add(Cube.conversion(charge[i]));
                                                                            /* ADD DANS LE TAB */
            }
        }
        side = new ArrayList<>();
        charge = string[1].split(" ");
        for(int i = 0; i < charge.length; i++){
            if(!charge[i].equals("")){
                side.add(Cube.conversion(charge[i]));
                                                                            /* ADD DANS LE TAB */
            }
        }
        loss = Boolean.parseBoolean(string[2]);
        pyramid = new Pyramid(string[3]);
        size = pyramid.getSize();
    }

    public String sauvegarde(){
        String chaine="";
        for (int i = 0; i < personalBag.size(); i++){
            chaine += Cube.conversionString(personalBag.get(i));
        }
        chaine+= "\n";
        for (int i = 0; i < side.size(); i++){
            chaine += Cube.conversionString(side.get(i));
        }
        chaine += "\n";
        chaine += Boolean.toString(loss) + "\n";
        chaine += pyramid.sauvegarde();
        return chaine;
    }

    public boolean lost(){
        return loss;
    }

    public void playerLost(){
        loss = true;
    }
    
    public ArrayList<Cube> getPersonalBag(){
        return personalBag;
    }
    
    public int totalCube(){
        //CUBE VIDE => Total of all colours
        return ColourAmmount(Cube.Vide);
    }
    
    public int ColourAmmount (Cube cube){
        if(cube == Cube.Vide){
            int total = 0;
            for(int i = 0; i < 7; i++){
                total+=nbCube[i];
            }
            return total;
        }
        return nbCube[cube.getInt()];
    }
    
    /**************** */
    /* Fonction  */
    public void fusion(){
        int bagSize = personalBag.size();
        for (int i = 0; i < bagSize;i++){
            side.add(personalBag.remove(0));
        }
    }

    public void increment(Cube c){
        if(c != Cube.Vide){nbCube[c.getInt()]++;}
    }

    public void decrement(Cube c){
        if(c != Cube.Vide){nbCube[c.getInt()]--;}
    }

    public int[] compte_personal_bag(){
        return nbCubeBag;
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

    public void removeCubeSide(Cube cube){
        int i = 0;
        for(Cube c : side){
            if (c==cube){
                removeSide(i);
                return;
            }
            i++;
        }
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

    public void remove(int x,int y){
        set(x,y,Cube.Vide);
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

    public boolean bagEmpty(){
        return personalBag.isEmpty();
    }

    public void emptyBag(){
        personalBag = new ArrayList<>();
    }

    public int getBagSize(){
        return personalBag.size();
    }

    public void incrementBag(Cube cube){
        if(cube != Cube.Vide){nbCubeBag[cube.getInt()]++;}
        
    }

    public void decrementBag(Cube cube){
        if(cube != Cube.Vide){nbCubeBag[cube.getInt()]--;}
    }

    public void addBag(Cube cube){
        //System.out.println(cube);
        incrementBag(cube);
        personalBag.add(cube);
    }

    /*Construct method -> Puts a cube in a position after checking its content:       */
   /*If cube already existing in position -> Puts it back in the bag and replaces it */
    public void construction(int x, int y,Cube cube){
        if(!(get(x, y) == Cube.Vide)){
            incrementBag(get(x, y));
            decrement(get(x, y));
            personalBag.add(get(x, y));
        }
        decrementBag(cube);
        increment(cube);
        personalBag.remove(cube);
        pyramid.set(x,y,cube);
    }

    /*Swaps two cubes positions*/
    public void permutation(int x, int y, int x_p, int y_p){
        Cube cube = get(x,y);
        set(x,y,get(x_p,y_p));
        set(x_p,y_p,cube);
    }

    public void resetBag(){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if(get(i,j) != Cube.Vide){
                try{
                    addBag(get(i,j));
                    remove(i,j);
                }
                catch(NullPointerException e){
                    System.err.println("le cube est en position" + i + " " +j);
                }
            }
            }
        }
    }

    public Player clone() throws CloneNotSupportedException {
        Player clone = new Player(size);  // Clone the basic object structure

        
        clone.pyramid = pyramid.clone();
        for(int i = 0; i < 7; i++){
            clone.nbCube[i] = nbCube[i];
        }
        clone.side = new ArrayList<>(side.size());
        for (Cube cube : side) {
          clone.addSide(cube);  // Add existing cube references
        }
        clone.personalBag = new ArrayList<>(personalBag.size());
        for (Cube cube : personalBag) {
          clone.addBag(cube);  // Add existing cube references
        }
        return clone;
    }
    
    @Override
    public String toString(){
        String chaine = "Noir: "+ nbCubeBag[Cube.Noir.getInt()] + "     Bleu: " + nbCubeBag[Cube.Bleu.getInt()] + "     Blanc: "+ nbCubeBag[Cube.Blanc.getInt()] + "     Rouge: " + nbCubeBag[Cube.Rouge.getInt()] +"\nJaune: " + nbCubeBag[Cube.Jaune.getInt()] +"     Vert: " + nbCubeBag[Cube.Vert.getInt()] + "      Neutre: " + nbCubeBag[Cube.Neutre.getInt()] + "\n";
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
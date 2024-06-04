package Model;

import java.util.ArrayList;
import java.util.Collections;

import Model.Iterateur.Iterateur;
import java.util.NoSuchElementException;
import java.util.Vector;

public class Player {
    Pyramid pyramid;

    ArrayList<Cube> side, personalBag;

    int[] nbCube;
    int[] nbCubeBag;
    int[] nbCubeSide;

    int noir, bleu, blanc, rouge, jaune, vert, neutre;
    int size;
    boolean loss;

    public Player(int i) {
        size = i;
        pyramid = new Pyramid(size);

        nbCube = new int[7];
        nbCubeBag = new int[7];
        nbCubeSide = new int[7];

        loss = false;
        side = new ArrayList<>();
        personalBag = new ArrayList<>();
    }

    Player(String[] string) {
        personalBag = new ArrayList<>();
        nbCube = new int[7];
        nbCubeBag = new int[7];
        Cube cube;
        String[] charge = string[0].split(" ");
        for (int i = 0; i < charge.length; i++) {
            if (!charge[i].equals("")) {
                cube = Cube.conversion(charge[i]);
                personalBag.add(cube);
                incrementBag(cube);
                /* ADD DANS LE TAB */
            }
        }
        side = new ArrayList<>();
        charge = string[1].split(" ");
        for (int i = 0; i < charge.length; i++) {
            if (!charge[i].equals("")) {
                cube = Cube.conversion(charge[i]);
                side.add(cube);
                increment(cube);
                /* ADD DANS LE TAB */
            }
        }
        loss = Boolean.parseBoolean(string[2]);
        pyramid = new Pyramid(string[3]);
        size = pyramid.getSize();
    }

    public String sauvegarde() {
        String chaine = "";
        for (int i = 0; i < personalBag.size(); i++) {
            chaine += Cube.conversionString(personalBag.get(i));
        }
        chaine += "\n";
        for (int i = 0; i < side.size(); i++) {
            chaine += Cube.conversionString(side.get(i));
        }
        chaine += "\n";
        chaine += Boolean.toString(loss) + "\n";
        chaine += pyramid.sauvegarde();
        countCube();
        return chaine;
    }

    public boolean lost() {
        return loss;
    }

    public void playerLost() {
        loss = true;
    }

    public void playerNoLost() {
        loss = false;
    }

    public ArrayList<Cube> getPersonalBag() {
        return personalBag;
    }

    public int totalCube(){
        Iterateur it = pyramid.iterateur("UP");
        int total = 0;
        while(it.hasNext()){if(it.next()!= Cube.Vide) total++;}
        total+= side.size();
        return total;
    }

    /*public int totalCube() {
        // CUBE VIDE => Total of all colours
        return ColourAmmount(Cube.Vide);
    }*/

    public int ColourAmmount(Cube cube) {
        if (cube == Cube.Vide) {
            int total = 0;
            for (int i = 0; i < 7; i++) {
                total += nbCube[i];
            }
            return total;
        }
        return nbCube[cube.getInt()];
    }

    private void countCube() {
        Iterateur it = pyramid.iterateur("UP");
        try {
            while (true) {
                increment(it.next());
            }
        } catch (NoSuchElementException e) {
        }
    }

    /**************** */
    /* Fonction */
    public void fusion() {
        resetBag();
        int bagSize = personalBag.size();
        for (int i = 0; i < bagSize; i++) {
            addSide(personalBag.remove(0));
        }
    }


    private void increment(Cube c) {
        if (c != Cube.Vide) {
            nbCube[c.getInt()]++;
        }
    }
    private void incrementSide(Cube c){
        if (c != Cube.Vide) nbCubeSide[c.getInt()]++;
    }

    private void decrement(Cube c) {
        if (c != Cube.Vide) {
            nbCube[c.getInt()]--;
        }
    }
    private void decrementSide(Cube c){
        if (c != Cube.Vide) nbCubeSide[c.getInt()]--;
    }

    public int[] compte_personal_bag() {
        return nbCubeBag;
    }

    /* Side access methods */
    public ArrayList<Cube> getSide() {
        return side;
    }

    public Cube getSide(int x) {
        return side.get(x);
    }

    public void addSide(Cube c) {
        side.add(c);
        increment(c);
        incrementSide(c);
    }

    public void removeSide(int x) {
        Cube cube = side.remove(x);
        decrement(cube);
        decrementSide(cube);
    }

    public void removeCubeSide(Cube cube) {
        side.remove(cube);
        decrement(cube);
        decrementSide(cube);
    }

    public Pyramid getPyramid() {
        return pyramid;
    }

    public Cube get(int x, int y) {
        if (y == -1) {
            return getSide(x);
        }
        return pyramid.get(x, y);
    }

    public void set(int x, int y, Cube c) {
        Cube cube = get(x, y);
        decrement(cube);
        pyramid.set(x, y, c);
        increment(c);
    }

    public int getSize() {
        return size;
    }

    public int getSideSize() {
        return side.size();
    }

    public boolean bagEmpty() {
        return personalBag.isEmpty();
    }

    public int getBagSize() {
        return personalBag.size();
    }

    public void incrementBag(Cube cube) {
        if (cube != Cube.Vide) {
            nbCubeBag[cube.getInt()]++;
        }
    }

    public void decrementBag(Cube cube) {
        if (cube != Cube.Vide) {
            nbCubeBag[cube.getInt()]--;
        }
    }

    public void addBag(Cube cube) {
        incrementBag(cube);
        if(cube != Cube.Vide) personalBag.add(cube);
    }

    public void build(Pyramid pyramide) {
        Iterateur it = pyramide.iterateur("UP");
        Iterateur personalIt = pyramid.iterateur("UP");
        Cube cubeAjouter, cubeEnlever;
        while (it.hasNext()) {
            cubeEnlever = personalIt.next();
            cubeAjouter = it.next();
            changementBag(cubeAjouter, cubeEnlever);
            personalIt.modify(cubeAjouter);
        }
        personalBag = new ArrayList<>();
    }

    private void changementBag(Cube cube1, Cube cube2) {
        increment(cube1);
        decrementBag(cube1);
        incrementBag(cube2);
        decrement(cube2);
    }

    public void emptyBag() {
        personalBag = new ArrayList<>();
    }

    /* Construct method -> Puts a cube in a position after checking its content: */
    /*
     * If cube already existing in position -> Puts it back in the bag and replaces
     * it
     */
    public void construction(int x, int y, Cube cube) {
        if (!(get(x, y) == Cube.Vide)) {
            incrementBag(get(x, y));
            decrement(get(x, y));
            personalBag.add(get(x, y));
        }
        decrementBag(cube);
        increment(cube);
        personalBag.remove(cube);
        pyramid.set(x, y, cube);
    }

    public void constructionAleatoire(){
        Iterateur it = pyramid.iterateur("UP");
        Cube cube;
        while(it.hasNext()){
            if(it.next() == Cube.Vide && !personalBag.isEmpty()){
                Collections.shuffle(personalBag);
                cube = personalBag.remove(0);
                decrementBag(cube);
                increment(cube);
                it.modify(cube);
            }
        }
    }

    /* Swaps two cubes positions */
    public void permutation(int x, int y, int x_p, int y_p) {
        Cube cube = get(x, y);
        set(x, y, get(x_p, y_p));
        set(x_p, y_p, cube);
    }

    public void resetBag() {
        Iterateur personalIt = pyramid.iterateur("UP");
        Cube cube;
        while (personalIt.hasNext()) {
            cube = personalIt.next();
            decrement(cube);
            addBag(cube);
            personalIt.modify(Cube.Vide);
        }
    }

    public Player clone() throws CloneNotSupportedException {
        Player clone = new Player(size); // Clone the basic object structure
        clone.loss = loss;
        clone.pyramid = pyramid.clone();
        clone.nbCubeSide = new int[7];
        clone.nbCubeBag = new int[7];
        for (int i = 0; i < 7; i++) {
            clone.nbCube[i] = nbCube[i]-nbCubeSide[i];
        }
        clone.side = new ArrayList<>(side.size());
        for (Cube cube : side) {
            clone.addSide(cube); // Add existing cube references
        }
        clone.personalBag = new ArrayList<>(personalBag.size());
        for (Cube cube : personalBag) {
            clone.addBag(cube); // Add existing cube references
        }
        return clone;
    }

    @Override
    public String toString() {
        String chaine = "Cube en jeu\nNoir: " + nbCube[Cube.Noir.getInt()] + "     Bleu: " + nbCube[Cube.Bleu.getInt()] + "     Blanc: " + nbCube[Cube.Blanc.getInt()] + "     Rouge: " + nbCube[Cube.Rouge.getInt()]
                + "\nJaune: " + nbCube[Cube.Jaune.getInt()] + "     Vert: " + nbCube[Cube.Vert.getInt()] + "      Neutre: " + nbCube[Cube.Neutre.getInt()] + "\n";
        chaine += "PersonalBag\nNoir: " + nbCubeBag[Cube.Noir.getInt()] + "     Bleu: " + nbCubeBag[Cube.Bleu.getInt()] + "     Blanc: " + nbCubeBag[Cube.Blanc.getInt()] + "     Rouge: " + nbCubeBag[Cube.Rouge.getInt()]
                + "\nJaune: " + nbCubeBag[Cube.Jaune.getInt()] + "     Vert: " + nbCubeBag[Cube.Vert.getInt()] + "      Neutre: " + nbCubeBag[Cube.Neutre.getInt()] + "\n";
        
        chaine += "Bag: ";
        int nb = 0;
        for (Cube cube : personalBag) {
            chaine += nb + ":" + cube + " ";
            nb++;
        }
        chaine += "\nSide: ";
        nb = 0;
        for (Cube cube : side) {
            chaine += nb + ":" + cube + " ";
            nb++;
        }
        chaine += "\nPyramide:\n" + pyramid;

        return chaine;
    }
    public void trieSide(){
        Collections.sort(side);
        // ArrayList<Cube> trier = new ArrayList<>();

        // for(Cube cube : Cube.values()){
        //     for(int i = 0; i < nbCubeSide[cube.getInt()]; i++){
        //         trier.add(cube);
        //     }
        // }
        // return trier;
    }

    public Vector<Integer> hash(boolean bool){
        Vector<Integer> vect = new Vector<>();
        vect.addAll(getPyramid().hash());
        trieSide();

        for(Cube cube : side){
            vect.add(cube.getInt());
        }
        
        return vect;
    }

}

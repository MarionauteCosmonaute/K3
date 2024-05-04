package Model;

import java.util.ArrayList;
import java.util.Collections;

public class PawnsBag {
    private ArrayList<Cube> PawnsBag;
    boolean care;

    public PawnsBag(int nb) {
        PawnsBag = new ArrayList<Cube>();
        care = false;
        if(nb == 2){care = true;}
        for (int i=0; i<9; i++){
            PawnsBag.add(Cube.Rouge);
            PawnsBag.add(Cube.Bleu);
            PawnsBag.add(Cube.Vert);
            PawnsBag.add(Cube.Jaune);
            PawnsBag.add(Cube.Noir);
        }
    }

    public boolean empty(){
        return PawnsBag.size() == 0;
    }
    public boolean count_colors (ArrayList<Cube> cubes){
        boolean Rouge=false , Vert=false, Bleu=false, Noir=false, Jaune=false;
        for (Cube cube : cubes){
            if (!(Rouge && Vert && Bleu && Noir && Jaune)){
                switch (cube){
                    case Rouge :
                        Rouge = true;
                        break;
                    case Vert :
                        Vert=true;
                        break;
                    case Bleu :
                        Bleu=true;
                        break;
                    case Noir :
                        Noir=true;
                        break;
                    default:
                        Jaune=true;
                        break;
                }
            } else {
                return true;
            }
        }
        int count = 0;
        if (Rouge) count++;
        if (Vert) count++;
        if (Bleu) count++;
        if (Noir) count++;
        if (Jaune) count++;

        return count >= 4;
    }

    public ArrayList<Cube> init_center() {
        ArrayList<Cube> cubes = new ArrayList<>();
        while (! count_colors (cubes)){
            for (int i = 0; i < cubes.size(); i++) {
                PawnsBag.add(cubes.remove(0)); // Remettre les pion in the PawnsBag to re shuflle
            }

            Collections.shuffle(PawnsBag); // Mélanger le PawnsBag pour assurer l'aléatoire
            for (int i = 0; i < 9; i++) {
                cubes.add(PawnsBag.remove(0)); // Piocher un pion et le retirer du PawnsBag
            }
        }
        return cubes;
    }

    public ArrayList<Cube> draw() {
        ArrayList<Cube> cubes = new ArrayList<>();
        Collections.shuffle(PawnsBag); // Mélanger le PawnsBag pour assurer l'aléatoire
        int todraw = 3;
        //System.out.println(PawnsBag.size());
        if(care && (PawnsBag.size() <= 6)){
            todraw = 2;
        }
        for (int i = 0; i < todraw; i++) {
            cubes.add(PawnsBag.remove(0)); // Piocher un pion et le retirer du PawnsBag
        }
        return cubes;
    }


    /* Fonction ajoutee */
    public String toString(){
        String chaine = "";
        for(Cube cube : PawnsBag){
            chaine += cube + " ";
        }
        return chaine;
    }

    public int getSize(){
        return PawnsBag.size();
    }

}
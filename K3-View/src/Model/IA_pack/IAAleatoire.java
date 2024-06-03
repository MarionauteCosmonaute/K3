package Model.IA_pack;
/*package Model;

import java.awt.Point;
import java.util.Random;

public class IAAleatoire extends IA {
    Random rand;

    public IAAleatoire() {
        rand = new Random();
    }

    // Génère un entier aléatoire entre 0 et max
    public int aleatoire(int max) {
        return rand.nextInt(max);
    }

    // Applique un coup aléatoire sur le Plateau plateau
    @Override
    public void add_central() {
        Arraylist<Point> coups_possibles = jeu.Accessible_Playable();
        Point joue = coups_possibles.get(aleatoire(coups_possibles.size()));
        ArrayList<Point> destination = jeu.CubeAccessibleDestinations((int)joue.getX(), (int)joue.getY());
        Point dest = destination.get(aleatoire(destination.size()));
        jeu.add_central((int)dest.getX(), (int)dest.getY(), (int)joue.getX(), (int)joue.getY());
    }

    @Override
    public void construction() {
        jeu.constructionAleatoire(jeu.getPlayer());
    }
}
*/
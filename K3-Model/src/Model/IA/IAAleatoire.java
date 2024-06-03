package Model;

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
    public void joue() {
        while (plateau.joue(aleatoire(plateau.hauteur), aleatoire(plateau.longueur)) == 2) {}
    }
}
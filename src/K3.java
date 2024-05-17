// javac -cp src -d out src/*.java src/*/*.java
// java -cp out K3

import Model.*;
import View.*;
import Controller.*;

class K3 {
    public static void main(String[] args) {
        Jeu jeu = new Jeu(2);
        jeu.initPrincipale();
        MusicPlayer musique = new MusicPlayer();
        ControleurMediateur contMEd = new ControleurMediateur(jeu, musique);
        InterfaceGraphique.demarrer(jeu, contMEd);
    }
}
package Model.IA_pack;


import Model.Runnables.StartConstruction;


public class IAFacile extends IA {

    /*@Override
    public int jouer_coup(boolean construction) {
        ArrayList<ArrayList<Point>> coups_possibles = coupIA(jeu, indiceJoueur, 0);
        if (coups_possibles.size() != 0) {
            Random random = new Random();
            ArrayList<Point> coup_a_jouer = coups_possibles.get(random.nextInt(coups_possibles.size()));
            return  jeu.jouer_coup((int) coup_a_jouer.get(1).getX(), (int) coup_a_jouer.get(1).getY(),
                    (int) coup_a_jouer.get(0).getX(), (int) coup_a_jouer.get(0).getY());
            
        }
        return 0;
    }*/                     /* INUTILE? X) */

    @Override
    public void construction(boolean aide) { // Tout refaire en choisissant les 3 cubes les plus hauts, puis en énumérant des
                                 // pyramides aléatoires et en calculant a chaque fois qu'on enleve un cube le
                                 // nombre de cubes de couleur différents accessibles
                                 // On prendra a la fin la pyramide où on a le plus de cubes de couleur
                                 // différente accessibles a chaque coup

        constructionThread = new Thread(new StartConstruction(this,aide));
        constructionThread.start();
    }
}
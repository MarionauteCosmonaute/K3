package View;


public interface CollecteurEvenements {
    void clicJoueurPyramide(int l, int c);

    void clicJoueurSide(int x);

    void clicPyramideCentrale(int l, int c);

    void clicSourisPyr(int l, int c);

    void clicSourisEchange(int x1, int y1, int x2, int y2);

    void clicSourisPioche(int couleur);

    boolean commande(String c);

    void ImporterVue(InterfaceGraphique vue);

    boolean penaltyPhase();
    
    void clicBlanc(int x, int y);

    void setIADifficulty(int i);

}
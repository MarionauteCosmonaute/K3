package View;

public interface CollecteurEvenements {
    void clicSouris(int l, int c);
    void clicSourisPyr(int l, int c);
    void clicSourisEchange(int x1, int y1, int x2, int y2);
    // void clicSourisPioche(int l, int c, int couleur);
    void clicSourisPioche(int couleur);
    boolean commande(String c);
    void ImporterVue(InterfaceGraphique vue); 
}
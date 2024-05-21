package View;

public interface CollecteurEvenements {
    void clicSouris(int l, int c);
    void clicSourisPyr(int l, int c);
    void clicSourisPioche(int l, int c);
    boolean commande(String c);
    void ImporterVue(InterfaceGraphique vue); 
    void addMenu(Menu m);
}
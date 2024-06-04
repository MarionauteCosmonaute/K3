import Model.JeuOnline;

public class Join {
    public static void main(String[] args) {
        JeuOnline jeu = new JeuOnline("localhost:" + args[0], false);
        //System.out.println(jeu);
        jeu.constructionAleatoire(1);
        jeu.doneConstruction();
        System.out.println(jeu);
    }
}

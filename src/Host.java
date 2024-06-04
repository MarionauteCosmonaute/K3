import Model.*;

public class Host {

    public static void main(String[] args) {
        JeuOnline jeu = new JeuOnline("localhost:" + args[0], true);
        try{Thread.sleep(1000);}
        catch(Exception e){}
        //System.out.println(jeu);
        jeu.constructionAleatoire(0);
        jeu.doneConstruction();
        System.out.println(jeu);
    }
}

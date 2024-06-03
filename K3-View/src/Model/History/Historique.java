package Model.History;

import java.util.Stack;
import java.awt.Point;
import Model.Coup;

public class Historique {

    Stack<Coup> coup_jouer;
    Stack<Coup> coup_annule;

    public Historique() {
        coup_jouer = new Stack<>();
        coup_annule = new Stack<>();
    }

    public void action(int type, Point s, Point d) {
        coup_jouer.add(new Coup(type, s, d));
        coup_annule = new Stack<Coup>();
    }
    
    public Coup annule() {
        if (!isEmptyAnnule()) {
            Coup coup = coup_jouer.pop();
            coup_annule.add(coup);
            return coup;
        }
        return null;
    }

    public Coup refais() {
        if (!isEmptyRefaire()) {
            Coup coup = coup_annule.pop();
            coup_jouer.add(coup);
            return coup;
        }
        return null;
    }

    public void backOnRefais() {
        Coup coup = coup_jouer.pop();
        coup_annule.add(coup);
    }

    public boolean isEmptyAnnule() {
        return coup_jouer.empty();
    }

    public boolean isEmptyRefaire() {
        return coup_annule.empty();
    }

    public String sauvegarde() {
        StringBuilder sb = new StringBuilder();
        sb.append("jouer:\n");
        for (Coup coup : coup_jouer) {
            sb.append(coup.toString()).append("\n");
        }
        sb.append("annule:\n");
        for (Coup coup : coup_annule) {
            sb.append(coup.toString()).append("\n");
        }
        return sb.toString();
    }

    public static Historique fromString(String str) {
        Historique historique = new Historique();
        String[] lines = str.split("\n");
        boolean isJouer = true;
        for (String line : lines) {
            if (line.equals("jouer:")) {
                isJouer = true;
            } else if (line.equals("annule:")) {
                isJouer = false;
            } else {
                Coup coup = Coup.fromString(line);
                if (isJouer) {
                    historique.coup_jouer.add(coup);
                } else {
                    historique.coup_annule.add(coup);
                }
            }
        }
        return historique;
    }

}

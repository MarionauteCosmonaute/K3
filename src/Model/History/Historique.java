package Model.History;

import java.util.Stack;
import java.awt.Point;
import Model.Coup;

public class Historique {

    Stack<Coup> Undo;
    Stack<Coup> Redo;

    public Historique() {
        Undo = new Stack<>();
        Redo = new Stack<>();
    }

    public void action(int type, Point s, Point d) {
        Undo.add(new Coup(type, s, d));
        Redo = new Stack<Coup>();
    }
    
    public Coup annule() {
        if (!isEmptyAnnule()) {
            Coup coup = Undo.pop();
            Redo.add(coup);
            return coup;
        }
        return null;
    }

    public Coup refais() {
        if (!isEmptyRefaire()) {
            Coup coup = Redo.pop();
//            Undo.add(coup);// On empile auto en traitant le coup
            return coup;
        }
        return null;
    }

    public void backOnRefais() {
        Coup coup = Undo.pop();
        Redo.add(coup);
    }

    public boolean isEmptyAnnule() {
        return Undo.empty();
    }

    public boolean isEmptyRefaire() {
        return Redo.empty();
    }

    public String sauvegarde() {
        StringBuilder sb = new StringBuilder();
        sb.append("jouer:\n");
        for (Coup coup : Undo) {
            sb.append(coup.toString()).append("\n");
        }
        sb.append("annule:\n");
        for (Coup coup : Redo) {
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
                    historique.Undo.add(coup);
                } else {
                    historique.Redo.add(coup);
                }
            }
        }
        return historique;
    }

    public Stack<Coup> getRefais(){
        return Redo;
    }

}

package View.Menu;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Point;

public abstract class Menu extends JPanel {
    public Menu() {
        super(new BorderLayout());
        setOpaque(false);
        if (!isVisible()) {
            setVisible(true);
        }
        setFocusable(false);
        
    }

    public void changeVisibilite() {
        if (isVisible()) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
    public void updateLanguageCode() {
        System.out.println("wdym??");
    }

    public void reset(){}

    public void setValider(boolean b){}

    public void setAnnuler(boolean bool){}

    public void setRefaire(boolean bool){}

    public void setLastCoup(boolean bool){}

    public void setDernierCoup(boolean bool){}

    public boolean getDernierCoup(){return false;}

    public void setSuggestion(boolean bool){}

    public void setCoupSuggere(Point joueur, Point pyramide_centrale){}

    public void updateSablier(boolean b){}

}

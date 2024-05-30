package View.Menu;

import java.awt.BorderLayout;
import javax.swing.JPanel;

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

    public void reset(){
    }

    public void setValider(boolean b){

    }
}

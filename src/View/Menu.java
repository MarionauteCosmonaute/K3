package View;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public abstract class Menu extends JPanel {
    public Menu() {
        super(new BorderLayout());
        if (!isVisible()) {
            setVisible(true);
        }
    }

    public void changeVisibilite() {
        if (isVisible()) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}

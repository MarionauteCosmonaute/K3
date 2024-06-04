package View.Adaptateurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JMenuBar;

public class SourisAdapteMenu extends MouseAdapter {
    private Color originalBackgroundColor;
    private Color originalForegroundColor;
    private JMenuBar menu;

    public SourisAdapteMenu(JMenuBar menu) {
        this.menu = menu;
        // Sauvegarder l'état d'origine du bouton
        this.originalBackgroundColor = menu.getBackground();
        this.originalForegroundColor = menu.getForeground();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Changer la couleur du bouton
        menu.setBackground(Color.PINK);
        menu.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Restaurer l'état d'origine du bouton
        menu.setBackground(originalBackgroundColor);
        menu.setForeground(originalForegroundColor);
    }
}

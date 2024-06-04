package View.Adaptateurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JMenu;

public class SourisAdapteMenu extends MouseAdapter {
    private Color originalBackgroundColor;
    private Color originalForegroundColor;
    private JMenu menuComponent;

    public SourisAdapteMenu(JMenu menuComponent) {
        this.menuComponent = menuComponent;
        // Sauvegarder l'état d'origine du bouton
        this.originalBackgroundColor = menuComponent.getBackground();
        this.originalForegroundColor = menuComponent.getForeground();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Changer la couleur du bouton
        menuComponent.setBackground(Color.PINK);
        menuComponent.setForeground(Color.BLACK);
        menuComponent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        menuComponent.setBackground(Color.PINK);
        menuComponent.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Restaurer l'état d'origine du bouton
        menuComponent.setBackground(originalBackgroundColor);
        menuComponent.setForeground(originalForegroundColor);
    }
}

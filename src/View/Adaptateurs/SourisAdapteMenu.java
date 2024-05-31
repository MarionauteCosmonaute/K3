package View.Adaptateurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.sound.sampled.*;

public class SourisAdapteMenu extends MouseAdapter {
    private Color originalBackgroundColor;
    private Color originalForegroundColor;
    private JMenu menu;

    public SourisAdapteMenu(JMenu menu) {
        this.menu = menu;
        // Sauvegarder l'état d'origine du bouton
        this.originalBackgroundColor = menu.getBackground();
        this.originalForegroundColor = menu.getForeground();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Changer la couleur du bouton
        menu.setBackground(Color.YELLOW);
        menu.setForeground(Color.BLACK);

        // Jouer le son dans un thread séparé
        // Thread t = new Thread() {
        //     public void run() {
        //         if (audioClip != null && !audioClip.isRunning()) {
        //             audioClip.setFramePosition(0); // Repositionner le clip au début
        //             audioClip.start(); // Jouer le son
        //         }
        //     }

        // };
        // t.start();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Restaurer l'état d'origine du bouton
        menu.setBackground(originalBackgroundColor);
        menu.setForeground(originalForegroundColor);
    }
}

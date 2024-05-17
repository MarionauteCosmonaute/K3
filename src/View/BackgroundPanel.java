package View;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JFrame {
    private static final String IMAGE_PATH = "res/Back.png";

    // Constructeur
    public BackgroundPanel() {
        // Configurer la fenêtre

        setLocationRelativeTo(null); // Centrer la fenêtre
        ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout()); // Utiliser BorderLayout pour le panneau
        backgroundPanel.setOpaque(false);
        // Utiliser le panneau personnalisé comme contenu de la JFrame
        setContentPane(backgroundPanel);
    }

    // Classe personnalisée pour le panneau avec image en arrière-plan
    class ImageBackgroundPanel extends JPanel {
        private Image backgroundImage;

        // Constructeur
        public ImageBackgroundPanel() {
            // Charger l'image en arrière-plan
            backgroundImage = new ImageIcon(IMAGE_PATH).getImage();
        }

        // Redéfinir paintComponent pour dessiner l'image en arrière-plan
        @Override
        protected void paintComponent(Graphics g) {
            System.out.println("PaintComponent de BackgroundPanel");
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Dessiner l'image en arrière-plan
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
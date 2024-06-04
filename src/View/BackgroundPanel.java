package View;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JFrame {
    ImageBackgroundPanel backgroundPanel;

    // Constructeur
    public BackgroundPanel() {
        // Configurer la fenêtre  
        backgroundPanel = new ImageBackgroundPanel();
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
            try {
                backgroundImage = Global.FileLoader.getImage("res/MenuPrincipal.png");
            } catch (Exception e) {
                System.exit(1);
            }
        }

        // Redéfinir paintComponent pour dessiner l'image en arrière-plan
        @Override
        protected void paintComponent(Graphics g) {
            //System.out.println("PaintComponent de BackgroundPanel");
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Dessiner l'image en arrière-plan
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        public void setBackgroundPicture(String s) {
            try {
                backgroundImage = Global.FileLoader.getImage(s);
            } catch (Exception e) {
                System.exit(1);
            }
            repaint();
        }
    }

    public void setBackgroundPicture(String s) {
        backgroundPanel.setBackgroundPicture(s);
    }

}
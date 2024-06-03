package View;

import javax.swing.*;
import java.awt.*;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class MenuArrondi extends JMenuBar {
    private int rayon;

    public MenuArrondi(int rayon)
    {
        this.rayon = rayon;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
    
        // Activer l'antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Créer une forme arrondie
        RoundRectangle2D formeArrondie = new RoundRectangle2D.Float(
            0, 0, getWidth(), getHeight(), rayon, rayon);
        
        // Appliquer la forme arrondie comme clip
        g2d.setClip(formeArrondie);
        
        // Dessiner la zone de contenu
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        // Antialiasing pour lisser les bords arrondis
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définir la couleur de la bordure
        g2d.setColor(getForeground());

        // Dessiner la bordure arrondie
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rayon, rayon);
    }
}

package View;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Curseur 
{
    public static Cursor Gerer_Curseur_main()
    {
        ImageIcon bananaIcon = new ImageIcon("res/curseur_main_ouverte.png");
        // Redimensionner l'image de la banane à 50x50 pixels
        Image scaledBananaImage = bananaIcon.getImage().getScaledInstance(28,28, Image.SCALE_SMOOTH);
        // Convertir l'image redimensionnée de la banane en curseur
        return Toolkit.getDefaultToolkit().createCustomCursor(scaledBananaImage, new Point(14,14), "banana cursor");
    }
}

package View;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;


import Global.FileLoader;

public class Curseur 
{
    public static Cursor Gerer_Curseur_main()
    {
        Image scaledBananaImage = null;
        // Redimensionner l'image de la banane à 50x50 pixels
        try{
            scaledBananaImage = FileLoader.getImage("res/curseur_main_ouverte.png").getScaledInstance(28,28, Image.SCALE_SMOOTH);
        }catch(Exception e){
            System.exit(1);
        }
        // Convertir l'image redimensionnée de la banane en curseur
        return Toolkit.getDefaultToolkit().createCustomCursor(scaledBananaImage, new Point(14,14), "banana cursor");
    }
}

package Model;

import java.awt.Point;

public class Coup{
    int type;
    Point[] coordonnee;
    
    Coup(int type, Point[] coordonnee){
        this.type = type;
        this.coordonnee = coordonnee;
    }
}

package Model;

import java.awt.Point;

public class Coup{
    int type;
    Point source;
    Point dest;
    
    public Coup(int type, Point source, Point dest){
        this.type = type;
        this.source = source;
        this.dest = dest;
    }
}

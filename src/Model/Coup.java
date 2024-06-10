package Model;

import java.awt.Point;

public class Coup{
    public int type;
    public Point source;
    public Point dest;
    
    public Coup(int type, Point source, Point dest){
        this.type = type;
        this.source = source;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return type + " " + source.x + " " + source.y + " " + dest.x + " " + dest.y;
    }

    public static Coup fromString(String str) {
        String[] parts = str.split(" ");
        int type = Integer.parseInt(parts[0]);
        Point start = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        Point destination = new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        return new Coup(type, start, destination);
    }
}
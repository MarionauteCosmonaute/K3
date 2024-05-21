package Reseau.Runnables.Transfer;

import Structure.Fifo;

import java.awt.Point;
import java.io.PrintWriter;

public class Consumer implements Runnable{
    Fifo<Point> file;
    PrintWriter out;

    public Consumer(Fifo<Point> file, PrintWriter out){
        this.file = file;
        this.out = out; 
    }

    @Override
    public void run(){
        try{
            while (true) {
                Point p = file.get();
                out.println(p.x + " " + p.y);
            }
        }catch(Exception e){e.getMessage();}
    }
}
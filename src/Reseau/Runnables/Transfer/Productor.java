package Reseau.Runnables.Transfer;

import java.io.BufferedReader;

import Structure.Fifo;
import java.awt.Point;
import java.util.ArrayList;

public class Productor implements Runnable{
    BufferedReader in;
    ArrayList<Fifo<Point>> file;
    int index;

    public Productor(BufferedReader in, ArrayList<Fifo<Point>> file, int index){
        this.in = in;
        this.file = file;
        this.index = index;
    }
    public Productor(BufferedReader in, ArrayList<Fifo<Point>> file){
        this.in = in;
        this.file = file;
        index = -1;
    }


    @Override
    public void run(){
        String input;
        String[] split;
        try{
            while((input = in.readLine()) != null){
                split = input.split(" ");
                // System.out.println("on est dedans, recu: " + input + " file size: " + file.size() + "value of split: "+ split[0] + " " + split[1]);
                Point p = new Point( Integer.parseInt(split[0]) , Integer.parseInt(split[1]) );
                System.out.println("the point added is: " + p);
                for(int i = 0; i < file.size(); i++){
                    if(i != index){
                        file.get(i).add(p);
                    }
                }
            }
        }catch(Exception e){System.err.println(e);}
    }
}

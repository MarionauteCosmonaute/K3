package Reseau.Runnables.Transfer;

import java.io.BufferedReader;

import Structure.Fifo;
import java.util.ArrayList;

import Model.Coup;

public class Productor implements Runnable{
    BufferedReader in;
    ArrayList<Fifo> file;
    int index;

    public Productor(BufferedReader in, ArrayList<Fifo> file, int index){
        init(in,file);
        this.index = index;
    }
    public Productor(BufferedReader in, ArrayList<Fifo> file){
        index = -1;
        init(in,file);
    }

    public void init(BufferedReader in, ArrayList<Fifo> file){
        this.in = in;
        this.file = file;
    }


    @Override
    public void run(){
        
        try{
            while (true) {
                Coup coup = Coup.fromString(in.readLine());
                for(int i = 0; i < file.size(); i++){
                    if(i != index){
                        file.get(i).add(coup);
                    }
                }
            }
        }catch(Exception e){e.getMessage();}
    }
}

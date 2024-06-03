package Reseau.Runnables.Transfer;

import Structure.Fifo;
import Model.Coup;

import java.io.PrintWriter;

public class Consumer implements Runnable{
    Fifo file;
    PrintWriter out;

    public Consumer(Fifo file, PrintWriter out){
        this.file = file;
        this.out = out; 
    }

    @Override
    public void run(){
        try{
            while (true) {
                Coup coup = file.get();
                out.println(coup);
            }
        }catch(Exception e){e.getMessage();}
    }
}
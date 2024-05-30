package Reseau;

import Structure.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

import Reseau.Runnables.Transfer.*;

public class Task {
    ArrayList<Fifo> file;
    
    int nbPlayer;
    Thread[] productor;
    Thread[] consumer;

    public Task(int nbPlayer){
        file = new ArrayList<>();
        this.nbPlayer = nbPlayer;
        productor = new Thread[nbPlayer];
        consumer = new Thread[nbPlayer];

        for(int i = 0; i < nbPlayer; i++){
            file.add(new Fifo());
        }
    }

    public Fifo get(int i){
        return file.get(i);
    }

    public void init(Socket[] socket){
        for(int i = 0; i < nbPlayer; i++){
            try{
                Productor p = new Productor(new BufferedReader(new InputStreamReader(socket[i].getInputStream())), file, i);
                productor[i] = new Thread(p);
                productor[i].start();
                
                Consumer c = new Consumer(file.get(i),new PrintWriter(socket[i].getOutputStream(),true));
                consumer[i] = new Thread(c);
                consumer[i].start();
            }catch(Exception e){System.err.println(e);System.exit(2);}
        }
    }

    
}

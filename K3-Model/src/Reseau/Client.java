package Reseau;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import Reseau.Runnables.Transfer.Consumer;
import Reseau.Runnables.Transfer.Productor;

import java.io.*;
import Structure.*;
import java.awt.Point;


public class Client {
    Socket socket;
    ArrayList<Fifo<Point>> file;
    Fifo<Point> send;
    Thread productor;
    Thread consumer;
    
    public Client(String Connection){
        try{
            String[] split = Connection.split(":");
            String host = split[0];
            int port = Integer.parseInt(split[1]);
            socket = new Socket(host,port);
            System.out.println("connected to the Transfer server");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            port = Integer.parseInt(in.readLine());
            socket.close();
            socket = new Socket(host,port);
            System.out.println("connected to the Game server");
        }
        catch(Exception e){e.getMessage();}
    }

    public void port(){
        System.out.println(socket.getPort());
    }

    public void begin(){
        file = new ArrayList<>();
        file.add(new Fifo<>());
        send = new Fifo<>();
        try{
            Productor p = new Productor(new BufferedReader(new InputStreamReader(socket.getInputStream())), file);
            productor = new Thread(p);

            Consumer c = new Consumer(send,new PrintWriter(socket.getOutputStream(),true));
            consumer = new Thread(c);

            productor.start();
            consumer.start();
        }catch(Exception e){e.getMessage();}
    }

    public void listen(){
        Scanner s = new Scanner(System.in);
        String[] entree;
        //int x,y;
        while(true){
            entree = s.nextLine().split("\\s+");
            if(entree[0].equals("add")){
                Point p = new Point(Integer.parseInt(entree[1]),Integer.parseInt(entree[2]));
                send.add(p);
                file.get(0).add(p);
            }
            if(entree[0].equals("check")){System.out.println(socket.isConnected());}
            if(entree[0].equals("show")){System.out.println(file.get(0));}
            if(entree[0].equals("quit")){break;}

        }
        s.close();
    }

}

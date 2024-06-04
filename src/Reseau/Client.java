package Reseau;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import Reseau.Runnables.Transfer.Consumer;
import Reseau.Runnables.Transfer.Productor;

import java.io.*;
import Structure.*;
import Model.*;


public class Client {
    
    Socket socket;
    ArrayList<Fifo> file;
    Fifo send;
    Thread productor;
    Thread consumer;
    int ID;
    
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

    public void writeLine(String string){
        try{PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println(string);
        }
        catch(IOException e){System.err.println(e);} 
    }

    public String readLine(){
        try{BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        }
        catch(IOException e){e.getMessage();}
        return null;
    }
    

    

    public void port(){
        System.out.println(socket.getPort());
    }

    public void begin(Fifo send, Fifo recieve){
        file = new ArrayList<>();
        file.add(recieve);
        this.send = send;
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
        String entree;
        String[] split;
        while(true){
            entree = s.nextLine();
            split = entree.split("\\s+");
            switch (split[0]) {
                case "add":
                    entree = entree.replace("add ","");
                    Coup coup = Coup.fromString(entree);
                    send.add(coup);
                    file.get(0).add(coup);
                    break;
                case "check":
                    System.out.println(socket.isConnected());
                    break;
                case "show":
                    System.out.print(file.get(0));
                    break;
                default:
                    break;
            }
            if(split[0].equals("quit")){break;}

        }
        s.close();
    }

}

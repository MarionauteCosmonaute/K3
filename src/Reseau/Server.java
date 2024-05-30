package Reseau;

import java.net.*;

import Reseau.Runnables.Connection.*;

import java.io.*;

public class Server {
    int nbClient;
    
    int[] portNumber;
    ServerSocket[] ServerSockets;
    Socket[] Sockets;
    Thread[] threads;

    ServerSocket connectionSsocket;
    int connectionPort;
    Thread connectionThread;
    

    public Server(int nbClient){
        portNumber = new int[nbClient];
        ServerSockets = new ServerSocket[nbClient];
        Sockets = new Socket[nbClient];
        threads = new Thread[nbClient];
        this.nbClient = nbClient;
    }

    public int initSockets(){
                /* get available port for the main socket */
        try{
            connectionSsocket = new ServerSocket(0);
            connectionPort = connectionSsocket.getLocalPort();
            
        } catch (IOException e) {e.getMessage();System.exit(2);}
    
        for (int i = 0; i < nbClient; i++){
            try{
                ServerSockets[i] = new ServerSocket(0);
                portNumber[i] = ServerSockets[i].getLocalPort();
            } catch (IOException e) {e.getMessage();System.exit(2);}
        }
        return connectionPort;
    }

    public void initConnection(){
        connectionThread = new Thread(new PortTransfer(connectionSsocket,portNumber));
        connectionThread.start();

        for(int i = 0; i < nbClient; i++){
            threads[i] = new Thread(new AcceptConnection(ServerSockets[i], Sockets,i));
            threads[i].start();
        }
    }

    public void begin(){            /* We'll have to change the nbClient depending on if not everyone connected */
        Task task = new Task(nbClient);
        task.init(Sockets);
    }

    public void Wait(){
        try{connectionThread.wait();}
        catch(Exception e){}
    }
    public void WaitAll(){
        for(Thread thread : threads){
            try{thread.join();}
            catch(Exception e){}
        }
        System.out.println("all got connected");
    }

    public int getPort(){
        return connectionPort;
    }

    public void showPort(){
        for (int i : portNumber){
            System.out.println(i);
        }
    }

    public void closeMain(){
        try{
            connectionSsocket.close();
        }catch(IOException e){e.getMessage();}
    }

    public void close(){
        closeMain();
        for (ServerSocket s : ServerSockets){
            try{s.close();}
            catch(IOException e){e.getMessage();}
        }
        for (Socket s : Sockets){
            try{s.close();}
            catch(IOException e){e.getMessage();}
        }
    }


}
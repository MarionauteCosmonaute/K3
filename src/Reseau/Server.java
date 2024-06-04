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
    


    private void transferPyraAndBag(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(Sockets[0].getInputStream()));
            String pyra = in.readLine();
            for (int i = 1; i < nbClient; i++){
                String bag = in.readLine();
                PrintWriter out = new PrintWriter(Sockets[i].getOutputStream(),true);
                out.println(pyra);
                out.println(bag);
            }
        }
        catch(IOException e){System.err.println("Excetption in PrincipaleTransfer:\n" + e);}
    }
    
    private void transferPyramides(){
        try{
            String[] string = new String[nbClient];
            for(int i = 0; i < nbClient; i++){
                BufferedReader in = new BufferedReader(new InputStreamReader(Sockets[i].getInputStream()));
                string[i] = in.readLine();
            }
            for(int i = 0; i < nbClient; i++){
                for(int j = 0; j < nbClient; j++){
                    if(i!=j){
                        PrintWriter out = new PrintWriter(Sockets[j].getOutputStream(),true);
                        out.println(string[i]);
                    }
                    
                }
                
            }
            for(int i = 0; i < nbClient; i++){
                System.out.println("Waiting");
                BufferedReader in = new BufferedReader(new InputStreamReader(Sockets[i].getInputStream()));
                string[i] = in.readLine();
                System.out.println("Pyramide recu: " + string);
                for (int j = 0; j < nbClient; j++){
                    if(j!=i){
                        System.out.println("Pyramide envoyer a la socket du joueur " + j + " a la socket: " + Sockets[j]);

                        PrintWriter out = new PrintWriter(Sockets[j].getOutputStream(),true);
                        out.println(string);
                    }
                }
            }
        }
        catch(IOException e){System.err.println("Excetption in PyramidTransfer:\n" + e);}
    }

    public void transferInfo(){
        transferPyraAndBag();
        transferPyramides();

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
        try{for(int i = 0; i < nbClient; i++){
            PrintWriter out = new PrintWriter(Sockets[i].getOutputStream(),true);
            out.println("ok");
        }}
        catch(Exception e){}
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
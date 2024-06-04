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

    BufferedReader[] in;
    PrintWriter[] out;
    

    public Server(int nbClient){
        portNumber = new int[nbClient];
        ServerSockets = new ServerSocket[nbClient];
        Sockets = new Socket[nbClient];
        threads = new Thread[nbClient];
        this.nbClient = nbClient;

        in = new BufferedReader[nbClient];
        out = new PrintWriter[nbClient];
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
            String pyra = in[0].readLine();
            System.out.println(pyra + " La pyramide milleu viens d'etre recu");
            for (int i = 1; i < nbClient; i++){
                String bag = in[0].readLine();
                System.out.println(bag + " Le bag" + i +" viens d'etre recu");
                out[i].println(pyra);
                System.out.println(pyra + " La pyramide milleu viens d'etre envoyer");
                out[i].println(bag);
                System.out.println(bag + " Le bag" + i + " viens d'etre envoyer");
            }
        }
        catch(IOException e){System.err.println("Excetption in PrincipaleTransfer:\n" + e);}
    }
    
    private void transferPyramides(){
        try{
            String[] string = new String[nbClient];
            for(int i = 0; i < nbClient; i++){
                
                string[i] = in[i].readLine();
                System.out.println("Pyramid received: " + string[i]);
            }
            
            System.out.println("toute les pyramide sont recus");
            
            for(int i = 0; i < nbClient; i++){
                for(int j = 0; j < nbClient; j++){
                    if(i != j){
                        out[i].println(string[j]);
                        System.out.println("Pyramid sent: " + string[j]);
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
        in = null;
        out = null;
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
        try{
            for(int i = 0; i < nbClient; i++){
                out[i] = new PrintWriter(Sockets[i].getOutputStream(),true);
                in[i] = new BufferedReader(new InputStreamReader(Sockets[i].getInputStream()));
                out[i].println("ok");
            }
        }
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
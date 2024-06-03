package Reseau.Runnables.Connection;
import java.net.*;

public class AcceptConnection implements Runnable{
    ServerSocket ssocket;
    Socket[] socket;
    int index;

    public AcceptConnection(ServerSocket ssocket, Socket[] sockets,int index){
        this.ssocket = ssocket;
        this.socket = sockets;
        this.index = index;
    }

    @Override
    public void run(){
        try{
            //System.out.println(ssocket + " connection opened");
            socket[index] = ssocket.accept();
            //System.out.println(socket + " connection established");
        }
        catch(Exception e){e.getMessage();}
    }
}

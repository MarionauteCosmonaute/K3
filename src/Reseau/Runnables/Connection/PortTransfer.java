// package Reseau.Runnables.Connection;

// import java.io.PrintWriter;
// import java.net.*;

// public class PortTransfer implements Runnable{
//     ServerSocket ssocket;
//     int[] ports;

//     public PortTransfer(ServerSocket ssocket,int[] ports){
//         this.ssocket = ssocket;
//         this.ports = ports;
//     }

//     @Override
//     public void run(){
//         Socket socket;
//         for(int i = 0; i < ports.length; i++ ){
//             try{
//                 socket = ssocket.accept();
//                 PrintWriter out = new PrintWriter(socket.getOutputStream(),true); 
//                 out.println(ports[i]);
//                 socket.close();
//             }
//             catch(Exception e){e.getMessage();}
//         }
//     }
// }

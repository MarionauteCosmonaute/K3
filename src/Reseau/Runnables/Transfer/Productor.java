// package Reseau.Runnables.Transfer;

// import java.io.BufferedReader;

// import Structure.Fifo;
// import java.util.ArrayList;

// import Model.Coup;

// public class Productor implements Runnable{
//     BufferedReader in;
//     ArrayList<Fifo> file;
//     Fifo coupJouer;
//     int index;

//     public Productor(BufferedReader in, ArrayList<Fifo> file,Fifo coupJouer, int index){
//         init(in,file,coupJouer);
//         this.index = index;
//     }
//     public Productor(BufferedReader in, ArrayList<Fifo> file){
//         init(in,file,coupJouer);
//         index = -1;
//     }

//     public void init(BufferedReader in, ArrayList<Fifo> file,Fifo coupJouer){
//         this.in = in;
//         this.file = file;
//         this.coupJouer = coupJouer;
//     }


//     @Override
//     public void run(){
//         //String[] split;
//         try{
//             while (true) {
//                 Coup coup = coupJouer.get();
//                 for(int i = 0; i < file.size(); i++){
//                     if(i != index){
//                         file.get(i).add(coup);
//                     }
//                 }
//             }
//         }catch(Exception e){e.getMessage();}
//     }
// }

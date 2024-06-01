package Model.Runnables;

import Model.IA_pack.*;


public class Compute implements Runnable{
    IA ia;
    public Compute(IA ia){
        this.ia = ia;
    }
    public void run(){
        ia.calcul();    
    }
}

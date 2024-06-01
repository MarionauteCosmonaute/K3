package Model.Runnables;

import Model.IA_pack.IA;

public class StartConstruction implements Runnable {

    IA ia;
    boolean aide;

    public StartConstruction(IA ia,boolean aide) {
        this.aide = aide;
        this.ia = ia;
    }

    public void run() {
        ia.generationPyramide(aide);
    }
}
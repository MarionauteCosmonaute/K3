package Model.Runnables;

import Model.IA_pack.IA;

public class StartConstruction implements Runnable {

    IA ia;

    public StartConstruction(IA ia) {
        this.ia = ia;
    }

    public void run() {
        ia.generationPyramide();
    }
}
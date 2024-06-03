package Patterns;
import java.util.Vector;

public abstract class MyObservable {
    Vector<Observateur> O=new Vector<>();
    public void AddObs(Observateur o){O.add(o);}
    public void MAJ(){
        for (Observateur obs : O) {
            obs.Update();   
        }
    }
}
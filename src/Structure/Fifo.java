package Structure;

import Model.Coup;

public class Fifo {
    Maillon tete,queue;
    
    public Fifo(){
        tete = null;
        queue = null;
    }
    
    public class Maillon {    
        Coup element;
        Maillon suivant;
    
        public Maillon(Coup e){
            element = e;
            suivant = null; 
        }
        
        public void suivant(Maillon m){
            suivant = m;
        }
        public Coup get(){
            return element;
        }
    }

    synchronized public void add(Coup e){
        Maillon m = new Maillon(e);
        System.out.println(e + " got added");
        if (tete == null){
            tete = m;
            queue = tete;
        }
        else{
            queue.suivant = m;
            queue = m;
        }
        notifyAll();
    }

    synchronized public Coup get(){
        if(isEmpty()){try{wait();}catch(Exception e){e.getMessage();}}
        Coup element = tete.get();
        if(tete == queue){queue = null;}
        tete = tete.suivant;
        return element;
    }

    boolean isEmpty(){
		return tete == null;
	}

    @Override
    public String toString() {
        Maillon m = tete;
        String chaine = "";
        while(m != null){
            chaine += m.get()+ "\n";
            m = m.suivant;
        }
        return chaine;
    }

}


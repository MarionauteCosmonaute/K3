package Structure;


public class Fifo<E> {
    Maillon<E> tete,queue;
    
    public Fifo(){
        tete = null;
        queue = null;
    }
    
    public class Maillon<E> {    
        E element;
        Maillon<E> suivant;
    
        public Maillon(E e){
            element = e;
            suivant = null; 
        }
        
        public void suivant(Maillon<E> m){
            suivant = m;
        }
        public E get(){
            return element;
        }
    }

    synchronized public void add(E e){
        Maillon<E> m = new Maillon<>(e);
        // System.out.println(e + "got added");
        if (tete == null){
            tete = m;
            queue = tete;
        }
        else{
            queue.suivant = m;
            queue = m;
        }
        notify();
    }

    synchronized public E get(){
        if(isEmpty()){try{wait();}catch(Exception e){e.getMessage();}}
        E element = tete.get();
        if(tete == queue){queue = null;}
        tete = tete.suivant;
        return element;
    }

    boolean isEmpty(){
		return tete == null;
	}

    @Override
    public String toString() {
        Maillon<E> m = tete;
        String chaine = "";
        while(m != null){
            chaine += m.get()+ "\n";
            m = m.suivant;
        }
        return chaine;
    }

}


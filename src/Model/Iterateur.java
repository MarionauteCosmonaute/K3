package Model;

public interface Iterateur {

    boolean aProchain();

    Cube prochain();

    void modifie(Cube cube);
}

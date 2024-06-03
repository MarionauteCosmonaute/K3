package Model.Iterateur;

import Model.Cube;

public interface Iterateur {

    boolean hasNext();

    Cube next();

    void modify(Cube cube);
}

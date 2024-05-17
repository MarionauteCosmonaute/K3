package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RetourMenuPAdapeur implements ActionListener {

    CollecteurEvenements controle;

    public RetourMenuPAdapeur(CollecteurEvenements c) {
        controle = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controle.commande("MenuP");
    }

}

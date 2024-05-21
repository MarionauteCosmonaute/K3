package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameActionListener implements ActionListener{
    CollecteurEvenements controle;
    public NewGameActionListener(CollecteurEvenements c){
        controle=c;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        controle.commande("MenuLocal");
    }

}

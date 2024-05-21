package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdaptateurNewGame implements ActionListener {
    CollecteurEvenements control;

    AdaptateurNewGame(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("NouvellePartie");
	}
}

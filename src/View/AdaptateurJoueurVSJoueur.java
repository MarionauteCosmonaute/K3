package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoueurVSJoueur implements ActionListener {
	CollecteurEvenements control;

	AdaptateurJoueurVSJoueur(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("JoueurVSJoueur");
	}
}
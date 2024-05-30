package View.Adaptateurs;

import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurIADifficile implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurIADifficile(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.setIADifficulty(2);
		control.commande("JoueurVSIA");
	}
}
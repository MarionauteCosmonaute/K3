package View.Adaptateurs;

import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurIAMoyen implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurIAMoyen(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.setIADifficulty(1);
		control.commande("JoueurVSIA");
	}
}
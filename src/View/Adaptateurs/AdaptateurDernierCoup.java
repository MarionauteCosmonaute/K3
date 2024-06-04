package View.Adaptateurs;
import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurDernierCoup implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurDernierCoup(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("DernierCoupJoué");
	}
}

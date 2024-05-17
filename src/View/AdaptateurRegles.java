package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRegles implements ActionListener {
	CollecteurEvenements control;

	AdaptateurRegles(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Regles");
	}
}

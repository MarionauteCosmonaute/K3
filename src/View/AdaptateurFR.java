package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurFR implements ActionListener {
	CollecteurEvenements control;

	AdaptateurFR(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("FR");
	}
}

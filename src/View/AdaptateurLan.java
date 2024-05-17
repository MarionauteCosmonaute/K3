package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurLan implements ActionListener {
	CollecteurEvenements control;

	AdaptateurLan(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Lan");
	}
}

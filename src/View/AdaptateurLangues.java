package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurLangues implements ActionListener {
	CollecteurEvenements control;

	AdaptateurLangues(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Langues");
	}
}

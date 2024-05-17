package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurValider implements ActionListener {
	CollecteurEvenements control;

	AdaptateurValider(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Valider");
	}
}

package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurEN implements ActionListener {
	CollecteurEvenements control;

	AdaptateurEN(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("EN");
	}
}

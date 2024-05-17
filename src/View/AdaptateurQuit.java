package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurQuit implements ActionListener {
	CollecteurEvenements control;

	AdaptateurQuit(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("quit");
	}
}

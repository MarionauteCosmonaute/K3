package View.Adaptateurs;
import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurReset implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurReset(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Reset");
	}
}

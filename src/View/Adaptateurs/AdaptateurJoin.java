package View.Adaptateurs;
import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoin implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurJoin(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Join");
	}
}

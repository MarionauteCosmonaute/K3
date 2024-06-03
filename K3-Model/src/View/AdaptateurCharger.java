package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdaptateurCharger implements ActionListener {
    CollecteurEvenements control;

    AdaptateurCharger(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Charger");
	}
}
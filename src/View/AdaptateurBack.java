package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdaptateurBack implements ActionListener {
    CollecteurEvenements control;

    AdaptateurBack(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Back");
	}
}

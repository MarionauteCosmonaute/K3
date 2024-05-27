package View.Adaptateurs;
import View.CollecteurEvenements;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurIAFacile implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurIAFacile(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.setIADifficulty(0);
		control.commande("JoueurVSIA");
	}
}
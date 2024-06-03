package View.Adaptateurs;
import View.CollecteurEvenements;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoueurVSIA implements ActionListener {
	CollecteurEvenements control;

	public AdaptateurJoueurVSIA(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
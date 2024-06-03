package View.Adaptateurs;
import View.CollecteurEvenements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class AdaptateurSon implements ActionListener {
	CollecteurEvenements control;
	JPanel parent;

	public AdaptateurSon(CollecteurEvenements c,JPanel con) {
		control = c;
		parent=con;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		control.commande("Son");
		parent.repaint();
	}
}
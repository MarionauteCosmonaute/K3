package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurSon implements ActionListener
{
    CollecteurEvenements control;

    AdaptateurSon(CollecteurEvenements c)
    {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e)
    {
		control.commande("Son");
	}
}
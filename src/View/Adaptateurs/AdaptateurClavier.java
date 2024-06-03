package View.Adaptateurs;
import View.CollecteurEvenements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdaptateurClavier extends KeyAdapter {
	CollecteurEvenements controle;

	public AdaptateurClavier(CollecteurEvenements c) {
		controle = c;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("KeyPressed AdaptateurClavier");
		switch (e.getKeyCode()) {
			case KeyEvent.VK_Q:
				controle.commande("quit");
				break;
			case KeyEvent.VK_U:
				controle.commande("Undo");
				break;
			case KeyEvent.VK_R:
				controle.commande("Redo");
				break;

			case KeyEvent.VK_F11:
				System.out.println("fullscreen");
				controle.commande("fullscreen");
				break;

			default:
				//System.out.println("default");
				break;
		}
	}
}
package View;
import View.Adaptateurs.*;
import View.Menu.Menu;
import View.Menu.*;

import Model.Jeu;
import Patterns.Observateur;
import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable, Observateur {
	JFrame frame;
	CollecteurEvenements controle;
	Jeu jeu;
	//NiveauGraphique niv;
	boolean maximized;
	Timer t;
	Boolean bool = true;

	InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
		this.jeu = jeu;
		controle = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		InterfaceGraphique vue = new InterfaceGraphique(j, c);
		c.ImporterVue(vue);

		SwingUtilities.invokeLater(vue);
	}

	public void stopTimer() {
		t.stop();
	}

	public void startTimer() {
		t.start();
	}

	public void basculePleinEcran() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(frame);
			maximized = true;
		}
	}

	@Override
	public void miseAJour() {

	}

	public void run() {
		frame = new BackgroundPanel();
		// frame = new JFrame();
		controle.addFenetre(frame);
		frame.setTitle("K3");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//OldPhaseConstruction cons = new OldPhaseConstruction(frame, controle, jeu);
		//niv = new NiveauGraphique(jeu, cons);

		// new FenetreNouvellePartie(frame, controle);

		// Generation de toutes les fenetres
		MenuPrincipal Mp = new MenuPrincipal(controle);
		frame.add(Mp);
		Mp.setVisible(true);
		MenuNouvellePartie Mnp = new MenuNouvellePartie(controle);
		MenuPhaseConstruction pC =new MenuPhaseConstruction(controle, jeu);
		MenuPhaseDeJeu2 PhaseDeJeu2 = new MenuPhaseDeJeu2(controle, jeu);
		controle.commande("MenuOnline");

		// On ajoute la souris et le clavier

		frame.addKeyListener(new AdaptateurClavier(controle));

		frame.setVisible(true);
		frame.requestFocusInWindow();
		// System.out.println(frame.getComponentCount());
	}

	public void addFrame(Menu getcurMenu) {
		frame.add(getcurMenu);
	}
}
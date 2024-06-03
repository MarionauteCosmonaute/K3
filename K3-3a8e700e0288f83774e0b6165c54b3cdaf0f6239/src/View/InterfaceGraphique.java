package View;
import View.Adaptateurs.*;
import View.Menu.Menu;
import View.Menu.*;

import Model.Jeu;
import Patterns.Observateur;
import javax.swing.*;

import Global.FileLoader;

import java.awt.*;

public class InterfaceGraphique implements Runnable, Observateur {
	JFrame frame;
	CollecteurEvenements controle;
	Jeu jeu;
	boolean maximized;
	Timer timer;
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
		timer.stop();
	}

	public void startTimer() {
		timer.start();
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

		try {
			frame.setIconImage(FileLoader.getImage("res/IconeV2.png"));
		} catch (Exception e) {
			System.out.println("Erreur de chargement de l'icone");
		}
		
		// frame = new JFrame();
		controle.addFenetre(frame);
		frame.setTitle("K3");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//OldPhaseConstruction cons = new OldPhaseConstruction(frame, controle, jeu);
		//niv = new NiveauGraphique(jeu, cons);

		// new FenetreNouvellePartie(frame, controle);

		// Generation de toutes les fenetres
		MenuPrincipal mp = new MenuPrincipal(controle);
		frame.add(mp);
		mp.setVisible(true);
		MenuNouvellePartie mnp = new MenuNouvellePartie(controle);
		MenuPhaseConstruction pC =new MenuPhaseConstruction(controle, jeu);
		MenuPhaseDeJeu2 phaseDeJeu2 = new MenuPhaseDeJeu2(controle, jeu);
		MenuPhaseDeJeuJVIA phaseDeJeuJVIA = new MenuPhaseDeJeuJVIA(controle,jeu);
		MenuOnline online =new MenuOnline(controle);
		timer = new Timer(5000,new AdaptateurJoueIA(controle));
		//controle.commande("MenuOnline");

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

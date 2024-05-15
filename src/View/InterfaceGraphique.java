package View;

import Model.Jeu;
import Patterns.Observateur;
import javax.swing.*;
import java.awt.*;


public class InterfaceGraphique implements Runnable, Observateur
{
    JFrame frame;
    CollecteurEvenements controle;
    Jeu jeu;
    AffichagePhaseConstruction niv;
    boolean maximized;
	Timer t;
	Boolean bool = true;

    InterfaceGraphique(Jeu jeu, CollecteurEvenements c)
    {
        this.jeu = jeu;
        controle = c;
    }

    public static void demarrer(Jeu j, CollecteurEvenements c)
    {
        InterfaceGraphique vue = new InterfaceGraphique(j, c);
        c.ImporterVue(vue);
		SwingUtilities.invokeLater(vue);
	}

	public void stopTimer(){
		t.stop();
	}

	public void startTimer(){
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
	public void miseAJour()
	{
	}

    public void run()
    {
		// frame = new BackgroundPanel();
		frame = new JFrame();
		frame.setTitle("K3");
		frame.setSize(500, 300);
       	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PhaseConstruction cons = new PhaseConstruction(frame, controle, jeu);
        niv = new AffichagePhaseConstruction(jeu, cons);

		

		//new FenetrePrincipale(frame,controle);
		// new FenetreNouvellePartie(frame, controle);

		// On ajoute la souris et le clavier
		niv.addMouseListener(new AdaptateurSouris(controle, niv));
		frame.addKeyListener(new AdaptateurClavier(controle));
		frame.add(niv);

		frame.setVisible(true);
		frame.requestFocusInWindow();
    }

	public void addFrame(Menu getcurMenu) {
		frame.add(getcurMenu);
	}
}
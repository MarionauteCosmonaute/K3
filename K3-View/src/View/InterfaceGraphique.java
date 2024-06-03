package View;
import View.Adaptateurs.*;
import View.Menu.Menu;
import View.Menu.*;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

import Model.Jeu;
import Patterns.Observateur;
import javax.swing.*;

import Global.FileLoader;

import java.awt.*;
import java.util.Vector;

public class InterfaceGraphique implements Runnable, Observateur {
	BackgroundPanel frame;
	Vector<Menu> menuListe = new Vector<>();
	int current_menu=0;
	int countMenu=0;
	CollecteurEvenements controle;
	Jeu jeu;
	boolean maximized;
	Timer tickIA;
	Timer anim;
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


	public void TimerIA(boolean timerIA) {
		if (timerIA){
			tickIA.start();
		}else{
			tickIA.stop();
		}
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
		controle.ImporterVue(this);
		frame.setTitle("K3");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//OldPhaseConstruction cons = new OldPhaseConstruction(frame, controle, jeu);
		//niv = new NiveauGraphique(jeu, cons);

		// new FenetreNouvellePartie(frame, controle);

		// Generation de toutes les fenetres
		MenuPrincipal mp = new MenuPrincipal(controle);//0
		addMenu(mp);
		frame.add(mp);
		mp.setVisible(true);
		MenuNouvellePartie mnp = new MenuNouvellePartie(controle);//1
		addMenu(mnp);
		MenuPhaseConstruction pC =new MenuPhaseConstruction(controle, jeu);//2
		addMenu(pC);
		MenuPhaseDeJeu2 phaseDeJeu2 = new MenuPhaseDeJeu2(controle, jeu);//3
		addMenu(phaseDeJeu2);
		MenuPhaseDeJeuJVIA phaseDeJeuJVIA = new MenuPhaseDeJeuJVIA(controle,jeu);//4
		addMenu(phaseDeJeuJVIA);
		MenuOnline online =new MenuOnline(controle);//5
		addMenu(online);
		tickIA = new Timer(2000,new AdaptateurJoueIA(controle));
		// anim=new Timer(1000,new ActionListener(){
// 
			// @Override
			// public void actionPerformed(ActionEvent e){
				// getcurMenu().repaint();
			// }
		// });
		//anim.start();
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

	public void addMenu(Menu m) {
		menuListe.add(m);
		countMenu++;
	}

	public void changeVisible(int n_indice) {
		getcurMenu().setVisible(false);
		frame.remove(getcurMenu());
		if(current_menu == 2)
		{
			((MenuPhaseConstruction)menuListe.get(current_menu)).getAffichagePhaseConstruction().resetBooleans();
		}
		current_menu = n_indice;
		addFrame(getcurMenu());
		getcurMenu().repaint();
		getcurMenu().setVisible(true);
	}

	public Menu getcurMenu() {
		return menuListe.get(current_menu);
	}

	public void setBackgroundPicture(String s){
		frame.setBackgroundPicture(s);
	}

	public Menu getDisplayedMenu(){
		return menuListe.get(current_menu);
	}


	public Menu getMenuPrincipal(){
		return menuListe.get(0);
	}
	
	public Menu getMenuNouvellePartie(){
		return menuListe.get(1);
	}

	public Menu getMenuPhaseConstruction(){
		return menuListe.get(2);
	}

	public Menu getMenuPhaseDeJeu2(){
		return menuListe.get(3);
	}
	
	public Menu getMenuPhaseDeJeuJVIA(){
		return menuListe.get(4);
	}

	public Menu getMenuOnline(){
		return menuListe.get(5);
	}

	public void updateSablier(boolean b){
		getMenuPhaseDeJeuJVIA().updateSablier(b);
	}


}

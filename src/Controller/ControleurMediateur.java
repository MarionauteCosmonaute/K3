package Controller;

import View.*;

import java.util.Vector;

import javax.swing.JFrame;

import Model.*;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	InterfaceGraphique vue;
	boolean toggleIA = false;
	MusicPlayer musique;
	JFrame frame = null;
	Vector<Menu> menuListe = new Vector<>();
	int indice_courant = 0;
	int joueur_initial;

	Cube cube;
	// int x, y;

	public ControleurMediateur(Jeu j, MusicPlayer musique) {
		jeu = j;
		this.musique = musique;
		joueur_initial = j.get_player();
	}

	public void addMenu(Menu m) {
		menuListe.add(m);
	}

	public void addFenetre(JFrame j) {
		frame = j;
	}

	private Menu getcurMenu() {
		return menuListe.get(indice_courant);
	}

	public void ImporterVue(InterfaceGraphique vue) {
		this.vue = vue;
	}

	@Override
	public void clicSouris(int x, int y) {
		System.out.println("x : " + x + ",y : " + y);
	}

	@Override
	public void clicSourisPyr(int ligne, int col) {
		jeu.construction(ligne, col, cube);
		// System.out.println(cube);
		// System.out.println("case : ("+ligne+","+col+")");
	}

	@Override
	public void clicSourisEchange(int x1, int y1, int x2, int y2) {
		jeu.permutation(x1, y1, x2, y2);
		// System.out.println(cube);
		// System.out.println("case : ("+ligne+","+col+")");
	}

	@Override
	public void clicSourisPioche(int couleur) {
		// System.out.println("coul : " + couleur);
		switch (couleur) {
			case 0:
				cube = Cube.Noir;
				break;
			case 1:
				cube = Cube.Neutre;
				break;
			case 2:
				cube = Cube.Blanc;
				break;
			case 3:
				cube = Cube.Vert;
				break;
			case 4:
				cube = Cube.Jaune;
				break;
			case 5:
				cube = Cube.Rouge;
				break;
			case 6:
				cube = Cube.Bleu;
				break;
		}

		// System.out.println("Pioche : ("+ligne+","+col+")");
	}

	@Override
	public boolean commande(String c) {
		switch (c) {
			case "quit":
				System.exit(0);
				break;

			case "fullscreen":
				vue.basculePleinEcran();
				break;

			case "Lan":
				break;

			case "Charger":
				break;

			case "FR":
				break;

			case "EN":
				break;

			// case "Reset":
			// jeu.resetBag();
			// vue.phaseConstruction().setValider(false);
			// vue.phaseConstruction().repaint(); // ça me paraît bizarre de faire ça comme
			// ça
			// break;
			//
			// case "AideConstruction":
			// jeu.constructionAleatoire(jeu.getPlayer(jeu.get_player()));
			// vue.phaseConstruction().repaint();
			// break;

			case "JoueurVSJoueur":
			   ((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				changeVisible(2);
				//jeu.reset(2, false); // On cree une partie a 2

				break;

			case "Valider":
				jeu.avance();
				// if(jeu.get_player().estIA()){
				// jeu.avance();
				// }
				if (jeu.get_player() == joueur_initial) {

					// passer au menu d'après
				}

			case "Son":
				System.out.println("Case son de ControleurMediateur");
				musique.jouerMusique();
				break;
			case "MenuP":
				((BackgroundPanel) frame).setBackgroundPicture("res/Back.png");
				changeVisible(0);
				break;
			case "MenuLocal":
				changeVisible(1);
				break;
			case "PDJ2":
				changeVisible(3);
				((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				break;
			default:
				return false;
		}
		return true;
	}

	public void changeVisible(int n_indice) {
		getcurMenu().setVisible(false);
		indice_courant = n_indice;
		vue.addFrame(getcurMenu());
		getcurMenu().setVisible(true);
	}
}
package Controller;

import View.*;
import Model.*;
import View.Menu.*;

import java.util.Vector;

import javax.swing.JFrame;



public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	InterfaceGraphique vue;
	boolean toggleIA = false;
	MusicPlayer musique;
	JFrame frame = null;
	Vector<Menu> menuListe = new Vector<>();
	int indice_courant = 0;
	int joueur_initial;
	static boolean clic = false;
	static int ligne, colonne;

	Cube cube, cube_selectionne;

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
	public void clicJoueur(int x, int y) {
		cube_selectionne = jeu.getPlayer().get(x, y);
		System.out.println("x: "+x+" y: "+y+ ", cube : "+cube_selectionne);
		clic = true;
		ligne = x;
		colonne = y;
	}

	@Override
	public void clicPyramideCentrale(int x, int y) {
		System.out.println("x: "+x+" y: "+y);
	}

	public static int GetLigne()
	{
		return ligne;
	}

	public static int GetColonne()
	{
		return colonne;
	}

	public static void SetClic(boolean bool){
		clic = bool;
	}

	public static boolean GetClic(){
		return clic;
	}

	@Override
	public void clicSourisPyr(int ligne, int col) {
		jeu.construction(ligne, col, cube);
		 System.out.println(cube);
		 System.out.println("case : ("+ligne+","+col+")");
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
		cube = Cube.intToCube(couleur);
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
				Global.Config.setLanguage("FR");
				menuListe.get(0).updateLanguageCode();
				break;

			case "EN":
				Global.Config.setLanguage("EN");
				menuListe.get(0).updateLanguageCode();
				break;

			case "Reset":
				jeu.resetBag();
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().setValider(false);
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().repaint(); // ça me paraît bizarre de faire ça comme ça
			break;
			
			case "AideConstruction":
				jeu.constructionAleatoire(jeu.getPlayer());
				System.out.println(jeu.getPlayer().getPyramid());
				((MenuPhaseConstruction)menuListe.get(indice_courant)).repaint();
			break;

			case "JoueurVSJoueur":
			    ((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				changeVisible(2);
				//jeu.reset(2, false); // On cree une partie a 2

				break;

			case "Valider":
				jeu.avance();
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().setValider(false);
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().repaint();
				// if(jeu.get_player().estIA()){
				// jeu.avance();
				// }
				if (jeu.get_player() == joueur_initial) {
					changeVisible(3);

				}
				break;

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
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
	static int ligne_joueur, colonne_joueur;
	boolean penalty;
	boolean IAON=false;
	IA ia;

	Cube cube, cube_selectionne;

	public ControleurMediateur(Jeu j, MusicPlayer musique) {
		jeu = j;
		this.musique = musique;
		joueur_initial = j.get_player();
		penalty = false;
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
	public void clicJoueurPyramide(int x, int y) {
		// cube_selectionne = jeu.getPlayer().get(x, y);
		if(penalty == true){
			//tester que le cube est accessible dans la pyramide du joueur
			if(jeu.accessible(x,y)){
				jeu.takePenaltyCube(x, y); // y : mettre -1 si ça vient du side
				penalty = false;
				// if (jeu.End_Game())
				// {
				// 	FinPartie();
				// }
			}
		}
		else{
			clic = true;
			ligne_joueur = x;
			colonne_joueur = y; //mettre -1 si ça vient du side
		}
		
	}

	@Override
	public void clicJoueurSide(int x) {
		// cube_selectionne = jeu.getPlayer().get(x, y);
		if(penalty == true){
			//tester que le cube est accessible dans la pyramide du joueur
			jeu.takePenaltyCube(x, -1); // y : mettre -1 si ça vient du side
			penalty = false;
			// if (jeu.End_Game())
			// {
			// 	FinPartie();
			// }
		}
		else{
			clic = true;
			ligne_joueur = x;
			colonne_joueur = -1; //mettre -1 si ça vient du side
		}
		
	}

	@Override
	public void clicPyramideCentrale(int x, int y) {
		int res;

		// if(jeu.accessible(ligne_joueur,colonne_joueur)){
		res = jeu.jouer_coup(x, y, ligne_joueur, colonne_joueur);
		if(res == 1 || res == 2 || res == 3){
			clic = false;
		}
		if(res == 2){
			penalty = true;
			if (IAON && jeu.get_player() == 0){
				ia.takePenaltyCube();
				penalty = false;
			}
		}
		vue.startTimer();
	}

	public void FinPartie()
	{
		System.out.println("Fin de la Partie!");
	}

	public static int GetLigne()
	{
		return ligne_joueur;
	}

	public static int GetColonne()
	{
		return colonne_joueur;
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
	}

	@Override
	public void clicSourisEchange(int x1, int y1, int x2, int y2) {
		jeu.permutation(x1, y1, x2, y2);
	}

	@Override
	public void clicSourisPioche(int couleur) {
		cube = Cube.intToCube(couleur);
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
				IAON=false;
			    ((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				changeVisible(2);
				jeu.reset(2);
				jeu.initPrincipale();
				joueur_initial=jeu.get_player();
				while(jeu.draw()){} // On cree une partie a 2
				break;

			case "JoueurVSIA":
				IAON=true;
				((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				changeVisible(2);
				jeu.reset(2);
				jeu.initPrincipale();
				joueur_initial=jeu.get_player();
				ia = IA.nouvelle(jeu,0,1);
				vue.startTimer();
				while(jeu.draw()){} // On cree une partie a 2
				jeu.constructionAleatoire(jeu.getPlayer(1)); // a enlever quand l'IA construira la pyramide
				if(IAON && jeu.get_player()==1){
					jeu.avance();
				}
				break;

			case "Valider":
				jeu.avance();
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().setValider(false);
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().repaint();
				
				if (jeu.get_player() == joueur_initial || (IAON && jeu.get_player()==1) ) {
					changeVisible(3);
					jeu.gameStart();
				}
				break;

			case "Son":
				System.out.println("Case son de ControleurMediateur");
				musique.jouerMusique();
				break;

			case "MenuP":
				vue.stopTimer();
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
			case "JoueIA":
				if (jeu.get_player()==1 && !penalty){
					if (ia.jouer_coup() == 2){
						penalty = true;
					}
					vue.stopTimer();
				}
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

	@Override
	public boolean penaltyPhase() {
		return penalty;
	}
}
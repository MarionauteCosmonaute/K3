package Controller;

import View.*;
import Model.*;
import View.Menu.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	int d=0;
	InterfaceGraphique vue;
	String qs="saves/quicksave.txt";
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
	int gagnant;
	Cube cube, cube_selectionne;

	public ControleurMediateur(Jeu j, MusicPlayer musique) {
		jeu = j;
		this.musique = musique;
		joueur_initial = j.get_player();
		penalty = false;
		gagnant = -1;
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
			PDJPyramideJoueur.SetCube_Select_Static(false);
			if(jeu.accessible(x,y)){
				jeu.takePenaltyCube(x, y); // y : mettre -1 si ça vient du side
				jeu.sauvegarde(qs);
				penalty = false;
				if(jeu.getPlayer().lost()){
					IAON=false;
					gagnant = jeu.next_player();
					System.out.println("cas 1 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
					int reponse = DialogueFinPartie(gagnant + 1);
					switch(reponse){
						case 0 :
							jeu.playerNoLost(jeu.get_player());
							break;
						case 1 :
							commande("MenuLocal");
							break;
						default:
							commande("MenuP");
							break;
					}
				}
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
			if(jeu.getPlayer().lost()){
				gagnant = jeu.next_player();
				System.out.println("cas 2 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
				int reponse = DialogueFinPartie(gagnant + 1);
				switch(reponse){
					case 0 :
						jeu.playerNoLost(jeu.get_player());
						break;
					case 1 :
						commande("MenuLocal");
						break;
					default:
						commande("MenuP");
						break;
				}
			}	
		}
		else{
			clic = true;
			ligne_joueur = x;
			colonne_joueur = -1; //mettre -1 si ça vient du side
		}
		
	}

	@Override 
    public void clicBlanc(int x, int y)
    {
        Cube cube ;
        if (colonne_joueur==-1){
            cube = jeu.getPlayer().getSide(ligne_joueur);
        }else{
            cube = jeu.getPlayer().getPyramid().get(ligne_joueur, colonne_joueur);
        }
        
        int test = cube.getInt();
        if (test == 0)
        {
            int res = jeu.jouer_coup(x, y, ligne_joueur, colonne_joueur);
            if(res == 1 || res == 2 || res == 3){
				PDJPyramideJoueur.SetCube_Select_Static(false);
                clic = false;
            }
            if (IAON){
                vue.startTimer();
            }
        }
        jeu.sauvegarde(qs);
    }

	@Override
	public void clicPyramideCentrale(int x, int y) {
		int res;

		// if(jeu.accessible(ligne_joueur,colonne_joueur)){
		res = jeu.jouer_coup(x, y, ligne_joueur, colonne_joueur);
		jeu.sauvegarde(qs);
		if(res == 1 || res == 3){
			clic = false;
			PDJPyramideJoueur.SetCube_Select_Static(false);
			if(jeu.getPlayer().lost()){
				gagnant = jeu.next_player();
				System.out.println("cas 3 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
				int reponse = DialogueFinPartie(gagnant + 1);
				switch(reponse){
					case 0 :
						jeu.playerNoLost(jeu.get_player());
						break;
					case 1 :
						commande("MenuLocal");
						break;
					default:
						commande("MenuP");
						break;
				}
			}
		}
		if(res == 2){
			clic = false;
			PDJPyramideJoueur.SetCube_Select_Static(false);
			penalty = true;
			if (IAON && jeu.get_player() == 0){
				ia.takePenaltyCube();
				penalty = false;
				if(jeu.getPlayer().lost()){
					gagnant = jeu.next_player();
					System.out.println("cas 4 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
					int reponse = DialogueFinPartie(gagnant + 1);
					switch(reponse){
						case 0 :
							jeu.playerNoLost(jeu.get_player());
							break;
						case 1 :
							commande("MenuLocal");
							break;
						default:
							commande("MenuP");
							break;
					}
				}
			}
		}
		if (IAON){
			vue.startTimer();
		}
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
			case "Annuler":
				jeu.annule();
				break;
			case "Refaire":
				jeu.refais();
				break;

			case "quit":
				System.exit(0);
				break;

			case "fullscreen":
				vue.basculePleinEcran();
				break;

			case "Lan":
				changeVisible(5);
				break;

			case "Charger":
				((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				changeVisible(3);
				jeu.reset(qs);
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
				jeu.resetBag(); 
				jeu.constructionAleatoire(jeu.getPlayer());
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().resetBooleans();
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
				ia = IA.nouvelle(jeu,d,1);
				vue.startTimer();
				while(jeu.draw()){} // On cree une partie a 2
				jeu.constructionAleatoire(jeu.getPlayer(1)); // a enlever quand l'IA construira la pyramide
				if(IAON && jeu.get_player()==1){
					jeu.avance();
				}
				jeu.sauvegarde("saves/quicksave.txt");
				break;

			case "Valider":
				jeu.avance();
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().setValider(false);
				((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().repaint();
				
				if (jeu.get_player() == joueur_initial || (IAON && jeu.get_player()==1) ) {
					changeVisible(3);
					jeu.gameStart();
					jeu.sauvegarde(qs);
					jeu.check_loss();
					if(jeu.getPlayer().lost()){
						gagnant = jeu.next_player();
						System.out.println("cas 5 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
						int reponse = DialogueFinPartie(gagnant + 1);
						switch(reponse){
							case 0 :
								jeu.playerNoLost(jeu.get_player());
								break;
							case 1 :
								commande("MenuLocal");
								break;
							default:
								commande("MenuP");
								break;
						}
						
					}
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
				((BackgroundPanel) frame).setBackgroundPicture("res/Back.png");
				changeVisible(1);
				break;

			case "PDJ2":
				changeVisible(3);
				((BackgroundPanel) frame).setBackgroundPicture("res/background.jpg");
				break;
			case "JoueIA":
				int res;
				if (IAON && jeu.get_player()==1 && !penalty){
					if ((res = ia.jouer_coup()) == 2){
						penalty = true;
					}
					else if (res == 1 || res == 3){
						// jeu.check_loss();
						if(jeu.getPlayer().lost()){
							gagnant = jeu.next_player();
							System.out.println("cas 5 : Le joueur " + (jeu.get_player() + 1) + " a perdu");
							int reponse = DialogueFinPartie(gagnant + 1);
							switch(reponse){
								case 0 :
									jeu.playerNoLost(jeu.get_player());
									break;
								case 1 :
									commande("MenuLocal");
									break;
								default:
									commande("MenuP");
									break;
							}
						}
					}
					vue.stopTimer();
					jeu.sauvegarde("saves/quicksave.txt");
				}
				break;

			case "Save":
				jeu.sauvegarde("saves/"+ DateTimeFormatter.ofPattern("dd-MM-yyyy-hh:mm:ss").format(LocalDateTime.now()));
				break;
				
			case "Host":
				break;
			
			case "Join":
				break;
			
			default:
				return false;
		}
		return true;
	}

	public void setIADifficulty(int difficulty){
		d=difficulty;
	}

	public void changeVisible(int n_indice) {
		getcurMenu().setVisible(false);
		if(indice_courant == 2)
		{
			((MenuPhaseConstruction)menuListe.get(indice_courant)).getAffichagePhaseConstruction().resetBooleans();
		}
		indice_courant = n_indice;
		vue.addFrame(getcurMenu());
		getcurMenu().setVisible(true);
	}

	@Override
	public boolean penaltyPhase() {
		return penalty;
	}

	public static int DialogueFinPartie(int gagnant)
    {
        String languageCode = Global.Config.getLanguage();
        String message = null;
        String title = null;
        String[] buttons = new String[3];
        switch (languageCode) {
            case "FR":
                buttons[0] = "Continuer";
                buttons[1] = "Rejouer";
                buttons[2] = "Quitter";
                message = "Le joueur "+gagnant+" a gagné";
                title = "Fin de partie";
                break;
            case "EN":
                buttons[0] = "Continue";
                buttons[1] = "Replay";
                buttons[2] = "Quit";
                message = "Player "+gagnant+" won";
                title = "Game Over";
                break;
        }
        return JOptionPane.showOptionDialog(null, message, title ,JOptionPane.YES_NO_CANCEL_OPTION, 0, null, buttons, buttons[2]);
    }
}
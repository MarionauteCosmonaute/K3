package Controller;

import View.*;
import Model.*;
import Model.IA_pack.IA;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import java.awt.Point;
import java.util.*;


public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	boolean IA_thinking=false;
	int d=0;
	InterfaceGraphique vue;
	String qs="saves/quicksave.txt";
	MusicPlayer musique;
	int joueur_initial;
	static boolean clic = false;
	static int ligne_joueur, colonne_joueur;
	boolean penalty, suggestion;
	boolean IAON=false;
	IA ia;
	IA[] iaJoueur;
	int gagnant;
	Cube cube, cube_selectionne;
	Thread iaCompute,iaComputeJ;

	int IA_id_player=1;
	Timer autorestart;
	Timer timer_sablier;

	public ControleurMediateur(Jeu j, MusicPlayer musique) {
		jeu = j;
		this.musique = musique;
		joueur_initial = j.get_player();
		penalty = false;
		suggestion = false;
		gagnant = -1;
		iaJoueur = new IA[2];
		iaJoueur[0] = IA.nouvelle(j, 1, 0);
		iaJoueur[1] = IA.nouvelle(j, 1, 1);
		timer_sablier = new Timer(500, new ActionListener()
			{
				@Override
				
				public void actionPerformed(ActionEvent e) {
					// Changer l'image
					vue.updateSablier(true);
				}
			});
	}

	public void ImporterVue(InterfaceGraphique vue) {
		this.vue = vue;
	}


	@Override
	public void clicJoueurPyramide(int x, int y) {

		// On désactive les boutons de l'historique tant que le coup n'est pas validé!
		vue.getMenuPhaseDeJeu2().setAnnuler(false);
		vue.getMenuPhaseDeJeuJVIA().setAnnuler(false);
		vue.getMenuPhaseDeJeu2().repaint();

		vue.getMenuPhaseDeJeu2().setRefaire(false);
		vue.getMenuPhaseDeJeuJVIA().setRefaire(false);
		vue.getMenuPhaseDeJeu2().repaint();

		if(IAON && jeu.get_player() == 1 && !penalty){
			return;
		}
		if(penalty == true){
			//tester que le cube est accessible dans la pyramide du joueur
			PDJPyramideJoueur.SetCube_Select_Static(false);
			if(jeu.accessible(x,y)){
				jeu.takePenaltyCube(x, y); // y : mettre -1 si ça vient du side
				suggestion = false;
				if(IAON){
					vue.getMenuPhaseDeJeuJVIA().setSuggestion(false);
				}
				else{
					vue.getMenuPhaseDeJeu2().setSuggestion(false);
				}
				
				metAJourAnnule();
				metAJourRefaire();
				jeu.sauvegarde(qs);
				penalty = false;
				
				if(jeu.getPlayer().lost()){
					timer_sablier.stop();
					vue.updateSablier(false);
					IAON=false;
					gagnant = jeu.next_player();
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

		// On désactive les boutons de l'historique tant que le coup n'est pas validé!
		vue.getMenuPhaseDeJeu2().setAnnuler(false);
		vue.getMenuPhaseDeJeuJVIA().setAnnuler(false);
		vue.getMenuPhaseDeJeu2().repaint();

		vue.getMenuPhaseDeJeu2().setRefaire(false);
		vue.getMenuPhaseDeJeuJVIA().setRefaire(false);
		vue.getMenuPhaseDeJeu2().repaint();
		vue.getMenuPhaseDeJeuJVIA().repaint();

		if(IAON && jeu.get_player() == 1 && !penalty){
			return;
		}
		if(penalty == true){
			//tester que le cube est accessible dans la pyramide du joueur
			jeu.takePenaltyCube(x, -1); // y : mettre -1 si ça vient du side
			suggestion = false;
			if(IAON){
				vue.getMenuPhaseDeJeuJVIA().setSuggestion(false);
			}
			else{
				vue.getMenuPhaseDeJeu2().setSuggestion(false);
			}
			metAJourAnnule();
			metAJourRefaire();
			penalty = false;
			if(jeu.getPlayer().lost()){
				timer_sablier.stop();
				vue.updateSablier(false);
				gagnant = jeu.next_player();
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

	public void metAJourRefaire()
	{
		vue.getDisplayedMenu().setRefaire(!jeu.refaisEmpty());
		vue.getDisplayedMenu().repaint();
	}

	public void metAJourAnnule()
	{
		vue.getDisplayedMenu().setAnnuler(!jeu.annuleEmpty());
		vue.getDisplayedMenu().repaint();
	}

	@Override 
    public void clicBlanc(int x, int y)
    {
		System.out.println("blanc 0");
		if(IAON){
			vue.getMenuPhaseDeJeuJVIA().setLastCoup(true);
		}
		else{
			vue.getMenuPhaseDeJeu2().setLastCoup(true);
		}
		
        Cube cube ;
        if (colonne_joueur==-1){
            cube = jeu.getPlayer().getSide(ligne_joueur);
        }else{
            cube = jeu.getPlayer().getPyramid().get(ligne_joueur, colonne_joueur);
        }
        
        int test = cube.getInt();
        if (test == 0)
        {
			System.out.println("blanc!!!");
            int res = jeu.jouer_coup(-1, -1, ligne_joueur, colonne_joueur);
			metAJourAnnule();
			metAJourRefaire();
            if(res != 0){
				suggestion = false;
				if(IAON){
					vue.getMenuPhaseDeJeuJVIA().setSuggestion(false);
				}
				else{
					vue.getMenuPhaseDeJeu2().setSuggestion(false);
				}
				if(jeu.getPlayer().lost()){
					// PDJPyramideJoueur.SetPremierCoup(true);
					timer_sablier.stop();
					vue.updateSablier(false);
					gagnant = jeu.next_player();
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
				PDJPyramideJoueur.SetCube_Select_Static(false);
                clic = false;
            }
            if (IAON){
				
				if(jeu.get_player() == 1){
					vue.updateSablier(true);
					timer_sablier.start();
				}
                vue.TimerIA(true);
            }
        }
        jeu.sauvegarde(qs);
    }

	@Override
	public void clicPyramideCentrale(int x, int y) {
		int res;
		if(IAON){
			vue.getMenuPhaseDeJeuJVIA().setLastCoup(true);
		}
		else{
			vue.getMenuPhaseDeJeu2().setLastCoup(true);
		}
		
		// if(jeu.accessible(ligne_joueur,colonne_joueur)){
		res = jeu.jouer_coup(x, y, ligne_joueur, colonne_joueur);
		
		if(IAON){
			vue.updateSablier(true);
			timer_sablier.start();
			if(res != 2) commande("IAcompute");
		}
		// metAJourAnnule();
		// metAJourRefaire();
		jeu.sauvegarde(qs);
		if(res == 1 || res == 3){
			suggestion = false;
			if(IAON){
				vue.getMenuPhaseDeJeuJVIA().setSuggestion(false);
			}
			else{
				vue.getMenuPhaseDeJeu2().setSuggestion(false);
			}
			clic = false;
			PDJPyramideJoueur.SetPremierCoup(true);
			PDJPyramideJoueur.SetCube_Select_Static(false);
			if(jeu.getPlayer().lost()){
				timer_sablier.stop();
				vue.updateSablier(false);
				gagnant = jeu.next_player();
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
			if(IAON && jeu.get_player() == 1){
				
				vue.getMenuPhaseDeJeu2().setAnnuler(false);
				vue.getMenuPhaseDeJeu2().setRefaire(false);
				vue.getMenuPhaseDeJeuJVIA().repaint();
			}
			else{
				metAJourAnnule();
				metAJourRefaire();
			}
		}
		if(res == 2){
			suggestion = false;
			if(IAON){
				vue.getMenuPhaseDeJeuJVIA().setSuggestion(false);
			}
			else{
				vue.getMenuPhaseDeJeu2().setSuggestion(false);
			}
			PDJPyramideJoueur.SetPremierCoup(true);
			vue.getMenuPhaseDeJeu2().setAnnuler(false);
			vue.getMenuPhaseDeJeu2().setRefaire(false);
			vue.getMenuPhaseDeJeu2().repaint();
			vue.getMenuPhaseDeJeuJVIA().repaint();
			clic = false;
			PDJPyramideJoueur.SetCube_Select_Static(false);
			penalty = true;
			if (IAON && jeu.get_player() == 0){
				ia.takePenaltyCube();
				commande("IAcompute");
				penalty = false;
				if(jeu.getPlayer().lost()){
					timer_sablier.stop();
					vue.updateSablier(false);
					gagnant = jeu.next_player();
					int reponse = DialogueFinPartie(gagnant + 1);
					metAJourAnnule();
					metAJourRefaire();
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
			vue.TimerIA(true);
			if(jeu.get_player() == 1){
				vue.updateSablier(true);
				timer_sablier.start();
			}
		}
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
		if (ligne == -1 && col == -1)
		{
			return;
		}
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
				if (IAON){
					vue.getMenuPhaseDeJeuJVIA().setLastCoup(false);
				}
				else{
					vue.getMenuPhaseDeJeu2().setLastCoup(false);
				}
				if(IAON && !IA_thinking){
					IAON=false;
					jeu.annule();
					autorestart =new Timer(5000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e){
							IAON=true;
							vue.TimerIA(true);
							vue.updateSablier(true);
							timer_sablier.start();
							autorestart.stop();
						}
					});
					autorestart.start();
				}else{
					jeu.annule();
				}
				metAJourAnnule();
				metAJourRefaire();
				break;

			case "Refaire":
				if(IAON){
					vue.getMenuPhaseDeJeuJVIA().setLastCoup(false);
				}
				else{
					vue.getMenuPhaseDeJeu2().setLastCoup(false);
				}
				
				if(IAON && !IA_thinking){
					if(jeu.refais() == 2){
						penalty = true;
					}
					autorestart =new Timer(5000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e){
							IAON=true;
							vue.TimerIA(true);
							vue.updateSablier(true);
							timer_sablier.start();
							autorestart.stop();
						}
					});
					autorestart.start();
				
				}
				else if(!IAON){
					if(jeu.refais() == 2){
						penalty = true;
					}
				}

				if(jeu.getPlayer().lost()){
					gagnant = jeu.next_player();
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
				metAJourAnnule();
				metAJourRefaire();
				break;

			case "DernierCoupJoué":
				if(IAON){
					vue.getMenuPhaseDeJeuJVIA().setDernierCoup(!vue.getMenuPhaseDeJeuJVIA().getDernierCoup());
				}
				else{
					vue.getMenuPhaseDeJeu2().setDernierCoup(!vue.getMenuPhaseDeJeu2().getDernierCoup());

				}
				break;

			case "Suggestion" :
				// if(suggestion){ //la suggestion a déjà été faite, on la "désaffiche"
				// 	vue.getMenuPhaseDeJeu2().setSuggestion(false);
				// }
				// else{
					iaComputeJ = iaJoueur[jeu.get_player()].compute();
					try{iaComputeJ.join();}
					catch(InterruptedException e){}
					ArrayList<Point> prochainCoup = iaJoueur[jeu.get_player()].prochainCoup();
					if(IAON){
						vue.getMenuPhaseDeJeuJVIA().setSuggestion(true);
						vue.getMenuPhaseDeJeuJVIA().setCoupSuggere(prochainCoup.get(0), prochainCoup.get(1));
					}
					else{
						vue.getMenuPhaseDeJeu2().setSuggestion(true);
						vue.getMenuPhaseDeJeu2().setCoupSuggere(prochainCoup.get(0), prochainCoup.get(1));
					}
					
					
				// }
				// suggestion = !suggestion;
				


				break;

			case "quit":
				System.exit(0);
				break;

			case "fullscreen":
				vue.basculePleinEcran();
				break;

			case "Lan":
				vue.changeVisible(5);
				break;

			case "FR":
				Global.Config.setLanguage("FR");
				vue.getMenuPrincipal().updateLanguageCode();
				break;

			case "EN":
				Global.Config.setLanguage("EN");
				vue.getMenuPrincipal().updateLanguageCode();
				break;

			case "Reset":
				jeu.resetBag();
				vue.getMenuPhaseConstruction().setValider(false);
				vue.getMenuPhaseConstruction().reset();
				vue.getMenuPhaseConstruction().repaint(); // ça me paraît normal de faire ça comme ça
				break;  
			
			case "AideConstruction":
				jeu.constructionAleatoire(jeu.get_player());
				vue.getMenuPhaseConstruction().reset();
				vue.getMenuPhaseConstruction().repaint();
				break;

			case "AideConstructionIA":
				jeu.resetBag();
				IA iaJouer = IA.nouvelle(jeu, 0, jeu.get_player());
				iaJouer.generationPyramide(true);
				vue.getDisplayedMenu().repaint();
				break;

			case "JoueurVSJoueur":
				IAON=false;
				vue.getMenuPhaseDeJeu2().setLastCoup(false);
			    vue.setBackgroundPicture("res/Fond bleu avec cubes transparents.png");
				vue.changeVisible(2);
				reset();
				jeu.reset(2);
				jeu.initPrincipale();
				joueur_initial=jeu.get_player();
				while(jeu.draw()){} // On cree une partie a 2
				break;

			case "JoueurVSIA":
				IAON=true;
				vue.getMenuPhaseDeJeuJVIA().setLastCoup(false);
				vue.setBackgroundPicture("res/Fond bleu avec cubes transparents.png");
				vue.changeVisible(2);
				jeu.reset(2);
				jeu.initPrincipale();
				joueur_initial=jeu.get_player();
				ia = IA.nouvelle(jeu,d,1);
				while(jeu.draw()){} // On cree une partie a 2
				ia.construction();

				
				if(jeu.get_player() == 1){
					vue.updateSablier(true);
					timer_sablier.start();
				}
				vue.TimerIA(true);
				if(IAON && jeu.get_player()==1){
					jeu.avance();
				}
				jeu.sauvegarde("saves/quicksave.txt");
				break;

			case "Valider":
				jeu.avance();
				vue.getMenuPhaseConstruction().setValider(false);
				vue.getMenuPhaseConstruction().repaint();
				if (IAON){
					jeu.changeCurrentPlayer(joueur_initial);
				}
				
				if (jeu.get_player() == joueur_initial) {
					if (IAON){
						jeu.playerEndConst(jeu.get_player());
						vue.changeVisible(4);
					}else{
						vue.changeVisible(3);
					}
					
					jeu.gameStart();
					if(IAON){
						try{ia.thread().join();}catch(Exception e){System.out.println(e);System.exit(1);}
						if(joueur_initial == 1) commande("IAcompute");
					}
					jeu.sauvegarde(qs);
					jeu.check_loss();
					if(jeu.getPlayer().lost()){
						timer_sablier.stop();
						vue.updateSablier(false);
						gagnant = jeu.next_player();
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
				musique.jouerMusique();
				break;

			case "MenuP":
				jeu.gameStart();
				vue.TimerIA(false);//on stoppe le timer de l'ia 
				vue.setBackgroundPicture("res/MenuPrincipal.png");
				vue.changeVisible(0);
				break;

			case "MenuLocal":
				vue.setBackgroundPicture("res/MenuPrincipal.png");
				vue.changeVisible(1);
				break;

			case "PDJ2":
				vue.changeVisible(3);
				vue.setBackgroundPicture("res/Fond bleu avec cubes transparents.png");
				break;

			case "IAcompute":
				IA_thinking=true;
				iaCompute = ia.compute();
				break;
			case "JoueIA":
				vue.getMenuPhaseDeJeuJVIA().setAnnuler(false);
				vue.getMenuPhaseDeJeuJVIA().setRefaire(false);
				vue.getMenuPhaseDeJeuJVIA().repaint();
				int res;
				if (IAON && jeu.get_player()==1 && !penalty){
					
					try{iaCompute.join();}
					catch(InterruptedException e){System.err.println(e);}
					if ((res = ia.jouer_coup()) == 2){
						vue.getMenuPhaseDeJeuJVIA().setLastCoup(true);
						penalty = true;
						PDJPyramideJoueur.SetPremierCoup(true);
					}
					else if (res == 1 || res == 3){
						vue.getMenuPhaseDeJeuJVIA().setLastCoup(true);
						PDJPyramideJoueur.SetPremierCoup(true);
						if(jeu.getPlayer().lost()){
							timer_sablier.stop();
							vue.updateSablier(false);
							gagnant = jeu.next_player();
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
					timer_sablier.stop();
					vue.updateSablier(false);
					IA_thinking=false;
					vue.TimerIA(false);
					jeu.sauvegarde("saves/quicksave.txt");
					
				}
				metAJourAnnule();
				metAJourRefaire();
				break;

			case "Save":
				jeu.sauvegarde("saves/"+ DateTimeFormatter.ofPattern("dd-MM-yyyy-hh:mm:ss").format(LocalDateTime.now())+".txt");
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
                buttons[1] = "Play again";
                buttons[2] = "Quit";
                message = "Player "+gagnant+" won";
                title = "Game Over";
                break;
        }
        return JOptionPane.showOptionDialog(null, message, title ,JOptionPane.YES_NO_CANCEL_OPTION, 0, null, buttons, buttons[2]);
    }

	private void reset(){
		vue.TimerIA(false);
		IA_thinking=false;
		clic = false;
		penalty=false;
		IAON=false;
		if (autorestart !=null){
			autorestart.stop();
		}
		
		timer_sablier.stop();
	}

	public void open(String fileName){
		reset();
		String[]filePath=fileName.split("/");
		fileName=filePath[filePath.length-1];
		System.err.println(fileName);
		vue.setBackgroundPicture("res/Fond bleu avec cubes transparents.png");
		vue.changeVisible(3);
		jeu.reset("saves/"+fileName);
	}
}
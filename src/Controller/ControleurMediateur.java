package Controller;

import View.*;
import Model.*;

public class ControleurMediateur implements CollecteurEvenements 
{    
    //Actuellement le controlleur de gofropwazon, a modifier
	Jeu jeu;
	InterfaceGraphique vue;
	IA ia;
	boolean toggleIA=false;

	public ControleurMediateur(Jeu j) {
		jeu = j;
		ia= IA.nouvelle(jeu);
    }
	
	public void ImporterVue(InterfaceGraphique vue)
	{
		this.vue = vue;
	}
    
    @Override
	public void clicSouris(int x, int y)
    {
		if (toggleIA){
			if(jeu.joueur()==0){ jeu.joue(y,x);}
		}else{
			jeu.joue(y,x);
		}
        
	}

	@Override
	public boolean commande(String c)
	{
		switch (c)
		{
			case "quit":
				System.exit(0);
				break;
			
			case "Redo":
				jeu.refait();
				// vue.dessine2();
				break;
			
			case "Undo":
				jeu.annule();
				// vue.dessine2();
				break;

			case "fullscreen":
				vue.basculePleinEcran();
				break;

			case "Restart":
				jeu.renitialiser();
				break;

			case "IA":
				toggleIA=!toggleIA;
				break;

			case "Ajoute_Ligne":
				jeu.ajoute_ligne();
				break;

			case "Ajoute_Colonne":
				jeu.ajoute_colonne();
				break;

			case "Supprime_Ligne":
				jeu.supprime_ligne();
				break;

			case "Supprime_Colonne":
				jeu.supprime_colonne();
				break;
			case "TIA":
				if(jeu.joueur()==1 && toggleIA){
					vue.stopTimer();
					ia.joue();
					vue.startTimer();
				}
				break;

			default:
				return false;
		}
		return true;
	}
}
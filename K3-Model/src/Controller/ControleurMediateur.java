package Controller;

import View.*;

import java.util.Vector;
import Model.*;

public class ControleurMediateur implements CollecteurEvenements 
{
	Jeu jeu;
	InterfaceGraphique vue;
	boolean toggleIA = false;
	MusicPlayer musique;
	Vector<Menu> menuListe = new Vector<>();
	int indice_courant = 0;
	
	public ControleurMediateur(Jeu j, MusicPlayer musique)
	{
		jeu = j;
		this.musique = musique;
    }
	public void addMenu(Menu m){
		menuListe.add(m);
	}

	private Menu getcurMenu()
	{
		return menuListe.get(indice_courant);
	}
	
	public void ImporterVue(InterfaceGraphique vue)
	{
		this.vue = vue;
	}
    
    @Override
	public void clicSouris(int x, int y)
    {
        System.out.println("x : "+x + ",y : "+y);
	}

	@Override
	public void clicSourisPyr(int ligne, int col)
    {
        // System.out.println("case : ("+ligne+","+col+")");
	}

	@Override
	public void clicSourisPioche(int ligne, int col)
    {
        // System.out.println("Pioche : ("+ligne+","+col+")");
	}

	@Override
	public boolean commande(String c)
	{
		switch (c)
		{
			case "Quit":
				System.exit(0);
				break;
			
			case "fullscreen":
				vue.basculePleinEcran();
				break;

			case "NouvellePartie":
				//jeu.renitialiser(); 
				break;

            case "Lan":
				break;
                
            case "Charger":
				break;
            
            case "FR":
                break;

            case "EN":
                break;
			
			case "Regles":
                break;

			case "Son":
				System.out.println("Case son de ControleurMediateur");
				musique.jouerMusique();
				break;
			case "MenuP":
				changeVisible(0);
				break;
			case "MenuLocal":
				changeVisible(1);
				break;
			case "MenuOnline":
				//changeVisible(2);
				//TODO: decommenter ci dessus quand fenetre online dispo
				break;
			default:
				return false;
		}
		return true;
	}

	public void changeVisible(int n_indice){
		getcurMenu().setVisible(false);
		indice_courant=n_indice;
		vue.addFrame(getcurMenu());
		getcurMenu().setVisible(true);
	}
}
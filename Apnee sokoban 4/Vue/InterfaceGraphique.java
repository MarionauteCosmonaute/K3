package Vue;

import javax.swing.JFrame;

import Controleur.ControleurClavierSouris;
import Modele.Jeu;

public class InterfaceGraphique implements Runnable,InterfaceUtilisateur{
    JFrame frame;
    ControleurClavierSouris c;
    Jeu J;
    public InterfaceGraphique(String s){
        frame= new JFrame("Sokoban");
        J=new Jeu(s);
    }

    public void run() {
        NiveauGraphique g;
		// Creation d'une fenetre
        
		// Ajout de notre composant de dessin dans la fenetre
		g=new NiveauGraphique(J);
        J.AddObs(g);
        c=new ControleurClavierSouris(J,this);
		frame.add(g);
		frame.addMouseListener(new EcouteurDeSouris(c));
		frame.addKeyListener(new EcouteurDeClavier(c));
		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// On fixe la taille et on demarre
		frame.setSize(500, 300);
		frame.setVisible(true);
        
	}
    public void toggleFullscreen(){
        if (frame.getExtendedState()==JFrame.NORMAL){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        }else{
            frame.setExtendedState(JFrame.NORMAL);
           
            frame.setSize(500,300);
        }
    }
}

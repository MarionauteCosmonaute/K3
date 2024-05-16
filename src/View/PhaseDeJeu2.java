package View;

import javax.swing.*;
import java.awt.*;

import Model.Jeu;

public class PhaseDeJeu2 extends Menu
{
    PhaseDeJeu2(CollecteurEvenements controle, Jeu J)
    {
        super();
        try
        {
            JPanel content = new JPanel(new BorderLayout());   

            // On sépare la partie qui contient les boutons retour/aide/son et la partie du jeu
            JPanel topPanel = new JPanel(new BorderLayout()); //pour les boutons
            JButton UnMute = Bouton.BoutonUnMute(controle);
            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topRightPanel.add(UnMute,BorderLayout.EAST);
            topRightPanel.setOpaque(false);
            topPanel.add(topRightPanel);

            JPanel centrePanel = new JPanel(); //reste du jeu
            centrePanel.setLayout(new GridLayout(2,1));
            content.add(topPanel, BorderLayout.NORTH);
            content.add(centrePanel, BorderLayout.CENTER);
            
            // On sépare la partie du jeu en une partie pyramide et une partie pour les joueurs
            JPanel pyramidePanel = new JPanel(new BorderLayout());
            JPanel joueursPanel = new JPanel();
            centrePanel.add(pyramidePanel);
            centrePanel.add(joueursPanel);
            pyramidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            PDJPyramideCentrale pdj = (new PDJPyramideCentrale(J, pyramidePanel)); //ajoute la pyramide centrale
            System.out.println(pdj.isOpaque());
            
            pyramidePanel.add(pdj);
            pdj.setVisible(true);

            
            // On sépare la partie des joueurs en jour gauche (joueur1) et joueur droit (joueur2)
            JPanel leftPanel = new JPanel(new BorderLayout());
            JPanel rightPanel = new JPanel(new BorderLayout());
            joueursPanel.setLayout(new GridLayout(1,2));
            joueursPanel.add(leftPanel);
            joueursPanel.add(rightPanel);
            leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            
            PDJPyramideJoueur joueur1 = (new PDJPyramideJoueur(J, leftPanel,0)); //ajoute la pyramide du joueur 1
            leftPanel.add(joueur1);
            joueur1.setVisible(true);
            rightPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            PDJPyramideJoueur joueur2 = (new PDJPyramideJoueur(J, rightPanel,1)); //ajoute la pyramide du joueur 2
            rightPanel.add(joueur2);
            joueur2.setVisible(true);


            // Du blabla pour la classe Menu
            content.setVisible(true);
            content.setOpaque(false);
            topPanel.setOpaque(false);
            centrePanel.setOpaque(false);
            pyramidePanel.setOpaque(false);
            joueursPanel.setOpaque(false);
            leftPanel.setOpaque(false);
            rightPanel.setOpaque(false);
            setOpaque(false);
            add(content);
            controle.addMenu(this);
            validate();
        }
        catch (Exception e)
        {
            System.exit(1);
        }
    }

}

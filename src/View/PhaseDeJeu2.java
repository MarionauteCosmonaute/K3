package View;

import Global.FileLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import Model.Jeu;

public class PhaseDeJeu2 extends Menu
{
    PhaseDeJeu2(CollecteurEvenements controle,Jeu J)
    {
        super();
        try
        {
            JPanel content = new JPanel(new BorderLayout());   

            // On sépare la partie qui contient les boutons retour/aide/son et la partie du jeu
            JPanel topPanel = new JPanel(); //pour les boutons
            JPanel centrePanel = new JPanel(); //reste du jeu
            centrePanel.setLayout(new GridLayout(2,1));
            // topPanel.setBackground(Color. MAGENTA);
            // centrePanel.setBackground(Color.ORANGE);
            content.add(topPanel, BorderLayout.NORTH);
            content.add(centrePanel, BorderLayout.CENTER);
            
            // On sépare la partie du jeu en une partie pyramide et une partie pour les joueurs
            JPanel pyramidePanel = new JPanel();
            JPanel joueursPanel = new JPanel();
            centrePanel.add(pyramidePanel);
            centrePanel.add(joueursPanel);
            // pyramidePanel.setBackground(Color.BLACK);
            // joueursPanel.setBackground(Color.YELLOW);
            pyramidePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
            pyramidePanel.add(new AffichagePhaseDeJeu2(J));

            // On sépare la partie des joueurs en jour gauche (joueur1) et joueur droit (joueur2)
            JPanel leftPanel = new JPanel();
            JPanel rightPanel = new JPanel();
            joueursPanel.setLayout(new GridLayout(1,2));
            joueursPanel.add(leftPanel);
            joueursPanel.add(rightPanel);
            // leftPanel.setBackground(Color.RED);
            // rightPanel.setBackground(Color.BLUE);
            leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
            rightPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
       

            // Du blabla pour la classe Menu
            content.setVisible(true);
            content.setOpaque(false);
            setOpaque(false);
            add(content);
            controle.addMenu(this);
        }
        catch (Exception e)
        {
            System.exit(1);
        }
    }

}

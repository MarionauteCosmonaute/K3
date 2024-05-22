package View;

import javax.swing.*;
import java.awt.*;

import Model.Jeu;

public class MenuPhaseDeJeu2 extends Menu {
    public MenuPhaseDeJeu2(CollecteurEvenements controle, Jeu J) {
        super();
        try {
            JPanel content = new JPanel(new BorderLayout());
            JButton UnMute, Retour, Aide;
            addKeyListener(new AdaptateurClavier(controle));

            // On sépare la partie qui contient les boutons retour/aide/son et la partie du
            // jeu
            JPanel topPanel = new JPanel(new GridLayout(1, 3)); // pour les boutons

            // Bouton Retour
            Retour = Bouton.BoutonRetour();
            Retour.addActionListener(new RetourMenuPAdapeur(controle));
            JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topLeftPanel.add(Retour, BorderLayout.EAST);
            topLeftPanel.setOpaque(false);
            topPanel.add(topLeftPanel, BorderLayout.WEST);
            Retour.setBorder(BorderFactory.createEmptyBorder());
            Retour.setContentAreaFilled(false);

            // Bouton Aide
            Aide = Bouton.creerButton("Aide");
            // Aide.addActionListener(new AideAdaptateur(controle));
            JPanel topCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topCenter.add(Aide, BorderLayout.CENTER);
            topCenter.setOpaque(false);
            topPanel.add(topCenter);

            // Bouton du Son
            UnMute = Bouton.BoutonUnMute(controle);
            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topRightPanel.add(UnMute, BorderLayout.EAST);
            topRightPanel.setOpaque(false);
            topPanel.add(topRightPanel);
            UnMute.setBorder(BorderFactory.createEmptyBorder());
            UnMute.setContentAreaFilled(false);

            // Séparation entre le topPanel et le centerPanel
            JPanel centrePanel = new JPanel(); // reste du jeu
            centrePanel.setLayout(new GridLayout(2, 1));
            content.add(topPanel, BorderLayout.NORTH);
            content.add(centrePanel, BorderLayout.CENTER);

            // On sépare la partie du jeu en une partie pyramide et une partie pour les
            // joueurs
            JPanel pyramidePanel = new JPanel(new BorderLayout());
            JPanel joueursPanel = new JPanel();
            centrePanel.add(pyramidePanel);
            centrePanel.add(joueursPanel);
            pyramidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            PDJPyramideCentrale pdj = (new PDJPyramideCentrale(J, pyramidePanel)); // ajoute la pyramide centrale
            pyramidePanel.add(pdj);
            pdj.setVisible(true);

            // On sépare la partie des joueurs en joueur gauche (joueur1) et joueur droit
            // (joueur2)
            JPanel bottomLeftPanel = new JPanel(new BorderLayout());
            JPanel bottomRightPanel = new JPanel(new BorderLayout());
            joueursPanel.setLayout(new GridLayout(1, 2));
            joueursPanel.add(bottomLeftPanel);
            joueursPanel.add(bottomRightPanel);

            // Joueur Bleu
            bottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            PDJPyramideJoueur joueur1 = (new PDJPyramideJoueur(J, bottomLeftPanel, 0)); // ajoute la pyramide du joueur 1
            bottomLeftPanel.addMouseListener(new AdaptateurSourisBasGauche(controle, joueur1));
            bottomLeftPanel.add(joueur1,BorderLayout.CENTER);
            joueur1.setVisible(true);

            // Joueur Rouge
            bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            PDJPyramideJoueur joueur2 = (new PDJPyramideJoueur(J, bottomRightPanel, 1)); // ajoute la pyramide du joueur 2
            bottomRightPanel.addMouseListener(new AdaptateurSourisBasGauche(controle, joueur2));  
		    bottomRightPanel.add(joueur2, BorderLayout.CENTER);
            joueur2.setVisible(true);

            // Du blabla pour la classe Menu
            content.setVisible(true);
            content.setOpaque(false);
            topPanel.setOpaque(false);
            centrePanel.setOpaque(false);
            pyramidePanel.setOpaque(false);
            joueursPanel.setOpaque(false);
            bottomLeftPanel.setOpaque(false);
            bottomRightPanel.setOpaque(false);
            setOpaque(false);
            add(content);
            controle.addMenu(this);
            validate();
        } catch (Exception e) {
            System.exit(1);
        }
    }

}

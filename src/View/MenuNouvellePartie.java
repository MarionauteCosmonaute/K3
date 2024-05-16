package View;

import Global.FileLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MenuNouvellePartie extends Menu
{
    public MenuNouvellePartie(CollecteurEvenements controle)
    {
        super();
        try
        {
            JPanel content = new JPanel(new BorderLayout());    
            JButton joueurs3, joueurs4, UnMute, Retour;
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement vertical
            centrePanel.add(Box.createVerticalGlue());
            centrePanel.add(Box.createVerticalStrut(40));

            //On s'occupe du bouton 2 Joueurs sous forme d'un menu déroulant
            JMenuBar menuBar = new MenuArrondi(20);
            JMenu fileMenu = new JMenu("2 Joueurs");
            menuBar.add(fileMenu); // Ajouter le menu "Fichier" à la barre de menu
            JMenuItem joueurVSjoueur = new JMenuItem("joueur VS joueur");
            JMenuItem joueurVSia = new JMenuItem("joueur VS ia");
            fileMenu.add(joueurVSjoueur); // Ajouter "Nouveau" au menu "Fichier"
            fileMenu.add(joueurVSia); // Ajouter "Ouvrir" au menu "Fichier"
            centrePanel.add(menuBar);
            centrePanel.add(Box.createVerticalStrut(10));

            // On s'occupe des deux boutons classiques 3 et 4 joueurs
            joueurs3 = Bouton.creerButton("3 Joueurs");
            // joueurs3.addActionListener(new Adaptateur...(controle));
            centrePanel.add(joueurs3);
            centrePanel.add(Box.createVerticalStrut(10));
            joueurs4 = Bouton.creerButton("4 Joueurs");
            // joueurs4.addActionListener(new Adaptateur...(controle));
            centrePanel.add(joueurs4);
            centrePanel.add(Box.createVerticalStrut(10));
            centrePanel.add(Box.createVerticalGlue());
            content.add(centrePanel, BorderLayout.CENTER);

            // On écrit le bouton du son en haut à droite
            UnMute = Bouton.BoutonUnMute(controle);

            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topRightPanel.add(UnMute,BorderLayout.EAST);
            topRightPanel.setOpaque(false);
            JPanel topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setLayout(new BorderLayout());
            topPanel.add(topRightPanel, BorderLayout.EAST);
            content.add(topPanel, BorderLayout.NORTH);
            UnMute.setBorder(BorderFactory.createEmptyBorder());
            UnMute.setContentAreaFilled(false);

            //On s'occupe de mettre la fleche retour en haut à gauche
            Retour = Bouton.BoutonRetour();
            Retour.addActionListener(new RetourMenuPAdapeur(controle));
            JPanel topLefttPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topLefttPanel.add(Retour,BorderLayout.EAST);
            topLefttPanel.setOpaque(false);
            JPanel leftPanel = new JPanel();
            leftPanel.setOpaque(false);
            leftPanel.setLayout(new BorderLayout());
            leftPanel.add(topLefttPanel, BorderLayout.WEST);
            content.add(leftPanel, BorderLayout.NORTH);
            Retour.setBorder(BorderFactory.createEmptyBorder());
            Retour.setContentAreaFilled(false);

            // Combiner les panneaux en bas à gauche et à droite dans un seul panneau en bas
            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false); 
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.add(topLefttPanel, BorderLayout.WEST);
            bottomPanel.add(topRightPanel, BorderLayout.EAST);
            content.add(bottomPanel, BorderLayout.NORTH);

            // On ajoute le son pour chaque bouton

            SourisAdapte sourisRetour = new SourisAdapte(Retour, FileLoader.getSound("res/clic.wav"));
            SourisAdapte souris3Joueur = new SourisAdapte(joueurs3, FileLoader.getSound("res/clic.wav"));
            SourisAdapte souris4Joueur = new SourisAdapte(joueurs4, FileLoader.getSound("res/clic.wav"));
            Retour.addMouseListener(sourisRetour);
            joueurs3.addMouseListener(souris3Joueur);
            joueurs4.addMouseListener(souris4Joueur);
            
            // Du blabla pour la classe Menu
            content.setVisible(true);
            content.setOpaque(false);
            setOpaque(false);
            add(content);
            controle.addMenu(this);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.exit(1);
        }
    }
}

package View.Menu;

import View.CollecteurEvenements;
import View.Bouton;
import View.BoutonUnMute;
import View.Adaptateurs.*;

import Global.FileLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.Clip;


public class MenuNouvellePartie extends Menu {
    JButton joueurs3, joueurs4;
    BoutonUnMute UnMute;
    JMenu menuPrincipal, menuIA;
    JMenuItem joueurVSjoueur, iaFacile, iaMoyen, iaDifficile;
    public MenuNouvellePartie(CollecteurEvenements controle) {
        super();
        try {
            JPanel content = new JPanel(new BorderLayout());
            JButton Retour;
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement
                                                                                 // vertical
            centrePanel.add(Box.createVerticalGlue());
            centrePanel.add(Box.createVerticalStrut(40));

            // On s'occupe du bouton 2 Joueurs sous forme d'un menu déroulant
            JMenuBar menuBar = new MenuArrondi(20);
            menuPrincipal = new JMenu("2 Joueurs");
            menuPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
            menuPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuBar.add(menuPrincipal); // Ajouter le menu "Fichier" à la barre de menu
            joueurVSjoueur = new JMenuItem("Joueur VS Joueur");
            joueurVSjoueur.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            joueurVSjoueur.addActionListener(new AdaptateurJoueurVSJoueur(controle));
            menuIA = new JMenu("Joueur VS IA");
            menuIA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            menuIA.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    (new Thread(){
                        public void run(){
                            Clip c=null;
                            try{
                                c=FileLoader.getSound("res/IA.wav");
                            }catch(Exception e){
                                System.exit(1);
                            }
                            c.setFramePosition(0);
                            c.start();
                        }
                    }).run();
                }
            });
            iaFacile = new JMenuItem("IA Facile");
            iaFacile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iaFacile.addActionListener(new AdaptateurIAFacile(controle));
            iaMoyen = new JMenuItem("IA Moyen");
            iaMoyen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iaMoyen.addActionListener(new AdaptateurIAMoyen(controle));
            iaDifficile = new JMenuItem("IA Difficile");
            iaDifficile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iaDifficile.addActionListener(new AdaptateurIADifficile(controle));
            menuIA.add(iaFacile);
            menuIA.add(iaMoyen);
            menuIA.add(iaDifficile);

            SourisAdapteMenu sourisMenuPrincipal = new SourisAdapteMenu(menuPrincipal);
            menuPrincipal.addMouseListener(sourisMenuPrincipal);

            menuPrincipal.add(joueurVSjoueur); // Ajouter "Nouveau" au menu "Fichier"
            menuPrincipal.add(menuIA); // Ajouter "Ouvrir" au menu "Fichier"
            centrePanel.add(menuBar);
            centrePanel.add(Box.createVerticalStrut(10));

            // On s'occupe des deux boutons classiques 3 et 4 joueurs
            joueurs3 = Bouton.creerButton("3 Joueurs");
            // joueurs3.addActionListener(new Adaptateur...(controle));
            joueurs3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            joueurs3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(content, "En cours d'implémentation :)", "En construction", JOptionPane.INFORMATION_MESSAGE);

                }
            });
            centrePanel.add(joueurs3);
            centrePanel.add(Box.createVerticalStrut(10));
            joueurs4 = Bouton.creerButton("4 Joueurs");
            // joueurs4.addActionListener(new Adaptateur...(controle));
            joueurs4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            joueurs4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(content, "En cours d'implémentation :)", "En construction", JOptionPane.INFORMATION_MESSAGE);

                }
            });
            centrePanel.add(joueurs4);
            centrePanel.add(Box.createVerticalStrut(10));
            centrePanel.add(Box.createVerticalGlue());
            content.add(centrePanel, BorderLayout.CENTER);

            // On écrit le bouton du son en haut à droite
            UnMute = new BoutonUnMute(controle,0);

            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topRightPanel.add(UnMute, BorderLayout.EAST);
            topRightPanel.setOpaque(false);
            JPanel topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setLayout(new BorderLayout());
            topPanel.add(topRightPanel, BorderLayout.EAST);
            content.add(topPanel, BorderLayout.NORTH);
            UnMute.setBorder(BorderFactory.createEmptyBorder());
            UnMute.setContentAreaFilled(false);
            UnMute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // On s'occupe de mettre la fleche retour en haut à gauche
            Retour = Bouton.BoutonRetour(0);
            Retour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Retour.addActionListener(new RetourMenuPAdapeur(controle));
            JPanel topLefttPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topLefttPanel.add(Retour, BorderLayout.EAST);
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

            // On ajoute le son pour chaque bouto
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
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.exit(1);
        }
    }

    @Override
    public void updateLanguageCode() {
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                joueurs3.setText("3 Joueurs");
                joueurs4.setText("4 Joueurs");
                menuPrincipal.setText("2 Joueurs");
                joueurVSjoueur.setText("Joueur VS Joueur");
                menuIA.setText("Joueur VS IA");
                iaFacile.setText("IA Facile");
                iaMoyen.setText("IA Moyenne");
                iaDifficile.setText("IA Difficile");
                break;
            case "EN":
                joueurs3.setText("3 Players");
                joueurs4.setText("4 Players");
                menuPrincipal.setText("2 Players");
                joueurVSjoueur.setText("Player VS Player");
                menuIA.setText("Player VS AI");
                iaFacile.setText("AI Easy");
                iaMoyen.setText("AI Medium");
                iaDifficile.setText("AI Hard");
                break;
            default:
                break;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        UnMute.repaint(); 
        super.paintComponent(g);
        updateLanguageCode();
    }
}

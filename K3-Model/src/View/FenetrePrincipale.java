package View;

import Global.FileLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

@Deprecated
public class FenetrePrincipale
{
    JFrame frame;
    CollecteurEvenements controle;
    JButton NewGame, Charger, Lan, Quit, Regles, FR, EN, UnMute, Mute;


    public FenetrePrincipale(JFrame frame, CollecteurEvenements controle)
    {
        this.frame = frame;
        this.controle = controle;
        GenereMenu();
    }

    public void Page_NewGame(Container container){
		//Supprimer tous les composants de la fenêtre
        for (Component component : container.getComponents())
		{
            container.remove(component);
        }
        // Rafraîchir la fenêtre pour refléter les changements
        container.revalidate();
        container.repaint();
    }

    public void GenereMenu()
    {
        // Charger l'image d'icône
        try{
        frame.setIconImage(FileLoader.getImage("res/IconeV2.png"));
        }catch(IOException e){
            System.exit(1);
        }

        // Panneau central avec les boutons
        JPanel centrePanel = new JPanel();
        centrePanel.setOpaque(false);
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement vertical
        centrePanel.add(Box.createVerticalGlue());
        centrePanel.add(Box.createVerticalStrut(40));

        NewGame = Bouton.creerButton("Nouvelle partie");
        NewGame.addActionListener(new AdaptateurNewGame(controle));
        centrePanel.add(NewGame);
        centrePanel.add(Box.createVerticalStrut(10));

        Charger = Bouton.creerButton("Charger partie");
        Charger.addActionListener(new AdaptateurCharger(controle));
        centrePanel.add(Charger);
        centrePanel.add(Box.createVerticalStrut(10));

        Lan = Bouton.creerButton("LAN");
        Lan.addActionListener(new AdaptateurLan(controle));
        centrePanel.add(Lan);
        centrePanel.add(Box.createVerticalStrut(10));

        Quit = Bouton.creerButton("Quitter");
        Quit.addActionListener(new AdaptateurQuit(controle));
        centrePanel.add(Quit);
        centrePanel.add(Box.createVerticalStrut(10));
        centrePanel.add(Box.createVerticalGlue());

        frame.add(centrePanel, BorderLayout.CENTER);

        // On récupère les images
        ImageIcon iconFR = new ImageIcon("res/Francev2.jpg");
        ImageIcon iconEN = new ImageIcon("res/anglais.png");
        
        // Redimensionner les images à 20x20 pixels
        Image resizedImageFR = iconFR.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        Image resizedImageEN = iconEN.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);

        // Créer les icônes redimensionnées
        iconFR = new ImageIcon(resizedImageFR);
        iconEN = new ImageIcon(resizedImageEN);

        // Créer les boutons avec les icônes d'images
        FR = new JButton(iconFR);
        EN = new JButton(iconEN);
        UnMute = Bouton.BoutonUnMute(controle);
        
        // Ajouter des écouteurs d'actions aux boutons
        FR.addActionListener(new AdaptateurLangues(controle));
        EN.addActionListener(new AdaptateurLangues(controle));
        Mute.addActionListener(new AdaptateurSon(controle));

        // Panneau en bas à gauche pour les drapeaux
        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeftPanel.setOpaque(false);
        // Ajouter les boutons au panneau
        bottomLeftPanel.add(FR);
        bottomLeftPanel.add(EN);

        // Panneau en bas à droite pour le bouton "Règles"
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  
        bottomRightPanel.setOpaque(false); 
        Regles =Bouton.creerButton("Règles");
        Regles.addActionListener(new AdaptateurRegles(controle));
        bottomRightPanel.add(Regles);

        // Combiner les panneaux en bas à gauche et à droite dans un seul panneau en bas
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); 
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
                
        // Panneau en haut à droite pour le son
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        topRightPanel.add(UnMute,BorderLayout.EAST);
        topRightPanel.setOpaque(false);
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topRightPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Associe le son aux boutons
        try{
            SourisAdapte sourisNewGame = new SourisAdapte(NewGame,FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisCharger = new SourisAdapte(Charger, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisLan = new SourisAdapte(Lan,FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisQuit = new SourisAdapte(Quit, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisRegles = new SourisAdapte(Regles, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisFr = new SourisAdapte(FR, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisEn = new SourisAdapte(EN, FileLoader.getSound("res/clic.wav"));
            NewGame.addMouseListener(sourisNewGame);
            Charger.addMouseListener(sourisCharger);
            Lan.addMouseListener(sourisLan);
            Quit.addMouseListener(sourisQuit);
            Regles.addMouseListener(sourisRegles);
            FR.addMouseListener(sourisFr);
            EN.addMouseListener(sourisEn);
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.exit(1);
        }
        

        FR.setBorder(BorderFactory.createEmptyBorder());
        FR.setContentAreaFilled(false);

        EN.setBorder(BorderFactory.createEmptyBorder());
        EN.setContentAreaFilled(false);



        NewGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Les trois lignes du dessous permettent d'ouvrir une nouvelle fenêtre
                // frame.dispose();
                // // Action exécutée lors du clic sur le bouton
                // openNewPage();

                // Les lignes du dessous permettent de "clear" la fenetre actuelle
                Page_NewGame(centrePanel); // Boutons du milieu
                Page_NewGame(bottomPanel);	// Boutons des langues
                // Page_NewGame(bottomRightPanel);
                // Page_NewGame(bottomLeftPanel);
                JRadioButton joueur = new JRadioButton("Joueur 1");
            }
        });
    }
}

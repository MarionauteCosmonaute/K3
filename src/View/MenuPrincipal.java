package View;

import Global.FileLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuPrincipal extends Menu {
    public MenuPrincipal(CollecteurEvenements controle) {
        super();
        JPanel content = new JPanel(new BorderLayout());
        JButton NewGame, Charger, Lan, Quit, Regles, FR, EN, UnMute;
        addKeyListener(new AdaptateurClavier(controle));
        try {
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement
                                                                                 // vertical
            centrePanel.add(Box.createVerticalGlue());
            centrePanel.add(Box.createVerticalStrut(40));

            NewGame = Bouton.creerButton("Nouvelle partie");
            NewGame.addActionListener(new NewGameActionListener(controle));
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

            content.add(centrePanel, BorderLayout.CENTER);

            // On récupère les images
            // Redimensionner les images à 20x20 pixels
            Image resizedImageFR = FileLoader.getImage("res/Francev2.jpg").getScaledInstance(40, 30,
                    Image.SCALE_SMOOTH);
            Image resizedImageEN = FileLoader.getImage("res/anglais.png").getScaledInstance(40, 30, Image.SCALE_SMOOTH);

            // Créer les boutons avec les icônes d'images
            FR = new JButton(new ImageIcon(resizedImageFR));
            EN = new JButton(new ImageIcon(resizedImageEN));
            UnMute = Bouton.BoutonUnMute(controle);

            // Ajouter des écouteurs d'actions aux boutons
            FR.addActionListener(new AdaptateurLangues(controle));
            EN.addActionListener(new AdaptateurLangues(controle));

            // Panneau en bas à gauche pour les drapeaux
            JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottomLeftPanel.setOpaque(false);
            // Ajouter les boutons au panneau
            bottomLeftPanel.add(FR);
            bottomLeftPanel.add(EN);

            // Panneau en bas à droite pour le bouton "Règles"
            JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomRightPanel.setOpaque(false);
            Regles = Bouton.creerButton("Règles");
            Regles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String rules = "DÉROULEMENT DU JEU\n" +
                            "À votre tour, vous devez jouez un des pions de votre pyramide. Vous pouvez :\n" +
                            " -> Jouer un pion coloré et le placer sur le K3.\n" +
                            " -> Jouer un pion naturel et le placer sur le K3. Un pion naturel est considéré comme un joker et peut remplacer n'importe quelle couleur.\n"
                            +
                            " -> Jouer un pion blanc : vous le retirez alors de votre pyramide, et passez votre tour. Chaque pion blanc n’est utilisable qu’une fois par partie.\n"
                            +
                            " -> Vous pouvez jouer un pion uniquement s'il est accessible.\n" +
                            " -> Un pion est toujours placé à cheval sur deux autres pions du K3.\n" +
                            " -> Un pion coloré doit toujours être placé sur au moins un pion de la même couleur, ou un pion naturel. Un pion naturel peut être placé sur n’importe quel pion.\n"
                            +
                            " Attention : placer un pion sur deux pions de la même couleur (colorés ou naturels) entraîne une pénalité.\n"
                            +
                            "\n" +
                            "PENALITÉ\n" +
                            "Jouer votre pion sur deux pions de la même couleur entraîne une pénalité. Le joueur qui vous précède récupère un de vos pions accessibles au choix et le place à côté de sa propre pyramide.\n"
                            +
                            "Il fait partie de ses pions accessibles pour le reste de la partie et pourra être joué lors d’un prochain tour de jeu, ou récupéré par un adversaire lors d’une prochaine pénalité.\n"
                            +
                            "Note: lors d’une partie à 4, le pion blanc à côté de la pyramide est également accessible et peut être récupéré comme pénalité.\n"
                            +
                            "\n" +
                            "ÉLIMINATION\n" +
                            "Si, lorsque vient votre tour, vous ne pouvez plus jouer, vous êtes éliminé de la partie.\n"
                            +
                            "Lors d’une partie à 3, le joueur précédant la première personne éliminée récupère le pion blanc écarté en début de jeu. Ce pion est placé à côté de sa pyramide et pourra être joué lors d’un prochain tour.";
                    JOptionPane.showMessageDialog(content, rules, "Regles du jeu", JOptionPane.INFORMATION_MESSAGE);
                }

            });
            bottomRightPanel.add(Regles);

            // Combiner les panneaux en bas à gauche et à droite dans un seul panneau en bas
            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
            bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
            content.add(bottomPanel, BorderLayout.SOUTH);

            // Panneau en haut à droite pour le son
            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            topRightPanel.add(UnMute, BorderLayout.EAST);
            topRightPanel.setOpaque(false);
            JPanel topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setLayout(new BorderLayout());
            topPanel.add(topRightPanel, BorderLayout.EAST);
            content.add(topPanel, BorderLayout.NORTH);

            // Associe le son aux boutons
            SourisAdapte sourisNewGame = new SourisAdapte(NewGame, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisCharger = new SourisAdapte(Charger, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisLan = new SourisAdapte(Lan, FileLoader.getSound("res/clic.wav"));
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

            FR.setBorder(BorderFactory.createEmptyBorder());
            FR.setContentAreaFilled(false);

            EN.setBorder(BorderFactory.createEmptyBorder());
            EN.setContentAreaFilled(false);

            content.setVisible(true);
            content.setOpaque(false);
            setOpaque(false);
            add(content);
            controle.addMenu(this);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.exit(1);
        }
    }
}

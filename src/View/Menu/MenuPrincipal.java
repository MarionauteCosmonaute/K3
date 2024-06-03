package View.Menu;

import Global.FileLoader;
import View.Adaptateurs.*;
import View.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;

public class MenuPrincipal extends Menu {
    JButton NewGame, Charger, Quit, Lan, Regles, Tuto;
    BoutonUnMute UnMute;
    public MenuPrincipal(CollecteurEvenements controle) {
        super();
        JPanel content = new JPanel(new BorderLayout());
        JButton FR, EN;
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
            NewGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            centrePanel.add(NewGame);
            centrePanel.add(Box.createVerticalStrut(10));

            Charger = Bouton.creerButton("Charger partie");
            Charger.addActionListener(new AdaptateurCharger(controle));
            Charger.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            centrePanel.add(Charger);
            centrePanel.add(Box.createVerticalStrut(10));

            Lan = Bouton.creerButton("En ligne");
            Lan.addActionListener(new AdaptateurLan(controle));
            Lan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            centrePanel.add(Lan);
            centrePanel.add(Box.createVerticalStrut(10));

            Tuto = Bouton.creerButton("Tutoriel");
            Tuto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Tuto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showTutoPopup();
                }
            });
            centrePanel.add(Tuto);
            centrePanel.add(Box.createVerticalStrut(10));

            Quit = Bouton.creerButton("Quitter");
            Quit.addActionListener(new AdaptateurQuit(controle));
            Quit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            UnMute = new BoutonUnMute(controle,0);

            // Ajouter des écouteurs d'actions aux boutons
            FR.addActionListener(new AdaptateurFR(controle));
            FR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            EN.addActionListener(new AdaptateurEN(controle));
            EN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            UnMute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Panneau en bas à gauche pour les drapeaux
            JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottomLeftPanel.setOpaque(false);
            // Ajouter les boutons au panneau
            bottomLeftPanel.add(FR);
            bottomLeftPanel.add(EN);

            // Panneau en bas à droite pour le bouton "Règles"
            JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomRightPanel.setOpaque(false);
            Regles = Bouton.Rules(content);
            Regles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            SourisAdapte sourisTuto = new SourisAdapte(Tuto, FileLoader.getSound("res/clic.wav"));
            NewGame.addMouseListener(sourisNewGame);
            Charger.addMouseListener(sourisCharger);
            Lan.addMouseListener(sourisLan);
            Quit.addMouseListener(sourisQuit);
            Regles.addMouseListener(sourisRegles);
            FR.addMouseListener(sourisFr);
            EN.addMouseListener(sourisEn);
            Tuto.addMouseListener(sourisTuto);

            FR.setBorder(BorderFactory.createEmptyBorder());
            FR.setContentAreaFilled(false);

            EN.setBorder(BorderFactory.createEmptyBorder());
            EN.setContentAreaFilled(false);

            updateLanguageCode();
            content.setVisible(true);
            content.setOpaque(false);
            setOpaque(false);
            add(content);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        // } catch (IOException e) {
            System.exit(1);
        }
    }

    private void showTutoPopup() {   
        // Créer une boîte de dialogue pour afficher le tutoriel
        JDialog tutoDialog = new JDialog((Frame) null, "Tuto", true);
        tutoDialog.setLayout(new BorderLayout());

        // Ajouter des images au tutoriel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        // Ajouter des images au panel
        for (String imagePath : new String[]{"res/Tuto1.png", "res/Tuto2.png", "res/Tuto_blanc.png", "res/Tuto_cube.png", "res/Tuto_penalite.png", "res/Tuto2_penalite.png", "res/Tuto_bois.png", "res/Tuto_finPartie.png"}) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            JLabel imageLabel = new JLabel(imageIcon);
            imagePanel.add(imageLabel);
        }

        // Ajouter une zone de défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setPreferredSize(new Dimension(1500, 1000));

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> tutoDialog.dispose());

        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                closeButton.setText("Fermer");
                break;
            case "EN":
                closeButton.setText("Close");
                break;
            default:
                break;
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        tutoDialog.add(scrollPane, BorderLayout.CENTER);
        tutoDialog.add(buttonPanel, BorderLayout.SOUTH);
        tutoDialog.pack();
        tutoDialog.setLocationRelativeTo(null); // Centrer la boîte de dialogue
        tutoDialog.setVisible(true);
    }

    @Override
    public void updateLanguageCode() {
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                NewGame.setText("Nouvelle Partie");
                Charger.setText("Charger Partie");
                Lan.setText("En ligne");
                Quit.setText("Quitter");
                Regles.setText("Règles");
                Tuto.setText("Tutoriel");
                break;
            case "EN":
                NewGame.setText("New game");
                Charger.setText("Load Game");
                Lan.setText("Online");
                Quit.setText("Quit");
                Regles.setText("Rules");
                Tuto.setText("Tutorial");
                break;
            default:
                break;
        }
    }

    @Override
    public void paintComponents(Graphics g){
        UnMute.repaint();
        super.paintComponents(g);
    }
}

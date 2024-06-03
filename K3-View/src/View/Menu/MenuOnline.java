package View.Menu;

import Global.FileLoader;
import View.Adaptateurs.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class MenuOnline extends Menu{
    JButton Host, Join;
    BoutonUnMute UnMute;

    public MenuOnline(CollecteurEvenements controle) {
        super();
        JButton  Retour;
        JPanel content = new JPanel(new BorderLayout());
        try {
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement
                                                                                 // vertical
            centrePanel.add(Box.createVerticalGlue());
            centrePanel.add(Box.createVerticalStrut(40));

            Host = Bouton.creerButton("Héberger");
            Host.addActionListener(new AdaptateurHost(controle));
            centrePanel.add(Host);
            centrePanel.add(Box.createVerticalStrut(10));

            Join = Bouton.creerButton("Rejoindre");
            Join.addActionListener(new AdaptateurJoin(controle));
            centrePanel.add(Join);
            centrePanel.add(Box.createVerticalStrut(10));
            centrePanel.add(Box.createVerticalGlue());

            content.add(centrePanel, BorderLayout.CENTER);

            UnMute = new BoutonUnMute(controle,0,content);

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

            // On s'occupe de mettre la fleche retour en haut à gauche
            Retour = Bouton.BoutonRetour(0);
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
            SourisAdapte sourisHost = new SourisAdapte(Host, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisJoin = new SourisAdapte(Join, FileLoader.getSound("res/clic.wav"));
            Retour.addMouseListener(sourisRetour);
            Host.addMouseListener(sourisHost);
            Join.addMouseListener(sourisJoin);
            
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
                Host.setText("Héberger");
                Join.setText("Rejoindre");
                break;
            case "EN":
                Host.setText("Host");
                Join.setText("Join");
                break;
            default:
                break;
        }
    }

    public void paintComponent(Graphics g){
        UnMute.repaint();
        super.paintComponent(g);
        updateLanguageCode();
    }
}

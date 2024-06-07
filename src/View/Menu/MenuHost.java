package View.Menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Global.FileLoader;
import View.Bouton;
import View.BoutonUnMute;
import View.CollecteurEvenements;
import View.Adaptateurs.RetourMenuPAdapeur;
import View.Adaptateurs.SourisAdapte;

public class MenuHost extends Menu{
    BoutonUnMute UnMute;

    public MenuHost(CollecteurEvenements controle) {
        super();
        JButton  Retour;
        JPanel content = new JPanel(new BorderLayout());
        int nbplayers = 1;
        try {
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour l'alignement
                                                                                 // vertical
            centrePanel.add(Box.createVerticalGlue());
            centrePanel.add(Box.createVerticalStrut(40));
            JLabel idDisplay=new JLabel("ID :" + 1000);
            idDisplay.setFont(new Font("default", Font.BOLD, 25));
            centrePanel.add(idDisplay);
            centrePanel.add(Box.createVerticalStrut(10));

            JLabel waitText=new JLabel("Waiting for players ..." + nbplayers +'/' + "2");
            waitText.setFont(new Font("default", Font.BOLD, 30));

            centrePanel.add(waitText);
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
            Retour.addMouseListener(sourisRetour);
            
            content.setVisible(true);
            content.setOpaque(false);
            setOpaque(false);
            add(content);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.exit(1);
        }
    }

    
}
        


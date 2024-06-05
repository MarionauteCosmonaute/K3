package View.Menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Global.FileLoader;
import View.Bouton;
import View.BoutonArrondi;
import View.BoutonUnMute;
import View.CollecteurEvenements;
import View.Adaptateurs.RetourMenuPAdapeur;
import View.Adaptateurs.SourisAdapte;

public class MenuJoin extends Menu{
    BoutonUnMute UnMute;
    String str=null;
    public MenuJoin(CollecteurEvenements controle) {
        super();
        JButton  Retour;
        JPanel content = new JPanel(new BorderLayout());
        try {
            // Panneau central avec les boutons
            JPanel centrePanel = new JPanel();
            centrePanel.setOpaque(false);
            centrePanel.setLayout(new GridLayout(3,1)); // Utiliser BoxLayout pour l'alignement
                                                                                 // vertical
            JLabel idDisplay=new JLabel("ID de l'Hote:");
            idDisplay.setFont(new Font("default", Font.BOLD, 25));
            centrePanel.add(idDisplay);

            JTextField givenID=new JTextField("Entrez l'ID de l'hote");
            givenID.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    givenID.setText("");
                }
            });
            givenID.setFont(new Font("default", Font.ITALIC, 15));

            centrePanel.add(givenID);
            JPanel validerPanel=new JPanel();
            validerPanel.setLayout(new BoxLayout(validerPanel, BoxLayout.Y_AXIS));
            validerPanel.add(Box.createVerticalGlue());
            validerPanel.add(Box.createVerticalStrut(10));
            JButton valider= Bouton.creerButton("Valider");
            validerPanel.add(valider);
            valider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    enteredText(givenID.getText());
                    

                }
            });
            validerPanel.add(Box.createVerticalStrut(10));
            JLabel noSuchHost=new JLabel("Hote inconnu");
            validerPanel.add(noSuchHost);
            noSuchHost.setFont(new Font("default",Font.BOLD,10));
            validerPanel.setOpaque(false);
            centrePanel.add(validerPanel);

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

    public void enteredText(String s){
        str=s;
        System.out.println(str);
    }

    
}
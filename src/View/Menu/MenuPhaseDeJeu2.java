package View.Menu;

import View.CollecteurEvenements;
import View.Curseur;
import View.Bouton;
import View.BoutonUnMute;
import View.PDJPyramideCentrale;
import View.PDJPyramideJoueur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Global.FileLoader;
import View.Adaptateurs.*;
import java.awt.*;

import Model.Jeu;
import Patterns.Observateur;

public class MenuPhaseDeJeu2 extends Menu implements Observateur {
    JButton Aide, Regles, Annuler, Refaire, last_coup, save;
    BoutonUnMute UnMute;
    PDJPyramideCentrale pdj;
    PDJPyramideJoueur joueur1,joueur2;
    

    public MenuPhaseDeJeu2(CollecteurEvenements controle, Jeu J) {
        super();
        J.ajouteObservateur(this);
        try {
            JPanel content = new JPanel(new BorderLayout());
            JButton  Retour;

            // On sépare la partie qui contient les boutons retour/aide/son et la partie du
            // jeu
            JPanel topPanel = new JPanel(new GridLayout(1, 6)); // pour les boutons

            // Bouton Retour
            Retour = Bouton.BoutonRetour(1);
            Retour.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int retour = showConfirmDialog();
                    if (retour == 0) {
                        controle.commande("MenuP");
                    }
                }
            });

            JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topLeftPanel.add(Retour, BorderLayout.EAST);
            topLeftPanel.setOpaque(false);

            save = Bouton.creerButton("Sauvegarder");
            save.addActionListener(new AdaptateurSave(controle));
            SourisAdapte sourissave = new SourisAdapte(save, FileLoader.getSound("res/clic.wav"));
            save.addMouseListener(sourissave);
            topLeftPanel.add(save, BorderLayout.CENTER);
            
            topPanel.add(topLeftPanel, BorderLayout.WEST);
            Retour.setBorder(BorderFactory.createEmptyBorder());
            Retour.setContentAreaFilled(false);

            JPanel topCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));

            // Bouton Annuler
            Image resizedImageFR = FileLoader.getImage("res/flecheAnnule.png").getScaledInstance(70, 50, Image.SCALE_SMOOTH);
            Annuler = new JButton(new ImageIcon(resizedImageFR));
            Annuler.setToolTipText("Annuler");
            Annuler.setBorder(BorderFactory.createEmptyBorder());
            Annuler.setContentAreaFilled(false);
            //Annuler = Bouton.creerButton("Annuler");
            Annuler.addActionListener(new AdaptateurAnnule(controle));
            Annuler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Annuler.setEnabled(false);
            topCenter.add(Annuler, BorderLayout.CENTER);

            // Bouton Refaire
            Image resizedImageEN = FileLoader.getImage("res/flecheRefais.png").getScaledInstance(70, 50, Image.SCALE_SMOOTH);
            Refaire = new JButton(new ImageIcon(resizedImageEN));
            //Refaire = Bouton.creerButton("Refaire");
            Refaire.setToolTipText("Refaire");
            Refaire.setBorder(BorderFactory.createEmptyBorder());
            Refaire.setContentAreaFilled(false);
            Refaire.addActionListener(new AdaptateurRefais(controle));
            Refaire.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Refaire.setEnabled(false);
            topCenter.add(Refaire, BorderLayout.CENTER);
            
            topCenter.setOpaque(false);
            topPanel.add(topCenter);
            
            topCenter.setOpaque(false);
            topPanel.add(topCenter);
            
            // Bouton Dernier Coup
            last_coup = Bouton.creerButton("Dernier Coup");
            last_coup.addActionListener(new AdaptateurDernierCoup(controle));
            last_coup.setEnabled(false);
            topPanel.add(last_coup, BorderLayout.CENTER);

            // Bouton Aide
            Aide = Bouton.creerButton("Suggestion");
            // Aide.addActionListener(new AideAdaptateur(controle));
            // Aide.addActionListener(new ActionListener() {
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         JOptionPane.showMessageDialog(content, "En cours d'implémentation :)", "En construction", JOptionPane.INFORMATION_MESSAGE);

            //     }
            // });
            Aide.addActionListener(new AdaptateurSuggestion(controle));

            topPanel.add(Aide, BorderLayout.CENTER);

            Regles = Bouton.Rules(content);
            topPanel.add(Regles);

            // Bouton du Son
            UnMute = new BoutonUnMute(controle,1,content);
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
            //pyramidePanel.setBorder(BorderFactory.createLineBorder(new Color(255,204,255), 5));
            pdj = (new PDJPyramideCentrale(J, pyramidePanel)); // ajoute la pyramide centrale
            // pyramidePanel.addMouseListener(new AdaptateurSourisPhasePyramide(controle, pdj));
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
            // bottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            joueur1 = (new PDJPyramideJoueur(J, bottomLeftPanel, 0)); // ajoute la pyramide du joueur
                                                                                        // 1
            // bottomLeftPanel.addMouseListener(new AdaptateurSourisPhaseJoueur(controle, joueur1, pdj));
            bottomLeftPanel.add(joueur1, BorderLayout.CENTER);
            joueur1.setVisible(true);

            // Joueur Rouge
            // bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            joueur2 = (new PDJPyramideJoueur(J, bottomRightPanel, 1)); // ajoute la pyramide du joueur
                                                                                         // 2
            bottomRightPanel.addMouseListener(new AdaptateurSourisPhaseJoueur(controle, joueur2, joueur1, pdj));
            bottomLeftPanel.addMouseListener(new AdaptateurSourisPhaseJoueur(controle, joueur1, joueur2, pdj));
            pyramidePanel.addMouseListener(new AdaptateurSourisPhasePyramide(controle, pdj, joueur1, joueur2));
            // Pour partir sur de bonne base!
            // joueur1.setCursor(Cursor.getDefaultCursor());
            // joueur2.setCursor(Cursor.getDefaultCursor());
            // pdj.setCursor(Cursor.getDefaultCursor());
            joueur1.setCursor(Curseur.Gerer_Curseur_main());
            joueur2.setCursor(Curseur.Gerer_Curseur_main());
            pdj.setCursor(Curseur.Gerer_Curseur_main());
            

            bottomRightPanel.add(joueur2, BorderLayout.CENTER);
            joueur2.setVisible(true);

            // Associe le son aux boutons
            SourisAdapte sourisAnnuler = new SourisAdapte(Annuler, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisRefaire = new SourisAdapte(Refaire, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisAide = new SourisAdapte(Aide, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisRegles = new SourisAdapte(Regles, FileLoader.getSound("res/clic.wav"));
            Annuler.addMouseListener(sourisAnnuler);
            Refaire.addMouseListener(sourisRefaire);
            Aide.addMouseListener(sourisAide);
            Regles.addMouseListener(sourisRegles);

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
            validate();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public void setAnnuler(boolean b) 
    {
        Annuler.setEnabled(b);
    }

    public void setRefaire(boolean b) 
    {
        Refaire.setEnabled(b);
    }

    public void setLastCoup(boolean bool){
        last_coup.setEnabled(bool);
    }

    public void setDernierCoup(boolean bool){
        pdj.setDernierCoup(bool);      
    }

    public boolean getDernierCoup(){
        return pdj.getDernierCoup();      
    }

    public void setSuggestion(boolean bool){
        if(pdj.GetJoueurCourant() == 0){
            joueur1.setSuggestion(bool);
            joueur2.setSuggestion(false);
        }
        else{
            joueur1.setSuggestion(false);
            joueur2.setSuggestion(bool);
        }
        pdj.setSuggestion(bool);
    }

    public void setCoupSuggere(Point joueur, Point pyramide_centrale){
        if(pdj.GetJoueurCourant() == 0){
            joueur1.setCoupSuggere(joueur);
        }
        else{
            joueur2.setCoupSuggere(joueur);
        }
        pdj.setCoupSuggere(pyramide_centrale);
    }
   

    @Override
    public void updateLanguageCode() {
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                Aide.setText("Suggestion");
                Regles.setText("Règles");
                Annuler.setToolTipText("Annuler");
                last_coup.setText("Dernier Coup");
                Refaire.setToolTipText("Refaire");
                break;
            case "EN":
                Aide.setText("Suggestion");
                Regles.setText("Rules");
                Annuler.setToolTipText("Undo");
                last_coup.setText("Last Played");
                Refaire.setToolTipText("Redo");
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

    static int showConfirmDialog() {
        String languageCode = Global.Config.getLanguage();
        String message = null;
        String title = null;
        switch (languageCode) {
            case "FR":
                message = "Voulez-vous vraiment quitter?";
                title = "Quitter";
                break;
            case "EN":
                message = "Are you sure you want to quit?";
                title = "Quit";
                break;
            default:
                break;
        }
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }

	@Override
	public void miseAJour() {
		joueur1.repaint();
        joueur2.repaint();
        pdj.repaint();
	}
}
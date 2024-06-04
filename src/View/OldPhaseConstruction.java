package View;

import Model.Jeu;
import Model.Cube;
import View.Adaptateurs.*;

import javax.swing.*;

import Global.FileLoader;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class OldPhaseConstruction {
    JPanel frame;
    JLabel joueurLabel, constructLabel;
    CollecteurEvenements controle;
    Graphics2D drawable;
    Image neutre, bleu, vert, jaune, noir, blanc, rouge, vide, carre_noir_vide;
    Jeu jeu;
    int nb_couleurs[];
    Point tab_pts[][];
    Point tab_pioche[];
    boolean cube_sel;
    int taille_cube;
    int emplacement;
    boolean dessiner_vide; //a renommer : correspond au booléen pour l'encadrement du cube selectionné dans la pioche
    boolean moins_un_pioche = false;
    boolean moins_un_pyramide = false;

    int echange;
    int x1, y1;

    int nbJoueur;
    int taille_base_pyramide;

    JButton reset, valider, Aide, Regles, Retour, IA;
    BoutonUnMute UnMute;
    public OldPhaseConstruction(JPanel frame, CollecteurEvenements controle, Jeu jeu) {
        this.frame = frame;
        this.controle = controle;
        this.jeu = jeu;
        try {
            InputStream in = new FileInputStream("res/neutre2.png");
            neutre = ImageIO.read(in);
            in = new FileInputStream("res/bleu.png");
            bleu = ImageIO.read(in);
            in = new FileInputStream("res/vert.png");
            vert = ImageIO.read(in);
            in = new FileInputStream("res/jaune.png");
            jaune = ImageIO.read(in);
            in = new FileInputStream("res/violet.png");
            noir = ImageIO.read(in);
            in = new FileInputStream("res/ange.png");
            blanc = ImageIO.read(in);
            in = new FileInputStream("res/rouge.png");
            rouge = ImageIO.read(in);
            in = new FileInputStream("res/carre_vide.png");
            vide = ImageIO.read(in);
            in = new FileInputStream("res/carre_noir_vide.png");
            carre_noir_vide = ImageIO.read(in);

        } catch (FileNotFoundException e) {
            System.err.println("ERREUR : impossible de trouver le fichier");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("ERREUR : impossible de charger l'image");
            System.exit(3);
        }

        while (jeu.draw()) {
        }

        nbJoueur = jeu.nbJoueur();
        taille_base_pyramide = 8 - nbJoueur;
        tab_pts = new Point[taille_base_pyramide][taille_base_pyramide];
        cube_sel = false;

        dessiner_vide = false;
        tab_pioche = new Point[21];

        echange = 0;

        Box boiteTexte = Box.createVerticalBox();
        JPanel centrePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        Regles = Bouton.creerButton("Règles");
        Regles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String languageCode = Global.Config.getLanguage();
                String rules = null;
                String title = null;
                switch (languageCode) {
                    case "FR":
                        title = "Règles de construction de pyramide";
                        rules = "MISE EN PLACE DE VOTRE PYRAMIDE\n" +
                        "Organisez et empilez tous vos pions en pyramide devant vous en les faisant chevaucher.\n" +
                        "Les pions non bloqués sont les seuls accessibles, choississez bien votre agencement pour l'adapter à votre stratégie.\n"+
                        "Le premier cube accessible sera le sommet de votre pyramide";
                        break;
                    case "EN" :
                        title = "Rules to set up a pyramid";
                        rules = "SETTING UP YOUR PYRAMID\n"+
                        "Arrange and stack all your pawns in a pyramid in front of you, making them overlap.\n" +
                        "The first accessible pawn will be the top of your pyramid";
                        break;
                }
                JOptionPane.showMessageDialog(frame, rules, title, JOptionPane.INFORMATION_MESSAGE);
            }

        });
        Regles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        centrePanel.add(Regles);
        
        boiteTexte.add(centrePanel);
        boiteTexte.setOpaque(false);
        frame.add(boiteTexte, BorderLayout.SOUTH);



        JPanel panel = new JPanel(new GridLayout(1, 3));
        Retour = Bouton.BoutonRetour(1);
        //Retour.addActionListener(new RetourMenuPAdapeur(controle));
        Retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int retour = showConfirmDialog();
                if (retour == 0) {
                    controle.commande("MenuP");
                }
            }
        });
        Retour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(Retour, BorderLayout.EAST);
        topLeftPanel.setOpaque(false);
        panel.add(topLeftPanel, BorderLayout.WEST);
        Retour.setBorder(BorderFactory.createEmptyBorder());
        Retour.setContentAreaFilled(false);

        joueurLabel= new JLabel("Joueur "+ (jeu.get_player()+1));
        joueurLabel.setFont(new Font("Default", Font.BOLD, 20));
        // joueurLabel.setBackground(Color.WHITE);
        // joueurLabel.setOpaque(true);
		panel.add(joueurLabel);

        // Bouton Aide
        Aide = Bouton.creerButton("Auto-complétion");
        Aide.addActionListener(new AdaptateurAideConstruction(controle));
        Aide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel topCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topCenter.add(Aide, BorderLayout.CENTER);
        topCenter.setOpaque(false);

        // Bouton construction IA
        IA = Bouton.creerButton("Par IA");
        IA.addActionListener(new AdaptateurAideConstructionIA(controle));
        IA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topCenter.add(IA, BorderLayout.CENTER);
        topCenter.setOpaque(false);

        reset = Bouton.creerButton("Réinitialiser");
        reset.addActionListener(new AdaptateurReset(controle));
        reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topCenter.add(reset, BorderLayout.CENTER);
        topCenter.setOpaque(false);

        valider = Bouton.creerButton("Valider");
        valider.addActionListener(new AdaptateurValider(controle));
        valider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        valider.setEnabled(false);
        topCenter.add(valider);

        panel.add(topCenter);

        // Bouton du Son
        UnMute = new BoutonUnMute(controle,1);
        UnMute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(UnMute, BorderLayout.EAST);
        topRightPanel.setOpaque(false);
        panel.add(topRightPanel);
        UnMute.setBorder(BorderFactory.createEmptyBorder());
        UnMute.setContentAreaFilled(false);
        
        Box boite_aide = Box.createVerticalBox();
        boite_aide.add(panel);
        boite_aide.setOpaque(false);
        frame.add(boite_aide, BorderLayout.NORTH);
        centrePanel.setOpaque(false);
        panel.setOpaque(false);
        updateLanguageCode();

        try {
            SourisAdapte sourisRetour = new SourisAdapte(Retour, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisvalider = new SourisAdapte(valider, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisreset = new SourisAdapte(reset, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisAide = new SourisAdapte(Aide, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisRegles = new SourisAdapte(Regles, FileLoader.getSound("res/clic.wav"));
            SourisAdapte sourisIA = new SourisAdapte(IA, FileLoader.getSound("res/clic.wav"));
            Retour.addMouseListener(sourisRetour);
            valider.addMouseListener(sourisvalider);
            reset.addMouseListener(sourisreset);
            Aide.addMouseListener(sourisAide);
            Regles.addMouseListener(sourisRegles);
            IA.addMouseListener(sourisIA);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.exit(1);
        }
    }

    public void updateLanguageCode() {
        UnMute.repaint();
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                reset.setText("Réinitialiser");
                valider.setText("Valider");
                Aide.setText("Auto-complétion");
                Regles.setText("Règles");
                joueurLabel.setText("Joueur "+ (jeu.get_player()+1));
                IA.setText("Par IA");
                break;
            case "EN":
                reset.setText("Reset");
                valider.setText("Complete");
                Aide.setText("Auto-build");
                Regles.setText("Rules");
                joueurLabel.setText("Player "+ (jeu.get_player()+1));
                IA.setText("By AI");
                break;
            default:
                break;
        }
    }

    public Point[][] points_pyr() {
        return tab_pts;
    }

    public int tailleCube() {
        return taille_cube;
    }

    public Point[] pointsPioche2() {
        return tab_pioche;
    }

    public int[] couleurs() {
        return nb_couleurs;
    }

    public void modifierPioche(int emplacement) {
        this.emplacement = emplacement;
        dessiner_vide = true;

    }

    public void setDessinVideFalse() {
        dessiner_vide = false;
    }

    public boolean peut_cliquer_pyramide() {
        return cube_sel;
    }

    public void set_cube_sel(boolean bool) {
        cube_sel = bool;
    }

    public int getEchange() {
        return echange;
    }

    public void setEchange(int val) {
        echange = val;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public void setX1(int x) {
        x1 = x;
    }

    public void setY1(int y) {
        y1 = y;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setValider(boolean b) {
        valider.setEnabled(b);
    }

    public int getCubePyramideJoueur(int ligne, int colonne){
        switch (jeu.getPlayer().get(ligne, colonne)){
            case Noir:
                return 1;
            case Bleu:
                return 2;
            case Vert :
                return 3;
            case Vide :
                return 7;
            case Blanc :
                return 0;
            case Rouge :
                return 4;
            case Jaune :
                return 5;
            case Neutre :
                return 6;
            default:
                return -1;
        }
    }

    public void fonction_globale(Jeu jeu, Graphics g, int width_fenetre, int height_fenetre) {
        if (pyramidePleine()) {
            setValider(true);
        }
        taille_cube = Math.min(height_fenetre / 12, width_fenetre / 18);
        if(moins_un_pyramide){
            dessiner_pyramide_joueur_moins_un(g, width_fenetre, height_fenetre);
        }
        else{
            dessiner_pyramide_joueur(g, width_fenetre, height_fenetre);
        }
        dessiner_pyramide_centrale(g, width_fenetre, height_fenetre);
        if(moins_un_pioche){
            dessiner_cubes_pioches_moins_un(g, width_fenetre, height_fenetre);
        }
        else{
            dessiner_cubes_pioches(g, width_fenetre, height_fenetre);
        }
    }

    public int max_nb(int[] tab) {
        int max = tab[0];
        for (int i = 0; i < 7; i++) {
            if (tab[i] > max) {
                max = tab[i];
            }
        }
        return max;
    }

    public int couleur_case(int emplacement, int[] couleurs) {
        int somme = 0;
        for (int i = 0; i < 7; i++) {
            somme += couleurs[i];
            if (emplacement <= somme) {
                return i;
            }
        }
        return somme; // juste parce qu'il faut renvoyer un int dans tous les cas
    }

    public boolean pyramidePleine() {
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y < (taille_base_pyramide - x); y++) {
                if (jeu.getPlayer().get(x, y) == Cube.Vide) {
                    return false;
                }
            }
        }
        return true;
    }

    public void SetMoinsUnPioche(boolean bool)
    {
        moins_un_pioche = bool;
    }

    public void SetMoinsUnPyramide(boolean bool)
    {
        moins_un_pyramide = bool;
    }

    public void dessiner_cubes_pioches(Graphics g, int width_fenetre, int height_fenetre) {
        nb_couleurs = jeu.compte_personal_bag();
        drawable = (Graphics2D) g;
        int espace = taille_cube / 10;
        int debut_zone_haut = height_fenetre * 7 / 10;
        int hauteur_utilisee = taille_cube * 3 + 2 * espace;
        int largeur_utilisee = taille_cube * 7 + 6 * espace;

        int y_haut = debut_zone_haut + (height_fenetre - debut_zone_haut - hauteur_utilisee) / 2;
        int x_gauche = (width_fenetre - largeur_utilisee) / 2;

        int somme = 0;
        for (int i = 0; i < 7; i++) {
            somme += nb_couleurs[i];
        }

        int x, y;
        Point p;
        int couleur;
        int ligne, col;
        Cube sCube;

        for (int i = 0; i < somme; i++) {
            ligne = i / 7;
            col = i % 7;
            if (somme >= i + 1) {
                x = x_gauche + col * (taille_cube + espace);
                y = y_haut + ligne * (taille_cube + espace);
                couleur = couleur_case(i + 1, nb_couleurs);
                p = new Point(x, y);
                tab_pioche[i] = p;
                sCube = Cube.intToCube(couleur);
                switch (sCube) {
                    case Noir:
                        drawable.drawImage(noir, x, y, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, x, y, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, x, y, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, x, y, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, x, y, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, x, y, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, x, y, taille_cube, taille_cube, null);
                        break;
                    default:
                        System.err.println("le cube Vide est dans le bag");
                        System.exit(2);
                }

            }
        }
    }

    public void dessiner_cubes_pioches_moins_un(Graphics g, int width_fenetre, int height_fenetre) {
        int nb_couleurs[] = jeu.compte_personal_bag();
        drawable = (Graphics2D) g;
        int espace = taille_cube / 10;
        int debut_zone_haut = height_fenetre * 7 / 10;
        int hauteur_utilisee = taille_cube * 3 + 2 * espace;
        int largeur_utilisee = taille_cube * 7 + 6 * espace;

        int y_haut = debut_zone_haut + (height_fenetre - debut_zone_haut - hauteur_utilisee) / 2;
        int x_gauche = (width_fenetre - largeur_utilisee) / 2;

        int somme = 0;
        for (int i = 0; i < 7; i++) {
            somme += nb_couleurs[i];
        }

        int coul = couleur_case(emplacement + 1, nb_couleurs);
        nb_couleurs[coul]--;
        somme--;

        int x, y;
        Point p;
        int couleur;
        int ligne, col;
        Cube sCube;

        for (int i = 0; i < somme; i++) {
            ligne = i / 7;
            col = i % 7;
            if (somme >= i + 1) {
                x = x_gauche + col * (taille_cube + espace);
                y = y_haut + ligne * (taille_cube + espace);
                couleur = couleur_case(i + 1, nb_couleurs);
                p = new Point(x, y);
                tab_pioche[i] = p;
                sCube = Cube.intToCube(couleur);
                switch (sCube) {
                    case Noir:
                        drawable.drawImage(noir, x, y, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, x, y, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, x, y, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, x, y, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, x, y, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, x, y, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, x, y, taille_cube, taille_cube, null);
                        break;
                    default:
                        System.err.println("le cube Vide est dans le bag");
                        System.exit(2);
                }

            }
        }

        nb_couleurs[coul]++;
        somme++;
    }

    public void dessiner_pyramide_centrale(Graphics g, int width_fenetre, int height_fenetre) {
        drawable = (Graphics2D) g;
        int taille_cube = this.taille_cube * 2 / 3;
        int debut_zone_haut = height_fenetre * 2 / 10;
        int espace = taille_cube / 10;
        int taille_pyramide = taille_cube * 9 + espace * 8;
        int debut_zone_gauche = width_fenetre - width_fenetre / 10 - taille_pyramide;
        Cube c;
        for (int col = 0; col < 9; col++) {

            c = jeu.getCubePrincipale(0, col);
            switch (c) {
                case Noir:
                    drawable.drawImage(noir, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Neutre:
                    drawable.drawImage(neutre, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Blanc:
                    drawable.drawImage(blanc, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Vert:
                    drawable.drawImage(vert, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Jaune:
                    drawable.drawImage(jaune, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Rouge:
                    drawable.drawImage(rouge, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Bleu:
                    drawable.drawImage(bleu, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                case Vide:
                    

                    drawable.drawImage(vide, debut_zone_gauche + col * (taille_cube + espace), debut_zone_haut,
                            taille_cube, taille_cube, null);
                    break;
                default:
                    break;
            }
        }
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre / 35, width_fenetre / 35)));
        drawable.setColor(Color.WHITE);
        String languageCode = Global.Config.getLanguage();
        switch (languageCode) {
            case "FR":
                drawable.drawString("Pyramide de jeu", debut_zone_gauche, Math.min(height_fenetre/3 , width_fenetre/3 ));
                break;

            case "EN":
                drawable.drawString("Central pyramid", debut_zone_gauche, Math.min(height_fenetre/3 , width_fenetre/3 ));
                break;
        }
    }

    public void dessiner_pyramide_joueur(Graphics g, int width_fenetre, int height_fenetre) {
        drawable = (Graphics2D) g;

        int debut_zone_haut = height_fenetre / 10;
        int debut_zone_gauche = width_fenetre * 1 / 5;
        int x_haut, y_haut;

        Point p;
        Cube cube;
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y < (taille_base_pyramide - x); y++) {
                cube = jeu.getPlayer().get(x, y);

                x_haut = debut_zone_haut + (taille_base_pyramide - 1 - x) * (taille_cube + taille_cube / 10);
                y_haut = debut_zone_gauche + x * (taille_cube + taille_cube / 10) / 2
                        + (taille_cube + taille_cube / 10) * (y);
                p = new Point(y_haut, x_haut);
                tab_pts[x][y] = p;
                switch (cube) {
                    case Noir:
                        drawable.drawImage(noir, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vide:
                        drawable.setColor(Color.WHITE);
                        drawable.drawRect(y_haut, x_haut, taille_cube, taille_cube);
                        drawable.drawRect(y_haut+1, x_haut+1, taille_cube-2, taille_cube-2);
                        drawable.drawRect(y_haut+2, x_haut+2, taille_cube-4, taille_cube-4);
                        // drawable.drawImage(vide, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    default:
                        break;
                }
            }
        }
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre / 35, width_fenetre / 35)));
        String languageCode = Global.Config.getLanguage();
        drawable.setColor(Color.WHITE);
        switch (languageCode) {
            case "FR":
                drawable.drawString("Construisez votre pyramide !", debut_zone_gauche+taille_cube/2, Math.min(height_fenetre*19/28 , width_fenetre*19/28 ));
                break;

            case "EN":
                drawable.drawString("Build your pyramid !", debut_zone_gauche+taille_cube/2, Math.min(height_fenetre*19/28 , width_fenetre*19/28 ));
                break;
        }
        // if (echange % 2 == 1) {
        //     x_haut = debut_zone_haut + (taille_base_pyramide - 1 - x1) * (taille_cube + taille_cube / 10);
        //     y_haut = debut_zone_gauche + x1 * (taille_cube + taille_cube / 10) / 2
        //             + (taille_cube + taille_cube / 10) * (y1);

        //     drawable.setColor(Color.RED);

        //     drawable.drawRect(y_haut, x_haut, taille_cube, taille_cube);
        //     drawable.drawRect(y_haut + 1, x_haut + 1, taille_cube - 2, taille_cube - 2);
        //     drawable.drawRect(y_haut + 2, x_haut + 2, taille_cube - 4, taille_cube - 4);

        //     drawable.setColor(Color.WHITE);

        //     drawable.drawRect(y_haut + 3, x_haut + 3, taille_cube - 6, taille_cube - 6);
        //     drawable.drawRect(y_haut + 4, x_haut + 4, taille_cube - 8, taille_cube - 8);
        //     drawable.drawRect(y_haut + 5, x_haut + 5, taille_cube - 10, taille_cube - 10);

        //     drawable.setColor(Color.BLACK);
        // }
    }

    public void dessiner_pyramide_joueur_moins_un(Graphics g, int width_fenetre, int height_fenetre) {
        drawable = (Graphics2D) g;

        int debut_zone_haut = height_fenetre / 10;
        int debut_zone_gauche = width_fenetre * 1 / 5;
        int x_haut, y_haut;

        Point p;
        Cube cube;
        for (int x = 0; x < taille_base_pyramide; x++) {
            for (int y = 0; y < (taille_base_pyramide - x); y++) {
                cube = jeu.getPlayer().get(x, y);
                if(x == x1 && y == y1){
                    cube = Cube.Vide;
                }
                x_haut = debut_zone_haut + (taille_base_pyramide - 1 - x) * (taille_cube + taille_cube / 10);
                y_haut = debut_zone_gauche + x * (taille_cube + taille_cube / 10) / 2
                        + (taille_cube + taille_cube / 10) * (y);
                p = new Point(y_haut, x_haut);
                tab_pts[x][y] = p;
                switch (cube) {
                    case Noir:
                        drawable.drawImage(noir, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Neutre:
                        drawable.drawImage(neutre, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Blanc:
                        drawable.drawImage(blanc, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vert:
                        drawable.drawImage(vert, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Jaune:
                        drawable.drawImage(jaune, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Rouge:
                        drawable.drawImage(rouge, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Bleu:
                        drawable.drawImage(bleu, y_haut, x_haut, taille_cube, taille_cube, null);
                        break;
                    case Vide:
                        drawable.setColor(Color.WHITE);
                        drawable.drawRect(y_haut, x_haut, taille_cube, taille_cube);
                        drawable.drawRect(y_haut+1, x_haut+1, taille_cube-2, taille_cube-2);
                        drawable.drawRect(y_haut+2, x_haut+2, taille_cube-4, taille_cube-4);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public void updateJoueurLabel(){
        String languageCode = Global.Config.getLanguage();
        String text = null;
        switch (languageCode) {
            case "FR":
                text = "Joueur ";
                break;
            case "EN":
                text = "Player ";
                break;
        }
        joueurLabel.setText(text + (jeu.get_player()+1));
        switch(jeu.get_player()){
            case 0:
                joueurLabel.setForeground(new Color(51,153,255));
                break;
            case 1:
                joueurLabel.setForeground(new Color(255,0,0));
                break;
        }
    }
    
    // Retourne la couleur du cube de la pioche sur lequel on vient de cliquer
    // public Cube GetCubeChope(int x, int y)
    // {
    //     return 
    // }

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
}
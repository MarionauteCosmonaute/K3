package View;

import Model.Jeu;
import Model.Player;
import Global.FileLoader;


import javax.swing.*;
import java.awt.*;
import java.util.Collections;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.io.IOException;

public class PDJPyramideJoueur extends JComponent {
    int width_fenetre, height_fenetre, nb_ligne, nb_colonne, largeur_case, hauteur_case;
    Graphics2D drawable;
    Jeu jeu;
    int joueur;
    static boolean cube_selec;
    int x_selec;
    int y_selec;
    static boolean charge;

    JPanel parent;

    // Image gifIcon1, gifIcon2, gifIcon3, gifIcon4;
    // static Timer timer;
    // static boolean bool_sablier = false;
    // int imageIndex = 0, compteur_image = 0;
    // Image[] images;

    public PDJPyramideJoueur(Jeu jeu, JPanel parent, int nj) {
        this.jeu = jeu;
        this.parent = parent;
        joueur = nj;
        setOpaque(false);
        cube_selec = false;

        // try
        // {
        //     gifIcon1 = FileLoader.getImage("res/sablier1.png").getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        //     gifIcon2 = FileLoader.getImage("res/sablier2.png").getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        //     gifIcon3 = FileLoader.getImage("res/sablier3.png").getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        //     gifIcon4 = FileLoader.getImage("res/sablier4.png").getScaledInstance(40, 30, Image.SCALE_SMOOTH);

        //     images = new Image[4];
        //     images[0] = gifIcon1;
        //     images[1] = gifIcon2;
        //     images[2] = gifIcon3;
        //     images[3] = gifIcon4;
        //     // gifLabel = new JLabel(gifIcon);
        // }
        // catch (IOException e) 
        // {
        //     System.exit(1);
        // }

        // timer = new Timer(500, new ActionListener()
        //             {
        //                 @Override
        //                 public void actionPerformed(ActionEvent e) {
        //                     System.out.println("Tic tac");
        //                     // Changer l'image
        //                     imageIndex = compteur_image % 4;
        //                     compteur_image++;
        //                     System.out.println("imageIndex: "+ imageIndex);
        //                     repaint();
        //                 }
        //             });
    }
    // Retourne le tableau qui contient les coordonnées des points de la pyramide du joueur
    public Point[][] PointPyramideJoueurs(int joueur){
        return StructurePainter.PointPyramideJoueurs(joueur);
    }

    public Point[] PointSide(){
        return StructurePainter.PointSide();
    }

    // Retourne la taille des cubes des joueurs
    public int TailleCubeJoueur(){
        return StructurePainter.TailleCubeJoueur();
    }

    // Retourne le nombre de joueur présent dans la partie
    public int NombreDeJoueur(){
        return jeu.nbJoueur();
    }
    
    public int NumeroJoueur(){
        return joueur;
    }

    public int tailleSide(){
        return jeu.getPlayer(joueur).getSideSize();
    }
    
    // public static void AfficheGif()
    // {
    //     bool_sablier = true;
    //     timer.start();
    // }

    // public static void CacheGif()
    // {
    //     timer.stop();
    //     bool_sablier = false;
    // }

    public static void SetCube_Select_Static(boolean bool)
    {
        cube_selec = bool;
    }

    public void SetX_Select(int x)
    {
        x_selec = x;
    }

    public void SetY_Select(int y)
    {
        y_selec = y;
    }

    public void paintComponent(Graphics g) {

        System.out.println("PaintComponent de PDJPyramideJoueur");
        drawable = (Graphics2D) g;
        width_fenetre = parent.getWidth();
        height_fenetre = parent.getHeight();
        setSize(width_fenetre, height_fenetre);
        StructurePainter.dessiner_pyramide(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getPyramid(), jeu.getPlayer(joueur).getSideSize() > 0, joueur);
        StructurePainter.dessiner_side(g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSide());

        // if ((joueur+1) == 2 && bool_sablier)
        // {
        //     System.out.println("Deesin sablier : " + imageIndex);
        //     int taille_sablier = Math.min(80 * height_fenetre / (100 * 6), 80 * width_fenetre / (100 * 6))/2;
        //     drawable.drawImage(images[imageIndex], width_fenetre - 2*taille_sablier, taille_sablier, taille_sablier, taille_sablier, null);
        //     // drawable.drawImage(gifIcon1, width_fenetre - 2*taille_sablier, taille_sablier, taille_sablier, taille_sablier, null);
        // }

        switch(joueur){
            case 0:
                drawable.setColor(Color.BLUE);
            break;
            case 1:
                drawable.setColor(Color.RED);
            break;
        }
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre/10,width_fenetre/10)));
        String languageCode = Global.Config.getLanguage();
        switch(languageCode){
            case "FR":
                drawable.drawString("Joueur "+(joueur+1), 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;

            case "EN":
                drawable.drawString("Player "+(joueur+1), 5, Math.min(height_fenetre/10,width_fenetre/10));
                break;
        }

        drawable.setColor(Color.BLACK);
        drawable.setFont(new Font("Default", Font.BOLD, Math.min(height_fenetre/10,width_fenetre/10)/3));
        if(jeu.getPenality()){
            if(jeu.get_player() == joueur){
                switch(languageCode){
                case "FR":
                    drawable.drawString("Pénalité, votre adversaire va vous prendre un cube", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                case "EN":
                    drawable.drawString("Penalty, your opponent will take one of your pawn", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                }
            }else{
                switch(languageCode){
                case "FR":
                    drawable.drawString("Pénalité, prenez un cube de votre adversaire", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                case "EN":
                    drawable.drawString("Penalty, take one pawn from your opponent", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                }  
            }
        }else{
            if((jeu.get_player() == joueur)){
                switch(languageCode){
                case "FR":
                    drawable.drawString("A votre tour de jouer", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                case "EN":
                    drawable.drawString("Your turn to play", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                
                }
            }else{
                switch(languageCode){
                case "FR":
                    drawable.drawString("Au tour de votre adversaire", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                case "EN":
                    drawable.drawString("Wait for your opponent to play", 5, Math.min(height_fenetre/10,width_fenetre/10)*2);
                    break;
                }             
            }
        
        }
        if(joueur == jeu.get_player()){
            StructurePainter.Contour_Accessible_Joueur(joueur, jeu, g, height_fenetre, width_fenetre, jeu.getPlayer(joueur).getSideSize() > 0);
            if (cube_selec)
            {
                StructurePainter.DessineSelectionne(joueur, jeu, drawable, height_fenetre, width_fenetre, x_selec, y_selec, jeu.getPlayer(joueur).getSideSize() > 0);
            }
        }
    }

    void build(Player player){
        Collections.shuffle(player.getPersonalBag());
        for(int i = player.getSize()-1; i >= 0; i--){
            for(int j = 0; j < player.getSize()-i; j++){
                //System.out.println( i + " " + j + " " + "0" );
                if(!player.bagEmpty()){player.construction(j, i, player.getPersonalBag().get(0));}
            }
        }
    }
}

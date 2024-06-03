package Vue;
import javax.imageio.ImageIO;
import javax.swing.*;

import Modele.Jeu;
import Modele.Niveau;
import Modele.NoLoadedLevelException;
import Patterns.Observateur;

import java.awt.*;
import java.io.*;

public class NiveauGraphique extends JComponent implements Observateur{
    Image But;
    Image Caisse_sur_but;
    Image Caisse;
    Image Mur;
    Image Pousseur;
    Image Sol;
    
    Jeu J;
    Boolean W=false;
    Graphics2D drawable;

    Point Last_click=new Point(0,0);
    Boolean OC=false;
    int click_x,click_y;

    int width;
    int height;
    Point center;
    int l;
    int c;
    int Size;

    
    public NiveauGraphique(Jeu j){
        load_files("res/Images/");
        J=j;
    }
    void setLC(int x,int y){
        Last_click= new Point(x,y);
    }

    void load_files(String path){
        try{
            InputStream in_But,in_Caisse_sur_but,in_Caisse,in_Mur,in_Pousseur,in_Sol;
            in_But=new FileInputStream(path.concat("But.png"));
            in_Caisse_sur_but=new FileInputStream(path.concat("Caisse_sur_but.png"));
            in_Caisse=new FileInputStream(path.concat("Caisse.png"));
            in_Mur=new FileInputStream(path.concat("Mur.png"));
            in_Pousseur=new FileInputStream(path.concat("Pousseur.png"));
            in_Sol=new FileInputStream(path.concat("Sol.png"));
            But=ImageIO.read(in_But);
            Caisse_sur_but=ImageIO.read(in_Caisse_sur_but);
            Caisse=ImageIO.read(in_Caisse);
            Mur=ImageIO.read(in_Mur);
            Pousseur=ImageIO.read(in_Pousseur);
            Sol=ImageIO.read(in_Sol);
        }catch(IOException e){
            System.err.println("ERREUR : Impossible de charger les images");
        }
    }
    void drawLevel(Graphics2D drawable,Niveau currentLevel){
        drawable.clearRect(0,0,width,height);
        for (int i=0;i<l;i++){
            for (int j=0;j<c;j++){
                switch (currentLevel.grille()[i][j]){
                    case Niveau.VIDE:
                        drawable.drawImage(Sol,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.MUR:
                        drawable.drawImage(Sol,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        drawable.drawImage(Mur,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.BUT:
                        drawable.drawImage(But,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.CAISSE:
                        drawable.drawImage(Sol,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        drawable.drawImage(Caisse,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.POUSSEUR:
                        drawable.drawImage(Sol,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        drawable.drawImage(Pousseur,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.POU_BUT:
                        drawable.drawImage(But,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        drawable.drawImage(Pousseur,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    case Niveau.CA_BUT:
                        drawable.drawImage(Sol,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        drawable.drawImage(Caisse_sur_but,center.x+(Size*(j-c/2)),center.y+(Size*(i-l/2)),Size,Size,null);
                        break;
                    default:
                        System.err.println("ERREUR: Case inconnue");
                        System.exit(ABORT);
                        break;
                }
            }
        }
    }

    public void Update(){
        System.out.println(J.niveau());
        repaint();
        if(W){
            System.out.println("GAGNE");
            W=false;
            if(!J.prochainNiveau()){
                System.out.println("FIN DES NIVEAUX");
                System.exit(0);
            }
        }
        if(J.Win()){
            W=true;
        }
    }

    void ReloadLevel(){
        Niveau currentLevel=J.niveau();
        J.reset_Level();
        drawLevel(drawable,currentLevel);
        repaint();
    }
    void OnClick(){
        Niveau currentLevel=J.niveau();
        click_x=-1;
        click_y=-1;
        for(int i=0;i<l;i++){
            for(int j=0;j<c;j++){
                if(Last_click.getX()>=center.x+(Size*(j-c/2)) && Last_click.getX()<center.x+(Size*(j-c/2))+Size){
                    click_y=j;
                }
                if(Last_click.getY()>=center.y+(Size*(i-l/2)) && Last_click.getY()<center.y+(Size*(i-l/2))+Size){
                    click_x=i;
                }
            }
        }
        System.out.println(click_x+" , "+click_y);
        System.out.println(currentLevel.toString());
        J.move_Pousseur(currentLevel.getPousseur().x,currentLevel.getPousseur().y, click_x, click_y,true);
    }

    @Override
    public void paintComponent(Graphics g){
        drawable =(Graphics2D) g;
        Niveau currentLevel=null;

        try{
            currentLevel=J.niveau();
        }catch(NoLoadedLevelException e){
            if (!J.prochainNiveau()){
                System.err.println("ERREUR : Aucun niveau dans ce jeu");
                System.exit(ABORT);
            }
            currentLevel=J.niveau();
        }

        width = getSize().width;
        height = getSize().height;
        center = new Point(width/2, height/2);
        l=currentLevel.max_l();
        c=currentLevel.max_c();
        Size=Integer.min(width/(c+1), height/(l+1));

        drawLevel(drawable, currentLevel);
        
    }
}

package Vue;


import Controleur.ControleurClavierSouris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class EcouteurDeClavier implements KeyListener {
    static final int Q=81;
    static final int R=82;
    static final int S=83;
    static final int T=84;
    static final int ESC=27;
    static final int UP=38;
    static final int DOWN=40;
    static final int LEFT=37;
    static final int RIGHT=39;
    static final int PGDN=34;

    ControleurClavierSouris C;

    public EcouteurDeClavier(ControleurClavierSouris c){
        C=c;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case UP:
                System.out.println("up");
                C.toucheClavier("up");
                //G.J.move_Pousseur(G.J.niveau().getPousseur().x,G.J.niveau().getPousseur().y,
                //                    G.J.niveau().getPousseur().x-1 ,G.J.niveau().getPousseur().y);
                //G.UpdateLevel();
                break;
            case DOWN:
                System.out.println("down");
                C.toucheClavier("down");
                //G.J.move_Pousseur(G.J.niveau().getPousseur().x,G.J.niveau().getPousseur().y,
                //                    G.J.niveau().getPousseur().x+1 ,G.J.niveau().getPousseur().y);
                //G.UpdateLevel();
                break;
            case LEFT:
                System.out.println("left");
                C.toucheClavier("left");
                //G.J.move_Pousseur(G.J.niveau().getPousseur().x, G.J.niveau().getPousseur().y,
                //                    G.J.niveau().getPousseur().x ,G.J.niveau().getPousseur().y-1);
                //G.UpdateLevel();
                break;
            case RIGHT:
                System.out.println("right");
                C.toucheClavier("right");
                //G.J.move_Pousseur(G.J.niveau().getPousseur().x, G.J.niveau().getPousseur().y,
                //                    G.J.niveau().getPousseur().x ,G.J.niveau().getPousseur().y+1);
                //G.UpdateLevel();
                break;
            case T:
                C.toucheClavier("Run");
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        switch( e.getKeyCode()){
            case PGDN:
                System.out.println("skipped");
                C.toucheClavier("next");
                //if(!G.J.prochainNiveau()){
                //    System.out.println("FIN DES NIVEAUX");
                //    System.exit(0);
                //}
                //G.UpdateLevel();
            break;
            case Q:
                System.out.println("exiting");
                C.toucheClavier("quit");
                //System.exit(0);
                break;
            case R:
                System.out.println("reloading");
                C.toucheClavier("r");
                //G.ReloadLevel();
                break;

            case ESC:
                System.out.println("switching window config");
                C.toucheClavier("fs");
                break;
            case S:
                System.out.println("IA moment");
                C.toucheClavier("IA");
                break;
            default:
                break;
            
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}


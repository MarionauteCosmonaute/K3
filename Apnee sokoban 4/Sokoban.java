import javax.swing.SwingUtilities;

import Vue.InterfaceGraphique;


public class Sokoban{

    public static void main(String[] args) {
        if (args.length>0){
            SwingUtilities.invokeLater(new InterfaceGraphique("res/Grille/"+args[0]));
        }else{
            SwingUtilities.invokeLater(new InterfaceGraphique("res/Grille/Original.txt"));
        }
        
    }
}



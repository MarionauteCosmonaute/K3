package View;

import Model.Jeu;
import javax.swing.*;
import java.awt.*;

public class MenuPhaseConstruction extends Menu {
    AffichagePhaseConstruction apc=null;
    public MenuPhaseConstruction(CollecteurEvenements controle, Jeu jeu) {
        super();
        JPanel content = new JPanel(new BorderLayout());
        JPanel pc = new JPanel(new BorderLayout());
        OldPhaseConstruction cons = new OldPhaseConstruction(pc, controle, jeu);
        apc = new AffichagePhaseConstruction(jeu, cons);
        apc.addMouseListener(new AdaptateurSouris(controle, apc));
        pc.add(apc);
        content.add(pc);
        //content.add(apc);
        content.setVisible(true);
        content.setOpaque(false);
        pc.setOpaque(false);
        add(content);
        controle.addMenu(this);
    }
    public AffichagePhaseConstruction getOldPhaseConstruction(){
        return apc;
    }

}
package View;

import Model.Jeu;
import javax.swing.*;
import java.awt.*;

public class MenuPhaseConstruction extends Menu {

    public MenuPhaseConstruction(CollecteurEvenements controle, Jeu jeu) {
        super();
        JPanel content = new JPanel(new BorderLayout());
        JPanel pc = new JPanel(new BorderLayout());
        OldPhaseConstruction cons = new OldPhaseConstruction(pc, controle, jeu);
        AffichagePhaseConstruction apc = new AffichagePhaseConstruction(jeu, cons);
        apc.addMouseListener(new AdaptateurSouris(controle, apc));
        content.add(apc);
        content.setVisible(true);
        content.setOpaque(false);
        pc.setOpaque(false);
        add(content);
        controle.addMenu(this);
    }

}
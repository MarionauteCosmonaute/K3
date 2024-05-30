package View.Menu;

import View.CollecteurEvenements;
import View.OldPhaseConstruction;
import View.AffichagePhaseConstruction;

import Model.Jeu;
import View.Adaptateurs.AdaptateurSouris;
import javax.swing.*;
import java.awt.*;

public class MenuPhaseConstruction extends Menu {
    AffichagePhaseConstruction apc=null;
    OldPhaseConstruction cons=null;
    public MenuPhaseConstruction(CollecteurEvenements controle, Jeu jeu) {
        super();
        JPanel content = new JPanel(new BorderLayout());
        JPanel pc = new JPanel(new BorderLayout());
        OldPhaseConstruction cons = new OldPhaseConstruction(pc, controle, jeu);
        apc = new AffichagePhaseConstruction(jeu, cons);
        apc.addMouseListener(new AdaptateurSouris(controle, apc));
        pc.add(apc);
        content.add(pc);
        content.setVisible(true);
        content.setOpaque(false);
        pc.setOpaque(false);
        add(content);
    }
    public AffichagePhaseConstruction getAffichagePhaseConstruction(){
        return apc;
    }
    
    @Override
    public void updateLanguageCode() {
        getOldPhaseConstruction().updateLanguageCode();

    }
    private OldPhaseConstruction getOldPhaseConstruction() {
        return cons;
    }

    @Override
    public void reset(){
        apc.resetBooleans();
    }

    @Override
    public void setValider(boolean b){
        apc.setValider(b);
    }
}
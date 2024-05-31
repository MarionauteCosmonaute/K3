package View;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Global.FileLoader;
import View.Adaptateurs.AdaptateurSon;
import View.Adaptateurs.SourisAdapte;

public class BoutonUnMute extends JButton{
    Image resizedImageUnMute = null;
	Image resizedImageMute = null;
    ImageIcon iconUnMute =null;
    ImageIcon iconMute=null; 
	public BoutonUnMute(CollecteurEvenements controle, int sombre){
		SourisAdapte sourisUnMute = null;
		try {
			sourisUnMute = new SourisAdapte(this, FileLoader.getSound("res/clic.wav"));
			if(sombre == 1){
				resizedImageUnMute = Global.FileLoader.getImage("res/son64_blanc.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
				resizedImageMute = Global.FileLoader.getImage("res/mute64_blanc.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
			}
			else {
				resizedImageUnMute = Global.FileLoader.getImage("res/son64.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
				resizedImageMute = Global.FileLoader.getImage("res/mute64.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
			}
		} catch (Exception e) {
			System.exit(1);
		}
		iconUnMute  = new ImageIcon(resizedImageUnMute);
		iconMute = new ImageIcon(resizedImageMute);
		if (Global.Config.isPlaying){
			setIcon(iconUnMute);
		}else{
		    setIcon(iconMute);
		}
		// Ajoute tous les listeners
		addMouseListener(sourisUnMute);
		addActionListener(new AdaptateurSon(controle));
		// change image
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setFocusable(false);
		
	}
    @Override
    public void repaint(){
        if (Global.Config.isPlaying){
			setIcon(iconUnMute);
		}else{
		    setIcon(iconMute);
		}
    }

}
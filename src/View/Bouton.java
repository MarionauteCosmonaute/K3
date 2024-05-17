package View;

import javax.swing.*;
import javax.swing.border.LineBorder;

import Global.FileLoader;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bouton {
	public static JButton creerButton(String text) {
		BoutonArrondi bouton = new BoutonArrondi(text, 20);
		Font police = new Font("Arial", Font.BOLD, 14);
		bouton.setFont(police);
		bouton.setBorder(new LineBorder(Color.BLACK, 2));
		bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
		bouton.setFocusable(false);
		return bouton;
	}

	@Deprecated
	public static JButton BoutonMute() {
		ImageIcon iconMute = new ImageIcon("res/son.png");
		Image resizedImageMute = iconMute.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
		iconMute = new ImageIcon(resizedImageMute);
		return new JButton(iconMute);
	}

	public static JButton BoutonUnMute(CollecteurEvenements controle) {
		Image resizedImageUnMute = null;
		Image resizedImageMute = null;
		JButton out = new JButton();
		SourisAdapte sourisUnMute = null;
		try {
			sourisUnMute = new SourisAdapte(out, FileLoader.getSound("res/clic.wav"));
			resizedImageUnMute = Global.FileLoader.getImage("res/mute64.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
			resizedImageMute = Global.FileLoader.getImage("res/son64.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
		} catch (Exception e) {
			System.exit(1);
		}
		ImageIcon iconUnMute = new ImageIcon(resizedImageUnMute);
		ImageIcon iconMute = new ImageIcon(resizedImageMute);
		out.setIcon(iconMute);
		// Ajoute tous les listeners
		out.addMouseListener(sourisUnMute);
		out.addActionListener(new AdaptateurSon(controle));
		// change image
		out.addActionListener(new ActionListener() {
			private boolean isMuted = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMuted) {
					out.setIcon(iconMute); // Changer en icône "son"
				} else {
					out.setIcon(iconUnMute); // Changer en icône "mute"
				}

				isMuted = !isMuted; // Inverser l'état
			}
		});
		out.setBorder(BorderFactory.createEmptyBorder());
		out.setContentAreaFilled(false);
		return out;
	}

	public static JButton BoutonRetour() {
		JButton out = new JButton();
		Image resizedImageRetour = null;
		SourisAdapte sourisRetour = null;
		try {
			sourisRetour = new SourisAdapte(out, FileLoader.getSound("res/clic.wav"));
			resizedImageRetour = Global.FileLoader.getImage("res/retour.png").getScaledInstance(40, 30,
					Image.SCALE_SMOOTH);
		} catch (Exception e) {
			System.exit(1);
		}
		out.setIcon(new ImageIcon(resizedImageRetour));
		out.addMouseListener(sourisRetour);
		out.setBorder(BorderFactory.createEmptyBorder());
		out.setContentAreaFilled(false);
		return out;
	}
}
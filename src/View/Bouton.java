package View;

import View.Adaptateurs.*;

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

	public static JButton Rules(Component content){
		JButton out;
		out = Bouton.creerButton("Règles");
		out.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String languageCode = Global.Config.getLanguage();
				String rules = null;
				String title = null;
				switch (languageCode) {
					case "FR":
						title = "Règles du jeu";
						rules = "DÉROULEMENT DU JEU\n" +
						"À votre tour, vous devez jouez un des pions de votre pyramide. Vous pouvez :\n" +
						" -> Jouer un pion coloré et le placer sur le K3.\n" +
						" -> Jouer un pion naturel et le placer sur le K3. Un pion naturel est considéré comme un joker et peut remplacer n'importe quelle couleur.\n"
						+
						" -> Jouer un pion blanc : vous le retirez alors de votre pyramide, et passez votre tour. Chaque pion blanc n’est utilisable qu’une fois par partie.\n"
						+
						" -> Vous pouvez jouer un pion uniquement s'il est accessible.\n" +
						" -> Un pion est toujours placé à cheval sur deux autres pions du K3.\n" +
						" -> Un pion coloré doit toujours être placé sur au moins un pion de la même couleur, ou un pion naturel. Un pion naturel peut être placé sur n’importe quel pion.\n"
						+
						" Attention : placer un pion sur deux pions de la même couleur (colorés ou naturels) entraîne une pénalité.\n"
						+
						"\n" +
						"PENALITÉ\n" +
						"Jouer votre pion sur deux pions de la même couleur entraîne une pénalité. Le joueur qui vous précède récupère un de vos pions accessibles au choix et le place à côté de sa propre pyramide.\n"
						+
						"Il fait partie de ses pions accessibles pour le reste de la partie et pourra être joué lors d’un prochain tour de jeu, ou récupéré par un adversaire lors d’une prochaine pénalité.\n"
						+
						"Note: lors d’une partie à 4, le pion blanc à côté de la pyramide est également accessible et peut être récupéré comme pénalité.\n"
						+
						"\n" +
						"ÉLIMINATION\n" +
						"Si, lorsque vient votre tour, vous ne pouvez plus jouer, vous êtes éliminé de la partie.\n"
						+
						"Lors d’une partie à 3, le joueur précédant la première personne éliminée récupère le pion blanc écarté en début de jeu. Ce pion est placé à côté de sa pyramide et pourra être joué lors d’un prochain tour.";
						break;
					case "EN" :
						title = "Game rules";
						rules = "HOW TO PLAY\n" +
						"On your turn, you must take a pawn from your pyramid. You can :\n" +
						" -> Take a coloured pawn and place it on K3.\n" +
						" -> ake a natural pawn and place it on K3. It is wild and represents any color.\n"
						+
						" -> Take a white pawn: remove it from yourpyramid, set it aside, and end your turn. Each white pawn can only be used once per game.\n"
						+
						" -> You can take a pawn from your pyramid if it is accessible, i.e. if no other pawn is on top of it.\n" +
						" -> Pawns you place on K3 must always sit on top of 2 other pawns.\n" +
						" -> Coloured pawns must always be placed Coloured pawns must always be placed colour, or a natural pawn. A natural pawn can be placed on top of any pawn.\n"
						+
						" Careful : placing a pawn on top of 2 pawns of the same colour (coloured or natural) results in a penalty (see Penalty).\n"
						+
						"\n" +
						"PENALTY\n" +
						"Placing your pawn on 2 pawns of the same colour results in a penalty. The next player clockwise who has not yet been eliminated chooses one of your accessible pawns and places it next to their pyramid.\n"
						+
						"It becomes part of their accessible pawns for the rest of the game and can be placed on K3 during a future turn, or taken by an opponent to resolve a future penalty.\n"
						+
						"Note:  in a 4-player game, the white pawn next to your pyramid is also accessible and can be taken to resolve a penalty.\n"
						+
						"\n" +
						"ELIMINATION\n" +
						"On your turn, if you can no longer play, you are eliminated from the game.\n"
						+
						"In a 3-player game, the player to the right of the first person eliminated takes the white pawn that was set aside at the beginning of the game. ";
						break;
				}
				JOptionPane.showMessageDialog(content, rules, title, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		return out;
	}
}
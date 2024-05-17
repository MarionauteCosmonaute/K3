package View;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import Global.*;

public class MusicPlayer extends JFrame {
    private static final String MUSIC_PATH = "res/jazz.wav";
    private boolean isPlaying = false;
    Clip audioClip;

    public MusicPlayer() {
        try {
            // Charger le fichier audio
            audioClip = FileLoader.getSound(MUSIC_PATH);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMusic() {
        audioClip.start();
    }

    public void stopMusic() {
        audioClip.stop();
    }

    public void jouerMusique() {
        if (isPlaying) {
            stopMusic();
        } else {
            playMusic();
        }
        isPlaying = !isPlaying;
    }

}

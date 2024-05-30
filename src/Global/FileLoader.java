package Global;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;


public class FileLoader {
    static HashMap<String,Clip> openedSoundMap=new HashMap<>();
    static HashMap<String,Image> openedImageMap=new HashMap<>();

    public static Clip getSound(String path)throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (openedSoundMap.containsKey(path)){
            return openedSoundMap.get(path);
        }else{
            try {
                File audioFile = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
                openedSoundMap.put(path, audioClip);
                return audioClip;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static Image getImage(String path) throws IOException{
        if( openedImageMap.containsKey(path)){
            return openedImageMap.get(path);
        }else{
            try{
                File imageFile=new File(path);
                Image out = new ImageIcon(ImageIO.read(imageFile)).getImage();
                openedImageMap.put(path, out);
                return out;
            }catch (IOException e){
                e.printStackTrace();
                throw e;
            }
        }
    }
}

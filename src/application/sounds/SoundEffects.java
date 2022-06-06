package application.sounds;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Line;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

public class SoundEffects
{
    
    
    public void playSoundEffect(final String fileName) {
        try {
            final File yourFile = new File(fileName);
            final AudioInputStream stream = AudioSystem.getAudioInputStream(yourFile);
            final AudioFormat format = stream.getFormat();
            final DataLine.Info info = new DataLine.Info(Clip.class, format);
            final Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
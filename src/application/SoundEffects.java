package application;

import java.io.*;
import javax.sound.sampled.*;

public class SoundEffects {
	private Clip clip;
	public void playSound(String filename) {
		try {
		    File yourFile = new File (filename);
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		
		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		    
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	public void stopSound() {
			clip.close();
		}
	
}
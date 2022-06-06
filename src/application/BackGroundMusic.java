package application;

import java.io.*;
import javax.sound.sampled.*;

public class BackGroundMusic {
	private Clip clip;
	public void playSound() {
		try {
		    File yourFile = new File ("/Users/joe/Documents/EclipseProjects/JavaFXGame/src/application/The Lion King  Hakuna Matata.wav");
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		
		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		    LineListener listener = new LineListener() {
		        public void update(LineEvent event) {
		            if (event.getType() == LineEvent.Type.STOP) {
		                clip.setFramePosition(0);
		                clip.start();
		            }
		        }
		    };
			clip.addLineListener(listener);
		    
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	public void stopSound() {
			clip.close();
		}
	
}
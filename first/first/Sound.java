package first;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	Clip clip;
	FileInputStream input;
	public Sound(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException
	{
		File file = new File(fileName);
		AudioInputStream sound = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(sound);
	}
	public void play()
	{
		clip.setFramePosition(0);
		clip.start();
	}
	public void stop()
	{
		clip.stop();
	}
	public void loopContinuously()
	{
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void loop(int numLoops)
	{
		clip.loop(numLoops);
	}
	public void close()
	{
		clip.close();
	}
}

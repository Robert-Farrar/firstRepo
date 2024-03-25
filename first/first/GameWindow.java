package first;


import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class GameWindow {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		JFrame gameWin = new JFrame();
		gameWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWin.setLocationRelativeTo(null);
		gameWin.setTitle("Disk Space");
		gameWin.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		gameWin.add(gamePanel);
		
		gameWin.pack();
		gameWin.setVisible(true);
		
		gamePanel.startGameThread();

	}

}

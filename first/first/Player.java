package first;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Player extends GameObject implements KeyListener{
	private GamePanel gamePanel;
	BufferedImage image;
	ArrayList<BufferedImage> deadImage;
	BufferedImage img;
	BufferedImage test;
	Animator death;
	private boolean dead;
	SpriteSheet ss;
	int count;
	Sound sound;
	Sound sound2;
	public Player(GamePanel gamePanel) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.gamePanel=gamePanel;
		this.setColor(Color.BLUE);
		this.setWidth(93);
		this.setHeight(125);
		this.setSpeed(6);
		this.setX((1200 - this.getHeight()) / 2);
		this.setY((700 - this.getWidth()));
		image = ImageIO.read(new File("ship.png"));
		img = ImageIO.read(new File("Ship Burning.png"));
		deadImage = new ArrayList<BufferedImage>();
		sound = new Sound("laser.wav");
		sound2 = new Sound("spaceship death.wav");
		this.dead = false;
		
			
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void dead() {
		int x = 0;
		int y = 0;
		ss = new SpriteSheet(img);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
		test = ss.grabSprite(x + j, y + i, 640, 640);
		this.deadImage.add(test);
			}
		}
		this.setSpeed(0);
		death = new Animator(deadImage, 22, true, true);
		death.setSpeed(100);
		death.start();
		sound2.play();
		
			
	}
	
	
	
	@Override
	public void update() {
		
		
	}

	@Override
	public void draw(Graphics g) {
		if(this.isDead() == false)
		{
		g.drawImage(image, this.getX() - 254, this.getY() - 275, null);	
		}
		else {
			death.update(System.currentTimeMillis());
			g.drawImage(death.sprite,this.getX() - 254, this.getY() - 275, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 83)
		{
			gamePanel.player.setY(this.getY() + getSpeed());
		}
		if(e.getKeyCode() == 87)
		{
			gamePanel.player.setY(this.getY() - getSpeed());
		}
		if(e.getKeyCode() == 65)
		{
			gamePanel.player.setX(this.getX() - getSpeed());
			if(e.getKeyCode() == 65 && e.getKeyCode() == 68)
			{
				gamePanel.player.setX(this.getX() + getSpeed());
			}
		}
		if(e.getKeyCode() == 68)
		{
			gamePanel.player.setX(this.getX() + getSpeed());
			if(e.getKeyCode() == 65 && e.getKeyCode() == 68)
			{
				gamePanel.player.setX(this.getX() - getSpeed());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==32) {
			if(this.isDead() == false)
			{
			gamePanel.missle.setHit(false);
			gamePanel.missle.setX(this.getX() + 54);
			gamePanel.missle.setY(this.getY() - 39);
			sound.play();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

package first;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Cannon extends GameObject{
	BufferedImage image;
	BufferedImage img;
	BufferedImage img2;
	BufferedImage img3;
	SpriteSheet ss;
	SpriteSheet ss2;
	private boolean complete;
	Animator entry;
	Animator death;
	ArrayList <BufferedImage> entryList;
	ArrayList <BufferedImage> deathList;
	Laser laser;
	Random rng;
	int count;
	int chance;
	int rand;
	int secondChance;
	private boolean fired;
	private GamePanel gamePanel;
	Sound sound;
	public Cannon(int x, int y, GamePanel gamePanel) throws IOException, UnsupportedAudioFileException, LineUnavailableException
	{
		this.setX(x);
		this.setY(y);
		image = ImageIO.read(new File("Cannon.png"));
		img = ImageIO.read(new File("Cannon Spawn.png"));
		img2 = ImageIO.read(new File("Cannon Death.png"));
		entryList = new ArrayList<BufferedImage>();
		deathList = new ArrayList<BufferedImage>();
		this.rng = new Random();
		this.gamePanel = gamePanel;
		sound = new Sound("Boss Laser.wav");
	}
	
	public void update()
	{
		count++;
		if(count == 60)
		{
			count = 0;
		}
		if(count == 0 && this.complete == true && gamePanel.laser.isDead() == false)
		{
			chance = rng.nextInt(2);
			if(chance == 0)
			{
				this.fired = true;
				gamePanel.laser.setX(this.getX() + 150);
				gamePanel.laser.setY(this.getY() + 255);
				sound.play();
				rand = rng.nextInt(10);
				this.secondChance = rng.nextInt(2);
			}
		}
		if(this.fired == true && secondChance == 0)
		{
			gamePanel.laser.setX(gamePanel.laser.getX() + rand);
			gamePanel.laser.setY(gamePanel.laser.getY() + gamePanel.laser.getSpeed());
		}
		else if(this.fired == true && secondChance == 1)
		{
			rand = rng.nextInt(5);
			if(gamePanel.laser.getX() - gamePanel.player.getX() < 0)
			{
				gamePanel.laser.setX(gamePanel.laser.getX() + (gamePanel.laser.getSpeed() /2));
			}
			else if(gamePanel.laser.getX() - gamePanel.player.getX() > 0)
			{
				gamePanel.laser.setX(gamePanel.laser.getX() - (gamePanel.laser.getSpeed()/2));
			}
			else
			{
				gamePanel.laser.setX(gamePanel.laser.getX());
			}
			if(gamePanel.laser.getY() - gamePanel.player.getY() < 0)
			{
				gamePanel.laser.setY(gamePanel.laser.getY() + gamePanel.laser.getSpeed());
			}
			else if(gamePanel.laser.getY() - gamePanel.player.getY() > 0)
			{
				gamePanel.laser.setY(gamePanel.laser.getY() - gamePanel.laser.getSpeed());
			}
			else
			{
				gamePanel.laser.setY(gamePanel.laser.getY());
			}
		}
		
		
	}
	public void dead()
	{
		gamePanel.laser.dead();
		this.setDead(true);
		ss2 = new SpriteSheet(img2);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				img3 = ss2.grabSprite(j, i, 320, 320);
				this.deathList.add(img3);
			}
		}
		this.death = new Animator(deathList, 54, true, false);
		this.death.setSpeed(100);
		this.death.start();
	}
	public void draw(Graphics g)
	{
		if(this.complete == false)
		{
			
			entry.update(System.currentTimeMillis());
			this.complete = entry.isComplete();
			g.drawImage(entry.sprite, this.getX(), this.getY(), null);		
		}
		else if(this.isDead() == true)
		{
			death.update(System.currentTimeMillis());
			g.drawImage(death.sprite, this.getX(), this.getY(), null);
		}
		else
		{
		g.drawImage(image, this.getX(), this.getY(), null);
		}
	}
	public void spawn(int x, int y)
	{
		this.fired = false;
		this.setX(x);
		this.setY(y);
		ss = new SpriteSheet(img);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				img3 = ss.grabSprite(j, i, 320, 320);
				entryList.add(img3);
			}
		}
		entry = new Animator(entryList, 56, true, false);
		entry.setSpeed(50);
		entry.start();
		
		
	}

}

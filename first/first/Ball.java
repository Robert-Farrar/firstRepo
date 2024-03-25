package first;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class Ball extends GameObject {
	private BufferedImage image;
	Random rng;
	int baseHealth;
	private BufferedImage img;
	SpriteSheet ss;
	ArrayList <BufferedImage>deadImage;
	private BufferedImage img2;
	Animator death;
	private boolean complete;
	Sound sound;
	public Ball(Color color, int x, int y, int width, int height, int speed, int health, boolean dead) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setSpeed(speed);
		this.setHealth(health);
		this.setDead(dead);
		image = ImageIO.read(new File("alienship1.png"));
		img = ImageIO.read(new File("Alien Ship Burning.png"));
		deadImage = new  ArrayList<BufferedImage>();
		baseHealth = health;
		rng = new Random();
		sound = new Sound("spaceship death.wav");
	}
	public void dead()
	{
		this.setDead(true);
		ss = new SpriteSheet(img);
		int x = 0;
		int y = 0;
		this.setSpeed(0);
		this.setWidth(0);
		this.setHeight(0);
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				img2 = ss.grabSprite(x + j, y + i, 320, 320);
				this.deadImage.add(img2);
			}
		}
		death = new Animator(deadImage, 14, true, false);
		death.setSpeed(100);
		death.start();
		sound.play();
	}
	
	public void respawn(int width, int height, int speed)
	{
		int spawnLocX;
		int spawnLocY;
			this.setWidth(width);
			this.setHeight(height);
			this.setSpeed(speed);
			spawnLocX = rng.nextInt(1150-10) + 10;
			spawnLocY = -250;
			this.setHealth(this.baseHealth);
			this.setDead(false);
			this.setX(spawnLocX);
			this.setY(spawnLocY);
			
	}
	@Override
	public void update() {
		this.setY(getY() + this.getSpeed());
		if(this.getY()>700) {
			this.setY(-50);
		}
	
	}

	@Override
	public void draw(Graphics g) {
		if(this.isDead() == false)
		{
		g.drawImage(image, this.getX() - 120, this.getY() - 130, null);
		
		}
		else
		{
			death.update(System.currentTimeMillis());
			this.complete = death.isComplete();
			if(this.complete == true)
			{
				respawn(59,59,5);
			}
			g.drawImage(death.sprite, this.getX() - 120, this.getY() - 130, null);
		}
	}
			
}

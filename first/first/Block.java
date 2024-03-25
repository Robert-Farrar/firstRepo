package first;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Block extends GameObject{
	BufferedImage image;
	BufferedImage image2;
	BufferedImage img;
	BufferedImage img2;
	boolean charge;
	Random rng;
	int baseHealth;
	SpriteSheet ss;
	Animator death;
	int counter;
	int previousY;
	Sound sound;
	ArrayList <BufferedImage>deadImage;
	private boolean complete;
	public Block(Color color, int x, int y, int width, int height, int speed, int health, boolean dead) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setSpeed(speed);
		this.setHealth(health);
		this.setDead(dead);
		image = ImageIO.read(new File("Eye.png"));
		image2 = ImageIO.read(new File("Eyecharge.png"));
		img = ImageIO.read(new File("Eye death.png"));		
		rng = new Random();
		baseHealth = health;
		deadImage = new ArrayList<BufferedImage>();
		sound = new Sound("Eye Death.wav");
	}
	
	public void respawn(int width, int height, int speed)
	{
		int spawnLocX;
		int spawnLocY;
			this.setWidth(width);
			this.setHeight(height);
			this.setSpeed(speed);
			spawnLocX = rng.nextInt(1100-10) + 10;
			spawnLocY = -250;
			this.setHealth(this.baseHealth);
			this.setDead(false);
			this.setX(spawnLocX);
			this.setY(spawnLocY);
			
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
	@Override
	public void update() {
		if(charge == false)
		{
		this.setY(getY() + this.getSpeed());
		if(this.getY()>700) {
			this.setY(-50);
		}
		}
		else
		{
			this.setY(getY() + (this.getSpeed() * 3));
			if(this.getY()>700) {
				this.setY(-50);
		}
		}
		if(this.getY() % 35 == 0 && charge == false)
		{
			this.previousY = this.getY();
			this.counter = rng.nextInt(10);
			if(this.counter == 2)
			{
			this.charge = true;
			}
		}
		else
		{	
			this.previousY = this.getY();
			this.counter = rng.nextInt(70);
			
				if(this.counter == 0)
				{
					this.charge = false;
				}
		}
	}

	@Override
	public void draw(Graphics g) {
		if(this.isDead() == false && this.charge == false)
		{
		g.drawImage(image, this.getX() - 121, this.getY() - 135, null);
			}
		else if(this.isDead() == false && this.charge == true)
		{
			g.drawImage(image2,this.getX() - 121, this.getY() - 135, null);
		}
		else
		{
			death.update(System.currentTimeMillis());
			this.complete = death.isComplete();
			if(this.complete == true)
			{
				respawn(75,75,2);
			}
			g.drawImage(death.sprite, this.getX() - 120, this.getY() - 130, null);
		}
	}

}

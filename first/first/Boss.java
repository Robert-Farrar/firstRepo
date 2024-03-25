package first;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Boss extends GameObject{
	
	BufferedImage image;
	BufferedImage img;
	BufferedImage img2;
	BufferedImage img3;
	SpriteSheet ss;
	SpriteSheet ss2;
	private boolean complete;
	Animator animator;
	Animator death;
	ArrayList <BufferedImage> list;
	ArrayList <BufferedImage> deathList;
	Sound sound;
	public Boss(int x, int y, int width, int height, int speed, int health) throws IOException, UnsupportedAudioFileException, LineUnavailableException
	{
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setSpeed(speed);
		this.setHealth(health);
		image = ImageIO.read(new File("boss.png"));
		img = ImageIO.read(new File("Boss Entry.png"));
		img3 = ImageIO.read(new File("Boss Death.png"));
		list = new ArrayList<BufferedImage>();
		deathList = new ArrayList<BufferedImage>();
		sound = new Sound("explosion.wav");
	}
	public void update(){
		
	}
	@Override
	public void draw(Graphics g)
	{
		if(this.complete == false)
		{
			
			animator.update(System.currentTimeMillis());
			this.complete = animator.isComplete();
			g.drawImage(animator.sprite, this.getX(), this.getY(), null);		
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
	public void dead()
	{
		this.setDead(true);
		ss2 = new SpriteSheet(img3);
		this.setSpeed(0);
		this.setWidth(0);
		this.setHeight(0);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				img2 = ss2.grabSprite(j, i, 320, 320);
				this.deathList.add(img2);
			}
		}
		this.death = new Animator(deathList, 24, true, false);
		this.death.setSpeed(100);
		this.death.start();
		sound.play();
	}
	public void spawn(int x, int y, int width, int height, int speed, int health)
	{
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setSpeed(speed);
		this.setHealth(health);
		ss = new SpriteSheet(img);
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				img2 = ss.grabSprite(j, i, 320, 320);
				list.add(img2);
			}
		}
		animator = new Animator(list, 15, true, false);
		animator.setSpeed(100);
		animator.start();
		
		
	}
}

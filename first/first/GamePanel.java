package first;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import java.util.Random;
public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = -3972033359713752978L;
	private Dimension dim;
	private Thread gameThread;
	private int framesPerSecond;
	private int killCount;
	ArrayList<GameObject> gameObjects;
	Player player;
	Missle missle;
	Random rng;
	Boss boss;
	Cannon cannon;
	Laser laser;
	private boolean spawned;
	
	public GamePanel() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.dim = new Dimension(1200,700);
		this.setPreferredSize(dim);
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.framesPerSecond=60;
		player = new Player(this);
		missle = new Missle(this);
		this.addKeyListener(player);
		rng = new Random();
		boss = new Boss(0,0,0,0,0,0);
		cannon = new Cannon(0,0,this);
		laser = new Laser(this);
		
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new Block(Color.BLUE,100,-25,75,75,2,5,false));
		gameObjects.add(new Ball(Color.GREEN,200,-25,59,59,5,3,false));
		gameObjects.add(new Block(Color.YELLOW,300,-25,75,75,2,5,false));
		gameObjects.add(new Ball(Color.RED,800,-25,59,59,5,3,false));
		gameObjects.add(new Block(Color.CYAN,900,-25,75,75,2,5,false));
		gameObjects.add(new Ball(Color.ORANGE,1000,0,59,59,5,3,false));
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.run();
	}

	@Override
	public void run() {
		DeltaTime deltaTime = new DeltaTime();
		deltaTime.start(this.framesPerSecond);
		
		
		// Main Game Loop
		while(gameThread!=null) {	
			deltaTime.update();
			
			if((deltaTime.getDelta())>=1) {
			
				update(deltaTime.getDelta());
				repaint();
				
				deltaTime.resetDelta();
			}
			
		}
		
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int index;
		GameObject gm;
		
		this.player.draw(g);
		this.missle.draw(g);
		if(killCount == 5)
		{
			this.boss.draw(g);
			this.cannon.draw(g);
			this.laser.draw(g);
		}
		for(index=0;index<gameObjects.size();index++) {
			gm = gameObjects.get(index);
			gm.draw(g);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	public void update(double delta) {
		int index;
		GameObject g;
		
		this.player.update();
		this.missle.update();
		if(killCount == 5)
		{
			for(int i = 0; i < gameObjects.size(); i++)
			{
				gameObjects.remove(i);
				
			}
			if(this.spawned == false)
			{
				this.boss.spawn(500,0,315,70,0,50);
				this.cannon.spawn(150, 0);
				this.spawned = true;
				
			}
			this.boss.update();
			this.cannon.update();
			this.laser.update();
			if(boss.getX() <= player.getX() + (player.getHeight()) 
					&& boss.getX() + ((boss.getWidth()/3)*2) >= player.getX() - (player.getHeight() / 2)
					&& boss.getY() +(boss.getHeight()/3) >= player.getY() - (player.getWidth() / 2)
					&& boss.getY() <= player.getY() + (player.getWidth() / 2))
					
					{
						
						player.setDead(true);
						player.setWidth(0);
						player.setHeight(0);
						player.dead();
						
					}
			else if((boss.getX() <= missle.getX() + (missle.getHeight()) 
					&& boss.getX() + boss.getWidth() >= missle.getX() - (missle.getHeight() / 2)
					&& boss.getY() +(boss.getHeight()) >= missle.getY() - (missle.getWidth() / 2)
					&& boss.getY() <= missle.getY() + (missle.getWidth() / 2)))
					
					{
						missle.setHit(true);
						boss.setHealth(boss.getHealth() - 1);
						if(boss.getHealth() == 0)
						{
							boss.dead();						
							cannon.dead();
						}
					}

		}
		for(index=0;index<gameObjects.size();index++) {
			g = gameObjects.get(index);
			g.update();
			
			if(gameObjects.get(index).getX() <= player.getX() + (player.getHeight()) 
			&& gameObjects.get(index).getX() >= player.getX() - (player.getHeight() / 2)
			&& gameObjects.get(index).getY() >= player.getY() - (player.getWidth() / 2)
			&& gameObjects.get(index).getY() <= player.getY() + (player.getWidth() / 2))
			
			{
				
				player.setDead(true);
				player.setWidth(0);
				player.setHeight(0);
				player.dead();
				
			}
			else if((missle.getX() + missle.getHeight()) <= gameObjects.get(index).getX() + gameObjects.get(index).getHeight() 
			&& missle.getX() - missle.getHeight() >= gameObjects.get(index).getX() - (gameObjects.get(index).getHeight()/2)
			&& missle.getY() >= gameObjects.get(index).getY() - (gameObjects.get(index).getWidth() / 2)
			&& missle.getY() <= gameObjects.get(index).getY() + (gameObjects.get(index).getWidth() / 2))
			{
				missle.setHit(true);
				gameObjects.get(index).setHealth(gameObjects.get(index).getHealth() - 1);
				if(gameObjects.get(index).getHealth() == 0)
				{
					gameObjects.get(index).dead();
					this.killCount++;
				}
			}
		}
		if(laser.getX() <= player.getX() + (player.getHeight()) 
				&& laser.getX() >= player.getX() - (player.getHeight() / 2)
				&& laser.getY() >= player.getY() - (player.getWidth() / 2)
				&& laser.getY() <= player.getY() + (player.getWidth() / 2))
		{
			player.setDead(true);
			player.setWidth(0);
			player.setHeight(0);
			player.dead();
			laser.dead();
		}
	}
}

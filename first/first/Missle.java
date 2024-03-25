package first;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Missle extends GameObject {
	BufferedImage image;
	private boolean hit;
	public Missle(GamePanel gamePanel) throws IOException {
		this.setColor(Color.RED);
		this.setWidth(40);
		this.setHeight(25);
		this.setX(gamePanel.player.getX() + 54);
		this.setY(gamePanel.player.getY() - 39);
		this.setSpeed(12);
		this.hit = true;;
		image = ImageIO.read(new File("Missile.png"));
	}
	public void dead()
	{
		this.setX(-50);
		this.setSpeed(0);
		this.setY(-50);
	}
	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	@Override
	public void update() {
		if(this.hit == false)
		{
		this.setSpeed(12);
		this.setY(this.getY() - this.getSpeed());
		}
		else
		{
			this.setSpeed(0);
			this.setY(-50);
			this.setX(-50);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, this.getX() - 69, this.getY() - 55, null);	
	}

}

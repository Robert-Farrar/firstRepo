package first;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Laser extends GameObject{
	BufferedImage image;
	private boolean fired;
	
	public boolean isFired() {
		return fired;
	}

	public void setFired(boolean fired) {
		this.fired = fired;
	}

	public Laser(GamePanel gamePanel) throws IOException
	{
		this.setX(-50);
		this.setY(-50);
		this.setWidth(17);
		this.setHeight(40);
		this.setSpeed(5);
		image = ImageIO.read(new File("laser.png"));
	}

	public void update()
	{
		
	}
	public void dead()
	{
		this.setFired(false);
		this.setDead(true);
		this.setX(-100);
		this.setSpeed(0);
		this.setY(-100);
		
	}
	public void draw(Graphics g)
	{
		
		g.drawImage(image,this.getX() - 69,this.getY() - 60, null);
	}
}

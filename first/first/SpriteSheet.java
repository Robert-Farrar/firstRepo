package first;

import java.awt.image.BufferedImage;
public class SpriteSheet {
	public BufferedImage spriteSheet;
	BufferedImage sprite;
	public SpriteSheet(BufferedImage ss)
	{
		this.spriteSheet = ss;
	}
	public BufferedImage grabSprite(int x, int y, int width, int height)
	{
		this.sprite = spriteSheet.getSubimage((x * width), (y * height), width, height);
		return this.sprite;
	}
}

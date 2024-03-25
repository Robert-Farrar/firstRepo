package first;


import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {
	private ArrayList <BufferedImage> frames;
	public BufferedImage sprite;
	private volatile boolean running = false;
	private long previousTime, speed;
	private int frameAtPause, currentFrame;
	private int cap;
	private boolean capped;
	private boolean complete;


public Animator(ArrayList <BufferedImage> frames, int cap, boolean capped, boolean complete)
{
	this.frames = frames;
	this.cap = cap;
	this.capped = capped;
	this.complete = complete;
}

public boolean isComplete() {
	return complete;
}

public void setComplete(boolean complete) {
	this.complete = complete;
}

public void setSpeed(long speed)
{
	this.speed = speed;
}

public void start()
{
	running = true;
	previousTime = 0;
	frameAtPause = 0;
	currentFrame = 0;
}
public void stop() {
	running = false;
	previousTime = 0;
	frameAtPause = 0;
	currentFrame = 0;
}
public void pause()
{
	frameAtPause = currentFrame;
	running = false;
}
public void resume()
{
	currentFrame = frameAtPause;
	running = true;
}
public void update(long time)
{	
	if(running)
	{
		if(time - previousTime >= speed)
		{
			currentFrame++;
			try {
				sprite = frames.get(currentFrame);
				if(currentFrame == cap && capped == true)
				{
					running = false;
					this.setComplete(true);
				}
			}
			catch(IndexOutOfBoundsException e){
				currentFrame = 0;
				frames.get(currentFrame);
			}
			previousTime = time;
		}
		
			
	}
}

}
package first;


public class DeltaTime {
	private final long oneSecond = 1000000000;
	private int framesPerSecond;
	private double drawInterval;
	private long lastTime;
	private long currentTime;
	private double delta;
	
	public double getDelta() {
		return delta;
	}

	public void resetDelta() {
		this.delta--;
	}

	public void start(int framesPerSecond) {
		this.framesPerSecond=framesPerSecond;
		this.drawInterval=oneSecond/this.framesPerSecond;
		this.delta=0;
		this.currentTime=0;
		this.lastTime=System.nanoTime();
	}
	
	public void update() {
		this.currentTime = System.nanoTime();
		this.delta = this.delta + (this.currentTime - this.lastTime)/this.drawInterval;
		this.lastTime = this.currentTime;
	}
}

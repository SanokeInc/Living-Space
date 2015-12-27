package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;

public class Warning {
	// position of warning
	private float x;
	private float y;

	private long initialTime;
	
	private Texture image;
	
	public static final int IMAGE_HEIGHT = 20;

	public Warning(float x, float y, long initialTime) {
		this.x = x;
		this.y = y;
		this.initialTime = initialTime;
		image = Assets.warningSign;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public long getInitialTime() {
		return initialTime;
	}

	public Texture getImage() {
		return image;
	}
}

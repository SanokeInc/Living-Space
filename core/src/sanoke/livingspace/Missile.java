package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Missile {
	// position of missile
	private float x;
	private float y;
	
	private float movementSpeed;
	private boolean onScreen;
	
	private static final Texture image = Assets.missile;
	
	public static final int MISSILE_WIDTH = 15;
	public static final int MISSILE_HEIGHT = 24;
	
	public Missile(float x, float y, float speed) {
		this.x = x;
		this.y = y;
		movementSpeed = speed;
		onScreen = true;
	}
	
	public Rectangle getMissileRegion() {
		return new Rectangle(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Texture getImage() {
		return image;
	}
	
	public void move(float delta, int gameHeight) {
		y += movementSpeed * delta;
		if (y > gameHeight) {
			onScreen = false;
		}
	}
	
	public boolean isOutOfScreen() {
		return !onScreen;
	}
}

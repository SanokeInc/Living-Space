package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Spaceship {
	// position of spaceship
	private float x;
	private float y;
	
	private boolean isAlive;
	
	private float movementSpeed;
	private float missileSpeed;
	private long firingCooldown;
	private long lastFireTime;
	
	private Array<Missile> missiles;
	
	private static final Texture image = Assets.spaceship;
	private static final float INIT_POS_X = 450;
	private static final float INIT_POS_Y = 80;
	private static final float INIT_MOVEMENT_SPEED = 80;
	private static final float INIT_MISSILE_SPEED = 200;
	private static final long INIT_COOLDOWN = 200;
	
	//TODO To adjust figures according to real size of image.
	public static final int SHIP_WIDTH = 38;
	public static final int SHIP_HEIGHT = 38;
	
	public Spaceship() {
		isAlive = true;
		x = INIT_POS_X;
		y = INIT_POS_Y;
		movementSpeed = INIT_MOVEMENT_SPEED;
		missileSpeed = INIT_MISSILE_SPEED;
		firingCooldown = INIT_COOLDOWN;
		lastFireTime = 0;
		missiles = new Array<Missile>();
	}
	
	public Rectangle getShipRegion() {
		return new Rectangle(x, y, SHIP_WIDTH, SHIP_HEIGHT);
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
	
	public void moveLeft(float delta) {
		x = Math.max(0, x - movementSpeed * delta);
	}
	
	public void moveRight(float delta, int gameWidth) {
		x = Math.min(gameWidth - SHIP_WIDTH, x + movementSpeed * delta);
	}
	
	public void moveForward(float delta, int gameHeight) {
		y = Math.min(gameHeight - SHIP_HEIGHT, y + movementSpeed * delta);
	}
	
	public void moveBackward(float delta) {
		y = Math.max(0, y - movementSpeed * delta);
	}
	
	public void fire() {
		if (!isCooldown() && isAlive()) {
		 // TODO play missile sound here.
			missiles.add(new Missile(x + SHIP_WIDTH / (float) 2, y + SHIP_HEIGHT, missileSpeed));
			lastFireTime = TimeUtils.millis();
		}
	}
	
	public Array<Missile> getMissiles() {
		return missiles;
	}
	
	private boolean isCooldown() {
		long currTime = TimeUtils.millis();
		if (currTime - lastFireTime < firingCooldown) {
			return true;
		}
		return false;
	}
	
	public void kill() {
		isAlive = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
}

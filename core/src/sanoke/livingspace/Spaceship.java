package sanoke.livingspace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Spaceship {
	// position of spaceship
	private float x;
	private float y;
	
	private boolean isEasy;
	private boolean isAlive;
	private boolean isSoundOn;
	
	private boolean isInvulnerable;
	private long lastHitTime;
	private static final long TIME_INVULNERABLE = 2500;
	
	private int lives;
	private float movementSpeed;
	private float missileSpeed;
	private long firingCooldown;
	private long lastFireTime;
	
	private int cash;
	
	private int upgradesBoughtSpeed;
	private int upgradesBoughtMissileCooldown;
	
	private Array<Missile> missiles;
	
	private PauseableTime timeReference;
	
	private TextureRegion image;
	private int currentFrameNumber;
	private static final int FRAME_SWITCH_DELAY = 3;
	
	private static final float INIT_POS_X = 450;
	private static final float INIT_POS_Y = 80;
	private static final float INIT_MOVEMENT_SPEED = 80;
	private static final float INIT_MISSILE_SPEED = 200;
	private static final long INIT_COOLDOWN = 500;
	private static final int INIT_LIVES = 3;
	
	private static final float UPGRADE_MULT_SPEED = 25;
	private static final long UPGRADE_MULT_MISSILE_COOLDOWN = -75;
	private static final int UPGRADE_MULT_LIVES = 1;
	private static final int MIN_NUM_EXTRA_LIVES = 0;
	
	public static final int SHIP_WIDTH = 35;
	public static final int SHIP_HEIGHT = 50;
	
	/** 
	 * For collision detections
	 */
	// Ship Body
	private static final int SHIP_ORIGIN_BODY_OFFSET_X = 9;
	private static final int SHIP_ORIGIN_BODY_OFFSET_Y = 1;
	private static final int SHIP_BODY_SIZE_X = 16; // 25 - 9
	private static final int SHIP_BODY_SIZE_Y = 48; // 49 - 1
	// Ship Wings
	private static final int SHIP_ORIGIN_WINGS_OFFSET_X = 0;
	private static final int SHIP_ORIGIN_WINGS_OFFSET_Y = 16;
	private static final int SHIP_WINGS_SIZE_X = 35; // 35 - 0
	private static final int SHIP_WINGS_SIZE_Y = 16; // 32 - 16
	
	public Spaceship(PauseableTime timeReference) {
	    isEasy = false;
		isAlive = true;
		isSoundOn = true;
		lives = INIT_LIVES;
		x = INIT_POS_X;
		y = INIT_POS_Y;
		movementSpeed = INIT_MOVEMENT_SPEED;
		missileSpeed = INIT_MISSILE_SPEED;
		firingCooldown = INIT_COOLDOWN;
		this.timeReference = timeReference;
		lastFireTime = timeReference.millis();
		missiles = new Array<Missile>();
		currentFrameNumber = 0;
		upgradesBoughtSpeed = 0;
	    upgradesBoughtMissileCooldown = 0;
	    cash = 0;
		image = Assets.spaceshipFrames[currentFrameNumber];
	}
	
	// Used when loading a new screen.
	public void refreshShip() {
		x = INIT_POS_X;
		y = INIT_POS_Y;
		lastFireTime = timeReference.millis();
		missiles = new Array<Missile>();
		isInvulnerable = false;
	}
	
	public boolean isCollidingWith(Rectangle object) {
		Rectangle bodyRegion = getBodyRegion();
		Rectangle wingsRegion = getWingsRegion();
		
		if (object.overlaps(bodyRegion) || object.overlaps(wingsRegion)) {
			return true;
		} else {
			return false;
		}
	}
	
	private Rectangle getBodyRegion() {
		float xPos = x + SHIP_ORIGIN_BODY_OFFSET_X;
		float yPos = y + SHIP_ORIGIN_BODY_OFFSET_Y;
		
		return new Rectangle(xPos, yPos, SHIP_BODY_SIZE_X, SHIP_BODY_SIZE_Y);
	}
	
	private Rectangle getWingsRegion() {
		float xPos = x + SHIP_ORIGIN_WINGS_OFFSET_X;
		float yPos = y + SHIP_ORIGIN_WINGS_OFFSET_Y;
		
		return new Rectangle(xPos, yPos, SHIP_WINGS_SIZE_X, SHIP_WINGS_SIZE_Y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setSound(boolean toPlay) {
		isSoundOn = toPlay;
	}
	
	public TextureRegion getImage() {
		TextureRegion imageToShow = image;
		currentFrameNumber = (currentFrameNumber + 1) % (Assets.NUM_FRAMES_SPACESHIP * FRAME_SWITCH_DELAY); 
		image = Assets.spaceshipFrames[currentFrameNumber / FRAME_SWITCH_DELAY];
		
		return imageToShow;
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
			if (isSoundOn) Assets.missileFireSound.play(0.20f);
			missiles.add(new Missile(x + (SHIP_WIDTH  - Missile.MISSILE_WIDTH) / (float) 2,
					y + SHIP_HEIGHT, missileSpeed));
			lastFireTime = timeReference.millis();
		}
	}
	
	public Array<Missile> getMissiles() {
		return missiles;
	}
	
	private boolean isCooldown() {
		long currTime = timeReference.millis();
		if (currTime - lastFireTime < firingCooldown) {
			return true;
		}
		return false;
	}
	
	public void minusOneLife() {	
		if (!isEasy) {
		    lives--;
		}
		if (lives <= 0) {
			isAlive = false;
			return;
		}
		
		if (isSoundOn) Assets.playerCollideSound.play();
		
		isInvulnerable = true;
		lastHitTime = timeReference.millis();
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getNumExtraLives() {
        return Math.max(lives - INIT_LIVES, MIN_NUM_EXTRA_LIVES);
    }
	
	public int getNumUpgradesSpeed() {
        return upgradesBoughtSpeed;
    }
	
	public int getNumUpgradesMissileCooldown() {
        return upgradesBoughtMissileCooldown;
    }

    public void upgradeSpeed() {
        upgradesBoughtSpeed++;
        movementSpeed = INIT_MOVEMENT_SPEED + UPGRADE_MULT_SPEED * upgradesBoughtSpeed;
    }
    
    public void upgradeMissiles() {
        upgradesBoughtMissileCooldown++;
        firingCooldown = INIT_COOLDOWN + UPGRADE_MULT_MISSILE_COOLDOWN * upgradesBoughtMissileCooldown;
        
    }
    
    public void upgradeLives() {
        lives += UPGRADE_MULT_LIVES;
        
    }

    public int getCash() {
        return cash;
    }
    
    public void addCash(int cash) {
        this.cash += cash;
    }
    
    public boolean isInvulnerable() {
    	if (isInvulnerable) {
    		long currentTime = timeReference.millis();
    		if (currentTime - lastHitTime > TIME_INVULNERABLE) {
    			isInvulnerable = false;
    		}
    	}
    	
    	return isInvulnerable;
    }
    
    public void moveForwardOffScreen(float delta) {
        y = y + movementSpeed * delta;
    }
    
    public void setEasyMode(boolean isEasyMode) {
        isEasy = isEasyMode;
    }
    
    
}

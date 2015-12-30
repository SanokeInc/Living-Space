package sanoke.livingspace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Spaceship {
	// position of spaceship
	private float x;
	private float y;
	
	private boolean isAlive;
	
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
	private int upgradesBoughtLives;
	
	private Array<Missile> missiles;
	
	private TextureRegion image;
	private int currentFrameNumber;
	private static final int FRAME_SWITCH_DELAY = 3;
	
	private static final float INIT_POS_X = 450;
	private static final float INIT_POS_Y = 80;
	private static final float INIT_MOVEMENT_SPEED = 80;
	private static final float INIT_MISSILE_SPEED = 200;
	private static final long INIT_COOLDOWN = 200;
	private static final int INIT_LIVES = 3;
	
	private static final float UPGRADE_MULT_SPEED = 20;
	private static final long UPGRADE_MULT_MISSILE_COOLDOWN = -50;
	private static final int UPGRADE_MULT_LIVES = 1;
	
	public static final int SHIP_WIDTH = 35;
	public static final int SHIP_HEIGHT = 50;
	
	public Spaceship() {
		isAlive = true;
		lives = INIT_LIVES;
		x = INIT_POS_X;
		y = INIT_POS_Y;
		movementSpeed = INIT_MOVEMENT_SPEED;
		missileSpeed = INIT_MISSILE_SPEED;
		firingCooldown = INIT_COOLDOWN;
		lastFireTime = TimeUtils.millis();
		missiles = new Array<Missile>();
		currentFrameNumber = 0;
		upgradesBoughtSpeed = 0;
	    upgradesBoughtMissileCooldown = 0;
	    upgradesBoughtLives = 0;
	    cash = 10000;
		image = Assets.spaceshipFrames[currentFrameNumber];
	}
	
	// Used when loading a new screen.
	public void refreshShip() {
		x = INIT_POS_X;
		y = INIT_POS_Y;
		lastFireTime = TimeUtils.millis();
		missiles = new Array<Missile>();
		isInvulnerable = false;
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
		 // TODO play missile sound here.
			missiles.add(new Missile(x + (SHIP_WIDTH  - Missile.MISSILE_WIDTH) / (float) 2,
					y + SHIP_HEIGHT, missileSpeed));
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
	
	public void minusOneLife() {
		lives--;
		
		if (lives <= 0) {
			isAlive = false;
			return;
		}
		
		isInvulnerable = true;
		lastHitTime = TimeUtils.millis();
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getNumUpgradesSpeed() {
        return upgradesBoughtSpeed;
    }
	
	public int getNumUpgradesMissileCooldown() {
        return upgradesBoughtMissileCooldown;
    }
	
	public int getNumUpgradesLives() {
        return upgradesBoughtLives;
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
        upgradesBoughtLives++;
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
    		long currentTime = TimeUtils.millis();
    		if (currentTime - lastHitTime > TIME_INVULNERABLE) {
    			isInvulnerable = false;
    		}
    	}
    	
    	return isInvulnerable;
    }
}

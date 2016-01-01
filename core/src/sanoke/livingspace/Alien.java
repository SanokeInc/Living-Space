package sanoke.livingspace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Alien {
	// position of alien
	private float x;
	private float y;
	private int type;
	
	private int currentFrameNumber;
	private static final int FRAME_SWITCH_DELAY = 3;
	
	private float movementX;
	private float movementY;
	
	private long timeSpawned;
	
	private boolean onScreen;
	
	private TextureRegion image;
	
	private int ALIEN_WIDTH;
	private int ALIEN_HEIGHT;
	
	private static final int BUFFER_SCREEN = 300;
	
	public Alien(float x, float y, int type, float moveX, float moveY) {
		this.x = x;
		this.y = y;
		this.type = type;
		movementX = moveX;
		movementY = moveY;
		initType(this.type);
		onScreen = true;
		timeSpawned = TimeUtils.millis();
	}
	
	private void initType(int alienType) {
		currentFrameNumber = 0;
		image = Assets.alienTypeFrame[alienType][currentFrameNumber];
		ALIEN_WIDTH = Assets.ALIEN_WIDTH;
		ALIEN_HEIGHT = Assets.ALIEN_HEIGHT;
	}
	
	public Rectangle getAlienRegion() {
		return new Rectangle(x, y, ALIEN_WIDTH, ALIEN_HEIGHT);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public long getTimeSpawned() {
		return timeSpawned;
	}
	
	public void setMoveX(float x) {
		movementX = x;
	}
	
	public void setMoveY(float y) {
		movementY = y;
	}
	
	public TextureRegion getImage() {
		TextureRegion imageToShow = image;
		currentFrameNumber = (currentFrameNumber + 1) % (Assets.NUM_FRAMES_ALIEN * FRAME_SWITCH_DELAY); 
		image = Assets.alienTypeFrame[type][currentFrameNumber / FRAME_SWITCH_DELAY];
		
		return imageToShow;
	}
	
	public void move(float delta, int gameWidth, int gameHeight) {
		x = x + movementX * delta;
		y = y + movementY * delta;
		if (isOutOfBounds(gameWidth, gameHeight)) {
			onScreen = false;
		}
	}
	
	private boolean isOutOfBounds(int gameWidth, int gameHeight) {
		if (x < -BUFFER_SCREEN || x > gameWidth + BUFFER_SCREEN || y < -BUFFER_SCREEN || y > gameHeight + BUFFER_SCREEN) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOutOfScreen() {
		return !onScreen;
	}
}

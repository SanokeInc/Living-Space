package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Alien {
	// position of alien
	private float x;
	private float y;
	private int type;
	
	private float movementX;
	private float movementY;
	
	private boolean onScreen;
	
	private Texture image;
	
	private int ALIEN_WIDTH;
	private int ALIEN_HEIGHT;
	
	public Alien(float x, float y, int type, float moveX, float moveY) {
		this.x = x;
		this.y = y;
		this.type = type;
		movementX = moveX;
		movementY = moveY;
		initType(this.type);
		onScreen = true;
	}
	
	private void initType(int alienType) {
		switch (alienType) {
			case 1:
				image = Assets.alienType1;
				ALIEN_WIDTH = 18;
				ALIEN_HEIGHT = 21;
				break;
		}
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
	
	public Texture getImage() {
		return image;
	}
	
	public void move(float delta, int gameWidth, int gameHeight) {
		x = x + movementX * delta;
		y = y + movementY * delta;
		if (x < 0 || x > gameWidth || y < 0 || y > gameHeight) {
			onScreen = false;
		}
	}
	
	public boolean isOutOfScreen() {
		return !onScreen;
	}
}

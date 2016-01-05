package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class StarBucks {
    private float x;
    private float y;
    private int value;
    
    private boolean onScreen;
    private boolean isCollected;
    
    private float collectedYPos;
    
    private static final int COIN_MIN_VALUE = 1;
    private static final int COIN_MAX_VALUE = 7;
    
    private static final int COIN_SPAWN_RATE = 4;
    
    private static final int COIN_FALL_RATE = -100;
    private static final int TEXT_RISE_RATE = 300;
    
    private static final int DIST_TO_RISE = 150;
    
    private static final int COIN_WIDTH = 16;
    private static final int COIN_HEIGHT = 16;
    
    
    
    /**
     * @return true if a coin should be spawned
     */
    public static boolean isSpawn() { 
        return COIN_SPAWN_RATE > MathUtils.random(9);
    }

    public StarBucks(float x, float y) {
        this.x = x;
        this.y = y;
        value = MathUtils.random(COIN_MIN_VALUE, COIN_MAX_VALUE);
        onScreen = true;
        isCollected = false;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public void move(float delta, int gameWidth, int gameHeight) {
        if (!isCollected) {
            moveCoin(delta);
        } else {
            moveText(delta);
        }
        if (isRemovableFromScreen(gameWidth, gameHeight)) {
            onScreen = false;
        }
    }

    private void moveCoin(float delta) {
        y = y + COIN_FALL_RATE * delta;
    }
    
    private void moveText(float delta) {
        y = y + TEXT_RISE_RATE * delta;
        if (isTextDoneRising()) {
            onScreen = false;
        }
    }

    private boolean isTextDoneRising() {
        return y - collectedYPos >= DIST_TO_RISE;
    }
    
    private boolean isRemovableFromScreen(int gameWidth, int gameHeight) {
        return x < 0 || x > gameWidth || y < 0 || y > gameHeight;
    }
    
    public boolean isOutOfScreen() {
        return !onScreen;
    }
    
    public boolean isCollected() {
        return isCollected;
    }

    public Texture getImage() {
        return Assets.coin;
    }

    public Rectangle getCoinRegion() {
        return new Rectangle(x, y, COIN_WIDTH, COIN_HEIGHT);
    }

    public int getValue() {
        return value;
    }
    
    public void collect() {
        isCollected = true;
        collectedYPos = y;
    }
}

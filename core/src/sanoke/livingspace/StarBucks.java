package sanoke.livingspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class StarBucks {
    private float x;
    private float y;
    
    private boolean onScreen;
    
    private static final int COIN_MIN_VALUE = 1;
    private static final int COIN_MAX_VALUE = 5;
    
    private static final int CASH_SPAWN_RATE = 3;
    
    private static final int FALL_RATE = -100;
    
    private static final int COIN_WIDTH = 16;
    private static final int COIN_HEIGHT = 16;
    
    public StarBucks(float x, float y) {
        this.x = x;
        this.y = y;
        onScreen = true;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public void move(float delta, int gameWidth, int gameHeight) {
        y = y + FALL_RATE * delta;
        if (y < 0 || y > gameHeight || x < 0 || x > gameWidth) {
            onScreen = false;
        }
    }
    
    public boolean isOutOfScreen() {
        return !onScreen;
    }
    
    /**
     * @return true if a coin should be spawned
     */
    public static boolean isSpawn() { 
        return CASH_SPAWN_RATE >= MathUtils.random(9);
    }

    public Texture getImage() {
        return Assets.coin;
    }

    public Rectangle getCoinRegion() {
        return new Rectangle(x, y, COIN_WIDTH, COIN_HEIGHT);
    }

    public static int getValue() {
        return MathUtils.random(COIN_MIN_VALUE, COIN_MAX_VALUE);
    }
}

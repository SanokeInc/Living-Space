package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture background;

    public static Texture spaceship;
    public static Texture missile;
    
    public static Texture warningSign;

    private static Texture [] alienTypeTemplate;
    public static TextureRegion [][] alienTypeFrame; // [Alien Type][Alien Frame]
    private static final int NUM_TYPE_ALIENS = 5;
    
    public static final int ALIEN_WIDTH = 36;
    public static final int ALIEN_HEIGHT = 42;
    public static final int NUM_FRAMES_ALIEN = 4;
    private static final int FRAME_FACTOR = 2;
    
    private static Music music;
    private static Sound missileSound;

    public static void loadAssets() {
        background = new Texture(Gdx.files.internal("space.jpg"));
        spaceship = new Texture(Gdx.files.internal("ship_placeholder.png"));
        missile = new Texture(Gdx.files.internal("missile_placeholder.png"));
        warningSign = new Texture(Gdx.files.internal("warning_placeholder.png"));
        
        loadAlienTemplates();
        loadAlienFrames();
   /*
        music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        
        missileSound = Gdx.audio.newSound(Gdx.files.internal("*.wav"));*/
    }

	private static void loadAlienFrames() {
		alienTypeFrame = new TextureRegion[NUM_TYPE_ALIENS][NUM_FRAMES_ALIEN];
		
		for (int i = 0; i < NUM_TYPE_ALIENS; i++) {
        	alienTypeFrame[i] = new TextureRegion[NUM_FRAMES_ALIEN];
        	
        	for (int j = 0; j < NUM_FRAMES_ALIEN; j++) {
            	int factorX = j % FRAME_FACTOR;
            	int factorY = j / FRAME_FACTOR;
            	
            	alienTypeFrame[i][j] = new TextureRegion(alienTypeTemplate[i], ALIEN_WIDTH * factorX, ALIEN_HEIGHT * factorY,
            			ALIEN_WIDTH, ALIEN_HEIGHT);
            }
        }
	}

	private static void loadAlienTemplates() {
		alienTypeTemplate = new Texture[NUM_TYPE_ALIENS];
		
        alienTypeTemplate[0] = new Texture(Gdx.files.internal("Alien1_SpriteFrames.png"));
        alienTypeTemplate[1] = new Texture(Gdx.files.internal("Alien2_SpriteFrames.png"));
        alienTypeTemplate[2] = new Texture(Gdx.files.internal("Alien3_SpriteFrames.png"));
        alienTypeTemplate[3] = new Texture(Gdx.files.internal("Alien4_SpriteFrames.png"));
        alienTypeTemplate[4] = new Texture(Gdx.files.internal("Alien5_SpriteFrames.png"));
	}

    public static void playMusic() {
        music.play();
    }

    public static void playMissileSound() {
    	missileSound.play();
    }
}

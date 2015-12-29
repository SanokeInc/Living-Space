package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture background;

    public static Texture missile;
    
    public static Texture warningSign;
    
    public static Texture life;
    
    public static Texture screenDeathNormal;
    public static Texture screenDeathQuit;
    public static Texture screenDeathRetry;
    
    public static Texture screenUpgradeNormal;
    
    public static Texture upgradesHighlight;
    public static Texture upgradesReturn;
    public static Texture upgradesBar;
    
    private static Texture spaceshipTemplate;
    public static TextureRegion [] spaceshipFrames;
    public static final int NUM_FRAMES_SPACESHIP = 2;
    
    public static final int LIVES_WIDTH = 25;
    public static final int LIVES_HEIGHT = 22;

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
        
        missile = new Texture(Gdx.files.internal("Missile_F1.png"));
        warningSign = new Texture(Gdx.files.internal("warning_placeholder.png"));
        life = new Texture(Gdx.files.internal("heart_placeholder.png"));
        
        loadDeathScreens();
        loadUpgrades();
        loadSpaceship();
        loadAlienTemplates();
        loadAlienFrames();
   /*
        music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        
        missileSound = Gdx.audio.newSound(Gdx.files.internal("*.wav"));*/
    
    }
    
    private static void loadDeathScreens() {
        screenDeathNormal = new Texture(Gdx.files.internal("Death.jpg"));
        screenDeathQuit = new Texture(Gdx.files.internal("Death_Quit.jpg"));
        screenDeathRetry = new Texture(Gdx.files.internal("Death_Retry.jpg"));
    }
    
    private static void loadUpgrades() {
        screenUpgradeNormal = new Texture(Gdx.files.internal("Upgrades.jpg"));
        upgradesHighlight = new Texture(Gdx.files.internal("Upgrades_Highlight.jpg"));
        upgradesReturn  = new Texture(Gdx.files.internal("Upgrades_Return.jpg"));
        upgradesBar = new Texture(Gdx.files.internal("Upgrades_Bar.png"));
    }
    
    private static void loadSpaceship() {
    	spaceshipTemplate = new Texture(Gdx.files.internal("Spaceship_SpriteFrames.png"));
    	spaceshipFrames = new TextureRegion[NUM_FRAMES_SPACESHIP];
    	
    	for (int i = 0; i < NUM_FRAMES_SPACESHIP; i++) {
    		spaceshipFrames[i] = new TextureRegion(spaceshipTemplate, i * Spaceship.SHIP_WIDTH, 0,
    				Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGHT);
    	}
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

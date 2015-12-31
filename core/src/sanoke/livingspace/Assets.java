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
    
    public static Texture coin;
    
    public static Texture mainScreenDefault;
    public static Texture mainScreenEnter;
    public static Texture mainScreenInstructions;
    public static Texture instructionScreenDefault;
    public static Texture instructionScreenOK;
    public static Texture pregameScreenDefault;
    public static Texture pregameScreenUpgrades;
    public static Texture pregameScreenPlay;
    public static Texture pregameScreenQuit;
    
    public static Texture pauseScreenDefault;
    public static Texture pauseScreenResume;
    public static Texture pauseScreenQuit;
    
    public static Texture screenDeathNormal;
    public static Texture screenDeathQuit;
    public static Texture screenDeathRetry;
    
    public static Texture screenUpgradeNormal;
    
    public static Texture upgradesHighlight;
    public static Texture upgradesReturn;
    public static Texture upgradesBar;
    
    public static Texture [] endScreenFrames;
    public static Texture [] endBackgroundFrames; 
    public static Texture endScreenMenu;
    public static Texture endScreenQuit;
    public static final int NUM_FRAMES_ENDSCREEN = 4;
    
    private static Texture spaceshipTemplate;
    public static TextureRegion [] spaceshipFrames;
    public static final int NUM_FRAMES_SPACESHIP = 2;
    
    private static Texture lifeLossTemplate;
    public static TextureRegion [] lifeLossFrames;
    public static final int NUM_FRAMES_LIFE_LOSS = 4;
    public static final int FRAME_FACTOR_LIFE_LOSS = 2;
    
    public static final int LIVES_WIDTH = 25;
    public static final int LIVES_HEIGHT = 22;

    private static Texture [] alienTypeTemplate;
    public static TextureRegion [][] alienTypeFrame; // [Alien Type][Alien Frame]
    private static final int NUM_TYPE_ALIENS = 9;
    
    public static final int ALIEN_WIDTH = 36;
    public static final int ALIEN_HEIGHT = 42;
    public static final int NUM_FRAMES_ALIEN = 4;
    private static final int FRAME_FACTOR_ALIEN = 2;
    
    private static Music music;
    
    public static Sound missileFireSound;
    public static Sound playerCollideSound;
    public static Sound playerDieSound;
    public static Sound alienDieSound;
    public static Sound upgradeSound;
    public static Sound zoomOffSound;
    public static Sound buttonClickSound;
    public static Sound coinCollectSound;

    public static void loadAssets() {
        background = new Texture(Gdx.files.internal("space.jpg"));
        coin = new Texture(Gdx.files.internal("coin.png"));
        missile = new Texture(Gdx.files.internal("Missile_F1.png"));
        warningSign = new Texture(Gdx.files.internal("warning_placeholder.png"));
        life = new Texture(Gdx.files.internal("heart_placeholder.png"));
        
        loadMainAndPregameScreens();
        loadDeathScreens();

    	loadPauseScreens();
        loadUpgrades();
        loadEndScreens();
        loadSpaceship();
        loadAlienTemplates();
        loadAlienFrames();
        loadLifeLoss();
        loadSounds();
   
   /*
        music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
   */
    
    }
    
    private static void loadMainAndPregameScreens() {
    	mainScreenDefault = new Texture(Gdx.files.internal("MainScreen.jpg"));
        mainScreenEnter = new Texture(Gdx.files.internal("MainScreen_Play.jpg"));
        mainScreenInstructions = new Texture(Gdx.files.internal("MainScreen_Instructions.jpg"));
        instructionScreenDefault = new Texture(Gdx.files.internal("Instructions.jpg"));
        instructionScreenOK = new Texture(Gdx.files.internal("Instructions_OK.jpg"));
        pregameScreenDefault = new Texture(Gdx.files.internal("PreGame.jpg"));
        pregameScreenUpgrades = new Texture(Gdx.files.internal("PreGame_Upgrades.jpg"));
        pregameScreenPlay = new Texture(Gdx.files.internal("PreGame_Play.jpg"));
        pregameScreenQuit = new Texture(Gdx.files.internal("PreGame_Leave.jpg"));
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
    
    private static void loadEndScreens() {
    	Texture frame1 = new Texture(Gdx.files.internal("Game_Finished.jpg"));
    	Texture frame2 = new Texture(Gdx.files.internal("Game_Finished_2.jpg"));
    	Texture frame3 = new Texture(Gdx.files.internal("Game_Finished_3.jpg"));
    	Texture frame4 = new Texture(Gdx.files.internal("Game_Finished_4.jpg"));
    	
    	Texture end1 = new Texture(Gdx.files.internal("space2.jpg"));
    	Texture end2 = new Texture(Gdx.files.internal("space3.jpg"));
    	Texture end3 = new Texture(Gdx.files.internal("space4.jpg"));
    	Texture end4 = new Texture(Gdx.files.internal("space5.jpg"));
    	
    	Texture[] arr = {frame1, frame2, frame3, frame4};
    	endScreenFrames = arr;
    	
    	Texture [] arr2 = {end1, end2, end3, end4};
    	endBackgroundFrames = arr2;
    	
    	endScreenMenu = new Texture(Gdx.files.internal("Game_Finished_Menu.jpg"));
    	endScreenQuit = new Texture(Gdx.files.internal("Game_Finished_Quit.jpg"));
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
            	int factorX = j % FRAME_FACTOR_ALIEN;
            	int factorY = j / FRAME_FACTOR_ALIEN;
            	
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
        alienTypeTemplate[5] = new Texture(Gdx.files.internal("Alien6_SpriteFrames.png"));
        alienTypeTemplate[6] = new Texture(Gdx.files.internal("Alien7_SpriteFrames.png"));
        alienTypeTemplate[7] = new Texture(Gdx.files.internal("Alien8_SpriteFrames.png"));
        alienTypeTemplate[8] = new Texture(Gdx.files.internal("UFO_SpriteFrames.png"));
	}
	
	private static void loadLifeLoss() {
		lifeLossTemplate = new Texture(Gdx.files.internal("Heartbreak_SpriteFrames.png"));
		lifeLossFrames = new TextureRegion[NUM_FRAMES_LIFE_LOSS];
    	
    	for (int i = 0; i < NUM_FRAMES_LIFE_LOSS; i++) {
    		int factorX = i % FRAME_FACTOR_LIFE_LOSS;
        	int factorY = i / FRAME_FACTOR_LIFE_LOSS;
        	
    		lifeLossFrames[i] = new TextureRegion(lifeLossTemplate, factorX * LifeLostAnimation.IMAGE_WIDTH, 
    				factorY * LifeLostAnimation.IMAGE_HEIGHT, LifeLostAnimation.IMAGE_WIDTH,
    				LifeLostAnimation.IMAGE_HEIGHT);
    	}
	}
	
	private static void loadPauseScreens() {
		pauseScreenDefault = new Texture(Gdx.files.internal("Paused.jpg"));
		pauseScreenResume = new Texture(Gdx.files.internal("Paused_Resume.jpg"));
		pauseScreenQuit = new Texture(Gdx.files.internal("Paused_Quit.jpg"));
	}

	public static void loadSounds() {
		missileFireSound = Gdx.audio.newSound(Gdx.files.internal("Fire.mp3"));
	    playerCollideSound = Gdx.audio.newSound(Gdx.files.internal("Player_Collide.mp3"));
	    playerDieSound = Gdx.audio.newSound(Gdx.files.internal("Player_Die.mp3"));
	    alienDieSound = Gdx.audio.newSound(Gdx.files.internal("Alien_Die.mp3"));
	    upgradeSound = Gdx.audio.newSound(Gdx.files.internal("Upgrade_Sound.mp3"));
	    zoomOffSound = Gdx.audio.newSound(Gdx.files.internal("Zoom_Off.mp3"));
	    buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_Click.mp3"));
	    coinCollectSound = Gdx.audio.newSound(Gdx.files.internal("Coin_Collect.mp3"));
	}
	
    public static void playMusic() {
        music.play();
    }

    public static void playMissileSound() {
    	//missileSound.play();
    }
}

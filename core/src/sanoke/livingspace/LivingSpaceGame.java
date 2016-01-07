package sanoke.livingspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LivingSpaceGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public boolean isSoundOn;
    
    private Spaceship player;
    
    private boolean isPaused;
    
    public int level;
    
    public PauseableTime timeReference; 
	
    public final int HEIGHT = 800;
    public final int WIDTH = 1000;
    
    public int gameCode; //0 = intro; 1 = main screen/instruction/hub; 2 = end screen; 3 = game levels/death screen
    
    public void create() {
        batch = new SpriteBatch();
        // default Arial
        font = new BitmapFont(Gdx.files.internal("courier.fnt"));
        isSoundOn = true;
        Assets.loadAssets();
        timeReference = new PauseableTime();
        player = new Spaceship(timeReference);        
        level = 1;
        isPaused = false;
        gameCode = 0;
        setIntroScreen();
        if (isSoundOn) Assets.introMusic.play();
        else Assets.introMusic.stop();
    }
    
    public void restart() {
    	if (isSoundOn) Assets.music.play();
    	else Assets.music.stop();
    	this.level = 1;
    	player = new Spaceship(timeReference);
    	player.setSound(isSoundOn);
    	setPregameScreen(this.level);
    }
    
    public void setLevelScreen(int level) {
    	Assets.music.stop();
    	gameCode = 3;
    	timeReference.refresh();
    	switch (level) {
    		case 1:
    			this.setScreen(new LevelOne(this, player));
    			break;
    		case 2:
    			this.setScreen(new LevelTwo(this, player));
    			break;
    		case 3:
    			this.setScreen(new LevelThree(this, player));
    			break;
    		case 4:
    			this.setScreen(new LevelFour(this, player));
    			break;
    		case 5:
    			this.setScreen(new LevelFive(this, player));
    			break;
    		case 6:
    			this.setScreen(new LevelSix(this, player));
    			break;
    		case 7:
    			this.setScreen(new LevelSeven(this, player));
    			break;
    		case 8:
    			this.setScreen(new LevelEight(this, player));
    			break;
    		case 9:
    			this.setScreen(new LevelNine(this, player));
    			break;
    		case 10:
    			this.setScreen(new LevelTen(this, player));
    			break;
    		case 11:
    			this.setScreen(new LevelEleven(this, player));
    			break;
    		case 12:
    			this.setScreen(new LevelTwelve(this, player));
    			break;
    		default:
    			assert(false);
    			break;
    	}
    }
    
    public void setMainScreen(boolean isNewGame) {
    	gameCode = 1;
    	if (!isNewGame) this.setScreen(new MainMenuScreen(this, player));
    	else {
    		player = new Spaceship(timeReference);
    		player.setSound(isSoundOn);
            level = 1;
            this.setScreen(new MainMenuScreen(this, player));
    	}
    }
    
    public void setIntroScreen() {
    	gameCode = 0;
    	this.setScreen(new IntroScreen(this, player));
    }
    
    public void setPregameScreen(int level) {
    	gameCode = 1;
    	this.setScreen(new PregameScreen(this, player, level));
    }

    public void setPauseScreen() {
    	this.setScreen(new PauseScreen(this, gameCode, player));
    }
    
    public void setEndScreen() {
    	gameCode = 2;
    	this.setScreen(new EndScreen(this));
    }
    
    public void setInstructionScreen() {
    	gameCode = 1;
    	this.setScreen(new InstructionScreen(this));
    }
    
    public void setDeathScreen() {
    	gameCode = 3;
    	this.setScreen(new DeathScreen(this));
    }
    
    public void setUpgradeScreen() {
    	gameCode = 1;
        this.setScreen(new UpgradeScreen(this, player));
    }
    
    public void setResume(int type) {
    	if (type == 0) {
    		if (isSoundOn) Assets.introMusic.play();
    		else Assets.introMusic.stop();
    	}
    	else if (type == 1) {
    		if (isSoundOn) Assets.music.play(); // if resume to main menu or to the hub
    		else Assets.music.stop();
    	}
    	else if (type == 2) {
    		if (isSoundOn) Assets.endMusic.play(); // if resume to end screen
    		else Assets.endMusic.stop();
    	}
    	isPaused = false;
    }
    
    public void setDataLoggerScreen(int level) {
    	gameCode = 1;
    	this.setScreen(new DataLoggerScreen(this, player, level));
    }

    @Override
    public void render() {
    		super.render();
    }
    
    /* ========== ADDED ========== */
    @Override
    public void pause() {
    	if (!isPaused) {
    		if (this.gameCode == 0) {
    			if (isSoundOn) Assets.introMusic.pause(); // if paused from intro
    			else Assets.introMusic.stop();
    		}
    		else if (this.gameCode == 1) {
    			if (isSoundOn) Assets.music.pause(); // if paused from main menu or from the hub
    			else Assets.music.stop();
    		}
    		else if (this.gameCode == 2) {
    			if (isSoundOn) Assets.endMusic.pause(); // if paused from end screen
    			else Assets.endMusic.stop();
    		}
    		this.setPauseScreen();
    		isPaused = true;
    	}
    }
    /* ========== !ADDED ========== */

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
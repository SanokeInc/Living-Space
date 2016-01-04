package sanoke.livingspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LivingSpaceGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    
    private Spaceship player;
    
    private boolean isPaused;
    
    public int level;
    
    public PauseableTime timeReference; 
	
    public final int HEIGHT = 800;
    public final int WIDTH = 1000;
    
    public int gameCode; // 1 = main screen/instruction/hub; 2 = end screen; 3 = game levels/death screen
    
    public void create() {
        batch = new SpriteBatch();
        // default Arial
        font = new BitmapFont(Gdx.files.internal("courier.fnt"));
        Assets.loadAssets();
        timeReference = new PauseableTime();
        player = new Spaceship(timeReference);        
        level = 1;
        isPaused = false;
        gameCode = 1;
        setMainScreen(false);
        Assets.music.play();
    }
    
    public void restart() {
    	Assets.music.play();
    	this.level = 1;
    	player = new Spaceship(timeReference);
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
    		case 10: // for gq to implement
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
    	if (!isNewGame) this.setScreen(new MainMenuScreen(this));
    	else {
    		player = new Spaceship(timeReference);        
            level = 1;
            this.setScreen(new MainMenuScreen(this));
    	}
    }
    
    public void setPregameScreen(int level) {
    	gameCode = 1;
    	this.setScreen(new PregameScreen(this, player, level));
    }

    public void setPauseScreen() {
    	this.setScreen(new PauseScreen(this, gameCode));
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
    	if (type == 1) Assets.music.play(); // if resume to main menu or to the hub
    	else if (type == 2) Assets.endMusic.play(); // if resume to end screen
    	isPaused = false;
    }

    @Override
    public void render() {
    		super.render();
    }
    
    /* ========== ADDED ========== */
    @Override
    public void pause() {
    	if (!isPaused) {
    		if (this.gameCode == 1) Assets.music.pause(); // if paused from main menu or from the hub
    		else if (this.gameCode == 2) Assets.endMusic.pause(); // if paused from end screen
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
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
	
    public final int HEIGHT = 800;
    public final int WIDTH = 1000;
    
    public void create() {
        batch = new SpriteBatch();
        // default Arial
        font = new BitmapFont(Gdx.files.internal("courier.fnt"));
        Assets.loadAssets();
        player = new Spaceship();        
        level = 1;
        isPaused = false;
        setMainScreen();
    }
    
    public void restart() {
    	this.level = 1;
    	player = new Spaceship();
    	setPregameScreen(this.level);
    }
    
    public void setLevelScreen(int level) {
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
    		default:
    			assert(false);
    			break;
    	}
    }
    
    public void setMainScreen() {
    	this.setScreen(new MainMenuScreen(this));
    }
    
    public void setPregameScreen(int level) {
    	this.setScreen(new PregameScreen(this, player, level));
    }

    public void setPauseScreen() {
    	this.setScreen(new PauseScreen(this));
    }
    
    public void setEndScreen() {
    	this.setScreen(new EndScreen(this));
    }
    
    public void setInstructionScreen() {
    	this.setScreen(new InstructionScreen(this));
    }
    
    public void setDeathScreen() {
    	this.setScreen(new DeathScreen(this));
    }
    
    public void setUpgradeScreen() {
        this.setScreen(new UpgradeScreen(this, player));
    }
    
    public void setResume() {
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
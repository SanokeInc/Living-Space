package sanoke.livingspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LivingSpaceGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    
    private Spaceship player;
	
    public final int HEIGHT = 800;
    public final int WIDTH = 1000;
    
    public void create() {
        batch = new SpriteBatch();
        // default Arial
        font = new BitmapFont(Gdx.files.internal("petitafont.fnt"));
        Assets.loadAssets();
        player = new Spaceship();
        //TODO main menu
        //this.setScreen(new MainMenuScreen(this, player));
        this.setScreen(new UpgradeScreen(this, player));
        //setLevelScreen(1);
    }
    
    public void restart() {
    	player = new Spaceship();
    	setLevelScreen(1);
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
    		default:
    			assert(false);
    			break;
    	}
    }
    
    public void setDeathScreen() {
    	this.setScreen(new DeathScreen(this));
    }
    
    // TODO: For changing current screen to upgrade/spaceship screen view.
    public void setUpgradeScreen() {
    	// this.setScreen(new ???(this, player));
    }

    @Override
    public void render() {
    		super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
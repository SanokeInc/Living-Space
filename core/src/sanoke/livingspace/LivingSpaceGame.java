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
        this.setScreen(new LevelOne(this, player));
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
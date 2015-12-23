package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final LivingSpaceGame game;

    OrthographicCamera camera;
    Spaceship player;
    
    private static final int BACKGROUND_HEIGHT = 1080;
    private static final int BACKGROUND_SPEED = 400;
    private float currentBgY;

    public GameScreen(final LivingSpaceGame game) {
        Assets.loadAssets();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
        player = new Spaceship();
        initBackground();
        // Assets.playMusic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        drawBackground(delta);

        updateUnitsPosition(delta);
        drawUnits();

        game.batch.end();
        processInput(delta);
    }

    private void initBackground() {
    	currentBgY = BACKGROUND_HEIGHT;
    }
    
	private void drawBackground(float delta) {
		game.batch.draw(Assets.background, 0, currentBgY - BACKGROUND_HEIGHT);
		game.batch.draw(Assets.background, 0, currentBgY);
		currentBgY -= BACKGROUND_SPEED * delta;
		if (currentBgY <= 0) {
			currentBgY = BACKGROUND_HEIGHT;
		}
	}

    private void updateUnitsPosition(float delta) {
    	updateMissilesPosition(delta);
    }
    
    private void updateMissilesPosition(float delta) {
    	Array<Missile> missiles = player.getMissiles();
    	Iterator<Missile> iter = missiles.iterator();
    	
		while (iter.hasNext()) {
			Missile missile = iter.next();
			missile.move(delta, game.HEIGHT);
			
			//TODO if collide with enemy.
			
			if (missile.isOutOfScreen()) {
				iter.remove();
			} 
		}
    }

    private void processInput(float delta) {
    	processKeyBoardInputs(delta);

        /**
         *  For mouse clicks only.
         *
    	if (Gdx.input.justTouched()) {
            /*float xPos = (Gdx.input.getX() - BOARD_X_OFFSET);
            float yPos = (game.HEIGHT - BOARD_Y_OFFSET - Gdx.input.getY());
            // lazy validation for positive numbers
            if (xPos >= 0 && yPos >= 0) {
                board.highlightAndSwapUnit((int) yPos / Assets.UNIT_LENGTH,
                        (int) xPos / Assets.UNIT_WIDTH);
            }
        }*/
    }


    private void processKeyBoardInputs(float delta) {
    	if (Gdx.input.isKeyPressed(Keys.LEFT)) {
    		player.moveLeft(delta);
    	}
    	if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
    		player.moveRight(delta, game.WIDTH);
    	}
    	if (Gdx.input.isKeyPressed(Keys.UP)) {
    		player.moveForward(delta, game.HEIGHT);
    	}
    	if (Gdx.input.isKeyPressed(Keys.DOWN)) {
    		player.moveBackward(delta);
    	}
    	if (Gdx.input.isKeyPressed(Keys.SPACE)) {
    		player.fire();
    		// TODO play missile sound here.
    	}
	}

	private void drawUnits() {
    	drawSpaceship();
    	drawMissiles();
    }

	private void drawSpaceship() {
		game.batch.draw(player.getImage(), player.getX(), player.getY());
	}
	
	private void drawMissiles() {
		Array<Missile> missiles = player.getMissiles();
		for (Missile missile : missiles) {
			game.batch.draw(missile.getImage(), missile.getX(), missile.getY());
		}
	}

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}

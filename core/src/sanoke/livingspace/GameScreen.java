package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final LivingSpaceGame game;

	OrthographicCamera camera;
	Spaceship player;

	Array<Alien> aliens;
	private long lastSpawnTime;
	private static final int NUM_TO_SPAWN = 8;
	private static final long SPAWN_TIME = 500;
	private static final int MIN_SPAWN_X = 50;
	private static final int MAX_SPAWN_X = 800;
	private static final int SPAWN_VARIATION_Y = 150;
	private static final int CONSTANT_MOVE_FACTOR = 200;

	private static final int BACKGROUND_HEIGHT = 1080;
	private static final int BACKGROUND_SPEED = 400;
	private float currentBgY;

	public GameScreen(final LivingSpaceGame game) {
		Assets.loadAssets();
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
		player = new Spaceship();
		aliens = new Array<Alien>();
		lastSpawnTime = TimeUtils.millis();
		initBackground();
		// Assets.playMusic();
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		drawBackground(delta);
		drawUnits();
		game.batch.end();

		spawnAliens();
        updateUnitsPosition(delta);

        checkCollisions();
        
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

	private void spawnAliens() {
		long currentTime = TimeUtils.millis();
		if (currentTime - lastSpawnTime > SPAWN_TIME) {
			lastSpawnTime = currentTime;
			int randomStartX = MathUtils.random(MIN_SPAWN_X, MAX_SPAWN_X);
			int randomStartY = game.HEIGHT
					- MathUtils.random(0, SPAWN_VARIATION_Y);

			for (int i = 0; i < NUM_TO_SPAWN; i++) {
				float movementX = CONSTANT_MOVE_FACTOR
						* MathUtils.cos((float) (MathUtils.PI
								/ (float) (NUM_TO_SPAWN - 1) * i));
				float movementY = -CONSTANT_MOVE_FACTOR
						* MathUtils.sin((float) (MathUtils.PI
								/ (float) (NUM_TO_SPAWN - 1) * i));

				aliens.add(new Alien(randomStartX, randomStartY, 1, movementX,
						movementY));
			}
		}
	}

	private void updateAliensPosition(float delta) {
		Iterator<Alien> iter = aliens.iterator();

		while (iter.hasNext()) {
			Alien alien = iter.next();
			alien.move(delta, game.WIDTH, game.HEIGHT);

			if (alien.isOutOfScreen()) {
				iter.remove();
			}
		}
	}

	private void updateUnitsPosition(float delta) {
		updateMissilesPosition(delta);
		updateAliensPosition(delta);
	}

	private void updateMissilesPosition(float delta) {
		Array<Missile> missiles = player.getMissiles();
		Iterator<Missile> iter = missiles.iterator();

		while (iter.hasNext()) {
			Missile missile = iter.next();
			missile.move(delta, game.HEIGHT);

			if (missile.isOutOfScreen()) {
				iter.remove();
			}
		}
	}

	private void checkCollisions() {
		Iterator<Alien> alienIter = aliens.iterator();
		while (alienIter.hasNext()) {
			Rectangle currentAlien = alienIter.next().getAlienRegion();

			Array<Missile> missiles = player.getMissiles();
			Iterator<Missile> missileIter = missiles.iterator();
			
			Rectangle currentShip = player.getShipRegion();
			if (currentAlien.overlaps(currentShip)) {
				player.kill();
			}

			while (missileIter.hasNext()) {
				Rectangle currentMissile = missileIter.next()
						.getMissileRegion();
				if (currentAlien.overlaps(currentMissile)) {
					alienIter.remove();
					missileIter.remove();
					break;
				}
			}
		}
	}

	private void processInput(float delta) {
		processKeyBoardInputs(delta);
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
		}
	}

	private void drawUnits() {
		drawSpaceship();
		drawMissiles();
		drawAliens();
	}

	private void drawSpaceship() {
		if (player.isAlive()) {
			game.batch.draw(player.getImage(), player.getX(), player.getY());
		}
	}

	private void drawMissiles() {
		Array<Missile> missiles = player.getMissiles();
		for (Missile missile : missiles) {
			game.batch.draw(missile.getImage(), missile.getX(), missile.getY());
		}
	}

	private void drawAliens() {
		for (Alien alien : aliens) {
			game.batch.draw(alien.getImage(), alien.getX(), alien.getY());
		}
	}

	@Override
    public void show() {
    
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

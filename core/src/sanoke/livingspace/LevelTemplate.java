package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class LevelTemplate implements Screen {
	final LivingSpaceGame game;

	OrthographicCamera camera;
	Spaceship player;

	Array<Alien> aliens;
	Array<StarBucks> coins;
	
	private boolean isOver;
	private boolean isOffScreen;
	
	private LifeLostAnimation heartBreak;
	private int blinkCounter;
	private static final int BLINK_NUM_FRAMES = 4;
	private static final int BLINK_FRAME_SWITCH_DELAY = 3;
	private static final float BLINK_ALPHA_INTERVAL = 0.25f; // do not change this value.
	
	private static final int LIFE_LOSS_DISPLAY_X = 340;
	private static final int LIFE_LOSS_DISPLAY_Y = 200;

	private long initialTime;
	private boolean displayLevel;
	private int currentLevel;

	private static final long ZOOM_SPEED_MULTIPLIER = 5;
	
	private static final long TIME_TO_DISPLAY_LVL = 4000;
	private static final int LEVEL_DISPLAY_OFFSET_X = 430;
	private static final int LEVEL_DISPLAY_OFFSET_Y = 150;
	
	private static final int BACKGROUND_HEIGHT = 1080;
	private static final int BACKGROUND_SPEED = 400;
	private float currentBgY;

	private static final int OFFSET_DRAW_LIVES = 10;

	public LevelTemplate(final LivingSpaceGame game, Spaceship player, int level) {
		this.player = player;
		player.refreshShip();
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
		aliens = new Array<Alien>();
		coins = new Array<StarBucks>();
		isOver = false;
		isOffScreen = false;
		initBackground();
		heartBreak = new LifeLostAnimation();
		blinkCounter = 0;

		initialTime = TimeUtils.millis();
		displayLevel = true;
		currentLevel = level;
		// Assets.playMusic();
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		drawBackground(delta);
		showLevel();
		drawUnits();
		drawLives();
		drawCoins();
		animateLossLife();
		animateZoomOffScreen(delta);
		game.batch.end();

		spawnAliens();
		updateUnitsPosition(delta);

		checkCollisions();
		checkAlive();

		processInput(delta);
		
		backToHub();
	}

    private void animateZoomOffScreen(float delta) {
        if (isOver) {
            player.moveForwardOffScreen(delta * ZOOM_SPEED_MULTIPLIER);
            if (player.getY() >= game.HEIGHT) {
                isOffScreen = true;
            }
        }
    }

    private void backToHub() {
        if (isOver && isOffScreen) {
		    game.level += 1;
	        game.setPregameScreen(game.level);
		}
    }
	
	private void animateLossLife() {
		if (player.isInvulnerable()) {
			game.batch.draw(heartBreak.getImage(), LIFE_LOSS_DISPLAY_X, LIFE_LOSS_DISPLAY_Y);
		} else {
			heartBreak.resetAnimation();
		}
	}

	private void showLevel() {
		if (displayLevel) {
			game.font.draw(game.batch, "Level: " + currentLevel,
					LEVEL_DISPLAY_OFFSET_X, LEVEL_DISPLAY_OFFSET_Y);

			long currentTime = TimeUtils.millis();
			if (currentTime - initialTime > TIME_TO_DISPLAY_LVL) {
				displayLevel = false;
			}
		}
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

	// Can be implemented in subclass via method overriding
	protected void spawnAliens() {

	}

	protected void updateAliensPosition(float delta) {
		Iterator<Alien> iter = aliens.iterator();

		while (iter.hasNext()) {
			Alien alien = iter.next();
			alien.move(delta, game.WIDTH, game.HEIGHT);

			if (alien.isOutOfScreen()) {
				iter.remove();
			}
		}
	}
	
	protected void updateCoinsPosition(float delta) {
        Iterator<StarBucks> iter = coins.iterator();

        while (iter.hasNext()) {
            StarBucks coin = iter.next();
            coin.move(delta, game.WIDTH, game.HEIGHT);

            if (coin.isOutOfScreen()) {
                iter.remove();
            }
        }
    }
	
	private void updateUnitsPosition(float delta) {
		updateMissilesPosition(delta);
		updateAliensPosition(delta);
		updateCoinsPosition(delta);
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

	protected void checkCollisions() {
	    checkCollisionsForAlienArray(aliens);
	    checkCollisionsForCoinsArray();
	}
	
	private void checkCollisionsForCoinsArray() {
        Iterator<StarBucks> coinsIter = coins.iterator();
        Rectangle currentShip = player.getShipRegion();
        while (coinsIter.hasNext()) {
            Rectangle currentCoin = coinsIter.next().getCoinRegion();
            if (currentCoin.overlaps(currentShip)) {
                player.addCash(StarBucks.getValue());
                coinsIter.remove();
            }
        }
    }

    protected void checkCollisionsForAlienArray(Array<Alien> givenArray) {
		Iterator<Alien> alienIter = givenArray.iterator();
		while (alienIter.hasNext()) {
			Rectangle currentAlien = alienIter.next().getAlienRegion();

			Array<Missile> missiles = player.getMissiles();
			Iterator<Missile> missileIter = missiles.iterator();

			Rectangle currentShip = player.getShipRegion();
			if (currentAlien.overlaps(currentShip) && !player.isInvulnerable()) {
				alienIter.remove();
				player.minusOneLife();
				continue;
			}

			while (missileIter.hasNext()) {
				Rectangle currentMissile = missileIter.next()
						.getMissileRegion();
				if (currentAlien.overlaps(currentMissile)) {
				    spawnCash(currentAlien.getX(), currentAlien.getY());
				    alienIter.remove();
					missileIter.remove();
					break;
				}
			}
		}
	}
	
	private void spawnCash(float x, float y) {
        if (StarBucks.isSpawn()) {
            coins.add(new StarBucks(x, y));
        }
        
    }

    private void processInput(float delta) {
		if (!isOver) {
		    processKeyBoardInputs(delta);
		}
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
		if (Gdx.input.isKeyPressed(Keys.P)) {
			game.pause();
		}
	}

	protected void drawUnits() {
		drawSpaceship();
		if (!isOver) {
		    drawMissiles();
		    drawAliens();
		}
		
	}

	protected void drawSpaceship() {
		if (player.isAlive()) {
			if (player.isInvulnerable()) {
				game.batch.setColor(1.0f, 1.0f, 1.0f, generateBlinkingAlpha());
				game.batch.draw(player.getImage(), player.getX(), player.getY());
				game.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			} else {
				game.batch.draw(player.getImage(), player.getX(), player.getY());
			}	
		}
	}
	
	private float generateBlinkingAlpha() {
		blinkCounter = (blinkCounter + 1) % (BLINK_NUM_FRAMES * BLINK_FRAME_SWITCH_DELAY);
		
		return BLINK_ALPHA_INTERVAL * ((blinkCounter / BLINK_FRAME_SWITCH_DELAY) + 1);
	}

	protected void drawMissiles() {
		Array<Missile> missiles = player.getMissiles();
		for (Missile missile : missiles) {
			game.batch.draw(missile.getImage(), missile.getX(), missile.getY());
		}
	}

	protected void drawAliens() {
		for (Alien alien : aliens) {
			game.batch.draw(alien.getImage(), alien.getX(), alien.getY());
		}
	}
	
	protected void drawCoins() {
        for (StarBucks coin : coins) {
            game.batch.draw(coin.getImage(), coin.getX(), coin.getY());
        }
    }
	
	private void drawLives() {
		int lives = player.getLives();

		float yPos = game.HEIGHT - Assets.LIVES_HEIGHT - OFFSET_DRAW_LIVES;
		for (int i = 0; i < lives; i++) {
			game.batch.draw(Assets.life, i
					* (Assets.LIVES_WIDTH + OFFSET_DRAW_LIVES)
					+ OFFSET_DRAW_LIVES, yPos);
		}
	}
	
	private void checkAlive() {
		if (!player.isAlive()) {
			game.setDeathScreen();
		}
	}
	
	public void passLevel() {
	    isOver = true;
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

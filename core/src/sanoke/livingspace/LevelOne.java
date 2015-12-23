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

public class LevelOne implements Screen {
	final LivingSpaceGame game;

	OrthographicCamera camera;
	Spaceship player;

	Array<Alien> aliens;
	private long lastSpawnTime;
	private long afkSpawnTime;
	
	private static final int NUM_TO_SPAWN = 2;
	private static final long SPAWN_TIME = 2700;
	
	private static final long AFK_SPAWN_TIME = 500;
	private static final int AFK_SPAWN_VARIATION_X = 100;
	
	private static final int SPAWN_VARIATION_X = 400;
	private static final int SPAWN_VARIATION_Y = 100;
	private static final int MOVE_FACTOR_X = 50;
	private static final int MOVE_FACTOR_Y = 130;
	private static final int SPAWN_PENALTY = 1;
	
	private static final int TOP_LEFT = 1;
	private static final int TOP_RIGHT = 2;
	private static final int BOTTOM_LEFT = 3;
	private static final int BOTTOM_RIGHT = 4;

	private static final int BACKGROUND_HEIGHT = 1080;
	private static final int BACKGROUND_SPEED = 400;
	private float currentBgY;

	public LevelOne(final LivingSpaceGame game, Spaceship player) {
		this.player = player;
		player.refreshShip();
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
		aliens = new Array<Alien>();
		afkSpawnTime = lastSpawnTime = TimeUtils.millis();
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
			
			spawnGroup(TOP_LEFT);
			spawnGroup(TOP_RIGHT);
			spawnGroup(BOTTOM_LEFT);
			spawnGroup(BOTTOM_RIGHT);
		}
		if (currentTime - afkSpawnTime > AFK_SPAWN_TIME) {
			afkSpawnTime = currentTime;
			killAFKPlayers();
		}
	}
	
	private void spawnGroup(int type) {
		int minSpawnX, maxSpawnX, minSpawnY, maxSpawnY, numSpawn;
		float movementX, movementY;
		
		switch (type) {
			case TOP_LEFT: 
				numSpawn = NUM_TO_SPAWN;
				minSpawnX = 0;
				maxSpawnX = SPAWN_VARIATION_X;
				minSpawnY = game.HEIGHT - SPAWN_VARIATION_Y;
				maxSpawnY = game.HEIGHT;
				movementX = MOVE_FACTOR_X;
				movementY = -MOVE_FACTOR_Y;
				break;
			case TOP_RIGHT:
				numSpawn = NUM_TO_SPAWN;
				minSpawnX = game.WIDTH - SPAWN_VARIATION_X;
				maxSpawnX = game.WIDTH;
				minSpawnY = game.HEIGHT - SPAWN_VARIATION_Y;
				maxSpawnY = game.HEIGHT;
				movementX = -MOVE_FACTOR_X;
				movementY = -MOVE_FACTOR_Y;
				break;
			case BOTTOM_LEFT:
				numSpawn = NUM_TO_SPAWN - SPAWN_PENALTY;
				minSpawnX = 0;
				maxSpawnX = SPAWN_VARIATION_X;
				minSpawnY = 0;
				maxSpawnY = SPAWN_VARIATION_Y;
				movementX = MOVE_FACTOR_X;
				movementY = MOVE_FACTOR_Y;
				break;
			case BOTTOM_RIGHT:
			default:
				numSpawn = NUM_TO_SPAWN - SPAWN_PENALTY;
				minSpawnX = game.WIDTH - SPAWN_VARIATION_X;
				maxSpawnX = game.WIDTH;
				minSpawnY = 0;
				maxSpawnY = SPAWN_VARIATION_Y;
				movementX = -MOVE_FACTOR_X;
				movementY = MOVE_FACTOR_Y;
				break;
		}
		
		for (int i = 0; i < numSpawn; i++) {
			int randomStartX = MathUtils.random(minSpawnX, maxSpawnX);
			int randomStartY = MathUtils.random(minSpawnY, maxSpawnY);
			
			aliens.add(new Alien(randomStartX, randomStartY, 1, movementX,
					movementY));
		}
	}
	
	private void killAFKPlayers() {
		int minSpawnX = 0;
		int maxSpawnX = AFK_SPAWN_VARIATION_X;
		int movementX = 0;
		int movementY = MOVE_FACTOR_Y;
		
		int randomStartX = MathUtils.random(minSpawnX, maxSpawnX);
		int startY = 0;
			
		aliens.add(new Alien(randomStartX, startY, 1, movementX,
				movementY));
		
		minSpawnX = game.WIDTH - AFK_SPAWN_VARIATION_X;
		maxSpawnX = game.WIDTH - 18;
		randomStartX = MathUtils.random(minSpawnX, maxSpawnX);
		
		aliens.add(new Alien(randomStartX, startY, 1, movementX,
				movementY));
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

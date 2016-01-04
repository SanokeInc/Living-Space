package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.GridPoint2;

public class LevelFour extends LevelTemplate {
	private int enemyCount;
	
	Array<Warning> warnings;
	Array<Alien> staticAliens;
	
	private long borderMobSpawnTime;
	
	private long lastSpawnTime;
	private long lastSpawnLocationChangeTime;
	private int warningCounter;
	
	private GridPoint2 [] spawnLocations;
	
	private static final int CURRENT_LEVEL = 4;
	
	private static final int NUMBER_TO_WIN = 250;
	
	private static final int NUM_PLACES_TO_SPAWN = 3;
	private static final int ALIEN_TYPE = 4;
	
	private static final long PARAM_WARNING_COUNTER_SPAWN = 2;
	private static final long SPAWN_INTERVAL = 600;
	private static final long CHANGE_SPAWN_LOC_DELAY_TIME = 6000;
	private static final int OFFSET_LOCATION = 50;

	private static final int INIT_MOVE_FACTOR_Y = 150;
	private static final int GENERAL_MOVE_FACTOR = 180;
	
	private static final int VARIANCE_FACTOR = 20;
	private static final float CONVERSION_FACTOR = 1000;
	
	private static final long BORDER_MOB_SPAWN_TIME = 500;
	private static final int BORDER_MOB_SPAWN_VARIATION = 100;

	public LevelFour(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;
		
		staticAliens = new Array<Alien>();
		warnings = new Array<Warning>();
		spawnLocations = new GridPoint2[NUM_PLACES_TO_SPAWN];
		setSpawnLocations();
		borderMobSpawnTime = lastSpawnLocationChangeTime = lastSpawnTime = game.timeReference.millis();
	}
	
	private void setSpawnLocations() {
		float playerX = player.getX();
		float playerY = player.getY();
		int locCount = 0;
		
		// Top - left
		int spawnX = (int) playerX - OFFSET_LOCATION;
		int spawnY = (int) playerY + OFFSET_LOCATION;
		spawnLocations[locCount] = new GridPoint2(spawnX, spawnY);
		locCount++;
		
		// Top - right
		spawnX = (int) playerX + OFFSET_LOCATION;
		spawnLocations[locCount] = new GridPoint2(spawnX, spawnY);
		locCount++;
		
		// Bottom
		spawnX = (int) playerX;
		spawnY = (int) playerY - OFFSET_LOCATION;
		spawnLocations[locCount] = new GridPoint2(spawnX, spawnY);
	
		warningCounter = 0;
	}

	@Override
	protected void spawnAliens() {		
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			staticAliens = new Array<Alien>();
			passLevel();
			return;
		}
		
		long currentTime = game.timeReference.millis();
		
		if (currentTime - lastSpawnLocationChangeTime > CHANGE_SPAWN_LOC_DELAY_TIME) {
			lastSpawnLocationChangeTime = currentTime;
			
			setSpawnLocations();
		}
		
		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;
			
			if (warningCounter == PARAM_WARNING_COUNTER_SPAWN) {
				killAllWarnings();
				spawnAll(currentTime);
			} else if (warningCounter > PARAM_WARNING_COUNTER_SPAWN) {
				spawnAll(currentTime);
			} else if (warningCounter == 0){
				spawnWarnings(currentTime);
			}
			
			warningCounter++;
		}
		
		if (currentTime - borderMobSpawnTime > BORDER_MOB_SPAWN_TIME) {
			borderMobSpawnTime = currentTime;
			spawnBorderMob();
		}
	}
	
	private void spawnWarnings(long currentTime) {
		for (int i = 0; i < NUM_PLACES_TO_SPAWN; i++) {
			int xPos = spawnLocations[i].x;
			int yPos = spawnLocations[i].y;
			
			warnings.add(new Warning(xPos, yPos, currentTime));
		}
	}
	
	private void killAllWarnings() {
		warnings.clear();
	}
	
	private void spawnAll(long currentTime) {
		for (int i = 0; i < NUM_PLACES_TO_SPAWN; i++) {
			int xPos = spawnLocations[i].x;
			int yPos = spawnLocations[i].y;
			long spawnTime = game.timeReference.millis();
			
			aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, 0, INIT_MOVE_FACTOR_Y, spawnTime));
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}
	
	// spawn time not used here.
	private void spawnBorderMob() {
		int minSpawnX = 0;
		int maxSpawnX = BORDER_MOB_SPAWN_VARIATION;
		int movementX = 0;
		int movementY = INIT_MOVE_FACTOR_Y;
		
		int randomStartX = MathUtils.random(minSpawnX, maxSpawnX);
			
		staticAliens.add(new Alien(randomStartX, 0, ALIEN_TYPE, movementX,
				movementY, 0));
		
		minSpawnX = game.WIDTH - BORDER_MOB_SPAWN_VARIATION - Assets.ALIEN_WIDTH;
		maxSpawnX = game.WIDTH - Assets.ALIEN_WIDTH;
		
		randomStartX = MathUtils.random(minSpawnX, maxSpawnX);
		
		staticAliens.add(new Alien(randomStartX, 0, ALIEN_TYPE, movementX,
				movementY, 0));
	}
	
	@Override
	protected void updateAliensPosition(float delta) {
		Iterator<Alien> iter = aliens.iterator();

		while (iter.hasNext()) {
			Alien alien = iter.next();
			float timeElapsed = game.timeReference.millis() - alien.getTimeSpawned();
			
			alien.setMoveX(MathUtils.sin(timeElapsed / CONVERSION_FACTOR) * GENERAL_MOVE_FACTOR + VARIANCE_FACTOR);
			alien.setMoveY(MathUtils.cos(timeElapsed / CONVERSION_FACTOR) * GENERAL_MOVE_FACTOR);
			
			alien.move(delta, game.WIDTH, game.HEIGHT);

			if (alien.isOutOfScreen()) {
				iter.remove();
			}
		}
		
		Iterator<Alien> staticIter = staticAliens.iterator();
		
		while (staticIter.hasNext()) {
			Alien staticAlien = staticIter.next();
			staticAlien.move(delta, game.WIDTH, game.HEIGHT);
			
			if (staticAlien.isOutOfScreen()) {
				staticIter.remove();
			}
		}
	}
	
	@Override
	protected void drawUnits() {	
		super.drawUnits();
		drawStaticAliens();
		drawWarnings();
	}
	
	protected void drawStaticAliens() {
		for (Alien alien : staticAliens) {
			game.batch.draw(alien.getImage(), alien.getX(), alien.getY());
		}
	}
	
	protected void drawWarnings() {
		for (Warning warning : warnings) {
			game.batch.draw(warning.getImage(), warning.getX(), warning.getY());
		}
	}
	
	@Override
	protected void checkCollisions() {
		super.checkCollisions();
		checkCollisionsForAlienArray(staticAliens);
	}
}
package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelThree extends LevelTemplate {
	Array<Warning> warnings;
	
	private int enemyCount;
	
	private long lastSpawnTime;
	
	private static final int CURRENT_LEVEL = 3;
	private static final int NUMBER_TO_WIN = 200;
	
	private static final int NUM_TO_SPAWN = 4;
	private static final int ALIEN_TYPE = 2;
	private static final long SPAWN_WARNING_TIME = 500;
	
	// Time delay for warning to spawn aliens.
	private static final long SPAWN_DELAY_TIME = 2200;
	
	private static final int MOVE_FACTOR_X = 120;
	private static final int MOVE_FACTOR_Y = 120;

	public LevelThree(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		warnings = new Array<Warning>();
		enemyCount = 0;
		lastSpawnTime = TimeUtils.millis();
	}

	@Override
	protected void spawnAliens() {		
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			passLevel();
			return;
		}
		
		long currentTime = TimeUtils.millis();
		
		// Control Warning Spawn
		if (currentTime - lastSpawnTime > SPAWN_WARNING_TIME) {
			lastSpawnTime = currentTime;
			
			spawnWarning(currentTime);
		}
		
		// Control Alien Spawn
		runAlienSpawnController(currentTime);
	}
	
	private void spawnWarning(long initialTime) {
		float x = MathUtils.random(0, game.WIDTH); 
		float y = MathUtils.random(0, game.HEIGHT - Warning.IMAGE_HEIGHT);
		warnings.add(new Warning(x, y, initialTime));
	}
	
	private void runAlienSpawnController(long currentTime) {
		Iterator<Warning> iter = warnings.iterator();

		while (iter.hasNext()) {
			Warning warning = iter.next();
			long initSpawnTime = warning.getInitialTime();
			
			if (currentTime - initSpawnTime >  SPAWN_DELAY_TIME) {
				float x = warning.getX();
				float y = warning.getY();
				iter.remove();
				
				int[] factor_x = {0, 1, 0, -1};
				int[] factor_y = {1, 0, -1, 0};
				
				for (int i = 0; i < NUM_TO_SPAWN; i++) {
					aliens.add(new Alien(x, y, ALIEN_TYPE, MOVE_FACTOR_X * factor_x[i],
							MOVE_FACTOR_Y * factor_y[i]));
					if (player.isAlive()) {
						enemyCount++;
					}
				}
			}
			
		}
	}
	
	@Override
	protected void drawUnits() {	
		drawSpaceship();
		drawMissiles();
		drawAliens();
		drawWarnings();
	}
	
	protected void drawWarnings() {
		for (Warning warning : warnings) {
			game.batch.draw(warning.getImage(), warning.getX(), warning.getY());
		}
	}
}

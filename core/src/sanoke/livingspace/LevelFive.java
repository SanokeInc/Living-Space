package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class LevelFive extends LevelTemplate {
	private int enemyCount;

	private long lastSpawnTime;

	private static final int CURRENT_LEVEL = 5;

	private static final int NUMBER_TO_WIN = 250;
	
	private static final int ALIEN_TYPE = 12;
	private static final long SPAWN_INTERVAL = 420;
	private static final int ALIEN_MOVE_SPEED = 250;

	public LevelFive(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;

		lastSpawnTime = game.timeReference.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			passLevel();
			return ;
		}
		
		long currentTime = game.timeReference.millis();

		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;

			spawnUFO();
		}
	}

	/**
	 * Note: As spawn time of alien is not used, last param of Alien constructor set to 0.
	 */
	private void spawnUFO() {
		// Spawn Left
		int xPos = 0;
		int yPos = MathUtils.random(0, game.HEIGHT - Assets.ALIEN_HEIGHT);

		aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, ALIEN_MOVE_SPEED, 0, 0));
		if (player.isAlive()) {
			enemyCount++;
		}

		// Spawn Right
		xPos = game.WIDTH - Assets.ALIEN_WIDTH;
		yPos = MathUtils.random(0, game.HEIGHT - Assets.ALIEN_HEIGHT);

		aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, -ALIEN_MOVE_SPEED, 0, 0));
		if (player.isAlive()) {
			enemyCount++;
		}
	}
}

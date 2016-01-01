package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelFive extends LevelTemplate {
	private int enemyCount;

	private long lastSpawnTime;

	private static final int CURRENT_LEVEL = 5;

	private static final int NUMBER_TO_WIN = 250;
	
	private static final int ALIEN_TYPE = 8;
	private static final long SPAWN_INTERVAL = 420;
	private static final int ALIEN_MOVE_SPEED = 250;

	public LevelFive(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;

		lastSpawnTime = TimeUtils.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			isEnd = true;
			aliens = new Array<Alien>();
			return ;
		}
		
		long currentTime = TimeUtils.millis();

		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;

			spawnUFO();
		}
	}

	private void spawnUFO() {
		// Spawn Left
		int xPos = 0;
		int yPos = MathUtils.random(0, game.HEIGHT - Assets.ALIEN_HEIGHT);

		aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, ALIEN_MOVE_SPEED, 0));
		if (player.isAlive()) {
			enemyCount++;
		}

		// Spawn Right
		xPos = game.WIDTH - Assets.ALIEN_WIDTH;
		yPos = MathUtils.random(0, game.HEIGHT - Assets.ALIEN_HEIGHT);

		aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, -ALIEN_MOVE_SPEED, 0));
		if (player.isAlive()) {
			enemyCount++;
		}
	}
}

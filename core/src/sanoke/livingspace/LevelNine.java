package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class LevelNine extends LevelTemplate {
	private int enemyCount;

	private long lastSpawnTime;

	private static final int CURRENT_LEVEL = 9;

	private static final int NUMBER_TO_WIN = 200;
	
	private static final int ALIEN_TYPE = 1;

	private static final long SPAWN_INTERVAL = 2500;

	private static final int NUM_ALIEN_SPAWN = 8;
	private static final int ANGLE_FACTOR = NUM_ALIEN_SPAWN / 2;

	private static final int ALIEN_MOVE_SPEED = 130; 
	private static final int ALIEN_SPAWN_OFFSET = 400; 

	public LevelNine(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;

		lastSpawnTime = game.timeReference.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			isEnd = true;
			return;
		}

		long currentTime = game.timeReference.millis();

		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;

			spawnAll();
		}
	}

	private void spawnAll() {
		float playerX = player.getX();
		float playerY = player.getY();
		
		for (int i = 0; i < NUM_ALIEN_SPAWN; i++) {
			float cosineFactor = MathUtils.cos(MathUtils.PI / ANGLE_FACTOR * i);
			float sineFactor = MathUtils.sin(MathUtils.PI / ANGLE_FACTOR * i);
			
			float xPos = playerX + ALIEN_SPAWN_OFFSET * cosineFactor;
			float yPos = playerY + ALIEN_SPAWN_OFFSET * sineFactor;
			
			float moveX = -cosineFactor * ALIEN_MOVE_SPEED;
			float moveY = -sineFactor * ALIEN_MOVE_SPEED;

			aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, moveX, moveY, 0)); // time spawned not used.
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}
}

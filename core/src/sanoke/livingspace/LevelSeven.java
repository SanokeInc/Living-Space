package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelSeven extends LevelTemplate {
	private int enemyCount;

	private long lastSpawnTime;
	private long borderMobSpawnTime;

	private static final int CURRENT_LEVEL = 7;
	
	private static final int NUMBER_TO_WIN = 350;
	
	private static final long BORDER_MOB_SPAWN_TIME = 500;
	private static final int BORDER_MOB_SPAWN_VARIATION_Y = 250;
	
	private static final int ALIEN_TYPE = 6;
	private static final long SPAWN_INTERVAL = 450;
	private static final int NUM_ALIEN_SPAWN = 2;
	private static final int ALIEN_MOVE_SPEED = 350;
	private static final int ALIEN_SPAWN_VARIATION = 150;
	
	private static final int PARAM_RANDOM_MIN = 0;
	private static final int PARAM_RANDOM_MAX = 2;
	private static final int RANDOM_PRECISION_DECIMAL_PLACES = 2;

	private int centerX;
	private int centerY;

	public LevelSeven(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;

		borderMobSpawnTime = lastSpawnTime = TimeUtils.millis();
		
		centerX = (game.WIDTH - Assets.ALIEN_WIDTH) / 2;
		centerY = (game.HEIGHT - Assets.ALIEN_HEIGHT) / 2;
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			//isEnd = true;
			aliens = new Array<Alien>();
			passLevel();
			return;
		}

		long currentTime = TimeUtils.millis();

		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;

			spawnAll();
		}
		
		if (currentTime - borderMobSpawnTime > BORDER_MOB_SPAWN_TIME) {
			borderMobSpawnTime = currentTime;
			spawnBorderMob();
		}
	}

	private float generateRandomMovement() {
		float precision = (float) Math.pow(10, RANDOM_PRECISION_DECIMAL_PLACES);
		float randomNumber = (float) MathUtils.random(PARAM_RANDOM_MIN, PARAM_RANDOM_MAX * precision) / 
				precision - 1;
		if (Math.abs(randomNumber) > 1) System.out.println("Error: " + randomNumber);
		return randomNumber * ALIEN_MOVE_SPEED;
	}

	private void spawnAll() {
		for (int i = 0; i < NUM_ALIEN_SPAWN; i++) {
			float moveX = generateRandomMovement();
			float moveY = generateRandomMovement();
			
			float randomX = MathUtils.random(centerX - ALIEN_SPAWN_VARIATION, centerX + ALIEN_SPAWN_VARIATION);
			float randomY = MathUtils.random(centerY - ALIEN_SPAWN_VARIATION, centerY + ALIEN_SPAWN_VARIATION);

			aliens.add(new Alien(randomX, randomY, ALIEN_TYPE, moveX, moveY));
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}
	
	private void spawnBorderMob() {
		int minSpawnY = 0;
		int maxSpawnY = BORDER_MOB_SPAWN_VARIATION_Y;
		int movementX = ALIEN_MOVE_SPEED;
		int movementY = 0;
		
		int randomStartY = MathUtils.random(minSpawnY, maxSpawnY);
		int startX = 0;
			
		aliens.add(new Alien(startX, randomStartY, ALIEN_TYPE, movementX,
				movementY));
	}
}

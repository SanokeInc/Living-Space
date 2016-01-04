package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class LevelTwo extends LevelTemplate {
	private int enemyCount;
	
	private long lastSpawnTime;
	
	private static final int CURRENT_LEVEL = 2;
	
	private static final int NUMBER_TO_WIN = 400;
	
	private static final int NUM_TO_SPAWN = 8;
	private static final int ALIEN_TYPE = 3;
	private static final long SPAWN_TIME = 500;
	private static final int MIN_SPAWN_X = 50;
	private static final int MAX_SPAWN_X = 800;
	private static final int SPAWN_VARIATION_Y = 150;
	private static final int CONSTANT_MOVE_FACTOR = 200;

	public LevelTwo(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);	
		
		enemyCount = 0;
		lastSpawnTime = game.timeReference.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			passLevel();
			return;
		}
		
		long currentTime = game.timeReference.millis();
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

				aliens.add(new Alien(randomStartX, randomStartY, ALIEN_TYPE, movementX,
						movementY, 0)); // spawn time not used
				if (player.isAlive()) {
					enemyCount++;
				}
			}
		}
	}
}

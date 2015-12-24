package sanoke.livingspace;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelOne extends LevelTemplate {
	private int enemyCount;
	
	private long lastSpawnTime;
	private long afkSpawnTime;
	
	private static final int CURRENT_LEVEL = 1;
	
	private static final int NUMBER_TO_WIN = 50;
	
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

	public LevelOne(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;
		afkSpawnTime = lastSpawnTime = TimeUtils.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			game.setLevelScreen(2);
			return;
		}
		
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
			enemyCount++;
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
}

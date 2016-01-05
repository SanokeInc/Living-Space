package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class LevelEleven extends LevelTemplate {
	private int enemyCount;
	
	private long lastSpawnTime;
	
	private static final int CURRENT_LEVEL = 11;
	
	private static final int NUMBER_TO_WIN = 90;
	
	private static final int ALIEN_TYPE = 11;
	private static final long SPAWN_INTERVAL = 800;
	private static final int OFFSET_LOCATION = 300;
	private static final int GENERAL_MOVE_FACTOR = 200;
	
	private static final int VARIANCE_FACTOR = 20;
	private static final float CONVERSION_FACTOR = 1000;
	

	public LevelEleven(final LivingSpaceGame game, Spaceship player) {
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
		
		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;
			
			float playerX = player.getX();
			float playerY = player.getY();
			
			float yPos = playerY;
			float xPos = playerX + OFFSET_LOCATION;
			
			aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, 0, 0, currentTime));
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}
	
	@Override
	protected void updateAliensPosition(float delta) {
		Iterator<Alien> iter = aliens.iterator();

		while (iter.hasNext()) {
			Alien alien = iter.next();
			float timeElapsed = game.timeReference.millis() - alien.getTimeSpawned();
			
			alien.setMoveX(MathUtils.sin(timeElapsed / CONVERSION_FACTOR + MathUtils.PI) * GENERAL_MOVE_FACTOR + VARIANCE_FACTOR);
			alien.setMoveY(MathUtils.cos(timeElapsed / CONVERSION_FACTOR + MathUtils.PI) * GENERAL_MOVE_FACTOR);
			
			alien.move(delta, game.WIDTH, game.HEIGHT);

			if (alien.isOutOfScreen()) {
				iter.remove();
			}
		}
	}
}

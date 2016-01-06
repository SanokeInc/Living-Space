package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class LevelFive extends LevelTemplate {
	private int enemyCount;
	
	private long lastSpawnTime;
	
	private static final int CURRENT_LEVEL = 5;
	
	private static final int NUMBER_TO_WIN = 30;
	
	private static final int ALIEN_TYPE = 10;
	private static final long SPAWN_INTERVAL = 2500;
	private static final int SPAWN_LOCATION_Y = -10;
	private static final int GENERAL_MOVE_FACTOR = 120;	

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
			return;
		}
		
		long currentTime = game.timeReference.millis();
		
		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;
			
			float xPos = MathUtils.random(0, game.WIDTH - Assets.ALIEN_WIDTH);
			float yPos = SPAWN_LOCATION_Y;
			
			aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, 0, 0, 0)); // spawn time not used
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}
	
	@Override
	protected void updateAliensPosition(float delta) {
		Iterator<Alien> iter = aliens.iterator();
		
		float playerX = player.getX();
		float playerY = player.getY();
		
		while (iter.hasNext()) {
			Alien alien = iter.next();
			float alienX = alien.getX();
			float alienY = alien.getY();
			float differenceX = Math.abs(alienX - playerX);
			float differenceY = Math.abs(alienY - playerY);
			
			float distanceAlienToPlayer = (float) Math.hypot(differenceX, differenceY);
			float ratio = GENERAL_MOVE_FACTOR / distanceAlienToPlayer;
			
			float moveX = adjustSign(ratio * differenceX, alienX, playerX);
			float moveY = adjustSign(ratio * differenceY, alienY, playerY);
			
			alien.setMoveX(moveX);
			alien.setMoveY(moveY);
			
			alien.move(delta, game.WIDTH, game.HEIGHT);

			if (alien.isOutOfScreen()) {
				iter.remove();
			}
		}
	}
	
	private static float adjustSign(float value, float alienPos, float playerPos) {
		if (alienPos < playerPos) {
			return value;
		} else {
			return -value;
		}
	}
}

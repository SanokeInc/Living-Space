package sanoke.livingspace;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelFive extends LevelTemplate {
	private int enemyCount;

	Array<Alien> ufos;

	private long lastSpawnTime; // for UFO

	private static final int CURRENT_LEVEL = 5;

	private static final int NUMBER_TO_WIN = 50;

	private static final int UFO_TYPE = 8;
	private static final int ALIEN_TYPE = 5;

	private static final long SPAWN_INTERVAL = 650;
	private static final int SPAWN_UFO_Y_MIN = 650;

	private static final long TIME_RELEASE_ALIEN_MIN = 500;
	private static final long TIME_RELEASE_ALIEN_MAX = 3000;

	private static final int NUM_ALIEN_SPAWN = 5;

	private static final int UFO_MOVE_SPEED = 250;
	private static final int ALIEN_MOVE_SPEED = 220;
	private static final float ALIEN_SPAWN_VARIANCE = 0.39f;

	public LevelFive(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);

		enemyCount = 0;

		ufos = new Array<Alien>();
		lastSpawnTime = TimeUtils.millis();
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			isEnd = true;
			aliens = new Array<Alien>();
			ufos = new Array<Alien>();
			return ;
		}
		
		long currentTime = TimeUtils.millis();

		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;

			spawnUFO();
		}

		checkSpawnAliens();
	}

	private void spawnUFO() {
		// Spawn Top
		int randomX = (int) MathUtils
				.random(0, game.WIDTH - Assets.ALIEN_WIDTH);
		int randomY = game.HEIGHT;

		ufos.add(new Alien(randomX, randomY, UFO_TYPE, 0, -UFO_MOVE_SPEED));

		// Spawn Left
		randomX = 0;
		randomY = MathUtils.random(SPAWN_UFO_Y_MIN, game.HEIGHT);

		ufos.add(new Alien(randomX, randomY, UFO_TYPE, UFO_MOVE_SPEED, 0));

		// Spawn Right
		randomX = game.WIDTH - Assets.ALIEN_WIDTH;

		ufos.add(new Alien(randomX, randomY, UFO_TYPE, -UFO_MOVE_SPEED, 0));
	}

	private void checkSpawnAliens() {
		Iterator<Alien> iter = ufos.iterator();

		while (iter.hasNext()) {
			Alien ufo = iter.next();
			float timeElapsed = TimeUtils.millis() - ufo.getTimeSpawned();

			if (timeElapsed > MathUtils.random(TIME_RELEASE_ALIEN_MIN,
					TIME_RELEASE_ALIEN_MAX)) {
				float x = ufo.getX();
				float y = ufo.getY();

				iter.remove();
				spawnAll(x, y);
			}
		}
	}

	private void spawnAll(float x, float y) {
		for (int i = 0; i < NUM_ALIEN_SPAWN; i++) {
			float moveX, moveY;

			if (i == NUM_ALIEN_SPAWN - 1) {
				moveX = 0;
				moveY = -ALIEN_MOVE_SPEED;
			} else {
				float spawnVariance = MathUtils.random() / 1
						* ALIEN_SPAWN_VARIANCE;

				moveX = MathUtils.cos(MathUtils.PI / (NUM_ALIEN_SPAWN - 1) + i
						* MathUtils.PI / 2 + spawnVariance)
						* ALIEN_MOVE_SPEED;
				moveY = MathUtils.sin(MathUtils.PI / (NUM_ALIEN_SPAWN - 1) + i
						* MathUtils.PI / 2 + spawnVariance)
						* ALIEN_MOVE_SPEED;
			}

			aliens.add(new Alien(x, y, ALIEN_TYPE, moveX, moveY));
			if (player.isAlive()) {
				enemyCount++;
			}
		}
	}

	@Override
	protected void updateAliensPosition(float delta) {
		super.updateAliensPosition(delta);

		Iterator<Alien> ufoIter = ufos.iterator();

		while (ufoIter.hasNext()) {
			Alien ufo = ufoIter.next();
			ufo.move(delta, game.WIDTH, game.HEIGHT);

			if (ufo.isOutOfScreen()) {
				ufoIter.remove();
			}
		}
	}

	@Override
	protected void drawUnits() {
		super.drawUnits();
		drawUFO();
	}

	protected void drawUFO() {
		for (Alien ufo : ufos) {
			game.batch.draw(ufo.getImage(), ufo.getX(), ufo.getY());
		}
	}

	@Override
	protected void checkCollisions() {
		super.checkCollisions();
		checkCollisionsForAlienArray(ufos);
	}
}

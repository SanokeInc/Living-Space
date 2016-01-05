package sanoke.livingspace;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class LevelTen extends LevelTemplate {
	private int enemyCount;
	Array<Warning> warnings;
	
	private long lastSpawnTime; // use this to spawn aliens
	private long lastWarningTime; // use this to spawn warnings
	private long lastForcefieldTime; // use this to spawn forcefields
	
	private boolean isWarningShown;
	private boolean isForcefieldShown;
	
	private static final int CURRENT_LEVEL = 10;
	
	private static final int NUMBER_TO_WIN = 330;
	
	private static final int ALIEN_TYPE = 7;
	
	private static final long SECONDS_TO_NANO = 1000000000;
	private static final long WARNING_DURATION = 2 * SECONDS_TO_NANO;
	private static final long FORCEFIELD_DURATION = 3 * SECONDS_TO_NANO;
	private static final long SPAWN_INTERVAL = 1 * SECONDS_TO_NANO;
	private static final int ALIEN_MOVE_SPEED = 350;
	
	// Offsets from warning coordinates for the forcefield areas
	private static final int FORCEFIELD_H_X_OFFSET = 1000;
	private static final int FORCEFIELD_H_Y_OFFSET = 125;
	private static final int FORCEFIELD_V_X_OFFSET = 100;
	private static final int FORCEFIELD_V_Y_OFFSET = 800;
	
	// Storage variables to track warned coordinates
	private float warnX;
	private float warnY;

	public LevelTen(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);
		
		enemyCount = 0;
		warnings = new Array<Warning>();
		
		isWarningShown = isForcefieldShown = false;
		
		lastWarningTime = game.timeReference.nanoTime();
		spawnWarnings(lastWarningTime);
		lastForcefieldTime = lastWarningTime + WARNING_DURATION;
	}

	@Override
	protected void spawnAliens() {
		if (enemyCount > NUMBER_TO_WIN) {
			aliens = new Array<Alien>();
			passLevel();
			return;
		}
		
		long currentTime = game.timeReference.nanoTime();
		
		if (isForcefieldShown) {
			// Check if it is time to remove forcefields
			if (currentTime - lastForcefieldTime > FORCEFIELD_DURATION) {
				isForcefieldShown = false;
				lastForcefieldTime = currentTime + WARNING_DURATION;
				spawnWarnings(currentTime);
			}			
		} else {
			// Check if it is time to spawn forcefields
			if (currentTime - lastWarningTime > WARNING_DURATION) {
				isForcefieldShown = true;
				lastWarningTime = currentTime + FORCEFIELD_DURATION;
				killAllWarnings();
				isWarningShown = false;
			}
		}
		
		if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
			lastSpawnTime = currentTime;
			spawnAll(lastSpawnTime);
		}
	}
	
	private void spawnWarnings(long currentTime) {
		if (!isWarningShown) {
			float showX = MathUtils.random(5, 995);
			float showY = MathUtils.random(5, 795);
			warnX = showX;
			warnY = showY;
			warnings.add(new Warning(showX, showY, currentTime));
			isWarningShown = true;
		}
	}
	
	private void killAllWarnings() {
		warnings.clear();
	}
	
	private void spawnAll(long currentTime) {
		float startX1 = MathUtils.random(0, 1000);
		float startY1 = MathUtils.random(0, 800);
		float startX2 = MathUtils.random(0, 1000);
		float startY2 = MathUtils.random(0, 800);
		float startX3 = MathUtils.random(0, 1000);
		float startY3 = MathUtils.random(0, 800);
		
		boolean spawnHorizontal = MathUtils.randomBoolean();
		boolean swapDir = MathUtils.randomBoolean();
		if (spawnHorizontal) {
			spawnHorizontal(swapDir, startY1, currentTime);
			spawnVertical(swapDir, startX2, currentTime);
			spawnHorizontal(swapDir, startY3, currentTime);
		} else {
			spawnVertical(swapDir, startX1, currentTime);
			spawnHorizontal(swapDir, startY2, currentTime);
			spawnVertical(swapDir, startX3, currentTime);
		}
		
		if (player.isAlive()) {
			enemyCount++;
		}
	}
	
	private void spawnHorizontal(boolean swapDirection, float y, long currentTime) {
		if (swapDirection) aliens.add(new Alien(1000, y, ALIEN_TYPE, ALIEN_MOVE_SPEED * -1, 0, currentTime));
		else aliens.add(new Alien(0, y, ALIEN_TYPE, ALIEN_MOVE_SPEED, 0, currentTime));
	}
	
	private void spawnVertical(boolean swapDirection, float x, long currentTime) {
		if (swapDirection) aliens.add(new Alien(x, 800, ALIEN_TYPE, 0, ALIEN_MOVE_SPEED * -1, currentTime));
		else aliens.add(new Alien(x, 0, ALIEN_TYPE, 0, ALIEN_MOVE_SPEED, currentTime));
	}
	
	private boolean isWithinForcefieldH(float warningX, float warningY) {
		float y = player.getY();
		if (y >= (warningY - FORCEFIELD_H_Y_OFFSET) &&
			y <= (warningY + FORCEFIELD_H_Y_OFFSET)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isWithinForcefieldV(float warningX, float warningY) {
		float x = player.getX();
		if (x >= (warningX - FORCEFIELD_V_X_OFFSET) &&
			x <= (warningX + FORCEFIELD_V_X_OFFSET)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected void drawUnits() {	
		super.drawUnits();
		drawWarnings();
		drawForcefields();
	}
	
	protected void drawWarnings() {
		for (Warning warning : warnings) {
			game.batch.draw(warning.getImage(), warning.getX(), warning.getY());
			game.batch.draw(Assets.ForcefieldH_Warn,
							warning.getX() - FORCEFIELD_H_X_OFFSET,
							warning.getY() - FORCEFIELD_H_Y_OFFSET);
			game.batch.draw(Assets.ForcefieldV_Warn,
							warning.getX() - FORCEFIELD_V_X_OFFSET,
							warning.getY() - FORCEFIELD_V_Y_OFFSET);
		}
	}
	
	protected void drawForcefields() {
		if (isForcefieldShown) {
			game.batch.draw(Assets.ForcefieldH,
							warnX - FORCEFIELD_H_X_OFFSET,
							warnY - FORCEFIELD_H_Y_OFFSET);
			game.batch.draw(Assets.ForcefieldV,
							warnX - FORCEFIELD_V_X_OFFSET,
							warnY - FORCEFIELD_V_Y_OFFSET);
			if (isWithinForcefieldH(warnX, warnY) || isWithinForcefieldV(warnX, warnY)
				&& !player.isInvulnerable()) player.minusOneLife();
		}
	}
	
	@Override
	protected void checkCollisions() {
		super.checkCollisions();
	}
}
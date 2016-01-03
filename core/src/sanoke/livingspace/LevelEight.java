package sanoke.livingspace;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;

public class LevelEight extends LevelTemplate {
	Array<Warning> warnings;
	
	private long lastSpawnTime; // use this to spawn aliens
	private long lastWarningTime; // use this to spawn warnings
	
	private int warningCounter;
	
	private GridPoint2 [] spawnLocations;
	private GridPoint2 [] warningLocations;
	
	private boolean isIncremented;
	private boolean isSpawned;
	private boolean toRefresh;
	
	private static final int CURRENT_LEVEL = 8;
	
	private static final int NUM_PLACES_TO_SPAWN = 320; // 320 coordinates to fill up whole screen
	private static final int ALIEN_TYPE = 4;
	
	private static final long WARNING_DURATION = 5000;
	private static final long SPAWN_DURATION = 3000;
	
	// Offsets from warning coordinates for the safe area
	private static final int SAFE_AREA_X_OFFSET = 60;
	private static final int SAFE_AREA_Y_OFFSET = 60;

	public LevelEight(final LivingSpaceGame game, Spaceship player) {
		super(game, player, CURRENT_LEVEL);
		
		warnings = new Array<Warning>();
		warningCounter = 0;
		warningLocations = new GridPoint2[9]; // initialise 9 random safety area coordinates
		
		isIncremented = isSpawned = toRefresh = false;
		
		spawnLocations = new GridPoint2[NUM_PLACES_TO_SPAWN];
		
		setSpawnLocations();
		setWarningLocations();
		
		lastWarningTime = TimeUtils.nanoTime();
		spawnWarnings(lastWarningTime);
		lastSpawnTime = lastWarningTime - WARNING_DURATION;
	}
	
	private void setSpawnLocations() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 20; j++) {
				int spawnX = j * (Assets.ALIEN_WIDTH + 14);
				int spawnY = i * (Assets.ALIEN_HEIGHT + 8);
				spawnLocations[i * 20 + j] = new GridPoint2(spawnX, spawnY);
			}
		}
	}
	
	private void setWarningLocations() {
		for (int i = 0; i < 9; i++) {
			int spawnX = MathUtils.random(SAFE_AREA_X_OFFSET, 1000 - SAFE_AREA_X_OFFSET);
			int spawnY = MathUtils.random(SAFE_AREA_Y_OFFSET, 800 - SAFE_AREA_Y_OFFSET);
			warningLocations[i] = new GridPoint2(spawnX, spawnY);
		}
	}

	@Override
	protected void spawnAliens() {		
		if (warningCounter > 8) {
			isEnd = true;
			aliens = new Array<Alien>();
			return;
		}
		
		long currentTime = TimeUtils.nanoTime();
		
		// if time to show safety area
		if (!toRefresh && currentTime - lastSpawnTime > (SPAWN_DURATION * 1000000)) {
			lastSpawnTime = currentTime;
			isSpawned = false;
			aliens = new Array<Alien>();
			spawnWarnings(currentTime);
		}
		
		// if time to spawn aliens
		if (!toRefresh && currentTime - lastWarningTime > (WARNING_DURATION * 1000000)) {
			lastWarningTime = currentTime;
			killAllWarnings();
			isIncremented = false;
			spawnAll(currentTime);
		}
	}
	
	private void spawnWarnings(long currentTime) {
		if (!isIncremented) {
			warnings.add(new Warning(warningLocations[warningCounter].x,
								 	 warningLocations[warningCounter].y, currentTime));
			warningCounter += 1;
		}
		isIncremented = true;
	}
	
	private void killAllWarnings() {
		warnings.clear();
	}
	
	private void spawnAll(long currentTime) {
		if (!isSpawned) {
			for (int i = 0; i < NUM_PLACES_TO_SPAWN; i++) {
				int xPos = spawnLocations[i].x;
				int yPos = spawnLocations[i].y;
			
				if (!isWithinSafety(xPos, yPos)) {
					aliens.add(new Alien(xPos, yPos, ALIEN_TYPE, 0, 0));
				}
			
				if (player.isAlive()) {}
			}
		}
		isSpawned = true;
	}
	
	private boolean isWithinSafety(int x, int y) {
		if (x >= (warningLocations[warningCounter - 1].x - SAFE_AREA_X_OFFSET) &&
			x <= (warningLocations[warningCounter - 1].x + SAFE_AREA_X_OFFSET) &&
			y >= (warningLocations[warningCounter - 1].y - SAFE_AREA_Y_OFFSET) &&
			y <= (warningLocations[warningCounter - 1].y + SAFE_AREA_Y_OFFSET)) return true;
		else return false;
	}
	
	@Override
	protected void updateAliensPosition(float delta) {
		/*
		float timeElapsed = TimeUtils.millis() - lastSpawnTime;

		if (timeElapsed > SPAWN_DURATION) {
			aliens = new Array<Alien>();
		}
		*/
	}
	
	@Override
	protected void drawUnits() {	
		super.drawUnits();
		drawWarnings();
	}
	
	protected void drawWarnings() {
		for (Warning warning : warnings) {
			game.batch.draw(warning.getImage(), warning.getX(), warning.getY());
			game.batch.draw(Assets.safetyAlert, warning.getX() - SAFE_AREA_X_OFFSET + 15,
											    warning.getY() - SAFE_AREA_Y_OFFSET + 15);
		}
	}
	
	@Override
	protected void checkCollisions() {
		super.checkCollisions();
	}
}
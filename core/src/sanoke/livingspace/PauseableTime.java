package sanoke.livingspace;

import com.badlogic.gdx.utils.TimeUtils;

public class PauseableTime {
	private long timeToBacktrack;
	private long timePaused;
	
	private static final long MILLIS_TO_NANO = 1000000;
	
	public PauseableTime() {
		refresh();
	}
	
	public long millis() {
		return TimeUtils.millis() - timeToBacktrack / MILLIS_TO_NANO;
	}
	
	public long nanoTime() {
		return TimeUtils.nanoTime() - timeToBacktrack;
	}
	
	public void pause() {
		timePaused = TimeUtils.nanoTime();
	}
	
	public void resume() {
		long timeToAdd = TimeUtils.nanoTime() - timePaused;
		
		timeToBacktrack += timeToAdd;
	}
	
	public void refresh() {
		timeToBacktrack = 0;
	}
	
}

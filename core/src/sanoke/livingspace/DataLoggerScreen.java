package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.GridPoint2;

public class DataLoggerScreen implements Screen {
	final LivingSpaceGame game;
	private int level;
	Spaceship ship;
	
	private static final int OFF_BUTTON_MIN_X = 397;
	private static final int OFF_BUTTON_MIN_Y = 640;
	private static final int OFF_BUTTON_MAX_X = 836;
	private static final int OFF_BUTTON_MAX_Y = 720;
	
    private static final int ALIEN_ENTRY_MIN_X = 15;
    private static final int ALIEN_ENTRY_MAX_X = 202;
    private static final int ALIEN_ENTRY_MIN_Y = 30;
    private static final int ALIEN_ENTRY_HEIGHT = 60;
    private static final int ALIEN_ENTRY_Y_PADDING = 2;
    private static final int NUMBER_ALIEN_ENTRIES = Assets.NUMBER_ALIEN_ENTRIES;
    private static final int PARAM_ENTRY_NOT_FOUND = -1;
    
    private GridPoint2 [] entriesYCoord;
    
    private int screenNumber;
    
    private static final int PARAM_SCREEN_DEFAULT = -1;
    private static final int PARAM_SCREEN_INVALID = -2;
	
	public DataLoggerScreen(final LivingSpaceGame game, Spaceship player, int initLevel) {
		this.game = game;
		this.ship = player;
		this.level = initLevel;
		this.screenNumber = PARAM_SCREEN_DEFAULT;
		
		if (game.isSoundOn) Assets.music.play();
		
		setupAlienEntriesYCoord();
	}
	
	private void setupAlienEntriesYCoord() {
		entriesYCoord = new GridPoint2[NUMBER_ALIEN_ENTRIES];
		for (int i = 0; i < NUMBER_ALIEN_ENTRIES; i++) {
			int minEntry = ALIEN_ENTRY_MIN_Y + (ALIEN_ENTRY_HEIGHT + ALIEN_ENTRY_Y_PADDING) * i;
			int maxEntry = minEntry + ALIEN_ENTRY_HEIGHT;
			
			entriesYCoord[i] = new GridPoint2(minEntry, maxEntry);
		}
	}
	
	private void drawScreen() {
		if (screenNumber == PARAM_SCREEN_DEFAULT) {
			game.batch.draw(Assets.catalogueScreenDefault, 0, 0);
		} else if (screenNumber == PARAM_SCREEN_INVALID) {
			game.batch.draw(Assets.catalogueScreenInvalid, 0, 0);
		} else if (screenNumber >= 0 && screenNumber < NUMBER_ALIEN_ENTRIES) {
			game.batch.draw(Assets.catalogueScreenNumbers[screenNumber], 0, 0);
		} else {
			assert(false);
		}
	}

	private boolean isWithinAlienEntries(float x) {
		if (x <= ALIEN_ENTRY_MAX_X && x >= ALIEN_ENTRY_MIN_X) {
			return true;
		} else {
			return false;
		}
	}
	
	private void checkParticularEntry(float y) {
		int entryNumber = getEntryNumber(y);
		
		if (entryNumber == PARAM_ENTRY_NOT_FOUND) {
			return;
		} else if (entryNumber < level) {
			if (game.isSoundOn) Assets.buttonClickSound.play();
			screenNumber = entryNumber;
		} else {
			if (game.isSoundOn) Assets.buttonClickSound.play();
			screenNumber = PARAM_SCREEN_INVALID;
		}
	}
	
	private int getEntryNumber(float y) {
		for (int i = 0; i < NUMBER_ALIEN_ENTRIES; i++) {
			int maxEntry = entriesYCoord[i].y;
			if (y <= maxEntry) {
				int minEntry = entriesYCoord[i].x;
				
				if (y >= minEntry) {
					return i;
				} else {
					return PARAM_ENTRY_NOT_FOUND;
				}
			}
		}
		
		return PARAM_ENTRY_NOT_FOUND;
	}
    
    private boolean isWithinOff(float x, float y) {
    	if ((x >= OFF_BUTTON_MIN_X && x <= OFF_BUTTON_MAX_X) &&
    			(y >= OFF_BUTTON_MIN_Y && y <= OFF_BUTTON_MAX_Y)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // FUNCTION: Sets game screen to Pre Game Screen
    private void loadPreGameScreen() {
    	game.setPregameScreen(this.level);
    }  

    private void processInput() {
        processClick();
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinAlienEntries(xPos)) {
            	checkParticularEntry(yPos);
            } else if (isWithinOff(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
                loadPreGameScreen();
            }
        }
    }
    
    @Override
    public void render(float delta) {
    	game.batch.begin();
    	drawScreen();
    	processInput();
    	game.batch.end();
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

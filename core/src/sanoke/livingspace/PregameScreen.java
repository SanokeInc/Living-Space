package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class PregameScreen implements Screen {
	final LivingSpaceGame game;
	private int level;
	Spaceship ship;
	
	// Bottom-left & Top-right points x-y coordinates for Play button
    private static int PLAY_BUTTON_P1_X;
    private static int PLAY_BUTTON_P1_Y;
    private static int PLAY_BUTTON_P2_X;
    private static int PLAY_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Upgrades button
    private static int UPGRADES_BUTTON_P1_X;
    private static int UPGRADES_BUTTON_P1_Y;
    private static int UPGRADES_BUTTON_P2_X;
    private static int UPGRADES_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Quit button
    private static int QUIT_BUTTON_P1_X;
    private static int QUIT_BUTTON_P1_Y;
    private static int QUIT_BUTTON_P2_X;
    private static int QUIT_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Data Logger
    private static int DATA_LOGGER_P1_X;
    private static int DATA_LOGGER_P1_Y;
    private static int DATA_LOGGER_P2_X;
    private static int DATA_LOGGER_P2_Y;
	
	public PregameScreen(final LivingSpaceGame game, Spaceship player, int initLevel) {
		this.game = game;
		this.ship = player;
		this.level = initLevel;
		
		PLAY_BUTTON_P1_X = 480;
	    PLAY_BUTTON_P1_Y = 0;
	    PLAY_BUTTON_P2_X = 1000;
	    PLAY_BUTTON_P2_Y = 370;
	    
	    UPGRADES_BUTTON_P1_X = 0;
	    UPGRADES_BUTTON_P1_Y = 0;
	    UPGRADES_BUTTON_P2_X = 330;
	    UPGRADES_BUTTON_P2_Y = 800;
	    
	    QUIT_BUTTON_P1_X = 480;
	    QUIT_BUTTON_P1_Y = 400;
	    QUIT_BUTTON_P2_X = 1000;
	    QUIT_BUTTON_P2_Y = 800;
	    
	    DATA_LOGGER_P1_X = 360;
	    DATA_LOGGER_P1_Y = 30;
	    DATA_LOGGER_P2_X = 360 + 70;
	    DATA_LOGGER_P2_Y = 30 + 105;
	}

    
    // =============== METHODS FOR PREGAME SCREEN =============== //
    private void setupPregameScreen() {
    	game.batch.draw(Assets.pregameScreenDefault, 0, 0);
    }
    
    private boolean isWithinPlay(float x, float y) {
    	return ((x >= PLAY_BUTTON_P1_X && x <= PLAY_BUTTON_P2_X) &&
    			(y >= PLAY_BUTTON_P1_Y && y <= PLAY_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Game Screen
    private void loadGame() {
    	game.setLevelScreen(this.level);
    }
    
    private boolean isWithinUpgrades(float x, float y) {
    	return ((x >= UPGRADES_BUTTON_P1_X && x <= UPGRADES_BUTTON_P2_X) &&
    			(y >= UPGRADES_BUTTON_P1_Y && y <= UPGRADES_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Instructions Screen
    private void loadUpgrades() {
    	game.setUpgradeScreen();
    }
    
    private boolean isWithinQuit(float x, float y) {
    	return ((x >= QUIT_BUTTON_P1_X && x <= QUIT_BUTTON_P2_X) &&
    			(y >= QUIT_BUTTON_P1_Y && y <= QUIT_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Instructions Screen
    private void loadMainScreen() {
    	game.setMainScreen(false);
    }
    
    private boolean isWithinDataLogger(float x, float y) {
    	return ((x >= DATA_LOGGER_P1_X && x <= DATA_LOGGER_P2_X) &&
    			(y >= DATA_LOGGER_P1_Y && y <= DATA_LOGGER_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Data Logger Screen
    private void loadDataLoggerScreen() {
    	game.setDataLoggerScreen(this.level);
    }

    private void processInput() {
    	processMouseOver();
        processClick();
    }
    
    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if (isWithinPlay(xPos, yPos)) {
            game.batch.draw(Assets.pregameScreenPlay, 0, 0);
        } else if (isWithinUpgrades(xPos, yPos)) {
            game.batch.draw(Assets.pregameScreenUpgrades, 0, 0);
        } else if (isWithinQuit(xPos, yPos)) {
        	game.batch.draw(Assets.pregameScreenQuit, 0, 0);
        } else {
        	game.batch.draw(Assets.pregameScreenDefault, 0, 0);
        }
        
        if (isWithinDataLogger(xPos, yPos)) {
        	game.batch.draw(Assets.dataLoggerSelect, DATA_LOGGER_P1_X, game.HEIGHT - DATA_LOGGER_P2_Y);
        } else {
        	game.batch.draw(Assets.dataLogger, DATA_LOGGER_P1_X, game.HEIGHT - DATA_LOGGER_P2_Y);
        }
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinPlay(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
                loadGame();
            } else if (isWithinUpgrades(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
                loadUpgrades();
            } else if (isWithinQuit(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
            	loadMainScreen();
            } else if (isWithinDataLogger(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
            	loadDataLoggerScreen();
            }
        }
    }

    // =============== !METHODS FOR PREGAME SCREEN =============== //



	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    	game.batch.begin();
    	setupPregameScreen();
    	processInput();
    	game.batch.end();
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
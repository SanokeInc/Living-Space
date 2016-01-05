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

    private void processInput() {
        processClick();
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinPlay(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                loadGame();
            } else if (isWithinUpgrades(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                loadUpgrades();
            } else if (isWithinQuit(xPos, yPos)) {
            	Assets.buttonClickSound.play();
            	loadMainScreen();
            } else {
            	
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
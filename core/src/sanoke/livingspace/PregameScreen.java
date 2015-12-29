package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class PregameScreen implements Screen {
	final LivingSpaceGame game;
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
	
	public PregameScreen(final LivingSpaceGame game, Spaceship player) {
		this.game = game;
		this.ship = player;
		
		PLAY_BUTTON_P1_X = 704;
	    PLAY_BUTTON_P1_Y = 0;
	    PLAY_BUTTON_P2_X = 965;
	    PLAY_BUTTON_P2_Y = 237;
	    
	    UPGRADES_BUTTON_P1_X = 0;
	    UPGRADES_BUTTON_P1_Y = 375;
	    UPGRADES_BUTTON_P2_X = 240;
	    UPGRADES_BUTTON_P2_Y = 772;
	    
	    QUIT_BUTTON_P1_X = 484;
	    QUIT_BUTTON_P1_Y = 502;
	    QUIT_BUTTON_P2_X = 868;
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
    	game.setLevelScreen(1);
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
    	game.setMainScreen();
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
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinPlay(xPos, yPos)) {
                loadGame();
            } else if (isWithinUpgrades(xPos, yPos)) {
                loadUpgrades();
            } else if (isWithinQuit(xPos, yPos)) {
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
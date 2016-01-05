/* ========== ADDED ========== */
package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class PauseScreen implements Screen {
	final LivingSpaceGame game;
	Screen state;
	Spaceship player;
	private int resumeCode;
	
	// Bottom-left & Top-right points x-y coordinates for Resume button
    private static int RESUME_BUTTON_P1_X;
    private static int RESUME_BUTTON_P1_Y;
    private static int RESUME_BUTTON_P2_X;
    private static int RESUME_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Quit button
    private static int QUIT_BUTTON_P1_X;
    private static int QUIT_BUTTON_P1_Y;
    private static int QUIT_BUTTON_P2_X;
    private static int QUIT_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Sound button
    private static final int SOUND_BUTTON_P1_X = 50;
    private static final int SOUND_BUTTON_P1_Y = 100;
    private static final int SOUND_BUTTON_P2_X = 125;
    private static final int SOUND_BUTTON_P2_Y = 175;
	
	public PauseScreen(final LivingSpaceGame game, int returnCode, Spaceship player) {
		this.game = game;
		this.resumeCode = returnCode;
		this.state = game.getScreen();
		this.player = player;
		game.timeReference.pause();
		
		RESUME_BUTTON_P1_X = 275;
	    RESUME_BUTTON_P1_Y = 424;
	    RESUME_BUTTON_P2_X = 739;
	    RESUME_BUTTON_P2_Y = 495;
	    
	    QUIT_BUTTON_P1_X = 275;
	    QUIT_BUTTON_P1_Y = 556;
	    QUIT_BUTTON_P2_X = 739;
	    QUIT_BUTTON_P2_Y = 627;
	}

    
    // =============== METHODS FOR PAUSE SCREEN =============== //
    private void setupPauseScreen() {
    	game.batch.draw(Assets.pauseScreenDefault, 0, 0);
    	if (game.isSoundOn) game.batch.draw(Assets.soundOn, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
    	else game.batch.draw(Assets.soundOff, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
    }
    
    private boolean isWithinResume(float x, float y) {
    	return ((x >= RESUME_BUTTON_P1_X && x <= RESUME_BUTTON_P2_X) &&
    			(y >= RESUME_BUTTON_P1_Y && y <= RESUME_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Return to Game Screen
    private void loadGame() {
    	game.timeReference.resume();
    	game.setResume(this.resumeCode);
    	game.setScreen(this.state);
    }
    
    private boolean isWithinQuit(float x, float y) {
    	return ((x >= QUIT_BUTTON_P1_X && x <= QUIT_BUTTON_P2_X) &&
    			(y >= QUIT_BUTTON_P1_Y && y <= QUIT_BUTTON_P2_Y)) ? true : false;
    }

    private boolean isWithinSoundSetting(float x, float y) {
    	return ((x >= SOUND_BUTTON_P1_X && x <= SOUND_BUTTON_P2_X) &&
                (y >= 2 * SOUND_BUTTON_P1_Y - SOUND_BUTTON_P2_Y && y <= SOUND_BUTTON_P1_Y)) ? true : false;
    }
    
    private void processInput() {
        processMouseOver();
        processClick();
    }
    
    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        
        if (isWithinResume(xPos, yPos)) {
            game.batch.draw(Assets.pauseScreenResume, 0, 0);
        } else if (isWithinQuit(xPos, yPos)) {
        	game.batch.draw(Assets.pauseScreenQuit, 0, 0);	
        } else if (isWithinSoundSetting(xPos, yPos)) {
        	if (game.isSoundOn) game.batch.draw(Assets.soundOnHighlight, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
        	else game.batch.draw(Assets.soundOffHighlight, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
        	return;
        } else {
        	game.batch.draw(Assets.pauseScreenDefault, 0, 0);
        }
        
        if (game.isSoundOn) game.batch.draw(Assets.soundOn, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
        	else game.batch.draw(Assets.soundOff, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinResume(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
                loadGame();
            } else if (isWithinQuit(xPos, yPos)) {
            	if (game.isSoundOn) Assets.buttonClickSound.play();
            	Gdx.app.exit();
            } else if (isWithinSoundSetting(xPos, yPos)) {
            	if (game.isSoundOn) {
            		game.isSoundOn = false;
            		player.setSound(game.isSoundOn);
            		game.batch.draw(Assets.soundOff, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
            	}
            	else {
            		game.isSoundOn = true;
            		Assets.buttonClickSound.play();
            		player.setSound(game.isSoundOn);
            		game.batch.draw(Assets.soundOn, SOUND_BUTTON_P1_X, game.HEIGHT - SOUND_BUTTON_P1_Y);
            	}
            } else {
            	
            }
        }
    }

    // =============== !METHODS FOR PAUSE SCREEN =============== //



	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    	game.batch.begin();
    	setupPauseScreen();
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

/* ========== !ADDED ========== */
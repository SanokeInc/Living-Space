package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class InstructionScreen implements Screen {
    final LivingSpaceGame game;
    
    // Bottom-left & Top-right points x-y coordinates for Acknowledgement button
    private static int OK_BUTTON_P1_X;
    private static int OK_BUTTON_P1_Y;
    private static int OK_BUTTON_P2_X;
    private static int OK_BUTTON_P2_Y;

    public InstructionScreen(final LivingSpaceGame game) {
        this.game = game;
        
        OK_BUTTON_P1_X = 326;
        OK_BUTTON_P1_Y = 637;
        OK_BUTTON_P2_X = 670;
        OK_BUTTON_P2_Y = 692;
    }
    
    // =============== METHODS FOR MAIN MENU SCREEN =============== //
    private void setupInstructionScreen() {
    	game.batch.draw(Assets.instructionScreenDefault, 0, 0);
    }
    
    private boolean isWithinOK(float x, float y) {
    	return ((x >= OK_BUTTON_P1_X && x <= OK_BUTTON_P2_X) &&
    			(y >= OK_BUTTON_P1_Y && y <= OK_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Main Screen
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
        if (isWithinOK(xPos, yPos)) {
            game.batch.draw(Assets.instructionScreenOK, 0, 0);
        } else {
        	game.batch.draw(Assets.instructionScreenDefault, 0, 0);
        }
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinOK(xPos, yPos)) {
                loadMainScreen();
            } else {
            	
            }
        }
    }

    // =============== !METHODS FOR MAIN MENU SCREEN =============== //
    
    @Override
    public void show() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    	game.batch.begin();
    	setupInstructionScreen();
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


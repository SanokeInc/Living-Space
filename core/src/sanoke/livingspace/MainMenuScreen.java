package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    final LivingSpaceGame game;
    
    // Bottom-left & Top-right points x-y coordinates for Play button
    private static int PLAY_BUTTON_P1_X;
    private static int PLAY_BUTTON_P1_Y;
    private static int PLAY_BUTTON_P2_X;
    private static int PLAY_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Instructions button
    private static int INSTRUCTION_BUTTON_P1_X;
    private static int INSTRUCTION_BUTTON_P1_Y;
    private static int INSTRUCTION_BUTTON_P2_X;
    private static int INSTRUCTION_BUTTON_P2_Y;

    OrthographicCamera camera;
    public MainMenuScreen(final LivingSpaceGame game) {
        this.game = game;
        
        PLAY_BUTTON_P1_X = 469;
        PLAY_BUTTON_P1_Y = 498;
        PLAY_BUTTON_P2_X = 853;
        PLAY_BUTTON_P2_Y = 557;
        
        INSTRUCTION_BUTTON_P1_X = 469;
        INSTRUCTION_BUTTON_P1_Y = 642;
        INSTRUCTION_BUTTON_P2_X = 853;
        INSTRUCTION_BUTTON_P2_Y = 697;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
    }
    
    // =============== METHODS FOR MAIN MENU SCREEN =============== //
    private void setupMainScreen() {
    	game.batch.draw(Assets.mainScreenDefault, 0, 0);
    }
    
    private boolean isWithinPlay(float x, float y) {
    	return ((x >= PLAY_BUTTON_P1_X && x <= PLAY_BUTTON_P2_X) &&
    			(y >= PLAY_BUTTON_P1_Y && y <= PLAY_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Pregame Screen
    private void loadPreGame() {
    	game.setPregameScreen(game.level);
    }
    
    private boolean isWithinInstructions(float x, float y) {
    	return ((x >= INSTRUCTION_BUTTON_P1_X && x <= INSTRUCTION_BUTTON_P2_X) &&
    			(y >= INSTRUCTION_BUTTON_P1_Y && y <= INSTRUCTION_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Instructions Screen
    private void loadInstructions() {
    	game.setInstructionScreen();
    }

    private void processInput() {
        processMouseOver();
        processClick();
    }
    
    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if (isWithinPlay(xPos, yPos)) {
            game.batch.draw(Assets.mainScreenEnter, 0, 0);
        } else if (isWithinInstructions(xPos, yPos)) {
            game.batch.draw(Assets.mainScreenInstructions, 0, 0);
        } else {
        	game.batch.draw(Assets.mainScreenDefault, 0, 0);
        }
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinPlay(xPos, yPos)) {
                loadPreGame();
            } else if (isWithinInstructions(xPos, yPos)) {
                loadInstructions();
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
    	setupMainScreen();
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


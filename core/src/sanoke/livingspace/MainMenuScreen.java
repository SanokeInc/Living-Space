package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    private LivingSpaceGame game;
    Spaceship player;
    
    private boolean isEasyMode;
    
    // Bottom-left & Top-right points x-y coordinates for Play button
    private static final int PLAY_BUTTON_P1_X = 466;
    private static final int PLAY_BUTTON_P1_Y = 498;
    private static final int PLAY_BUTTON_P2_X = 857;
    private static final int PLAY_BUTTON_P2_Y = 556;

    private static final int EASYMODE_BUTTON_P1_X = 466;
    private static final int EASYMODE_BUTTON_P1_Y = 597;
    private static final int EASYMODE_BUTTON_P2_X = 857;
    private static final int EASYMODE_BUTTON_P2_Y = 660;
    
    // Bottom-left & Top-right points x-y coordinates for Instructions button
    private static final int INSTRUCTION_BUTTON_P1_X = 466;
    private static final int INSTRUCTION_BUTTON_P1_Y = 695;
    private static final int INSTRUCTION_BUTTON_P2_X = 857;
    private static final int INSTRUCTION_BUTTON_P2_Y = 751;
    
    

    OrthographicCamera camera;
    public MainMenuScreen(final LivingSpaceGame game, Spaceship player) {
        this.game = game;
        this.player = player;
        isEasyMode = false;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
    }
    
    @Override
    public void render(float delta) {
    	game.batch.begin();
    	setupMainScreen();
    	processInput();
    	drawEasySelected();
    	game.batch.end();
    }

    private void setupMainScreen() {
    	game.batch.draw(Assets.mainScreenDefault, 0, 0);
    }

    private void drawEasySelected() {
        if (isEasyMode) {
            game.batch.draw(Assets.mainScreenEasySelect, EASYMODE_BUTTON_P1_X,
                    game.HEIGHT - EASYMODE_BUTTON_P2_Y);
        }
    }
    
    private boolean isWithinPlay(float x, float y) {
        return ((x >= PLAY_BUTTON_P1_X && x <= PLAY_BUTTON_P2_X) &&
                (y >= PLAY_BUTTON_P1_Y && y <= PLAY_BUTTON_P2_Y)) ? true : false;
    }
    
    private boolean isWithinEasyMode(float x, float y) {
        return ((x >= EASYMODE_BUTTON_P1_X && x <= EASYMODE_BUTTON_P2_X) &&
                (y >= EASYMODE_BUTTON_P1_Y && y <= EASYMODE_BUTTON_P2_Y)) ? true : false;
    }
    
    // Sets game screen to Pregame Screen
    private void loadPreGame() {
        player.setEasyMode(isEasyMode);
    	game.setPregameScreen(game.level);
    }
    
    private boolean isWithinInstructions(float x, float y) {
    	return ((x >= INSTRUCTION_BUTTON_P1_X && x <= INSTRUCTION_BUTTON_P2_X) &&
    			(y >= INSTRUCTION_BUTTON_P1_Y && y <= INSTRUCTION_BUTTON_P2_Y)) ? true : false;
    }
    
    // Sets game screen to Instructions Screen
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
        } else if (isWithinEasyMode(xPos, yPos) && !isEasyMode) {
            game.batch.draw(Assets.mainScreenEasy, 0, 0);
        } else if (isWithinInstructions(xPos, yPos)) {
            game.batch.draw(Assets.mainScreenInstructions, 0, 0);
        }
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinPlay(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                loadPreGame();
            } else if (isWithinInstructions(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                loadInstructions();
            } else if (isWithinEasyMode(xPos, yPos)) {
                Assets.buttonClickSound.play();
            	isEasyMode = !isEasyMode;
            }
        }
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


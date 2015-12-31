package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class EndScreen implements Screen {
    final LivingSpaceGame game;
    
    private int currentFrameNumber;
	private static final int FRAME_SWITCH_DELAY = 38;
	
	private Texture image;
    
    // Bottom-left & Top-right points x-y coordinates for Play button
    private static int MENU_BUTTON_P1_X;
    private static int MENU_BUTTON_P1_Y;
    private static int MENU_BUTTON_P2_X;
    private static int MENU_BUTTON_P2_Y;
    
    // Bottom-left & Top-right points x-y coordinates for Instructions button
    private static int QUIT_BUTTON_P1_X;
    private static int QUIT_BUTTON_P1_Y;
    private static int QUIT_BUTTON_P2_X;
    private static int QUIT_BUTTON_P2_Y;

    public EndScreen(final LivingSpaceGame game) {
        this.game = game;
        this.image = Assets.endScreenFrames[0];
        this.currentFrameNumber = 0;
        
        MENU_BUTTON_P1_X = 614;
        MENU_BUTTON_P1_Y = 568;
        MENU_BUTTON_P2_X = 888;
        MENU_BUTTON_P2_Y = 638;
        
        QUIT_BUTTON_P1_X = 614;
        QUIT_BUTTON_P1_Y = 669;
        QUIT_BUTTON_P2_X = 888;
        QUIT_BUTTON_P2_Y = 743;
    }
    
    // =============== METHODS FOR END SCREEN =============== //
    private void setupEndScreen(float delta) {
    	game.batch.draw(this.getImage(), 0, 0);
    }
    
    private boolean isWithinMenu(float x, float y) {
    	return ((x >= MENU_BUTTON_P1_X && x <= MENU_BUTTON_P2_X) &&
    			(y >= MENU_BUTTON_P1_Y && y <= MENU_BUTTON_P2_Y)) ? true : false;
    }
    
    // FUNCTION: Sets game screen to Pregame Screen
    private void loadMenu() {
    	game.setScreen(new MainMenuScreen(new LivingSpaceGame()));
    }
    
    private boolean isWithinQuit(float x, float y) {
    	return ((x >= QUIT_BUTTON_P1_X && x <= QUIT_BUTTON_P2_X) &&
    			(y >= QUIT_BUTTON_P1_Y && y <= QUIT_BUTTON_P2_Y)) ? true : false;
    }
    
    private void processInput() {
        processMouseOver();
        processClick();
    }
    
    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if (isWithinMenu(xPos, yPos)) {
            game.batch.draw(Assets.endScreenMenu, 0, 0);
        } else if (isWithinQuit(xPos, yPos)) {
            game.batch.draw(Assets.endScreenQuit, 0, 0);
        } else {
        	
        }
    }
    
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinMenu(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                loadMenu();
            } else if (isWithinQuit(xPos, yPos)) {
            	Assets.buttonClickSound.play();
                Gdx.app.exit();
            } else {
            	
            }
        }
    }

    public Texture getImage() {
		Texture imageToShow = image;
		currentFrameNumber = (currentFrameNumber + 1) % (4 * FRAME_SWITCH_DELAY); 
		image = Assets.endScreenFrames[currentFrameNumber / FRAME_SWITCH_DELAY];
		
		return imageToShow;
	}
    // =============== !METHODS FOR END SCREEN =============== //
    
    @Override
    public void show() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    	game.batch.begin();
    	setupEndScreen(delta);
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


package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class UpgradeScreen implements Screen {
    final LivingSpaceGame game;
    private int status;

    Spaceship ship;

    private static final int NORMAL = 0;
    private static final int MOUSE_OVER = 1;
    private static final int MOUSE_OVER_QUIT = 2;

    private static final int COORD_X_SPEED_BOOST_TOP = 891;
    private static final int COORD_Y_SPEED_BOOST_TOP = 231;
    private static final int COORD_X_SPEED_BOOST_BTM = 953;
    private static final int COORD_Y_SPEED_BOOST_BTM = 297;
    
    private static final int COORD_X_MISSILES_BOOST_TOP = 891;
    private static final int COORD_Y_MISSILES_BOOST_TOP = 386;
    private static final int COORD_X_MISSILES_BOOST_BTM = 953;
    private static final int COORD_Y_MISSILES_BOOST_BTM = 451;
    
    private static final int COORD_X_LIVES_BOOST_TOP = 891;
    private static final int COORD_Y_LIVES_BOOST_TOP = 542;
    private static final int COORD_X_LIVES_BOOST_BTM = 953;
    private static final int COORD_Y_LIVES_BOOST_BTM = 608;

    public UpgradeScreen(final LivingSpaceGame game, Spaceship ship) {
        this.game = game;
        status = NORMAL;
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        drawScreen();
        processInput();
        game.batch.end();

    }

    private void drawScreen() {
        game.batch.draw(Assets.screenUpgradeNormal, 0, 0);
    }

    private void processInput() {
        processMouseOver();
        // processClick();
    }

    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if (isWithinSpeedUpgradeButton(xPos, yPos)) {
            game.batch.draw(Assets.upgradesHighlight, COORD_X_SPEED_BOOST_TOP,
                    game.HEIGHT - COORD_Y_SPEED_BOOST_BTM);
        } else if (isWithinMissileUpgradeButton(xPos, yPos)) {
            game.batch.draw(Assets.upgradesHighlight, COORD_X_MISSILES_BOOST_TOP,
                    game.HEIGHT - COORD_Y_MISSILES_BOOST_BTM);
        } else if (isWithinLivesUpgradeButton(xPos, yPos)) {
            game.batch.draw(Assets.upgradesHighlight, COORD_X_LIVES_BOOST_TOP,
                    game.HEIGHT - COORD_Y_LIVES_BOOST_BTM);
        } else {
            status = NORMAL;
        }
    }

    private boolean isWithinSpeedUpgradeButton(float x, float y) {
        if (x >= COORD_X_SPEED_BOOST_TOP && x <= COORD_X_SPEED_BOOST_BTM
                && y >= COORD_Y_SPEED_BOOST_TOP && y <= COORD_Y_SPEED_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isWithinMissileUpgradeButton(float x, float y) {
        if (x >= COORD_X_MISSILES_BOOST_TOP && x <= COORD_X_MISSILES_BOOST_BTM
                && y >= COORD_Y_MISSILES_BOOST_TOP && y <= COORD_Y_MISSILES_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isWithinLivesUpgradeButton(float x, float y) {
        if (x >= COORD_X_LIVES_BOOST_TOP && x <= COORD_X_LIVES_BOOST_BTM
                && y >= COORD_Y_LIVES_BOOST_TOP && y <= COORD_Y_LIVES_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * private void processClick() { if (Gdx.input.justTouched()) { float xPos =
     * Gdx.input.getX(); float yPos = Gdx.input.getY(); if
     * (isWithinTryAgainButton(xPos, yPos)) { game.restart(); } else if
     * (isWithinQuitButton(xPos, yPos)) { Gdx.app.exit(); } else { status =
     * NORMAL; } } }
     */

    /*
     * 
     * 
     * private boolean isWithinQuitButton(float x, float y) { if (x >=
     * COORD_X_QUIT_TOP && x <= COORD_X_QUIT_BTM && y >= COORD_Y_QUIT_TOP && y
     * <= COORD_Y_QUIT_BTM) { return true; } else { return false; } }
     */
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

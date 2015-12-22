package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    final LivingSpaceGame game;

    OrthographicCamera camera;

    public GameScreen(final LivingSpaceGame game) {
        Assets.loadAssets();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
        // Assets.playMusic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        // game.batch.draw(Assets.background, 0, 0);

        // updateUnitsPosition(delta);
        // drawUnits();

        game.batch.end();
        processInput();
    }

    // For animation purposes.
    /*private void updateUnitsPosition(float delta) {
        Array<Unit> fallingUnits = board.getFallingUnits();

        for (int i = 0; i < fallingUnits.size; i++) {
            Unit unit = fallingUnits.get(i);
            if (unit.isFalling()) {
                unit.setRow(Math.max(unit.getRow() - delta * FALL_RATE,
                        unit.getFinalRow()));
            } else {
                fallingUnits.removeIndex(i);
                i--;
            }
        }
    }*/

    private void processInput() {
        if (Gdx.input.justTouched()) {
            /*float xPos = (Gdx.input.getX() - BOARD_X_OFFSET);
            float yPos = (game.HEIGHT - BOARD_Y_OFFSET - Gdx.input.getY());
            // lazy validation for positive numbers
            if (xPos >= 0 && yPos >= 0) {
                board.highlightAndSwapUnit((int) yPos / Assets.UNIT_LENGTH,
                        (int) xPos / Assets.UNIT_WIDTH);
            }*/
        }

    }

    /* Draw at particular position.
    private void drawUnits() {
        for (int c = 0; c < NUM_COLS; c++) {
            Array<Unit> col = board.getCol(c);
            for (int r = 0; r < NUM_ROWS; r++) {
                Unit unit = col.get(r);
                if (unit.isSelected()) {
                    unitTexture = Assets.selectedTextures[unit.getType()];
                } else {
                    unitTexture = Assets.unselectedTextures[unit.getType()];
                }
                float xPos = c * Assets.UNIT_WIDTH + BOARD_X_OFFSET;
                float yPos = unit.getRow() * Assets.UNIT_LENGTH
                        + BOARD_Y_OFFSET;
                if (yPos <= NUM_ROWS * Assets.UNIT_LENGTH + BOARD_Y_OFFSET) {
                    game.batch.draw(unitTexture, xPos, yPos);
                }

            }
        }
    }*/

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

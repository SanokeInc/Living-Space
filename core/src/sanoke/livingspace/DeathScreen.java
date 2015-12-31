package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class DeathScreen implements Screen {
	final LivingSpaceGame game;
	private int status;

	private static final int NORMAL = 0;
	private static final int MOUSE_OVER_RETRY = 1;
	private static final int MOUSE_OVER_QUIT = 2;

	private static final int COORD_X_TRY_AGAIN_TOP = 67;
	private static final int COORD_Y_TRY_AGAIN_TOP = 649;
	private static final int COORD_X_TRY_AGAIN_BTM = 442;
	private static final int COORD_Y_TRY_AGAIN_BTM = 736;

	private static final int COORD_X_QUIT_TOP = 551;
	private static final int COORD_Y_QUIT_TOP = 649;
	private static final int COORD_X_QUIT_BTM = 927;
	private static final int COORD_Y_QUIT_BTM = 736;

	public DeathScreen(final LivingSpaceGame game) {
		this.game = game;
		status = NORMAL;
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		drawScreen();
		game.batch.end();

		processInput();
	}

	private void drawScreen() {
		switch (status) {
			case NORMAL:
				game.batch.draw(Assets.screenDeathNormal, 0, 0);
				break;
			case MOUSE_OVER_RETRY:
				game.batch.draw(Assets.screenDeathRetry, 0, 0);
				break;
			case MOUSE_OVER_QUIT:
				game.batch.draw(Assets.screenDeathQuit, 0, 0);
				break;
			default:
				assert (false);
		}
	}

	private void processInput() {
		processMouseOver();
		processClick();
	}

	private void processClick() {
		if (Gdx.input.justTouched()) {
			float xPos = Gdx.input.getX();
			float yPos = Gdx.input.getY();
			if (isWithinTryAgainButton(xPos, yPos)) {
				Assets.buttonClickSound.play();
				game.restart();
			} else if (isWithinQuitButton(xPos, yPos)) {
				Assets.buttonClickSound.play();
				Gdx.app.exit();
			} else {
				status = NORMAL;
			}
		}
	}

	private void processMouseOver() {
		float xPos = Gdx.input.getX();
		float yPos = Gdx.input.getY();
		if (isWithinTryAgainButton(xPos, yPos)) {
			status = MOUSE_OVER_RETRY;
		} else if (isWithinQuitButton(xPos, yPos)) {
			status = MOUSE_OVER_QUIT;
		} else {
			status = NORMAL;
		}
	}

	private boolean isWithinTryAgainButton(float x, float y) {
		if (x >= COORD_X_TRY_AGAIN_TOP && x <= COORD_X_TRY_AGAIN_BTM
				&& y >= COORD_Y_TRY_AGAIN_TOP && y <= COORD_Y_TRY_AGAIN_BTM) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isWithinQuitButton(float x, float y) {
		if (x >= COORD_X_QUIT_TOP && x <= COORD_X_QUIT_BTM
				&& y >= COORD_Y_QUIT_TOP && y <= COORD_Y_QUIT_BTM) {
			return true;
		} else {
			return false;
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

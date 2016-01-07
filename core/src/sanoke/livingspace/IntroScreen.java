package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class IntroScreen implements Screen {
	private LivingSpaceGame game;
	Spaceship player;

	private static final int SKIP_BUTTON_BOTTOM_LEFT_X = 684;
	private static final int SKIP_BUTTON_BOTTOM_LEFT_Y = 762;
	private static final int SKIP_BUTTON_TOP_RIGHT_X = 966;
	private static final int SKIP_BUTTON_TOP_RIGHT_Y = 708;

	private static final String textBlock1 = " Humanity's attempt to make contact\n"
			+ " with extraterrestrial life\n"
			+ " resulted in a mass of aliens responding\n"
			+ " to signals sent out by human satellites. ";
	private float textBlock1X;
	private float textBlock1Y;
	private static final int START_COORD_TEXT_1_X = -800;
	private static final int START_COORD_TEXT_1_Y = 650;
	private static final int END_COORD_TEXT_1_X = 100;
	private static final int SPEED_TEXT_1 = 125;

	private static final String textBlock2 = " Having fled Earth during a\n"
			+ " previous alien invasion,\n"
			+ " you now find yourself facing\n"
			+ " more alien invasions.";
	private float textBlock2X;
	private float textBlock2Y;
	private static final int START_COORD_TEXT_2_X = 1050;
	private static final int START_COORD_TEXT_2_Y = 400;
	private static final int END_COORD_TEXT_2_X = 100;
	private static final int SPEED_TEXT_2 = 125;

	private float currentAlpha;
	private static final float FADING_SPEED = 0.005f;
	private boolean isFading;

	OrthographicCamera camera;

	public IntroScreen(final LivingSpaceGame game, Spaceship player) {
		this.game = game;
		this.player = player;
		textBlock1X = START_COORD_TEXT_1_X;
		textBlock1Y = START_COORD_TEXT_1_Y;
		textBlock2X = START_COORD_TEXT_2_X;
		textBlock2Y = START_COORD_TEXT_2_Y;
		currentAlpha = 1f;
		isFading = false;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		processInput();
		animate(delta);
		drawTextLeft();
		drawTextRight();
		checkSwitchScreen();
		game.batch.end();
	}

	private boolean isWithinSkip(float x, float y) {
		if ((x >= SKIP_BUTTON_BOTTOM_LEFT_X && x <= SKIP_BUTTON_TOP_RIGHT_X)
				&& (y <= SKIP_BUTTON_BOTTOM_LEFT_Y && y >= SKIP_BUTTON_TOP_RIGHT_Y)) {
			return true;
		} else {
			return false;
		}
	}

	private void animate(float delta) {
		if (isFading) {
			fadeOut();
		} else if (textBlock1X <= (float) END_COORD_TEXT_1_X) {
			moveLeftText(delta);
		} else if (textBlock2X >= (float) END_COORD_TEXT_2_X) {
			moveRightText(delta);
		} else {
			isFading = true;
		}
	}

	private void moveLeftText(float delta) {
		textBlock1X += SPEED_TEXT_1 * delta;
	}

	private void moveRightText(float delta) {
		textBlock2X -= SPEED_TEXT_2 * delta;
	}

	private void fadeOut() {
		game.font.setColor(1, 1, 1, currentAlpha);
		currentAlpha -= FADING_SPEED;
	}

	private void drawTextLeft() {
		game.font.draw(game.batch, textBlock1, textBlock1X,
				textBlock1Y);
	}

	private void drawTextRight() {
		game.font.draw(game.batch, textBlock2, textBlock2X,
				textBlock2Y);
	}

	// Sets game screen to Main Menu Screen
	private void loadMainMenu() {
		Assets.introMusic.stop();
		Assets.music.play();
		game.setMainScreen(false);
	}

	private void checkSwitchScreen() {
		if (currentAlpha <= 0.0f) {
			loadMainMenu();
			game.font.setColor(1, 1, 1, 1);
		}
	}

	private void processInput() {
		processMouseOver();
		processClick();
	}

	private void processMouseOver() {
		float xPos = Gdx.input.getX();
		float yPos = Gdx.input.getY();
		if (isWithinSkip(xPos, yPos) && !isFading) {
			game.batch.draw(Assets.introductionScreenSkip, 0, 0);
		} else {
			game.batch.draw(Assets.introductionScreenDefault, 0, 0);
		}
	}

	private void processClick() {
		if (Gdx.input.justTouched() && !isFading) {
			float xPos = Gdx.input.getX();
			float yPos = Gdx.input.getY();
			if (isWithinSkip(xPos, yPos)) {
				isFading = true;
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

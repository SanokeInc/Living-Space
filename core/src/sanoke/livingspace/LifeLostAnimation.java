package sanoke.livingspace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LifeLostAnimation {		
		private int currentFrameNumber;
		private static final int FRAME_SWITCH_DELAY = 10;
		
		private TextureRegion image;
		
		public static final int IMAGE_WIDTH = 400;
		public static final int IMAGE_HEIGHT = 400;
		
		public static final int INIT_FRAME_NUMBER = Assets.NUM_FRAMES_LIFE_LOSS - 1;
		
		public LifeLostAnimation() {
			currentFrameNumber = INIT_FRAME_NUMBER;
			image = Assets.lifeLossFrames[currentFrameNumber];
		}
		
		public TextureRegion getImage() {
			TextureRegion imageToShow = image;
			currentFrameNumber = (currentFrameNumber + 1) % (Assets.NUM_FRAMES_LIFE_LOSS * FRAME_SWITCH_DELAY); 
			image = Assets.lifeLossFrames[currentFrameNumber / FRAME_SWITCH_DELAY];
			
			return imageToShow;
		}
		
		public void resetAnimation() {
			currentFrameNumber = INIT_FRAME_NUMBER;
		}
}

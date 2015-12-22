package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture background;

    private static Texture spaceship;

    private static TextureRegion alienType1;
    private static TextureRegion alienType2;
    private static TextureRegion alienType3;
    public static TextureRegion[] alienTypes;

    private static Music music;
    private static Sound missileSound;

    public static void loadAssets() {
        background = new Texture(Gdx.files.internal("space.jpg"));
        /*spaceship = new Texture(Gdx.files.internal("*.jpg"));
   
        
        music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        
        missileSound = Gdx.audio.newSound(Gdx.files.internal("*.wav"));*/
    }

    public static void playMusic() {
        music.play();
    }

    public static void playMissileSound() {
    	missileSound.play();
    }
}

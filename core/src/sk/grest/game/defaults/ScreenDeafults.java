package sk.grest.game.defaults;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScreenDeafults {

    public static final SimpleDateFormat timeLeftFormat = new SimpleDateFormat("hh:MM:ss", Locale.getDefault());

    public static final Vector2 BOTTOM_LEFT = new Vector2(0, 0);
    public static final Vector2 TOP_LEFT = new Vector2(0, Gdx.graphics.getHeight());
    public static final Vector2 BOTTOM_RIGHT = new Vector2(Gdx.graphics.getWidth(), 0);
    public static final Vector2 TOP_RIGHT = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Vector2 MIDDLE = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    public static final float DEFAULT_DIALOG_WIDTH = Gdx.graphics.getWidth()/1.75f;
    public static final float DEFAULT_DIALOG_HEIGHT = Gdx.graphics.getHeight()/1.5f;

    public static Drawable textureToDrawable(Texture texture){
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    // CLEARING SCREEN
    public static void clear(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static String getTimeFormat(long time){
        time /= 1000;
        int hours = (int) time / 3600;
        int remainder = (int) time - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return ((hours<10) ? "0" : "") + hours + ":" + ((mins<10) ? "0" : "") + mins + ":" + ((secs<10) ? "0" : "") +secs;
    }


}

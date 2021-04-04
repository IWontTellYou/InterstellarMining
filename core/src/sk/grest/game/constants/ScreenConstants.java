package sk.grest.game.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScreenConstants {

    // COLORS
    public static final Color DARK_GRAY = new Color(96/255f,96/255f,96/255f, 1);
    public static final Color LIGHT_GRAY = new Color(144/255f,144/255f,144/255f, 1);
    public static final Color DARK_BLUE = new Color(0/255f, 3/255f, 199/255f, 1);
    public static final Color CYAN_BLUE = new Color(45/255f, 221/255f, 252/255f, 1);
    public static final Color RED = new Color(112/255f, 0/255f, 0/255f, 0.9f);
    public static final Color TRANSPARENT = new Color(1f, 1f, 1f, 0f);

    public static Texture blank = new Texture(Gdx.files.internal("image/white_background.png"));

    public static final SimpleDateFormat timeLeftFormat = new SimpleDateFormat("hh:MM:ss");

    public static final Vector2 BOTTOM_LEFT = new Vector2(0, 0);
    public static final Vector2 TOP_LEFT = new Vector2(0, Gdx.graphics.getHeight());
    public static final Vector2 BOTTOM_RIGHT = new Vector2(Gdx.graphics.getWidth(), 0);
    public static final Vector2 TOP_RIGHT = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Vector2 MIDDLE = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    public static final float DEFAULT_PADDING = 5f;

    public static final float DEFAULT_ACTOR_HEIGHT = 80f;
    public static final float DEFAULT_ACTOR_WIDTH = 200f;

    public static final float DEFAULT_BUTTON_SIZE = 100f;

    public static final float DEFAULT_DIALOG_WIDTH = Gdx.graphics.getWidth()/1.75f;
    public static final float DEFAULT_DIALOG_HEIGHT = Gdx.graphics.getHeight()/1.5f;

    @Deprecated
    private static Drawable textureToDrawable(Texture texture){
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    // CLEARING SCREEN
    public static void clear(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // TIME DISPLAYING
    public static String getTimeFormat(long time){
        time /= 1000;
        int hours = (int) time / 3600;
        int remainder = (int) time - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return ((hours<10) ? "0" : "") + hours + ":" + ((mins<10) ? "0" : "") + mins + ":" + ((secs<10) ? "0" : "") +secs;
    }

    // MONEY DISPLAYING
    public static String getMoneyFormat(long amount){

        if(amount >= 100000 * GameConstants.QUADRILION){
            return "TOO MUCH MONEY";
        }else if(amount >= 100 * GameConstants.QUADRILION){
            return String.format("%.0f", amount / GameConstants.QUADRILION) + GameConstants.QUADRILLION_SIGN;
        }else if(amount >= 100 * GameConstants.TRILLION) {
            return String.format("%.0f", amount / GameConstants.TRILLION) + GameConstants.TRILLION_SIGN;
        }else if(amount >= 100 * GameConstants.BILLION) {
            return String.format("%.0f", amount / GameConstants.BILLION) + GameConstants.BILLION_SIGN;
        }else if(amount >= 100 * GameConstants.MILLION){
            return String.format("%.0f", amount / GameConstants.MILLION) + GameConstants.MILLION_SIGN;
        }else if(amount >= 100 * GameConstants.THOUSAND){
            return String.format("%.0f", amount / GameConstants.THOUSAND) + GameConstants.THOUSAND_SIGN;
        }else
            return amount + "";
    }

    public static String getMoneyFormatShorter(long amount){

        if(amount >= 1000 * GameConstants.QUADRILION){
            return "TOO MUCH MONEY";
        }else if(amount >= GameConstants.QUADRILION){
            return String.format("%.0f", amount / GameConstants.QUADRILION) + GameConstants.QUADRILLION_SIGN;
        }else if(amount >= GameConstants.TRILLION) {
            return String.format("%.0f", amount / GameConstants.TRILLION) + GameConstants.TRILLION_SIGN;
        }else if(amount >= GameConstants.BILLION) {
            return String.format("%.0f", amount / GameConstants.BILLION) + GameConstants.BILLION_SIGN;
        }else if(amount >= GameConstants.MILLION){
            return String.format("%.0f", amount / GameConstants.MILLION) + GameConstants.MILLION_SIGN;
        }else if(amount >= GameConstants.THOUSAND){
            return String.format("%.0f", amount / GameConstants.THOUSAND) + GameConstants.THOUSAND_SIGN;
        }else
            return amount + "";
    }

    public static TextureRegionDrawable getBackground(Color color){
        Pixmap bgPixmap = new Pixmap(1,1,Pixmap.Format.RGBA8888);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }
}

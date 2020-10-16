package sk.grest.game.defaults;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class ScreenDeafults {

    public static final Vector2 BOTTOM_LEFT = new Vector2(0, 0);
    public static final Vector2 TOP_LEFT = new Vector2(0, Gdx.graphics.getHeight());
    public static final Vector2 BOTTOM_RIGHT = new Vector2(Gdx.graphics.getWidth(), 0);
    public static final Vector2 TOP_RIGHT = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Vector2 MIDDLE = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    // CLEARING SCREEN
    public static void clear(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


}

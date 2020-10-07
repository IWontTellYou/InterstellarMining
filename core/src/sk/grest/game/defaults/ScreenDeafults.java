package sk.grest.game.defaults;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ScreenDeafults {

    // CLEARING SCREEN
    public static void clear(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}

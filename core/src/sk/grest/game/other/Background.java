package sk.grest.game.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sk.grest.game.InterstellarMining;

public class Background {

    public static Texture BACKGROUND = new Texture(Gdx.files.internal("background.png"));;

    private SpriteBatch batch;
    private Sprite backgroundSprite;

    public Background(SpriteBatch batch){

        this.batch = batch;

        backgroundSprite = new Sprite(BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite.flip(false, true);

        // SIZE SET TO FIT SCREEN SIZE
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    // RENDERING BACKGROUND IMAGE USING SpriteBatch
    public void render(){
        backgroundSprite.draw(batch);
    }

}

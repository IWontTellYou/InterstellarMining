package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sk.grest.game.screens.MainMenuScreen;

public class InterstellarMining extends Game {

	private Texture background;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		background = new Texture(Gdx.files.internal(""));
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public Texture getBackground() {
		return background;
	}

}

package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import sk.grest.game.screens.MainMenuScreen;

public class InterstellarMining extends Game {

	private Texture background;
	private SpriteBatch batch;

	private Skin btnSkin;
	
	@Override
	public void create () {

		background = new Texture(Gdx.files.internal("sprites\\background.png"));
		batch = new SpriteBatch();

		TextureAtlas area = new TextureAtlas(Gdx.files.internal("sprites\\sprite.atlas"));
		btnSkin = new Skin(area);

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
	public Skin getBtnSkin() {
		return btnSkin;
	}
}

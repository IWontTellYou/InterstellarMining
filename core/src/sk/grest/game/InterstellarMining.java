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

	private Skin spriteSkin;
	private Skin uiskin;
	
	@Override
	public void create () {

		background = new Texture(Gdx.files.internal("sprites\\background.png"));
		batch = new SpriteBatch();

		TextureAtlas area = new TextureAtlas(Gdx.files.internal("sprites\\sprite.atlas"));
		spriteSkin = new Skin(area);

		TextureAtlas uiarea = new TextureAtlas(Gdx.files.internal("skins\\uiskin.atlas"));
		uiskin = new Skin(Gdx.files.internal("skins\\uiskin.json"), uiarea);

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

	public Skin getUISkin() {
		return uiskin;
	}
	public Skin getSpriteSkin() {return spriteSkin;}
}

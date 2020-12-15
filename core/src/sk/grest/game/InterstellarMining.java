package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.database.DatabaseConnection;
import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.PlanetSystem;
import sk.grest.game.entities.Research;
import sk.grest.game.entities.Ship;
import sk.grest.game.screens.GameScreen;
import sk.grest.game.screens.MainMenuScreen;

import static sk.grest.game.database.DatabaseConstants.*;

public class InterstellarMining extends Game implements DatabaseConnection.ConnectorEvent {

	private DatabaseHandler handler;
	private DatabaseConnection connection;

	private Texture background;

	private SpriteBatch batch;
	private BitmapFont defaultFont;

	private Skin spriteSkin;
	private Skin uiskin;

	private ArrayList<Ship> shipsShop;
	private ArrayList<PlanetSystem> planetSystems;
	private ArrayList<Research> researches;

	@Override
	public void create () {

		shipsShop = null;
		planetSystems = null;
		researches = null;

		handler = new DatabaseHandler(DatabaseConnection.getInstance(), this);

		background = new Texture(Gdx.files.internal("sprites\\background.png"));

		defaultFont = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
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

	public DatabaseHandler getHandler() {
		return handler;
	}

	// ConnectionEvent methods

	@Override
	public void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData) {
		/*

		switch (tableName){
			case ShipTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					Ship ship = new Ship(
							(Integer) data.get(ShipTable.ID),
							(String) data.get(ShipTable.NAME),
							(Float) data.get(ShipTable.MINING_SPEED),
							(Float) data.get(ShipTable.TRAVEL_SPEED),
							(Float) data.get(ShipTable.RESOURCE_CAPACITY),
							(Float) data.get(ShipTable.FUEL_CAPACITY),
							(Float) data.get(ShipTable.FUEL_EFFICIENCY),
							(Float) data.get(ShipTable.PRICE),
							null,
							null,
							0,
							0
					);
					shipsShop.add(ship);
				}
				break;
			case PlanetTable.TABLE_NAME:
				Map<Integer, Planet> pSystem = new HashMap<>();
				for (Map<String, Object> data : tableData) {
					Planet p = new Planet(
							(int) data.get(PlanetTable.ID),
							null,
							(String) data.get(PlanetTable.NAME),
							(float) data.get(PlanetTable.SIZE),
							(float) data.get(PlanetTable.DISTANCE),
							(boolean) data.get(PlanetTable.HABITABLE),
							(String) data.get(PlanetTable.INFO),

					);
					pSystem.put((Integer) data.get(PlanetTable.PLANET_SYSTEM_ID), )
				}
				break;
			case ResearchTable.TABLE_NAME:

				break;
		}
		*/
	}
	@Override
	public void onUpdateSuccess(int requestCode) {
	}
	@Override
	public void onConnect() {
		Gdx.app.log("DATABASE", "Database successfully connected!");
	}
	@Override
	public void onConnectionFailed() {
		Gdx.app.log("DATABASE", "Database could not be connected!");
	}
	@Override
	public void onResultFailed(int requestCode, String message) {
	}
}

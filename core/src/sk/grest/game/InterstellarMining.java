package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.database.DatabaseInitalization;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.planet.PlanetSystem;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.Research;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.TravelPlan;
import sk.grest.game.entities.resource.ResourceRarity;
import sk.grest.game.entities.resource.ResourceState;
import sk.grest.game.listeners.DatabaseChangeListener;
import sk.grest.game.screens.GameScreen;
import sk.grest.game.screens.MainMenuScreen;

import static sk.grest.game.database.DatabaseConnection.*;
import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class InterstellarMining extends Game implements ConnectorEvent, DatabaseChangeListener {

	// TUESDAY
	// TODO FIX BUTTONS
	// TODO SHIP SHOP (BASICALLY DONE)

	// WEDNESDAY
	// TODO FIX SCROLLING LAYOUT FOR RESOURCE_DIALOG, SHIPS_LIST AND SHIPS_SHOP
	// TODO FINISH SHIP UPGRADE WINDOW

	// THURSDAY
	// TODO PLANET SYSTEM LIST
	// TODO FIX SHIP UPDATE (PROBLEM IS WITH PAUSING WHEN MINIMIZED)

	// FRIDAY
	// TODO TOASTS (WRONG PASSWORD, CANT UPGRADE WHILE SHIP IS NOT AT_THE_BASE)
	// TODO REMEMBER NAME AND PASSWORD

	// PROBLEMS THAT WILL WAIT
	// TODO CLEAN UP CODE
	// TODO DATABASE CAN BE POSSIBLY MADE SIMPLER USING JOINs (IF THERE'S TIME)

	private Player player;

	public static Drawable back;

	private DatabaseHandler handler;
	private DatabaseInitalization dataInit;

	private SpriteBatch batch;
	private BitmapFont defaultFont;

	private Skin spriteSkin;
	private Skin uiskin;

	private ArrayList<Ship> shipsShop;
	private ArrayList<Resource> resources;
	private ArrayList<PlanetSystem> planetSystems;
	private ArrayList<Planet> planets;
	private ArrayList<Research> researches;

	@Override
	public void create () {

		Gdx.app.log("Time", new Date(System.currentTimeMillis()).toString());

		handler = DatabaseHandler.getInstance();
		handler.init(this);
		dataInit = new DatabaseInitalization();

		resources = new ArrayList<>();
		shipsShop = new ArrayList<>();
		planetSystems = new ArrayList<>();
		researches = new ArrayList<>();
		planets = new ArrayList<>();

		defaultFont = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
		batch = new SpriteBatch();

		TextureAtlas area = new TextureAtlas(Gdx.files.internal("sprites\\sprites.atlas"));
		spriteSkin = new Skin(area);

		TextureAtlas uiarea = new TextureAtlas(Gdx.files.internal("skins\\uiskin.atlas"));
		uiskin = new Skin(Gdx.files.internal("skins\\uiskin.json"), uiarea);

		back = spriteSkin.getDrawable("actor_background");

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		uiskin.dispose();
		spriteSkin.dispose();
		handler.getConnection().disconnect();
	}

	// GETTERS

	public boolean isDatabaseInitialized(){
		return dataInit.isDatabaseInitialized();
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public Drawable getBackground() {
		return spriteSkin.getDrawable("background");
	}
	public Skin getUISkin() {
		return uiskin;
	}
	public Skin getSpriteSkin() {return spriteSkin;}
	public DatabaseHandler getHandler() {
		return handler;
	}
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Ship> getShipsShop() {
		return shipsShop;
	}
	public ArrayList<PlanetSystem> getPlanetSystems() {
		return planetSystems;
	}
	public ArrayList<Research> getResearches() {
		return researches;
	}
	public ArrayList<Resource> getResources() {
		return resources;
	}

	// Get by ID methods
	public Ship getShipByID(int id){
		for (Ship s : shipsShop) {
			if (s.getId() == id){
				return s;
			}
		}
		return null;
	}
	public Planet getPlanetByID(int id){
		for (Planet p : planets){
			if (p.getID() == id)
				return p;
		}
		return null;
	}
	public PlanetSystem getPlanetSystemByID(int id){
		for (PlanetSystem pS : planetSystems){
			if (pS.getId() == id)
				return pS;
		}
		return null;
	}
	public Resource getResourceByID(int id){
		for (Resource r : resources) {
			if(r.getID() == id)
				return r;
		}
		return null;
	}

	public ArrayList<Ship> getShipsNotOwned(){
		ArrayList<Ship> shipsNotOwned = new ArrayList<>();
		for (Ship shopShip : shipsShop) {
			boolean shipOwned = false;
			for (Ship playerShip : player.getShips()) {
				if (playerShip.getId() == shopShip.getId()) {
					shipOwned = true;
					break;
				}
			}
			if(!shipOwned)
				shipsNotOwned.add(shopShip);
		}
		return shipsNotOwned;
	}

	// Get by name methods

	public Planet getPlanetByName(String name){
		for (Planet p : planets){
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}
	public PlanetSystem getPlanetSystemByID(String name){
		for (PlanetSystem pS : planetSystems){
			if (pS.getName().equals(name))
				return pS;
		}
		return null;
	}

	// DatabaseConnection methods
	@Override
	public void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData) {
		switch (tableName){
			case PlayerTable.TABLE_NAME:
				Gdx.app.log("LOGIN", "Login succesfull");

				if (tableData.size() == 1) {
					final InterstellarMining game = this;
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							setScreen(new GameScreen(game));
						}
					});
				}else if (tableData.size() == 0) {
					Gdx.app.log("SQL_DATA_ERROR", "No players with the same credentials");
				}else {
					Gdx.app.log("SQL_DATA_ERROR", "Multiple players with the same credentials");
				}

				Map<String, Object> playerData = tableData.get(0);
				this.player = new Player(
						this,
						(Integer) playerData.get(PlayerTable.ID),
						(String) playerData.get(PlayerTable.NAME),
						(String) playerData.get(PlayerTable.PASSWORD),
						(String) playerData.get(PlayerTable.EMAIL),
						(Integer) playerData.get(PlayerTable.LEVEL),
						(Integer) playerData.get(PlayerTable.EXPERIENCE),
						Long.parseLong((String) playerData.get(PlayerTable.MONEY))
				);

				Gdx.app.log(PlayerTable.TABLE_NAME, "INITIALIZATION DONE!");

				handler.writeIntoLoginHistorty(true);

				dataInit.setPlayerTable();
				if(dataInit.isPlanetResourceTable())
					handler.getTableWherePlayer(ShipInFleetTable.TABLE_NAME, player.getID(),this);
				break;
			case ShipTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					Ship ship = new Ship(
							this,
							(Integer) data.get(ShipTable.ID),
							(String) data.get(ShipTable.NAME),
							(Float) data.get(ShipTable.MINING_SPEED),
							(Float) data.get(ShipTable.TRAVEL_SPEED),
							(Float) data.get(ShipTable.RESOURCE_CAPACITY),
							(Float) data.get(ShipTable.FUEL_CAPACITY),
							(Float) data.get(ShipTable.FUEL_EFFICIENCY),
							(Float) data.get(ShipTable.PRICE),
							0
					);
					shipsShop.add(ship);
				}

				Gdx.app.log(ShipTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setShipTable();

				handler.getTable(PlanetSystemTable.TABLE_NAME, this);
				break;

			case PlanetSystemTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					PlanetSystem system = new PlanetSystem(
						(Integer) data.get(PlanetSystemTable.ID),
						(String) data.get(PlanetSystemTable.STAR_NAME),
						(String) data.get(PlanetSystemTable.NAME),
						(Integer) data.get(PlanetSystemTable.UNLOCK_LVL),
						false,
						new ArrayList<Planet>()
					);
					planetSystems.add(system);
				}

				Gdx.app.log(PlanetSystemTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setPlanetSystemTable();

				handler.getTable(PlanetTable.TABLE_NAME, this);
				break;

			case PlanetTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					for (PlanetSystem pS : planetSystems) {
						Planet planet = new Planet(
								(int) data.get(PlanetTable.ID),
								(String) data.get(PlanetTable.NAME),
								(String) data.get(PlanetTable.ASSET_ID),
								(float) data.get(PlanetTable.DISTANCE),
								(boolean) data.get(PlanetTable.HABITABLE),
								(String) data.get(PlanetTable.INFO),
								new ArrayList<Resource>()
						);
						planets.add(planet);

						if(pS.getId() == (Integer) data.get(PlanetTable.PLANET_SYSTEM_ID))
							pS.getPlanets().add(planet);
					}
				}

				Gdx.app.log(PlanetTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setPlanetTable();

				handler.getTable(ResourceTable.TABLE_NAME,this);
				break;

			case ResourceTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					Resource resource = new Resource(
							(Integer) data.get(ResourceTable.ID),
							(String) data.get(ResourceTable.NAME),
							ResourceState.getState((Integer) data.get(ResourceTable.STATE_ID)),
							ResourceRarity.getRarity((Integer) data.get(ResourceTable.RARITY_ID)),
							(Float) data.get(ResourceTable.PRICE),
							0
					);
					resources.add(resource);
				}

				Gdx.app.log(ResourceTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setResourcesTable();

				handler.getTable(PlanetResourceTable.TABLE_NAME, this);
				break;

			case PlanetResourceTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					for (Planet p : planets) {
						for (Resource r : resources) {
							if((Integer) data.get(PlanetResourceTable.PLANET_ID) == p.getID() &&
									(Integer) data.get(PlanetResourceTable.RESOURCE_ID) == r.getID())
								p.getResources().add(r);
						}
					}
				}

				Gdx.app.log(PlanetResourceTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setPlanetResourceTable();

				if(dataInit.isPlayerTable())
					handler.getTableWherePlayer(ShipInFleetTable.TABLE_NAME, player.getID(), this);
				break;

			case ShipInFleetTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					Ship s = getShipByID((Integer) data.get(ShipInFleetTable.SHIP_ID));

					Planet destination;
					if (data.get(ShipInFleetTable.DESTINATION_ID) != null)
						destination = getPlanetByID((Integer) data.get(ShipInFleetTable.DESTINATION_ID));
					else
						destination = null;

					Ship ship = new Ship(
							this,
							s.getId(),
							s.getName(),
							s.getAttribute(MINING_SPEED),
							s.getAttribute(TRAVEL_SPEED),
							s.getAttribute(RESOURCE_CAPACITY),
							s.getAttribute(FUEL_CAPACITY),
							s.getAttribute(FUEL_EFFICIENCY),
							s.getPrice(),
							(Integer) data.get(ShipInFleetTable.UPGRADE_LEVEL)
					);

					ship.setAttributes(
							(Integer) data.get(ShipInFleetTable.MINING_SPEED_LVL),
							(Integer) data.get(ShipInFleetTable.TRAVEL_SPEED_LVL),
							(Integer) data.get(ShipInFleetTable.RESOURCE_CAPACITY_LVL),
							(Integer) data.get(ShipInFleetTable.FUEL_CAPACITY_LVL),
							(Integer) data.get(ShipInFleetTable.FUEL_EFFICIENCY_LVL)
					);

					if (data.get(ShipInFleetTable.TASK_TIME) != null && Long.parseLong((String) data.get(ShipInFleetTable.TASK_TIME)) != 0 && destination != null) {
						long taskTime = Long.parseLong((String) data.get(ShipInFleetTable.TASK_TIME));
						Resource resource = getResourceByID((Integer) data.get(ShipInFleetTable.RESOURCE_ID));
						resource.setAmount((Float) data.get(ShipInFleetTable.AMOUNT));
						TravelPlan plan = new TravelPlan(this, destination, ship, resource, taskTime);

						if(plan.getCurrentState() == ShipState.AT_THE_BASE){
						}

						ship.setTravelPlan(plan);
					}

					player.getShips().add(ship);

				}

				Gdx.app.log(ShipInFleetTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setShipFleetTable();

				handler.getTableWherePlayer(ResourceAtBase.TABLE_NAME, player.getID(), this);
				break;

			case ResourceAtBase.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					Resource r = getResourceByID((Integer) data.get(ResourceAtBase.RESOURCE_ID));
					r.setAmount((Float) data.get(ResourceAtBase.AMOUNT));
					player.getResourcesAtBase().add(r);
				}
				handler.getTableWherePlayer(DiscoveredSystemsTable.TABLE_NAME, player.getID(), this);
				break;

			case DiscoveredSystemsTable.TABLE_NAME:
				for (Map<String, Object> data : tableData) {
					PlanetSystem pS = getPlanetSystemByID((Integer) data.get(DiscoveredSystemsTable.PLANET_SYSTEM_ID));

					PlanetSystem system = new PlanetSystem(
							pS.getId(),
							pS.getStarName(),
							pS.getName(),
							pS.getUnlockLvl(),
							(Boolean) data.get(DiscoveredSystemsTable.UNLOCKED),
							pS.getPlanets()
					);

					player.getSystemsDiscovered().add(system);

				}

				Gdx.app.log(DiscoveredSystemsTable.TABLE_NAME, "INITIALIZATION DONE!");
				dataInit.setDiscoveredSystemsTable();

				break;

		}
	}
	@Override
	public void onUpdateSuccess(int requestCode, String tableName) {
		if(tableName.equals(ShipInFleetTable.TABLE_NAME)){
			Gdx.app.log("SHIP_UPDATE", "UPDATE ENDED SUCCESSFULLY");
		}
	}
	@Override
	public void onConnect() {
		Gdx.app.log("DATABASE", "Database successfully connected!");
		handler.getTable(ShipTable.TABLE_NAME, this);
	}
	@Override
	public void onConnectionFailed() {
		Gdx.app.log("DATABASE", "Database could not be connected!");
	}
	@Override
	public void onResultFailed(int requestCode, String message) {
		Gdx.app.log("RESULT_FAILED", message);
	}
	@Override
	public void onUserLoginSuccessful(int requestCode, Map<String, Object> tableData) {
		ArrayList<Map<String, Object>> data = new ArrayList<>();
		data.add(tableData);
		onFetchSuccess(requestCode, PlayerTable.TABLE_NAME, data);
	}


	// DATABASE CHANGE LISTENER METHODS

	@Override
	public void onShipDataChanged(Ship ship) {
		handler.updateShipInFleet(player.getID(), ship, this);
	}

	@Override
	public void onAttributesChanged(Ship ship, Attributes attributes) {
		handler.updateShipInFleet(player.getID(), ship, this);
	}

	@Override
	public void onShipArrivedAtBase(Ship ship, Resource resource) {
		for (Resource r : player.getResourcesAtBase()) {
			if(resource.getID() == r.getID()){
				r.addAmount(resource);
				Gdx.app.log("RESOURCE", r.getAmount()+"");
				handler.updateResourceAtBase(player.getID(), r.getID(), r.getAmount(), this);
				handler.updateShipInFleet(player.getID(), ship, this);
			}
		}
	}
}

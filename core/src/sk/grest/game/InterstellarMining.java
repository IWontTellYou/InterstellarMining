package sk.grest.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.database.DatabaseInitialization;
import sk.grest.game.entities.Achievement;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.Research;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.upgrade.UpgradeRecipe;
import sk.grest.game.listeners.DatabaseChangeListener;
import sk.grest.game.screens.MainMenuScreen;

import static sk.grest.game.database.DatabaseConnection.*;
import static sk.grest.game.database.DatabaseConstants.*;

public class InterstellarMining extends Game implements ConnectorEvent, DatabaseChangeListener {

	// FRIDAY
	// TODO TOASTS (WRONG PASSWORD, CANT UPGRADE WHILE SHIP IS NOT AT_THE_BASE)

	private Player player;

	public static Drawable back;

	private DatabaseHandler handler;
	private DatabaseInitialization dataInit;

	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private BitmapFont defaultFont;

	private Skin spriteSkin;
	private Skin uiSkin;

	private ArrayList<Ship> shipsShop;
	private ArrayList<Resource> resources;
	private ArrayList<Planet> planets;
	private ArrayList<Research> researches;
	private ArrayList<Achievement> achievements;
	private ArrayList<FactoryItem> factoryItems;
	private ArrayList<UpgradeRecipe> upgradeRecipes;

	@Override
	public void create () {
		Gdx.app.log("Time", new Date(System.currentTimeMillis()).toString());

		handler = DatabaseHandler.getInstance();
		handler.init(this);
		dataInit = new DatabaseInitialization(this);

		resources = new ArrayList<>();
		shipsShop = new ArrayList<>();
		researches = new ArrayList<>();
		planets = new ArrayList<>();
		achievements = new ArrayList<>();
		factoryItems = new ArrayList<>();
		upgradeRecipes = new ArrayList<>();

		defaultFont = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);

		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
		batch = new SpriteBatch();

		TextureAtlas area = new TextureAtlas(Gdx.files.internal("sprites\\sprites.atlas"));
		spriteSkin = new Skin(area);

		TextureAtlas uiarea = new TextureAtlas(Gdx.files.internal("skins\\uiskin.atlas"));
		uiSkin = new Skin(Gdx.files.internal("skins\\uiskin.json"), uiarea);

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
		uiSkin.dispose();
		spriteSkin.dispose();
		handler.getConnection().disconnect();
	}

	// GETTERS

	public boolean isRegularDatabaseInitialized(){
		return dataInit.isRegularDatabaseInitialized();
	}
	public boolean isWholeDatabaseInitialized(){
		return dataInit.isWholeDatabaseInitialized();
	}

	public ShapeRenderer getRenderer() {
		return renderer;
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public Drawable getBackground() {
		return spriteSkin.getDrawable("background");
	}
	public Skin getUISkin() {
		return uiSkin;
	}
	public Skin getSpriteSkin() {return spriteSkin;}
	public DatabaseHandler getHandler() {
		return handler;
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player){
		this.player = player;
	}

	public ArrayList<Ship> getShipsShop() {
		return shipsShop;
	}
	public ArrayList<Research> getResearches() {
		return researches;
	}
	public ArrayList<Resource> getResources() {
		return resources;
	}
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	public ArrayList<Planet> getPlanetsNotFound(){
		ArrayList<Planet> planetsNotFound = new ArrayList<>();
		for (Planet p : planets) {
			if (!p.isFound())
				planetsNotFound.add(p);
		}
		return planetsNotFound;
	}
	public ArrayList<Planet> getSolarSystem(){
		ArrayList<Planet> solarSystem = new ArrayList<>();
		for (Planet p : planets) {
			if(p.getID() >= 1 && p.getID() <= 8)
				solarSystem.add(p);
		}
		return solarSystem;
	}
	public ArrayList<Achievement> getAchievements() {
		return achievements;
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
	public ArrayList<FactoryItem> getFactoryItems() {
		return factoryItems;
	}
	public ArrayList<FactoryItem> getFactoryItemsByType(int type){
		ArrayList<FactoryItem> itemsByType = new ArrayList<>();
		for (FactoryItem item : factoryItems) {
			if(item.getType() == type){
				itemsByType.add(item);
			}
		}
		return itemsByType;
	}
	public ArrayList<UpgradeRecipe> getUpgradeRecipes() {
		return upgradeRecipes;
	}
	public UpgradeRecipe[] getUpgradeRecipesByType(int type) {
		ArrayList<UpgradeRecipe> recipesByType = new ArrayList<>();
		for (UpgradeRecipe recipe : upgradeRecipes) {
			if(recipe.getType() == type)
				recipesByType.add(recipe);
		}

		Collections.sort(recipesByType);
		UpgradeRecipe[] recipes = new UpgradeRecipe[recipesByType.size()];
		int i = 0;
		for (UpgradeRecipe recipe : recipesByType) {
			recipes[i] = recipe;
			i++;
		}

		return recipes;
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
	public Resource getResourceByID(int id){
		for (Resource r : resources) {
			if(r.getID() == id)
				return r;
		}
		return null;
	}
	public Research getResearchByID(int id){
		for (Research r : researches) {
			if(r.getId() == id)
				return r;
		}
		return null;
	}
	public Achievement getAchievementByID(int id){
		for (Achievement a : achievements) {
			if(a.getId() == id)
				return a;
		}
		return null;
	}
	public FactoryItem getFactoryItemByID(int id){
		for (FactoryItem item : factoryItems) {
			if(item.getResource().getID() == id){
				return item;
			}
		}
		return null;
	}
	public UpgradeRecipe getUpgradeRecipe(int level, int type){
		for (UpgradeRecipe recipe : upgradeRecipes) {
			if(recipe.getLevel() == level && recipe.getType() == type)
				return recipe;
		}
		return null;
	}

	// Get by name methods
	public Planet getPlanetByName(String name){
		for (Planet p : planets){
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	// DATABASE CONNECTION METHODS
	@Override
	public void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData) {
		switch (tableName){

			// 1ST
			case PlanetTable.TABLE_NAME:
				dataInit.initializePlanetTable(tableData);
				handler.getTable(ResourceTable.TABLE_NAME,this);
				break;

			// 2ND
			case ResourceTable.TABLE_NAME:
				dataInit.initializeResourceTable(tableData);
				handler.getTable(PlanetResourceTable.TABLE_NAME, this);
				break;

			// 3RD
			case PlanetResourceTable.TABLE_NAME:
				dataInit.initializePlanetResourceTable(tableData);
				handler.getTable(ShipTable.TABLE_NAME, this);
				break;

			// 4TH
			case ShipTable.TABLE_NAME:
				dataInit.initializeShipTable(tableData);
				handler.getTable(UpgradeRecipeTable.TABLE_NAME, this);
				break;

			// 5TH
			case UpgradeRecipeTable.TABLE_NAME:
				dataInit.initializeUpgradeRecipe(tableData);
				handler.getTable(FactoryRecipeTable.TABLE_NAME, this);
				break;

			// 6TH
			case FactoryRecipeTable.TABLE_NAME:
				dataInit.initializeFactoryRecipe(tableData);
				break;

			// PLAYER INITIALIZATION

			// 1ST
			case PlayerTable.TABLE_NAME:
				dataInit.initializePlayerTable(tableData);
				handler.writeIntoPlayerLoginHistory(true);
				handler.getTableWherePlayer(PlayerShipTable.TABLE_NAME, player.getID(),this);
				break;

			// 3RD
			case PlayerShipTable.TABLE_NAME:
				dataInit.initializePlayerShipTable(tableData);
				handler.getTableWherePlayer(PlayerResourceTable.TABLE_NAME, player.getID(), this);
				break;

			// 4TH
			case PlayerResourceTable.TABLE_NAME:
				dataInit.initializePlayerResourceTable(tableData);
				handler.getTableWherePlayer(PlayerPlanetTable.TABLE_NAME, player.getID(), this);
				break;

			// 5TH
			case PlayerPlanetTable.TABLE_NAME:
				dataInit.initializePlayerPlanetTable(tableData);
				handler.getTable(PlayerFactoryTable.TABLE_NAME, this);
				break;

			// 6TH
			case PlayerFactoryTable.TABLE_NAME:
				dataInit.initializePlayerFactory(tableData);
				handler.getTableWherePlayer(PlayerObservatoryTable.TABLE_NAME, player.getID(), this);
				break;

			// 7TH
			case PlayerObservatoryTable.TABLE_NAME:
				dataInit.initializePlayerObservatory(tableData);
				handler.getTableWherePlayer(PlayerGoalTable.TABLE_NAME, player.getID(), this);
				break;

			case PlayerGoalTable.TABLE_NAME:
				dataInit.initializePlayerGoalTable(tableData);
				break;
		}
	}
	@Override
	public void onUpdateSuccess(int requestCode, String tableName) {
	}
	@Override
	public void onDeleteSuccess(int requestCode, String tableName) {
	}
	@Override
	public void onLeaderBoardLoaded(int requestCode, Map<String, Float> leaderBoard) {

	}
	@Override
	public void onWinnerDataLoaded(int requestCode, Map<String, Object> data) {

	}

	@Override
	public void onConnect() {
		Gdx.app.log("DATABASE", "Database successfully connected!");
		handler.getTable(PlanetTable.TABLE_NAME, this);
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
	}

	// DATABASE CHANGE LISTENER METHODS
	@Override
	public void onShipDataChanged(Ship ship) {
		handler.updatePlayerShip(player.getID(), ship, this);
	}
	@Override
	public void onAttributesChanged(Ship ship, Attributes attributes) {
		handler.updatePlayerShip(player.getID(), ship, this);
		handler.updatePlayer();
	}
	@Override
	public void onShipArrivedAtBase(Ship ship, Resource resource) {
		for (Resource r : player.getResourcesAtBase()) {
			if (resource.getID() == r.getID()) {
				r.addAmount(resource);
				Gdx.app.log("RESOURCE", r.getAmount() + "");
				handler.updatePlayerResourceTable(r.getID());
				handler.updatePlayerShip(player.getID(), ship, this);
			}
		}
	}
}

package sk.grest.game.database;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.Achievement;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.Research;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.planet.PlanetSystem;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.resource.ResourceRarity;
import sk.grest.game.entities.resource.ResourceState;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.entities.ship.TravelPlan;
import sk.grest.game.screens.GameScreen;

import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType.FUEL_CAPACITY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.FUEL_EFFICIENCY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.MINING_SPEED;
import static sk.grest.game.entities.ship.Attributes.AttributeType.RESOURCE_CAPACITY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.TRAVEL_SPEED;

public class DatabaseInitialization {

    public static final int TABLE_COUNT = 16;

    public static final int ACHIEVEMENT_TABLE = 0;
    public static final int PLANET_SYSTEM_TABLE = 1;
    public static final int PLANET_TABLE = 2;
    public static final int PLANET_RESOURCE_TABLE = 3;
    public static final int SHIP_TABLE = 4;
    public static final int RESEARCH_TABLE = 5;
    public static final int RESEARCH_REQUIREMENT_TABLE = 6;
    public static final int RESOURCE_TABLE = 7;
    public static final int FACTORY_RECIPE = 8;

    public static final int PLAYER_TABLE = 9;
    public static final int PLAYER_ACHIEVEMENT_TABLE = 10;
    public static final int PLAYER_RESEARCH_TABLE = 11;
    public static final int PLAYER_RESOURCE_TABLE = 12;
    public static final int PLAYER_PLANET_SYSTEM_TABLE = 13;
    public static final int PLAYER_SHIP_TABLE = 14;
    public static final int PLAYER_FACTORY = 15;

    private InterstellarMining game;
    private boolean[] tables;

    public DatabaseInitialization(InterstellarMining game) {
        this.game = game;
        tables = new boolean[TABLE_COUNT];
        for (int i = 0; i < TABLE_COUNT; i++) {
            tables[i] = false;
        }
    }

    public boolean getTableState(int tableIndex){
        return tables[tableIndex];
    }

    public boolean isRegularDatabaseInitialized(){
        for (int i = 0; i < 9; i++) {
            if(!tables[i]) return false;
        }
        return true;
    }
    public boolean isWholeDatabaseInitialized(){
        for (int i = 0; i < TABLE_COUNT; i++) {
            if(!tables[i]) return false;
        }
        return true;
    }

    public void initializePlanetTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            for (PlanetSystem pS : game.getPlanetSystems()) {
                Planet planet = new Planet(
                        (int) data.get(PlanetTable.ID),
                        (String) data.get(PlanetTable.NAME),
                        (String) data.get(PlanetTable.ASSET_ID),
                        (int) data.get(PlanetTable.DISTANCE),
                        (boolean) data.get(PlanetTable.HABITABLE),
                        (String) data.get(PlanetTable.INFO),
                        new ArrayList<Resource>()
                );
                game.getPlanets().add(planet);

                if(pS.getId() == (Integer) data.get(PlanetTable.PLANET_SYSTEM_ID))
                    pS.getPlanets().add(planet);
            }
        }
        Gdx.app.log(PlanetTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLANET_TABLE] = true;
    }
    public void initializePlanetResourceTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            for (Planet p : game.getPlanets()) {
                for (Resource r : game.getResources()) {
                    if((Integer) data.get(PlanetResourceTable.PLANET_ID) == p.getID() &&
                            (Integer) data.get(PlanetResourceTable.RESOURCE_ID) == r.getID())
                        p.getResources().add(r);
                }
            }
        }

        Gdx.app.log(PlanetResourceTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLANET_RESOURCE_TABLE] = true;
    }
    public void initializePlanetSystemTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            PlanetSystem system = new PlanetSystem(
                    (Integer) data.get(PlanetSystemTable.ID),
                    (String) data.get(PlanetSystemTable.STAR_NAME),
                    (String) data.get(PlanetSystemTable.NAME),
                    (Integer) data.get(PlanetSystemTable.UNLOCK_LVL),
                    false,
                    new ArrayList<Planet>()
            );
            game.getPlanetSystems().add(system);
        }

        Gdx.app.log(PlanetSystemTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLANET_SYSTEM_TABLE] = true;
    }
    public void initializeResearchTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Research research = new Research(
                    (Integer) data.get(ResearchTable.ID),
                    (String) data.get(ResearchTable.NAME),
                    (String) data.get(ResearchTable.INFO),
                    (String) data.get(ResearchTable.EFFECT),
                    (Integer) data.get(ResearchTable.PRICE),
                    0,
                    0,
                    (Integer) data.get(ResearchTable.COLUMN)
            );
            game.getResearches().add(research);
        }
        Gdx.app.log(ResearchTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[RESEARCH_TABLE] = true;
    }
    public void initializeResearchRequirementTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Research research = game.getResearchByID((int) data.get(ResearchRequirementTable.RESEARCH_ID));
            research.addResearchRequired(game.getResearchByID((int) data.get(ResearchRequirementTable.RESEARCH_REQUIRED_ID)));
        }
        Gdx.app.log(ResearchRequirementTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[RESEARCH_REQUIREMENT_TABLE] = true;
    }
    public void initializeResourceTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Resource resource = new Resource(
                    (Integer) data.get(ResourceTable.ID),
                    (String) data.get(ResourceTable.NAME),
                    (String) data.get(ResourceTable.ASSET_NAME),
                    ResourceState.getState((Integer) data.get(ResourceTable.STATE)),
                    ResourceRarity.getRarity((Integer) data.get(ResourceTable.RARITY)),
                    (Integer) data.get(ResourceTable.PRICE),
                    0
            );
            game.getResources().add(resource);
        }

        Gdx.app.log(ResourceTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[RESOURCE_TABLE] = true;
    }
    public void initializeShipTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Ship ship = new Ship(
                    game,
                    (Integer) data.get(ShipTable.ID),
                    (String) data.get(ShipTable.NAME),
                    (Integer) data.get(ShipTable.MINING_SPEED),
                    (Integer) data.get(ShipTable.TRAVEL_SPEED),
                    (Integer) data.get(ShipTable.RESOURCE_CAPACITY),
                    (Integer) data.get(ShipTable.FUEL_CAPACITY),
                    (Integer) data.get(ShipTable.FUEL_EFFICIENCY),
                    (Integer) data.get(ShipTable.PRICE),
                    0
            );
            game.getShipsShop().add(ship);
        }

        Gdx.app.log(ShipTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[SHIP_TABLE] = true;
    }
    public void initializeAchievementTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Achievement achievement = new Achievement(
                    (Integer) data.get(AchievementTable.ID),
                    (String) data.get(AchievementTable.NAME),
                    (String) data.get(AchievementTable.INFO),
                    (Integer) data.get(AchievementTable.TYPE)
            );
            game.getAchievements().add(achievement);
        }
        Gdx.app.log(AchievementTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[ACHIEVEMENT_TABLE] = true;
    }
    public void initializeFactoryRecipe(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            FactoryItem item = game.getFactoryItemByID((Integer) data.get(FactoryRecipeTable.RESOURCE_ID));
            if(item == null){
                item = new FactoryItem(
                        game.getResourceByID((Integer) data.get(FactoryRecipeTable.RESOURCE_ID)),
                        0,
                        0,
                        (Integer) data.get(FactoryRecipeTable.DURATION),
                        (Integer) data.get(FactoryRecipeTable.TYPE)
                );
                game.getFactoryItems().add(item);
            }

            item.addItemNecessary(
                    game.getResourceByID((Integer) data.get(FactoryRecipeTable.RESOURCE_REQUIRED_ID)),
                    (Integer) data.get(FactoryRecipeTable.AMOUNT));

        }
        Gdx.app.log(FactoryRecipeTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[FACTORY_RECIPE] = true;
    }

    public void initializePlayerTable(ArrayList<Map<String, Object>> tableData){
        Gdx.app.log("LOGIN", "Login succesfull");

        if (tableData.size() == 1) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameScreen(game));
                }
            });
        }else if (tableData.size() == 0) {
            Gdx.app.log("SQL_DATA_ERROR", "No players with the same credentials");
        }else {
            Gdx.app.log("SQL_DATA_ERROR", "Multiple players with the same credentials");
        }

        Map<String, Object> playerData = tableData.get(0);
        Player p = new Player(
                game,
                (Integer) playerData.get(PlayerTable.ID),
                (String) playerData.get(PlayerTable.NAME),
                (String) playerData.get(PlayerTable.PASSWORD),
                (String) playerData.get(PlayerTable.EMAIL),
                (Integer) playerData.get(PlayerTable.LEVEL),
                (Integer) playerData.get(PlayerTable.EXPERIENCE),
                Long.parseLong((String) playerData.get(PlayerTable.MONEY))
        );
        game.setPlayer(p);

        Gdx.app.log(PlayerTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_TABLE] = true;
    }
    public void initializePlayerPlanetSystemTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            PlanetSystem pS = game.getPlanetSystemByID((Integer) data.get(PlayerPlanetSystemTable.PLANET_SYSTEM_ID));

            PlanetSystem system = new PlanetSystem(
                    pS.getId(),
                    pS.getStarName(),
                    pS.getName(),
                    pS.getUnlockLvl(),
                    (Boolean) data.get(PlayerPlanetSystemTable.UNLOCKED),
                    pS.getPlanets()
            );

            game.getPlayer().getSystemsDiscovered().add(system);

        }

        Gdx.app.log(PlayerPlanetSystemTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_PLANET_SYSTEM_TABLE] = true;

    }
    public void initializePlayerResearchTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Research r = game.getResearchByID((Integer) data.get(PlayerResearchTable.RESEARCH_ID));
            if((Boolean) data.get(PlayerResearchTable.UNLOCKED)){
                r.setUnlocked(true);
                game.getPlayer().getResearches().add(r);
            }
        }
        Gdx.app.log(PlayerResearchTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_RESEARCH_TABLE] = true;
    }
    public void initializePlayerResourceTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Resource r = game.getResourceByID((Integer) data.get(PlayerResourceTable.RESOURCE_ID));
            r.setAmount((Integer) data.get(PlayerResourceTable.AMOUNT));
            game.getPlayer().getResourcesAtBase().add(r);
        }
        Gdx.app.log(PlayerResourceTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_RESOURCE_TABLE] = true;
    }
    public void initializePlayerShipTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Ship s = game.getShipByID((Integer) data.get(PlayerShipTable.SHIP_ID));

            Planet destination;
            if (data.get(PlayerShipTable.DESTINATION_ID) != null)
                destination = game.getPlanetByID((Integer) data.get(PlayerShipTable.DESTINATION_ID));
            else
                destination = null;

            Ship ship = new Ship(
                    game,
                    s.getId(),
                    s.getName(),
                    s.getAttribute(MINING_SPEED),
                    s.getAttribute(TRAVEL_SPEED),
                    s.getAttribute(RESOURCE_CAPACITY),
                    s.getAttribute(FUEL_CAPACITY),
                    s.getAttribute(FUEL_EFFICIENCY),
                    s.getPrice(),
                    (Integer) data.get(PlayerShipTable.UPGRADE_LEVEL)
            );

            ship.setAttributes(
                    (Integer) data.get(PlayerShipTable.MINING_SPEED_LVL),
                    (Integer) data.get(PlayerShipTable.TRAVEL_SPEED_LVL),
                    (Integer) data.get(PlayerShipTable.RESOURCE_CAPACITY_LVL),
                    (Integer) data.get(PlayerShipTable.FUEL_CAPACITY_LVL),
                    (Integer) data.get(PlayerShipTable.FUEL_EFFICIENCY_LVL)
            );

            if (data.get(PlayerShipTable.TASK_TIME) != null && Long.parseLong((String) data.get(PlayerShipTable.TASK_TIME)) != 0 && destination != null) {
                long taskTime = Long.parseLong((String) data.get(PlayerShipTable.TASK_TIME));
                Resource resource = game.getResourceByID((Integer) data.get(PlayerShipTable.RESOURCE_ID));
                resource.setAmount((Integer) data.get(PlayerShipTable.AMOUNT));
                TravelPlan plan = new TravelPlan(game, destination, ship, resource, taskTime);

                if(plan.getCurrentState() == ShipState.AT_THE_BASE){
                }
                ship.setTravelPlan(plan);
            }
            game.getPlayer().getShips().add(ship);
        }

        Gdx.app.log(PlayerShipTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_SHIP_TABLE] = true;
    }
    public void initializePlayerAchievementTable(ArrayList<Map<String, Object>> tableData){
        for (Map<String, Object> data : tableData) {
            Achievement a = game.getAchievementByID((Integer) data.get(PlayerAchievementTable.ID));
            game.getPlayer().getAchievements().addAll(Achievement.getAchievement(a, (Integer) data.get(PlayerAchievementTable.CURRENT_AMOUNT)));
        }
        Gdx.app.log(AchievementTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_ACHIEVEMENT_TABLE] = true;
    }
    public void initializePlayerFactory(ArrayList<Map<String, Object>> tableData){
        int itemsDeleted = 0;
        for (Map<String, Object> data : tableData) {
            int resourceID = (Integer) data.get(PlayerFactoryTable.RESOURCE_ID);
            for (FactoryItem item : game.getFactoryItems()) {
                if(item.getResource().getID() == resourceID){
                    try {
                        FactoryItem queueItem = (FactoryItem) item.clone();
                        queueItem.setStartTime(Long.parseLong((String) data.get(PlayerFactoryTable.START_TIME)));
                        queueItem.setCount((Integer) data.get(PlayerFactoryTable.COUNT));
                        if(queueItem.getTimeLeftMillis() > 0)
                            game.getPlayer().addToQueue(queueItem);
                        else {
                            game.getHandler().removePlayerFactoryRow(
                                    queueItem.getResource().getID(), queueItem.getStartTime(), queueItem.getCount());
                            itemsDeleted++;
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Gdx.app.log(PlayerFactoryTable.TABLE_NAME, "INITIALIZATION DONE!");
        tables[PLAYER_FACTORY] = true;
    }
}

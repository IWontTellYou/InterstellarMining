package sk.grest.game.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.Observatory;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.ship.Ship;

import static sk.grest.game.database.DatabaseConnection.*;
import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.*;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EMAIL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EXPERIENCE;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.LEVEL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.NAME;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.PASSWORD;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.TABLE_NAME;
import static sk.grest.game.database.DatabaseConstants.PLAYER_ID;
import static sk.grest.game.database.DatabaseConstants.PlayerShipTable.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class DatabaseHandler {

    private static DatabaseHandler databaseHandler;

    private static int requestCode = 0;

    private DatabaseConnection connection;
    private InterstellarMining game;

    private DatabaseHandler() {}
    public static DatabaseHandler getInstance(){
        if(databaseHandler == null)
            databaseHandler = new DatabaseHandler();

        return databaseHandler;
    }
    public void init(InterstellarMining game){
        databaseHandler.game = game;
        databaseHandler.connection = DatabaseConnection.getInstance();
        databaseHandler.connection.connect(game);
    }

    public void verifyPlayer(String name, String password, ConnectorEvent listener){
        connection.verifyPlayer(requestCode++, name, password, listener);
    }
    public void writeIntoPlayerLoginHistory(boolean in){
        Map<String, Object> data = new HashMap<>();
        if(in)
            data.put(PlayerLoginHistoryTable.LOGGED_IN, new Date(System.currentTimeMillis()).getTime());
        else
            data.put(PlayerLoginHistoryTable.LOGGED_OUT, new Date(System.currentTimeMillis()).getTime());
        data.put(PlayerLoginHistoryTable.PLAYER_ID, game.getPlayer().getID());
        connection.addRow(requestCode++, PlayerLoginHistoryTable.TABLE_NAME, data, game);
    }

    public void addPlayer(String name, String password, String email, ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(PASSWORD, password);
        data.put(EMAIL, email);
        data.put(LEVEL, 1);
        data.put(EXPERIENCE, 0);
        data.put(MONEY, 10000);
        connection.addRow(requestCode++, TABLE_NAME, data, listener);
    }
    public void updatePlayer(){
        Player p = game.getPlayer();
        Map<String, Object> data = new HashMap<>();
        data.put(LEVEL, p.getLevel());
        data.put(EXPERIENCE, p.getExperience());
        data.put(MONEY, p.getMoney());
        connection.updateRow(requestCode++, game.getPlayer().getID(), 0, PlayerTable.TABLE_NAME, data, game);
    }

    // TODO UPDATE REGISTRATION
    public void addPlayerData(Map<String, Object> data, ConnectorEvent listener){

        int playerId = (Integer) data.get(PlayerTable.ID);

        // RESOURCE AT BASE
        for (int i = 0; i < game.getResources().size(); i++) {
            Map<String, Object> resourceData = new HashMap<>();
            resourceData.put(PLAYER_ID, playerId);
            resourceData.put(PlayerResourceTable.RESOURCE_ID, i+1);
            resourceData.put(PlayerResourceTable.AMOUNT, 0);
            connection.addRow(requestCode++, PlayerResourceTable.TABLE_NAME, resourceData, listener);
        }

        // PLANETS FOUND
        for (int i = 0; i < 8 ; i++) {
            Map<String, Object> resourceData = new HashMap<>();
            resourceData.put(PLAYER_ID, playerId);
            resourceData.put(PlayerPlanetTable.ID, i+1);
            resourceData.put(PlayerPlanetTable.FOUND, true);
            connection.addRow(requestCode++, PlayerPlanetTable.TABLE_NAME, resourceData, listener);
        }

        // Solar System
        for (int i = 0; i < game.getSolarSystem().size() ; i++) {
            Map<String, Object> resourceData = new HashMap<>();
            resourceData.put(PLAYER_ID, playerId);
            resourceData.put(PlayerPlanetTable.ID, i+1);
            resourceData.put(PlayerPlanetTable.FOUND, true);
            connection.addRow(requestCode++, PlayerPlanetTable.TABLE_NAME, resourceData, listener);
        }

        Map<String, Object> observatory = new HashMap<>();
        observatory.put(PLAYER_ID, playerId);
        observatory.put(PlayerObservatoryTable.PLANET_ID, null);
        observatory.put(PlayerObservatoryTable.END_TIME, null);
        observatory.put(PlayerObservatoryTable.SPEED_LVL, 0);
        observatory.put(PlayerObservatoryTable.ACCURACY_LVL, 0);
        connection.addRow(requestCode++, PlayerObservatoryTable.TABLE_NAME, observatory, listener);

        Map<String, Object> basicShip = new HashMap<>();
        basicShip.put(PLAYER_ID, playerId);
        basicShip.put(SHIP_ID, 1);
        connection.addRow(requestCode++, PlayerShipTable.TABLE_NAME, basicShip, listener);

    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }
    public DatabaseConnection getConnection() {
        return connection;
    }

    // UPDATE PLAYER ENTITIES
    public void buyShip(Ship s){
        Map<String, Object> data = new HashMap<>();
        data.put(SHIP_ID, s.getId());
        data.put(PLAYER_ID, game.getPlayer().getID());
        connection.addRow(requestCode++, PlayerShipTable.TABLE_NAME, data, game);

        updatePlayer();
    }
    public void updatePlayerResourceTable(int resourceId){
        Map<String, Object> data = new HashMap<>();
        data.put(PlayerResourceTable.RESOURCE_ID, resourceId);
        data.put(PlayerResourceTable.AMOUNT, game.getPlayer().getResource(resourceId).getAmount());
        connection.updateRow(requestCode++, game.getPlayer().getID(), resourceId, PlayerResourceTable.TABLE_NAME, data, game);
    }
    public void updatePlayerShip(int playerId, Ship ship, ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(DESTINATION_ID, (ship.getCurrentDestination() != null) ? ship.getCurrentDestination().getID() : null);
        data.put(RESOURCE_ID, (ship.getTravelPlan() != null && ship.getTravelPlan().getResource() != null) ? ship.getTravelPlan().getResource().getID() : null);
        data.put(AMOUNT, (ship.getTravelPlan() != null && ship.getTravelPlan().getResource() != null) ? ship.getTravelPlan().getResource().getAmount() : 0);
        data.put(TASK_TIME, (ship.getTravelPlan() != null && ship.getTravelPlan().getResource() != null) ? ship.getTravelPlan().getStartTime() : null);
        data.put(FUEL_CAPACITY_LVL, ship.getAttributes().getAttribute(FUEL_CAPACITY));
        data.put(FUEL_EFFICIENCY_LVL, ship.getAttributes().getAttribute(FUEL_EFFICIENCY));
        data.put(TRAVEL_SPEED_LVL, ship.getAttributes().getAttribute(TRAVEL_SPEED));
        data.put(MINING_SPEED_LVL, ship.getAttributes().getAttribute(MINING_SPEED));
        data.put(RESOURCE_CAPACITY_LVL, ship.getAttributes().getAttribute(RESOURCE_CAPACITY));
        data.put(UPGRADE_LEVEL, ship.getLevel(0));
        connection.updateRow(requestCode++, playerId, ship.getId(), PlayerShipTable.TABLE_NAME, data, listener);
    }
    public void updatePlayerObservatory(int playerId, Observatory observatory, ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(PlayerObservatoryTable.ACCURACY_LVL, observatory.getLevel(Observatory.OBSERVATORY_ACCURACY));
        data.put(PlayerObservatoryTable.SPEED_LVL, observatory.getLevel(Observatory.OBSERVATORY_SPEED));
        data.put(PlayerObservatoryTable.PLANET_ID, (observatory.getPlanet() == null) ? null : observatory.getPlanet().getID());
        data.put(PlayerObservatoryTable.END_TIME, observatory.getEndTime());
        connection.updateRow(requestCode++, playerId, 0, PlayerObservatoryTable.TABLE_NAME, data, listener);
    }

    // GET TABLE
    public void getPlayersTable(ConnectorEvent listener){
        connection.getTable(requestCode++, TABLE_NAME, listener);
    }
    public void getTable(String tableName, ConnectorEvent listener){
        connection.getTable(requestCode++, tableName, listener);
    }
    public void getTableWherePlayer(String tableName, int playerID, ConnectorEvent listener){
        connection.getTableWherePlayer(requestCode++, tableName, playerID, listener);
    }

    // FACTORY
    public void addPlayerFactoryRow(int resourceID, long startTime, int count){
        Map<String, Object> data = new HashMap<>();
        data.put(PlayerFactoryTable.RESOURCE_ID, resourceID);
        data.put(PlayerFactoryTable.START_TIME, startTime);
        data.put(PlayerFactoryTable.COUNT, count);
        data.put(PlayerFactoryTable.PLAYER_ID, game.getPlayer().getID());
        connection.addRow(requestCode++, PlayerFactoryTable.TABLE_NAME, data, game);
    }
    public void removePlayerFactoryRow(int resourceID, long startTime, int count){
        Map<String, Object> data = new HashMap<>();
        data.put(PlayerFactoryTable.RESOURCE_ID, resourceID);
        data.put(PlayerFactoryTable.START_TIME, startTime);
        data.put(PlayerFactoryTable.COUNT, count);
        data.put(PlayerFactoryTable.PLAYER_ID, game.getPlayer().getID());
        connection.deleteRow(requestCode++, PlayerFactoryTable.TABLE_NAME, data, game);
    }

}

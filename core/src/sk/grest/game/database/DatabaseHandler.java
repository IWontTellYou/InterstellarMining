package sk.grest.game.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

import static sk.grest.game.database.DatabaseConnection.*;
import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EMAIL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EXPERIENCE;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.LEVEL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.NAME;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.PASSWORD;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.TABLE_NAME;
import static sk.grest.game.database.DatabaseConstants.ShipInFleetTable.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class DatabaseHandler {

    private static DatabaseHandler databaseHandler;

    private static int requestCode = 0;

    private DatabaseConnection connection;
    private InterstellarMining game;

    public static DatabaseHandler getInstance(){
        if(databaseHandler == null)
            databaseHandler = new DatabaseHandler();

        return databaseHandler;
    }

    private DatabaseHandler() {}

    public void init(InterstellarMining game){
        databaseHandler.game = game;
        databaseHandler.connection = DatabaseConnection.getInstance();
        databaseHandler.connection.connect(game);
    }

    public void verifyPlayer(String name, String password, ConnectorEvent listener){
        connection.verifyPlayer(requestCode++, name, password, listener);
    }

    public void writeIntoLoginHistorty(boolean in){
        Map<String, Object> data = new HashMap<>();
        if(in)
            data.put(LoginHistoryTable.LOGGED_IN, new Date(System.currentTimeMillis()).getTime());
        else
            data.put(LoginHistoryTable.LOGGED_OUT, new Date(System.currentTimeMillis()).getTime());
        data.put(LoginHistoryTable.PLAYER_ID, game.getPlayer().getID());
        connection.addRow(requestCode++, LoginHistoryTable.TABLE_NAME, data, game);
    }

    public void addPlayer(String name, String password, String email, int level, int experience){
        Map<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(PASSWORD, password);
        data.put(EMAIL, email);
        data.put(LEVEL, level);
        data.put(EXPERIENCE, experience);
        connection.addRow(requestCode++, TABLE_NAME, data, game);
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }

    public void updateResourceAtBase(int playerId, int resourceId, float amount, ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(ResourceAtBase.RESOURCE_ID, resourceId);
        data.put(ResourceAtBase.AMOUNT, amount);
        connection.updateRow(requestCode++, playerId, resourceId, ResourceAtBase.TABLE_NAME, data, listener);
    }

    public void updateShipInFleet(int playerId, Ship ship, ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(DESTINATION_ID, (ship.getCurrentDestination() != null) ? ship.getCurrentDestination().getID() : null);
        data.put(RESOURCE_ID, (ship.getTravelPlan().getResource() != null) ? ship.getTravelPlan().getResource().getID() : null);
        data.put(AMOUNT, (ship.getTravelPlan().getResource() != null) ? ship.getTravelPlan().getResource().getAmount() : 0);
        data.put(TASK_TIME, (ship.getTravelPlan() != null) ? ship.getTravelPlan().getStartTime() : null);
        data.put(FUEL_CAPACITY_LVL, ship.getAttributes().getAttribute(FUEL_CAPACITY));
        data.put(FUEL_EFFICIENCY_LVL, ship.getAttributes().getAttribute(FUEL_EFFICIENCY));
        data.put(TRAVEL_SPEED_LVL, ship.getAttributes().getAttribute(TRAVEL_SPEED));
        data.put(MINING_SPEED_LVL, ship.getAttributes().getAttribute(MINING_SPEED));
        data.put(RESOURCE_CAPACITY_LVL, ship.getAttributes().getAttribute(RESOURCE_CAPACITY));
        connection.updateRow(requestCode++, playerId, ship.getId(), ShipInFleetTable.TABLE_NAME, data, listener);
    }

    public void updateTable(String tableName, int id, Map<String, Object> data, ConnectorEvent listener){
    }

    public void getTable(String tableName, ConnectorEvent listener){
        connection.getTable(requestCode++, tableName, listener);
    }

    public void getTableWherePlayer(String tableName, int playerID, ConnectorEvent listener){
        connection.getTableWherePlayer(requestCode++, tableName, playerID, listener);
    }
}

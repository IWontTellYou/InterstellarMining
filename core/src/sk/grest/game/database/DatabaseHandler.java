package sk.grest.game.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.InterstellarMining;

import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EMAIL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.EXPERIENCE;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.LEVEL;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.NAME;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.PASSWORD;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.TABLE_NAME;

public class DatabaseHandler {

    private static int requestCode = 0;

    private DatabaseConnection connection;
    private InterstellarMining game;

    public DatabaseHandler(DatabaseConnection connection, InterstellarMining game) {
        this.connection = connection;
        connection.connect(game);
        this.game = game;
    }

    public void verifyPlayer(String name, String password, DatabaseConnection.ConnectorEvent listener){
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

    public void addNewShip(){

    }

    public void updateResourceAtBase(int playerId, int resourceId, float amount, DatabaseConnection.ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(ResourceAtBase.RESOURCE_ID, resourceId);
        data.put(ResourceAtBase.AMOUNT, amount);
        connection.updateRow(resourceId++, playerId, resourceId, ResourceAtBase.TABLE_NAME, data, listener);
    }

    public void updateShipInFleet(int playerId, int shipId, int destinationId, int resourceId,
                                  float amount, long taskTime, int travelSpeedLvl,
                                  int miningSpeedLvl, int fuelCapacityLvl, int fuelEfficiencyLvl,
                                  int resourceCapacityLvl, DatabaseConnection.ConnectorEvent listener){
        Map<String, Object> data = new HashMap<>();
        data.put(ShipInFleetTable.DESTINATION_ID, destinationId);
        data.put(ShipInFleetTable.RESOURCE_ID, resourceId);
        data.put(ShipInFleetTable.AMOUNT, amount);
        data.put(ShipInFleetTable.TASK_TIME, taskTime);
        data.put(ShipInFleetTable.FUEL_CAPACITY_LVL, fuelCapacityLvl);
        data.put(ShipInFleetTable.FUEL_EFFICIENCY_LVL, fuelEfficiencyLvl);
        data.put(ShipInFleetTable.TRAVEL_SPEED_LVL, travelSpeedLvl);
        data.put(ShipInFleetTable.MINING_SPEED_LVL, miningSpeedLvl);
        data.put(ShipInFleetTable.RESOURCE_CAPACITY_LVL, resourceCapacityLvl);
        connection.updateRow(requestCode++, playerId, shipId, ShipInFleetTable.TABLE_NAME, data, listener);
    }

    public void updateTable(String tableName, int id, Map<String, Object> data, DatabaseConnection.ConnectorEvent listener){
    }

    public void getTable(String tableName, DatabaseConnection.ConnectorEvent listener){
        connection.getTable(requestCode++, tableName, listener);
    }

    public void getTableWherePlayer(String tableName, int playerID, DatabaseConnection.ConnectorEvent listener){
        connection.getTableWherePlayer(requestCode++, tableName, playerID, listener);
    }
}

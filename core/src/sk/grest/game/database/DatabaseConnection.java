package sk.grest.game.database;

import com.badlogic.gdx.Gdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.entities.Player;

import static sk.grest.game.database.DatabaseConstants.*;

public class DatabaseConnection {

    private Thread connectionThread;
    private Thread taskThread;
    private Connection connection;
    private static DatabaseConnection instance;
    private static final String DATABASE_NAME = "interstellar_mining";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    public DatabaseConnection(){}

    public static DatabaseConnection getInstance() {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public void connect(final ConnectorEvent eventListener) {
        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3307/" + DATABASE_NAME + "?serverTimezone=CET",
                            DATABASE_USERNAME,
                            DATABASE_PASSWORD
                    );
                    eventListener.onConnect();
                } catch (SQLException | ClassNotFoundException e) {
                    eventListener.onConnectionFailed();
                }
            }
        });
        connectionThread.setDaemon(true);
        connectionThread.start();
    }
    public void disconnect() {
        Gdx.app.log("DISCONECT", "Disconected");
        try {
            if (taskThread.isAlive())
                taskThread.interrupt();
            if (connectionThread.isAlive())
                taskThread.interrupt();
            connection.close();
        } catch (SQLException e) {
            Gdx.app.log("SQL_ERROR", e.getMessage());
        }
    }

    public void verifyPlayer(final int requestCode, final String username, final String password, final ConnectorEvent eventListener){
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM " + PlayerTable.TABLE_NAME + " WHERE "
                            + PlayerTable.NAME + " = '" + username + "' AND " + PlayerTable.PASSWORD +
                            " = '" + password + "'";
                    ResultSet result = statement.executeQuery(sql);
                    ArrayList<Map<String, Object>> requestData = new ArrayList<>();

                    while (result.next()) {
                        Map<String, Object> rowData = new HashMap<>();
                        for (int i = 1; i < result.getMetaData().getColumnCount()+1; i++) {
                            rowData.put(result.getMetaData().getColumnName(i), result.getObject(i));
                        }
                        requestData.add(rowData);
                    }

                    // Gdx.app.log("SQL_QUERRY", requestData.get(0).toString());

                    if(requestData.size() > 0)
                        eventListener.onUserLoginSuccessful(requestCode, requestData.get(0));
                    else
                        Gdx.app.log("WRONG_CREDENTIALS", "Could not log in");

                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public void getTable(final int requestCode, final String tableName, final ConnectorEvent eventListener) {
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM " + tableName;

                    //Gdx.app.log("SQL_QUERRY", sql);

                    ResultSet result = statement.executeQuery(sql);
                    ArrayList<Map<String, Object>> requestData = new ArrayList<>();
                    while (result.next()) {
                        Map<String, Object> rowData = new HashMap<>();
                        for (int i = 1; i < result.getMetaData().getColumnCount()+1; i++) {
                            rowData.put(result.getMetaData().getColumnName(i), result.getObject(i));
                        }
                        requestData.add(rowData);
                    }
                    eventListener.onFetchSuccess(requestCode, tableName, requestData);
                } catch (SQLException e) {
                    // eventListener.onResultFailed(requestCode, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }
    public void getTableWherePlayer(final int requestCode, final String tableName, final int playerID, final ConnectorEvent eventListener){
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM " + tableName + " WHERE "
                            + DatabaseConstants.PLAYER_ID + " = " + playerID;
                    if(tableName.equals(PlayerFactoryTable.TABLE_NAME))
                        sql += " ORDER BY " + PlayerFactoryTable.START_TIME + " ASC";

                    ResultSet result = statement.executeQuery(sql);
                    ArrayList<Map<String, Object>> requestData = new ArrayList<>();

                    while (result.next()) {
                        Map<String, Object> rowData = new HashMap<>();
                        for (int i = 1; i < result.getMetaData().getColumnCount()+1; i++) {
                            rowData.put(result.getMetaData().getColumnName(i), result.getObject(i));
                        }
                        requestData.add(rowData);
                    }

                    eventListener.onFetchSuccess(requestCode, tableName, requestData);

                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public void addRow(final int requestCode, final String tableName, final Map<String, Object> data, final ConnectorEvent eventListener) {
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement stm = connection.createStatement();
                    String sql = getAddRowSQL(tableName, data);

                    // Gdx.app.log("SQL_QUERY", sql);

                    stm.executeUpdate(sql);
                    eventListener.onUpdateSuccess(requestCode, tableName);
                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }
    public void updateRow(final int requestCode, final int playerId, final int objectId, final String tableName, final Map<String, Object> data, final ConnectorEvent eventListener) {
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement stm = connection.createStatement();
                    stm.executeUpdate(getUpdateSQL(tableName, playerId, objectId, data));
                    eventListener.onUpdateSuccess(requestCode, tableName);
                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }
    public void deleteRow(final int requestCode, final String tableName, final Map<String, Object> data, final ConnectorEvent eventListener){
        // DELETE FROM table_name WHERE condition;

        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement stm = connection.createStatement();
                    String sql = getDeleteRowSQL(tableName, data);

                    //Gdx.app.log("DELETE QUERY", sql);

                    stm.executeUpdate(sql);
                    eventListener.onDeleteSuccess(requestCode, tableName);
                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();

    }

    private String getUpdateSQL(String tableName, int playerId, int objectId, Map<String, Object> data){
        String sql = "UPDATE " + tableName + " SET ";

        switch (tableName) {
            case PlayerShipTable.TABLE_NAME:
                sql += PlayerShipTable.DESTINATION_ID + " = " + data.get(PlayerShipTable.DESTINATION_ID) + ", ";
                sql += PlayerShipTable.RESOURCE_ID + " = " + data.get(PlayerShipTable.RESOURCE_ID) + ", ";
                sql += PlayerShipTable.AMOUNT + " = " + data.get(PlayerShipTable.AMOUNT) + ", ";
                sql += PlayerShipTable.TASK_TIME + " = " + data.get(PlayerShipTable.TASK_TIME) + ", ";
                sql += PlayerShipTable.FUEL_CAPACITY_LVL + " = " + data.get(PlayerShipTable.FUEL_CAPACITY_LVL) + ", ";
                sql += PlayerShipTable.FUEL_EFFICIENCY_LVL + " = " + data.get(PlayerShipTable.FUEL_EFFICIENCY_LVL) + ", ";
                sql += PlayerShipTable.RESOURCE_CAPACITY_LVL + " = " + data.get(PlayerShipTable.RESOURCE_CAPACITY_LVL) + ", ";
                sql += PlayerShipTable.TRAVEL_SPEED_LVL + " = " + data.get(PlayerShipTable.TRAVEL_SPEED_LVL) + ", ";
                sql += PlayerShipTable.MINING_SPEED_LVL + " = " + data.get(PlayerShipTable.MINING_SPEED_LVL) + ", ";
                sql += PlayerShipTable.UPGRADE_LEVEL + " = " + data.get(PlayerShipTable.UPGRADE_LEVEL) + " ";
                sql += "WHERE " + PLAYER_ID + " = " + playerId + " AND " + PlayerShipTable.SHIP_ID + " = " + objectId;
                //Gdx.app.log("SQL", sql);
                break;
            case PlayerResourceTable.TABLE_NAME:
                sql += PlayerResourceTable.AMOUNT + " = " + data.get(PlayerShipTable.AMOUNT);
                sql += " WHERE " + PLAYER_ID + " = " + playerId + " AND " + PlayerResourceTable.RESOURCE_ID + " = " + objectId;
                //Gdx.app.log("SQL", sql);
                break;
            case PlayerTable.TABLE_NAME:
                sql += PlayerTable.LEVEL + " = " + data.get(PlayerTable.LEVEL) + ", ";
                sql += PlayerTable.EXPERIENCE + " = " + data.get(PlayerTable.EXPERIENCE) + ", ";
                sql += PlayerTable.MONEY + " = " + data.get(PlayerTable.MONEY);
                sql += " WHERE " + PlayerTable.ID + " = " + data.get(PlayerTable.ID);
                //Gdx.app.log("SQL", sql);
                break;
            case PlayerObservatoryTable.TABLE_NAME:
                sql += PlayerObservatoryTable.ACCURACY_LVL + " = " + data.get(PlayerObservatoryTable.ACCURACY_LVL) + ", ";
                sql += PlayerObservatoryTable.SPEED_LVL + " = " + data.get(PlayerObservatoryTable.SPEED_LVL) + ", ";
                sql += PlayerObservatoryTable.PLANET_ID + " = " + data.get(PlayerObservatoryTable.PLANET_ID) + " ";
                sql += " WHERE " + PLAYER_ID + " = " + playerId;
                //Gdx.app.log("SQL", sql);
                break;
        }

        return sql;
    }
    private String getAddRowSQL(final String tableName, final Map<String, Object> data){
        StringBuilder keyNames = new StringBuilder();
        StringBuilder keyValues = new StringBuilder();

        if(tableName.equals(PlayerTable.TABLE_NAME)){
            for (String key : data.keySet()) {
                keyNames.append(key).append(",");

                if(key.equals(PlayerTable.NAME) || key.equals(PlayerTable.EMAIL) || key.equals(PlayerTable.PASSWORD))
                    keyValues.append("'").append(data.get(key)).append("'").append(",");
                else
                    keyValues.append(data.get(key)).append(",");
            }
        }else {
            for (String s : data.keySet()) {
                keyNames.append(s).append(",");
                keyValues.append(data.get(s)).append(",");
            }
        }

        keyNames = new StringBuilder(keyNames.substring(0, keyNames.length() - 1));
        keyValues = new StringBuilder(keyValues.substring(0, keyValues.length() - 1));

        return "INSERT INTO " + tableName + "(" + keyNames + ") VALUES (" + keyValues + ")";
    }
    private String getDeleteRowSQL(final String tableName, final Map<String, Object> data){

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName).append(" WHERE ");

        int counter = 0;
        for (String s : data.keySet()) {
            if(counter < data.size()-1)
                sql.append(s).append(" = ").append(data.get(s)).append(" AND ");
            else
                sql.append(s).append(" = ").append(data.get(s));
            counter++;
        }

        return sql.toString();
    }

    public interface ConnectorEvent {
        void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData);
        void onUpdateSuccess(int requestCode, String tableName);
        void onConnect();
        void onConnectionFailed();
        void onResultFailed(int requestCode, String message);
        void onUserLoginSuccessful(int requestCode, Map<String, Object> tableData);
        void onDeleteSuccess(int requestCode, String message);
    }

}
package sk.grest.game.database;

import com.badlogic.gdx.Gdx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.dialogs.GameOverDialog;
import sk.grest.game.entities.Player;

import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.dialogs.GameOverDialog.GAME_START;
import static sk.grest.game.dialogs.GameOverDialog.WINNERS_COUNT;

public class DatabaseConnection {

    public static final int SHA512 = 512;

    private Thread connectionThread;
    private Thread taskThread;
    private Connection connection;
    private static DatabaseConnection instance;

    private static final String DATABASE_NAME = "interstellar_mining";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String DATABASE_SERVER = "localhost";
    private static final String DATABASE_PORT = "3307";


    /*
    private static final String DATABASE_NAME = "sql11404959";
    private static final String DATABASE_USERNAME = "sql11404959";
    private static final String DATABASE_PASSWORD = "vdunS5kbch";
    private static final String DATABASE_SERVER = "sql11.freemysqlhosting.net";
    private static final String DATABASE_PORT = "3306";
    */

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
                            "jdbc:mysql://"+DATABASE_SERVER+":"+DATABASE_PORT+"/" + DATABASE_NAME + "?serverTimezone=CET",
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
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT * FROM " + PlayerTable.TABLE_NAME + " WHERE "
                            + PlayerTable.NAME + " = ? AND " +
                            PlayerTable.PASSWORD + " = SHA2(?,"+ SHA512 +")");
                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet result = statement.executeQuery();
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
                        eventListener.onResultFailed(requestCode, ConnectorEvent.WRONG_CREDENTIALS_MESSAGE);

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

                    if(tableName.equals(PlayerTable.TABLE_NAME)){
                        PreparedStatement statement =
                                connection.prepareStatement(
                                        "INSERT INTO " + PlayerTable.TABLE_NAME +
                                                "(" + PlayerTable.NAME + ", " + PlayerTable.PASSWORD + ", " + PlayerTable.MONEY + ") " +
                                                "VALUES (?,  SHA2(?, " + SHA512 + "), ? )");
                        statement.setString(1, (String) data.get(PlayerTable.NAME));
                        statement.setString(2, (String) data.get(PlayerTable.PASSWORD));
                        statement.setInt(3, (Integer) data.get(PlayerTable.MONEY));

                        statement.executeUpdate();

                    }else {
                        Statement stm = connection.createStatement();
                        String sql = getAddRowSQL(tableName, data);
                        stm.executeUpdate(sql);
                    }

                    // Gdx.app.log("SQL_QUERY", sql);


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
                sql += PlayerTable.MONEY + " = " + data.get(PlayerTable.MONEY);
                sql += " WHERE " + PlayerTable.ID + " = " + data.get(PlayerTable.ID);
                //Gdx.app.log("SQL", sql);
                break;
            case PlayerObservatoryTable.TABLE_NAME:
                sql += PlayerObservatoryTable.ACCURACY_LVL + " = " + data.get(PlayerObservatoryTable.ACCURACY_LVL) + ", ";
                sql += PlayerObservatoryTable.SPEED_LVL + " = " + data.get(PlayerObservatoryTable.SPEED_LVL) + ", ";
                sql += PlayerObservatoryTable.END_TIME + " = " + data.get(PlayerObservatoryTable.END_TIME) + ", ";
                sql += PlayerObservatoryTable.PLANET_ID + " = " + data.get(PlayerObservatoryTable.PLANET_ID) + " ";
                sql += " WHERE " + PLAYER_ID + " = " + playerId;
                //Gdx.app.log("SQL", sql);
                break;
            case PlayerGoalTable.TABLE_NAME:
                sql += PlayerGoalTable.AMOUNT_COMPLETED + " = " + data.get(PlayerGoalTable.AMOUNT_COMPLETED);
                sql += " WHERE " + PLAYER_ID + " = " + playerId + " AND " + PlayerGoalTable.RESOURCE_ID + " = " + objectId;
        }

        return sql;
    }
    private String getAddRowSQL(final String tableName, final Map<String, Object> data){
        StringBuilder keyNames = new StringBuilder();
        StringBuilder keyValues = new StringBuilder();

        for (String s : data.keySet()) {
            keyNames.append(s).append(",");
            keyValues.append(data.get(s)).append(",");
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

    public void getLeaderBoard(final int requestCode, final ConnectorEvent eventListener){
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Float> leaderBoard = new HashMap<>();

                    Statement statement = connection.createStatement();
                    String sql = "SELECT " + PlayerTable.ID + ", " + PlayerTable.NAME + " FROM " + PlayerTable.TABLE_NAME;

                    Map<Integer, String> players = new HashMap<>();

                    ResultSet result = statement.executeQuery(sql);
                    while (result.next()) {
                        players.put((Integer) result.getObject(1), (String) result.getObject(2));
                    }

                    ResultSet set;
                    for (Integer playerId : players.keySet()) {

                        String sqlScrip = "SELECT SUM(amount_completed) FROM " + PlayerGoalTable.TABLE_NAME + " WHERE " + PlayerGoalTable.PLAYER_ID + " = " + playerId;
                        set = statement.executeQuery(sqlScrip);
                        int amountCompleted = -1;
                        if(set.next())
                            amountCompleted = ((BigDecimal) set.getObject(1)).intValueExact();

                        sqlScrip = "SELECT SUM(amount_needed) FROM " + PlayerGoalTable.TABLE_NAME + " WHERE " + PlayerGoalTable.PLAYER_ID + " = " + playerId;
                        set = statement.executeQuery(sqlScrip);

                        int amountNeeded = -1;
                        if(set.next())
                            amountNeeded = ((BigDecimal) set.getObject(1)).intValueExact();

                        leaderBoard.put(players.get(playerId), (float) amountCompleted/amountNeeded*100);

                    }

                    eventListener.onLeaderBoardLoaded(requestCode, leaderBoard);
                } catch (SQLException e) {
                    // eventListener.onResultFailed(requestCode, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }
    public void getFinishedGameData(final int requestCode, final ConnectorEvent eventListener){
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> data = new HashMap<>();

                    int counter = 0;

                    Statement statement = connection.createStatement();
                    String sql = "SELECT " + PlayerTable.ID + " FROM " + PlayerTable.TABLE_NAME;

                    ArrayList<Integer> playerIds = new ArrayList<>();

                    ResultSet result = statement.executeQuery(sql);
                    while (result.next()) {
                        playerIds.add((Integer) result.getObject(1));
                    }

                    ResultSet set;
                    for (Integer playerId : playerIds) {

                        String sqlScrip = "SELECT SUM(amount_completed) FROM " + PlayerGoalTable.TABLE_NAME + " WHERE " + PlayerGoalTable.PLAYER_ID + " = " + playerId;
                        set = statement.executeQuery(sqlScrip);
                        int amountCompleted = -1;
                        if(set.next())
                            amountCompleted = ((BigDecimal) set.getObject(1)).intValueExact();

                        sqlScrip = "SELECT SUM(amount_needed) FROM " + PlayerGoalTable.TABLE_NAME + " WHERE " + PlayerGoalTable.PLAYER_ID + " = " + playerId;
                        set = statement.executeQuery(sqlScrip);

                        int amountNeeded = -1;
                        if(set.next())
                            amountNeeded = ((BigDecimal) set.getObject(1)).intValueExact();

                        if(amountCompleted/amountNeeded >= 1)
                            counter++;

                    }

                    data.put(WINNERS_COUNT, counter);
                    data.put(GAME_START, 0);

                    eventListener.onWinnerDataLoaded(requestCode, data);
                } catch (SQLException e) {
                    // eventListener.onResultFailed(requestCode, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public interface ConnectorEvent {

        String WRONG_CREDENTIALS_MESSAGE = "WRONG_CREDENTIALS";

        void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData);
        void onUpdateSuccess(int requestCode, String tableName);
        void onConnect();
        void onConnectionFailed();
        void onResultFailed(int requestCode, String message);
        void onUserLoginSuccessful(int requestCode, Map<String, Object> tableData);
        void onDeleteSuccess(int requestCode, String message);
        void onLeaderBoardLoaded(int requestCode, Map<String, Float> leaderBoard);
        void onWinnerDataLoaded(int requestCode, Map<String, Object> data);
    }

}
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

                    eventListener.
                            onFetchSuccess
                                    (requestCode,
                                            PlayerTable.TABLE_NAME,
                                            requestData);

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
                    StringBuilder keyNames = new StringBuilder();
                    StringBuilder keyValues = new StringBuilder();
                    for (String s : data.keySet()) {
                        keyNames.append(s).append(",");
                        keyValues.append(data.get(s)).append(",");
                    }
                    keyNames = new StringBuilder(keyNames.substring(0, keyNames.length() - 1));
                    keyValues = new StringBuilder(keyValues.substring(0, keyValues.length() - 1));
                    String sql = "INSERT INTO " + tableName + "(" + keyNames + ") VALUE (" + keyValues + ")";

                    Gdx.app.log("SQL_QUERRY", sql);

                    stm.executeUpdate(sql);
                    eventListener.onUpdateSuccess(requestCode);
                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public void updateRow(final int requestCode, final int id, final String tableName, final Map<String, Object> data, final ConnectorEvent eventListener) {
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement stm = connection.createStatement();
                    StringBuilder keyNames = new StringBuilder();
                    StringBuilder keyValues = new StringBuilder();
                    for (String s : data.keySet()) {
                        keyNames.append(s).append(",");
                        keyValues.append(data.get(s)).append(",");
                    }
                    keyNames.deleteCharAt(keyNames.length() - 1);
                    keyNames.deleteCharAt(keyNames.length() - 1);
                    String sql = "UPDATE " + tableName + " SET (" + keyNames.toString() + ") VALUE (" + keyValues.toString() + ") WHERE id = " + id;
                    stm.executeQuery(sql);
                    eventListener.onUpdateSuccess(requestCode);
                } catch (SQLException e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
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

    public interface ConnectorEvent {
        void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData);
        void onUpdateSuccess(int requestCode);
        void onConnect();
        void onConnectionFailed();
        void onResultFailed(int requestCode, String message);
    }
}
package sk.grest.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class DatabaseConnection {

    private Thread connectionThread;
    private Thread taskThread;
    private Connection connection;
    private static DatabaseConnection instance;
    private static final String DATABASE_NAME = "";
    private static final String DATABASE_USERNAME = "";
    private static final String DATABASE_PASSWORD = "";

    private DatabaseConnection(){}

    public static DatabaseConnection getInstance() {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    void connect(final ConnectorEvent eventListener) {
        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?serverTimezone=CET",
                            DATABASE_USERNAME,
                            DATABASE_PASSWORD
                    );
                    eventListener.onConnect();
                } catch (Exception e) {
                    eventListener.onConnectionFailed();
                }
            }
        });
        connectionThread.setDaemon(true);
        connectionThread.start();
    }

    void getTable(final int requestCode, final String tableName, final ConnectorEvent eventListener) {
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM " + tableName;
                    ResultSet result = statement.executeQuery(sql);
                    ArrayList<Map<String, Object>> requestData = new ArrayList();
                    while (result.next()) {
                        Map<String, Object> rowData = new HashMap<>();
                        for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
                            rowData.put(result.getMetaData().getColumnName(i), result.getObject(i));
                        }
                        requestData.add(rowData);
                    }
                    eventListener.onFetchSuccess(requestCode, requestData);
                } catch (Exception e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    void addRow(final int requestCode, final String tableName, final Map<String, Object> data, final ConnectorEvent eventListener) {
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
                    String sql = "INSERT INTO " + tableName + "(" + keyNames.toString() + ") VALUE (" + keyValues.toString() + ")";
                    stm.executeQuery(sql);
                    eventListener.onUpdateSuccess(requestCode);
                } catch (Exception e) {
                    eventListener.onResultFailed(requestCode, e.getMessage());
                }
            }
        });
        taskThread.setDaemon(true);
        taskThread.start();
    }

    void disconnect() {
        try {
            if (taskThread.isAlive())
                taskThread.interrupt();
            if (connectionThread.isAlive())
                taskThread.interrupt();
            connection.close();
        } catch (Exception e) {

        }
    }

    interface ConnectorEvent {
        void onFetchSuccess(int requestCode, ArrayList<Map<String, Object>> tableData);

        void onUpdateSuccess(int requestCode);

        void onConnect();

        void onConnectionFailed();

        void onResultFailed(int requestCode, String message);
    }
}
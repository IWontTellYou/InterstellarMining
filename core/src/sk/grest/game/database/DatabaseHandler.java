package sk.grest.game.database;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.PlanetSystem;
import sk.grest.game.entities.Ship;

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

    public void updatePlanet(int id, PlanetSystem system, String name, float size, float distance,
                             boolean habitable, String info, ArrayList<Resource> resources){
    }

    public void getTable(String tableName, DatabaseConnection.ConnectorEvent listener){
        connection.getTable(requestCode++, tableName, listener);
    }

    public void getTableWherePlayer(String tableName, int playerID, DatabaseConnection.ConnectorEvent listener){
        connection.getTableWherePlayer(requestCode++, tableName, playerID, listener);
    }
}

package sk.grest.game.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

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
    private DatabaseConnection.ConnectorEvent listener;
    private InterstellarMining game;

    public DatabaseHandler(DatabaseConnection connection, InterstellarMining game) {
        this.connection = connection;
        connection.connect(game);
        this.game = game;
    }

    public void verifyPlayer(String name, String password, DatabaseConnection.ConnectorEvent listener){
        connection.verifyPlayer(requestCode++, PlayerTable.TABLE_NAME, name, password, listener);
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

    public void addNewShip(){

    }

    public void updatePlanet(int id, PlanetSystem system, String name, float size, float distance,
                             boolean habitable, String info, ArrayList<Resource> resources){

    }

    public void getPlanetSystems(){

    }

    public ArrayList<Ship> getShipsList(){
        return null;
    }

}

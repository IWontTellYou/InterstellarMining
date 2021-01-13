package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.enums.ShipState;
import sk.grest.game.listeners.DatabaseChangeListener;
import sk.grest.game.listeners.TravelListener;

import static sk.grest.game.defaults.GameConstants.*;

public class Player implements TravelListener {

    private int ID;
    private String name;
    private String password;
    private String email;
    private int level;
    private int experience;

    private ArrayList<Ship> ships;
    private ArrayList<PlanetSystem> systemsDiscovered;
    private ArrayList<Resource> resourcesAtBase;

    private InterstellarMining game;

    public Player(InterstellarMining game, int ID, String name, String password, String email, int level, int experience) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.experience = experience;
        this.game = game;

        this.ships = new ArrayList<>();
        this.systemsDiscovered = new ArrayList<>();
        this.resourcesAtBase = new ArrayList<>();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }
    public ArrayList<PlanetSystem> getSystemsDiscovered() {
        return systemsDiscovered;
    }
    public ArrayList<Resource> getResourcesAtBase() {
        return resourcesAtBase;
    }

    public ArrayList<Ship> getShipsAtBase(){
        ArrayList<Ship> ships = new ArrayList<>();

        for (Ship s : this.ships) {
            if(s.getState().equals(ShipState.AT_THE_BASE))
                ships.add(s);
        }

        return ships;
    }
    public ArrayList<Resource> getResourcesWithAmount(){
        ArrayList<Resource> resourcesWithAmount = new ArrayList<>();
        for (Resource r : resourcesAtBase) {
            if(r.getAmount() > 0){
                resourcesWithAmount.add(r);
            }
        }
        return resourcesWithAmount;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return "Player{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                ", experience=" + experience +
                '}';
    }

    public void update(float delta){
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).update(delta);
        }
    }

    @Override
    public void onMiningFinished(Resource r) {

    }

    @Override
    public void onShipArrivedAtHome(Resource r) {
        for (Resource resource : getResourcesAtBase()) {
            if(resource.getID() == r.getID()){
                resource.addAmount(r);
            }
        }
    }

    @Override
    public void onShipDataChanged(Ship ship) {

        String log = ((ship != null) ? ship.toString() : "null") + " TravelPlan: ";
        if(ship != null) {
            log += (ship.getTravelPlan() != null) ? ship.getTravelPlan().toString() : "null";
            log += " State: " + ship.getState();
        } else
            log += "null State: null";

        Gdx.app.log("LOG", log);


        game.getHandler().updateShipInFleet(
                ID,
                ship.getId(),
                ship.getCurrentDestination().getID(),
                ship.getTravelPlan().getResource().getID(),
                ship.getTravelPlan().getResource().getAmount(),
                ship.getTravelPlan().getStartTime(),
                ShipState.getID(ship.getState()),
                game
        );
    }
}

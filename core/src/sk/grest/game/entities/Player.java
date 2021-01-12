package sk.grest.game.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;

import sk.grest.game.entities.enums.ShipState;

import static sk.grest.game.defaults.GameConstants.*;

public class Player {

    private int ID;
    private String name;
    private String password;
    private String email;
    private int level;
    private int experience;

    private ArrayList<Ship> ships;
    private ArrayList<PlanetSystem> systemsDiscovered;
    private ArrayList<Resource> resourcesAtBase;

    public Player(int ID, String name, String password, String email, int level, int experience) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.experience = experience;

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
        for (Ship s : ships) {
            s.update(delta);
        }
    }

}

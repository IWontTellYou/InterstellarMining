package sk.grest.game.entities;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.planet.PlanetSystem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.listeners.DatabaseChangeListener;

public class Player {

    DatabaseChangeListener listener;

    private int ID;
    private String name;
    private String password;
    private String email;

    private long money;
    private int level;
    private int experience;

    private ArrayList<sk.grest.game.entities.ship.Ship> ships;
    private ArrayList<sk.grest.game.entities.planet.PlanetSystem> systemsDiscovered;
    private ArrayList<Resource> resourcesAtBase;
    private ArrayList<Achievement> achievements;
    private ArrayList<Research> researches;

    private InterstellarMining game;

    public Player(InterstellarMining game, int ID, String name, String password, String email, int level, int experience, long money) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.experience = experience;
        this.game = game;
        this.money = money;

        ships = new ArrayList<>();
        systemsDiscovered = new ArrayList<>();
        resourcesAtBase = new ArrayList<>();
        researches = new ArrayList<>();
        achievements = new ArrayList<>();
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
    public ArrayList<Research> getResearches(){
        return researches;
    }
    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public long getMoney() { return money;}
    public void increaseMoney(long amount) {
        money += amount;
    }
    public void decreaseMoney(long amount) {
        if(amount > money && amount+1 < money)
            money = 0;
        else
            money -= amount;
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

}

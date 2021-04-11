package sk.grest.game.entities;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.factory.FactoryQueue;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.listeners.DatabaseChangeListener;

public class Player {

    DatabaseChangeListener listener;

    private int ID;
    private String name;
    private String password;

    private long money;

    private ArrayList<Ship> ships;
    private ArrayList<Resource> resourcesAtBase;
    private ArrayList<Achievement> achievements;
    private ArrayList<Research> researches;
    private ArrayList<Resource> goalResources;

    private Observatory observatory;
    private ArrayList<FactoryItem> queue;

    private InterstellarMining game;

    public Player(InterstellarMining game, int ID, String name, String password, long money) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.game = game;
        this.money = money;

        ships = new ArrayList<>();
        resourcesAtBase = new ArrayList<>();
        researches = new ArrayList<>();
        achievements = new ArrayList<>();
        queue = new ArrayList<>();
        goalResources = new ArrayList<>();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }
    public ArrayList<Ship> getShipsAtBase(){
        ArrayList<Ship> ships = new ArrayList<>();

        for (Ship s : this.ships) {
            if(s.getState().equals(ShipState.AT_THE_BASE))
                ships.add(s);
        }

        return ships;
    }
    public Ship getShipByID(int id){
        for (Ship s : ships) {
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    public ArrayList<Research> getResearches(){
        return researches;
    }
    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }
    public ArrayList<Resource> getGoalResources(){
        return goalResources;
    }

    // RESOURCE
    public ArrayList<Resource> getResourcesAtBase() {
        return resourcesAtBase;
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
    public Resource getResource(int id){
        for (Resource r : resourcesAtBase) {
            if(r.getID() == id)
                return r;
        }
        return null;
    }

    // OBSERVATORY
    public void setObservatory(Observatory observatory){
        this.observatory = observatory;
    }
    public Observatory getObservatory(){
        return observatory;
    }

    // QUEUE
    public ArrayList<FactoryItem> getQueue(){
        return queue;
    }
    public void addToQueue(FactoryItem item){
        if(queue.size() < FactoryQueue.MAX_ITEMS)
            queue.add(item);
    }
    public void clearQueue(){
        queue.clear();
    }

    // MONEY
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

    public Resource getGoalResourceByID(int id){
        for (Resource r : goalResources) {
            if(r.getID() == id)
                return r;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Player{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public String getFactoryToString(){
        StringBuilder s = new StringBuilder();
        for (FactoryItem item : queue) {
            s.append(ScreenConstants.getTimeFormat(item.getStartTime())).append(" ").append(item.getCount()).append("\n");
        }
        return s.toString();
    }

    public void update(float delta){
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).update(delta);
        }
    }

}

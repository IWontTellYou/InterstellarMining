package sk.grest.game.entities;

import java.sql.Time;
import java.sql.Timestamp;

import sk.grest.game.entities.enums.ShipState;

import static sk.grest.game.defaults.GameConstants.DEFAULT_MINING_TIME;
import static sk.grest.game.entities.enums.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.enums.ShipState.MINING;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_BACK;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_OUT;

public class Ship {

    private int id;

    private String name;

    // UPGRADABLES
    private float miningSpeed;
    private float travelSpeed;
    private float resourceCapacity;
    private float fuelCapacity;
    private float fuelEficiency;
    private Resource carriage;

    //
    private float price;
    private float upgradeLevel;

    // TRAVELING
    private Planet currentDestination;
    private ShipState state;

    private Timestamp taskTime;

    public Ship(int id, String name, Resource carriage, float miningSpeed, float travelSpeed, float resourceCapacity,
                float fuelCapacity, float fuelEficiency, float price, Planet currentDestination,
                ShipState state, Timestamp taskTime, float upgradeLevel) {
        this.id = id;
        this.name = name;
        this.carriage = carriage;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.fuelCapacity = fuelCapacity;
        this.fuelEficiency = fuelEficiency;
        this.price = price;
        this.currentDestination = currentDestination;
        this.state = state;
        this.taskTime = taskTime;
        this.upgradeLevel = upgradeLevel;
    }

    public void update(float delta){

        switch (state){

            case AT_THE_BASE:
                break;

            case TRAVELLING_OUT:
                if (taskTime.before(new Timestamp(System.currentTimeMillis()))){
                    state = MINING;
                    taskTime = new Timestamp(System.currentTimeMillis() + (long) (resourceCapacity * DEFAULT_MINING_TIME / miningSpeed));
                }
                break;

            case MINING:
                if(taskTime.before(new Timestamp(System.currentTimeMillis()))) {
                    state = TRAVELLING_BACK;
                    taskTime = new Timestamp(System.currentTimeMillis() + (long) (currentDestination.getDistance() / travelSpeed));
                }
                break;

            case TRAVELLING_BACK:
                if(taskTime.before(new Timestamp(System.currentTimeMillis()))){
                    state = AT_THE_BASE;
                }
                break;
        }
    }

    public void setDestination(Planet destination){
        currentDestination = destination;
        taskTime = new Timestamp(System.currentTimeMillis() + (long) (destination.getDistance() / travelSpeed));
        state = TRAVELLING_OUT;
    }

    public void upgrade(){
        if(state == AT_THE_BASE){

        }else{
            // TOAST FOR UNABILITY TO UPGRADE WHILE AWAY FROM BASE
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getMiningSpeed() {
        return miningSpeed;
    }

    public float getTravelSpeed() {
        return travelSpeed;
    }

    public float getResourceCapacity() {
        return resourceCapacity;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public float getFuelEficiency() {
        return fuelEficiency;
    }

    public Resource getCarriage() {
        return carriage;
    }

    public float getPrice() {
        return price;
    }

    public Planet getCurrentDestination() {
        return currentDestination;
    }

    public ShipState getState() {
        return state;
    }

    public Timestamp getTaskTime() {
        return taskTime;
    }

    public float getUpgradeLevel() {
        return upgradeLevel;
    }
}

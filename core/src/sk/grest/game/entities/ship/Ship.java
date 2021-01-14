package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import java.sql.Timestamp;

import sk.grest.game.entities.Planet;
import sk.grest.game.entities.Resource;
import sk.grest.game.entities.enums.ShipState;
import sk.grest.game.entities.ship.Attributes.AttributeType;
import sk.grest.game.listeners.TravelListener;

import static sk.grest.game.entities.enums.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.enums.ShipState.MINING;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class Ship {

    private int id;

    private String name;

    // UPGRADABLES
    private float miningSpeed;
    private float travelSpeed;
    private float resourceCapacity;
    private float fuelCapacity;
    private float fuelEfficiency;

    //
    private float price;
    private float upgradeLevel;

    // TRAVELING
    private TravelPlan travelPlan;
    private Attributes attributes;

    private TravelListener listener;

    // private float timeElapsed;

    private boolean resourceAddedToInventory;

    public Ship(int id, String name, TravelListener listener, float miningSpeed, float travelSpeed, float resourceCapacity,
                float fuelCapacity, float fuelEfficiency, float price, float upgradeLevel) {
        this.listener = listener;
        this.id = id;
        this.name = name;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.fuelCapacity = fuelCapacity;
        this.fuelEfficiency = fuelEfficiency;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.travelPlan = null;
        this.resourceAddedToInventory = true;
        // timeElapsed = 1;
    }

    public void setAttributes(int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl,
                              int fuelCapacityLvl, int fuelEfficiencyLvl){
        this.attributes = new Attributes(
                miningSpeedLvl,
                travelSpeedLvl,
                resourceCapacityLvl,
                fuelCapacityLvl,
                fuelEfficiencyLvl
        );
    }

    public void setDestination(Planet destination, Resource resource){
        if(travelPlan != null) {
            travelPlan.reset(destination, resource);
            Gdx.app.log("RESOURCE", resourceAddedToInventory + " " + travelPlan.getCurrentState());
            resourceAddedToInventory = false;
            Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER SETTING DESTINATION");
            listener.onShipDataChanged(this);
        }else{
            setTravelPlan(travelPlan = new TravelPlan(destination, this, resource));
        }
    }

    public void resetDestination(){
        travelPlan.reset();
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setTravelPlan(TravelPlan travelPlan){
        this.travelPlan = travelPlan;
        resourceAddedToInventory = false;
        Gdx.app.log("RESOURCE", resourceAddedToInventory + " " + travelPlan.getCurrentState());
        Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER SETTING TRAVELPLAN");
        listener.onShipDataChanged(this);
    }

    public void upgrade(){
        // TODO FIX - POSSIBLY WRONG
        if(travelPlan.getCurrentState() == AT_THE_BASE){

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

    public int getAttribute(AttributeType type){
        switch (type){
            case MINING_SPEED:
                return (int) miningSpeed + attributes.getAttribute(type);
            case TRAVEL_SPEED:
                return (int) travelSpeed + attributes.getAttribute(type);
            case FUEL_CAPACITY:
                return (int) fuelCapacity + attributes.getAttribute(type);
            case FUEL_EFFICIENCY:
                return (int) fuelEfficiency + attributes.getAttribute(type);
            case RESOURCE_CAPACITY:
                return (int) resourceCapacity + attributes.getAttribute(type);
            default:
                return -1;
        }
    }

    public float getMiningSpeed() {
        if(attributes != null)
            return miningSpeed + attributes.getAttribute(MINING_SPEED);
        else
            return miningSpeed;
    }

    public float getTravelSpeed() {
        if(attributes != null)
            return travelSpeed + attributes.getAttribute(TRAVEL_SPEED);
        else
            return travelSpeed;
    }

    public float getResourceCapacity() {
        if(attributes != null)
            return resourceCapacity + attributes.getAttribute(RESOURCE_CAPACITY);
        else
            return resourceCapacity;
    }

    public float getFuelCapacity() {
        if(attributes != null)
            return fuelCapacity + attributes.getAttribute(FUEL_CAPACITY);
        else
            return fuelCapacity;
    }

    public float getFuelEfficiency() {
        if(attributes != null)
            return fuelEfficiency + attributes.getAttribute(FUEL_EFFICIENCY);
        else
            return fuelEfficiency;
    }

    public Resource getCarriage() {
        if(travelPlan != null){
            return travelPlan.getResource();
        }else{
            return null;
        }
    }

    public float getPrice() {
        return price;
    }

    public Planet getCurrentDestination() {
        if(travelPlan != null)
            return travelPlan.getDestination();
        else
            return null;
    }

    public ShipState getState() {
        if(travelPlan != null)
            return travelPlan.getCurrentState();
        else
            return AT_THE_BASE;
    }

    public long getTimeLeft() {
        if(travelPlan != null)
            return travelPlan.getTimeLeft();
        else
            return 0;
    }

    public TravelPlan getTravelPlan(){
        return travelPlan;
    }

    public float getUpgradeLevel() {
        return upgradeLevel;
    }

    public void update(float delta){

        if(travelPlan != null)
            travelPlan.update(listener, delta);

        if(travelPlan != null && !resourceAddedToInventory){
            listener.onShipArrivedAtHome(travelPlan.getResource().setAmount(travelPlan.getAmount()));
            Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER RESOURCE WAS ADDED TO INVENTORY");
            listener.onShipDataChanged(this);
            resourceAddedToInventory = true;
        }
    }

    @Override
    public String toString() {
        return name + " " + "(Level " + upgradeLevel + ")";
    }
}

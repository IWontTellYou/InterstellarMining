package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import sk.grest.game.entities.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes.AttributeType;
import sk.grest.game.listeners.DatabaseChangeListener;

import static sk.grest.game.entities.ship.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.ship.ShipState.TRAVELLING_BACK;

public class Ship {

    private int id;
    private String name;

    // PROPERTIES
    private float miningSpeed;
    private float travelSpeed;
    private float resourceCapacity;
    private float fuelCapacity;
    private float fuelEfficiency;

    private Attributes attributes;

    private float price;
    private float upgradeLevel;

    // TRAVELING
    private TravelPlan travelPlan;

    private int stateCounter;

    DatabaseChangeListener listener;

    public Ship(DatabaseChangeListener listener, int id, String name, float miningSpeed, float travelSpeed, float resourceCapacity,
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
        this.stateCounter = 0;
    }

    public void setAttributes(int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl, int fuelCapacityLvl, int fuelEfficiencyLvl){
        this.attributes = new Attributes(
                listener,
                miningSpeedLvl,
                travelSpeedLvl,
                resourceCapacityLvl,
                fuelCapacityLvl,
                fuelEfficiencyLvl
        );
    }
    public Attributes getAttributes() {
        return attributes;
    }
    public int getAttribute(AttributeType type){
        switch (type){
            case MINING_SPEED:
                return (int) miningSpeed + ((attributes != null) ? attributes.getAttribute(type) : 0);
            case TRAVEL_SPEED:
                return (int) travelSpeed + ((attributes != null) ? attributes.getAttribute(type) : 0);
            case FUEL_CAPACITY:
                return (int) fuelCapacity + ((attributes != null) ? attributes.getAttribute(type) : 0);
            case FUEL_EFFICIENCY:
                return (int) fuelEfficiency + ((attributes != null) ? attributes.getAttribute(type) : 0);
            case RESOURCE_CAPACITY:
                return (int) resourceCapacity + ((attributes != null) ? attributes.getAttribute(type) : 0);
            default:
                return -1;
        }
    }

    // TODO DONT NEED THIS (I GUESS) REPLACED BY ATTRIBUTES
    public void saveAttributes(){
        // TODO FIX - POSSIBLY WRONG
        if(travelPlan.getCurrentState() == AT_THE_BASE){
            attributes.saveAttributes();
            listener.onAttributesChanged(this, attributes);
        }else{
            // TOAST FOR UNABILITY TO UPGRADE WHILE AWAY FROM BASE
        }

    }

    public void setTravelPlan(TravelPlan travelPlan){
        this.travelPlan = travelPlan;
        Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER SETTING TRAVELPLAN");
        listener.onShipDataChanged(this);
    }
    public void setDestination(DatabaseChangeListener listener, Planet destination, Resource resource){
        if(travelPlan != null) {
            travelPlan.reset(destination, resource);
            Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER SETTING DESTINATION");
            listener.onShipDataChanged(this);
        }else{
            setTravelPlan(travelPlan = new TravelPlan(listener, destination, this, resource));
        }
    }
    public void resetDestination(){
        travelPlan.reset();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
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
        if(travelPlan != null) {
            travelPlan.update(delta);
            if(travelPlan.getCurrentState() == TRAVELLING_BACK && stateCounter == 0){
                Gdx.app.log("PHASE 1", travelPlan.getCurrentState().toString());
                stateCounter++;
            }else if(travelPlan.getCurrentState() == AT_THE_BASE && stateCounter == 1){
                Gdx.app.log("PHASE 2", travelPlan.getCurrentState().toString());
                listener.onShipArrivedAtBase(this, travelPlan.getResource());
                travelPlan.reset();
                stateCounter = 0;
            }
        }
    }

    @Override
    public String toString() {
        return name + " " + "(Level " + (int) upgradeLevel + ")";
    }
}
package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes.AttributeType;
import sk.grest.game.entities.upgrade.Upgradable;
import sk.grest.game.listeners.DatabaseChangeListener;

import static sk.grest.game.entities.ship.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.ship.ShipState.TRAVELLING_BACK;

public class Ship implements Upgradable {

    private static final int MAX_LEVEL = 4;

    private int id;
    private String name;

    // PROPERTIES
    private int miningSpeed;
    private int travelSpeed;
    private int resourceCapacity;

    private Attributes attributes;

    private int price;
    private int upgradeLevel;

    private String assetID;

    // TRAVELING
    private TravelPlan travelPlan;

    private int stateCounter;

    DatabaseChangeListener listener;

    public Ship(DatabaseChangeListener listener, int id, String name, String assetID, int miningSpeed, int travelSpeed, int resourceCapacity,
                int price, int upgradeLevel) {
        this.listener = listener;
        this.id = id;
        this.assetID = assetID;
        this.name = name;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.travelPlan = null;
        this.stateCounter = 0;
    }

    public void setAttributes(int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl){
        this.attributes = new Attributes(
                listener,
                miningSpeedLvl,
                travelSpeedLvl,
                resourceCapacityLvl
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
            case RESOURCE_CAPACITY:
                return (int) resourceCapacity + ((attributes != null) ? attributes.getAttribute(type) : 0);
            default:
                return -1;
        }
    }

    public void saveAttributes(){
        if(travelPlan == null || travelPlan.getCurrentState() == AT_THE_BASE){
            listener.onAttributesChanged(this, attributes);
        }
    }

    public String getAssetID() {
        return assetID;
    }

    public void setTravelPlan(TravelPlan travelPlan){
        this.travelPlan = travelPlan;
        Gdx.app.log("SHIP_UPDATE", "SHIP UPDATE AFTER SETTING TRAVEL PLAN");
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

    public int getPrice() {
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

    public void update(float delta){
        if(travelPlan != null && travelPlan.isSet()) {
            travelPlan.update(delta);
            if(travelPlan.getCurrentState() == AT_THE_BASE && travelPlan.getResource().getAmount() > 0){
                Gdx.app.log("SHIP_UPDATE", "SHIP UPDATED AFTER ARRIVAL AT BASE");
                listener.onShipArrivedAtBase(this, travelPlan.getResource());
                travelPlan.reset();
                stateCounter = 0;
            }
        }
    }

    @Override
    public String toString() {
        return name + " " + "(Level " + upgradeLevel + ")";
    }

    @Override
    public void upgrade(int type) {
        if(upgradeLevel < 4)
            upgradeLevel++;
    }

    @Override
    public int getLevel(int type) {
        return upgradeLevel;
    }

    @Override
    public int getMaxLevel(int type) {
        return MAX_LEVEL;
    }
}

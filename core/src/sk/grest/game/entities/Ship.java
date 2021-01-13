package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.sql.Time;
import java.sql.Timestamp;

import sk.grest.game.defaults.GameConstants;
import sk.grest.game.entities.enums.ShipState;
import sk.grest.game.listeners.TravelListener;

import static sk.grest.game.entities.enums.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.enums.ShipState.MINING;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_BACK;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_OUT;

public class Ship {

    // TODO CREATE MINING PROCESS WHERE IT WOULD BE SPECIFIED WHAT RESOURCE IS BEING MINED AND WHAT AMOUNT OF IT

    private int id;

    private String name;

    // UPGRADABLES
    private float miningSpeed;
    private float travelSpeed;
    private float resourceCapacity;
    private float fuelCapacity;
    private float fuelEficiency;

    //
    private float price;
    private float upgradeLevel;

    // TRAVELING
    private TravelPlan travelPlan;

    private TravelListener listener;

    // private float timeElapsed;

    private boolean resourceAddedToInventory;

    public Ship(int id, String name, TravelListener listener, float miningSpeed, float travelSpeed, float resourceCapacity,
                float fuelCapacity, float fuelEficiency, float price, float upgradeLevel) {
        this.listener = listener;
        this.id = id;
        this.name = name;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.fuelCapacity = fuelCapacity;
        this.fuelEficiency = fuelEficiency;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.travelPlan = null;
        this.resourceAddedToInventory = true;
       // timeElapsed = 1;
    }

    public void setDestination(Planet destination, Resource resource){
        if(travelPlan != null) {
            travelPlan.reset(destination, resource);
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

    public void setTravelPlan(TravelPlan travelPlan){
        this.travelPlan = travelPlan;
        resourceAddedToInventory = false;
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

    public Timestamp getTimeLeft() {
        if(travelPlan != null)
            return travelPlan.getTimeLeft();
        else
            return new Timestamp(0);
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

        if(getState() == AT_THE_BASE && !resourceAddedToInventory){
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

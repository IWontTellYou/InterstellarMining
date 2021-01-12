package sk.grest.game.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.sql.Time;
import java.sql.Timestamp;

import sk.grest.game.defaults.GameConstants;
import sk.grest.game.entities.enums.ShipState;

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

    private Resource carriage;
    private float resourceAmount;

    //
    private float price;
    private float upgradeLevel;

    // TRAVELING
    private TravelPlan travelPlan;

    private float timeElapsed;

    public Ship(int id, String name, Resource carriage, float miningSpeed, float travelSpeed, float resourceCapacity,
                float fuelCapacity, float fuelEficiency, float price, Planet currentDestination,
                ShipState state, Timestamp taskStart, float upgradeLevel) {
        this.id = id;
        this.name = name;
        this.carriage = carriage;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.fuelCapacity = fuelCapacity;
        this.fuelEficiency = fuelEficiency;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.resourceAmount = 0;

        timeElapsed = 1;

        if(currentDestination != null && taskStart != null)
            this.travelPlan = new TravelPlan(currentDestination, this, taskStart);
        else
            this.travelPlan = null;

    }

    public void setDestination(Planet destination){
        travelPlan.reset(destination);
    }

    public void resetDestination(){
        travelPlan.reset();
    }

    public void setTravelPlan(TravelPlan travelPlan, Resource resource){
        this.carriage = resource;
        this.resourceAmount = 0;
        this.travelPlan = travelPlan;
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
        return carriage;
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

    public float getUpgradeLevel() {
        return upgradeLevel;
    }

    public void update(float delta){
        /*

        if(timeElapsed < 0){
            timeElapsed = 1;

            if(getState() == MINING){
                resourceAmount += miningSpeed/60;
        }else {
            timeElapsed -= delta;
        }

        */
    }

    public float getAmountMined(){
        return resourceAmount;
    }

    @Override
    public String toString() {
        return name + " " + "(Level " + upgradeLevel + ")";
    }
}

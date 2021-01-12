package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;

import sk.grest.game.entities.enums.ShipState;

import static sk.grest.game.defaults.GameConstants.BASE_DISTANCE;
import static sk.grest.game.defaults.GameConstants.BASE_MINING_TIME;

public class TravelPlan {

    private final static int FROM_HOME = 0;
    private final static int TO_DEST = 1;
    private final static int FROM_DEST = 2;
    private final static int TO_HOME = 3;

    private Planet destination;
    private Ship ship;

    private Timestamp[] schedule;

    private long miningTime;
    private long travelTime;

    private boolean resourceMined;
    private boolean resourceAddedToInventory;

    public TravelPlan(Planet destination, Ship ship, Timestamp startTime) {
        this.destination = destination;
        this.ship = ship;
        setTravel(startTime);
        this.resourceMined = false;
        this.resourceAddedToInventory = false;
    }

    public TravelPlan(Planet destination, Ship ship){
        this.destination = destination;
        this.ship = ship;
        setTravel(new Timestamp(System.currentTimeMillis()));
    }

    private void setTravel(Timestamp startTime){

        // IF DISTANCE == SHIP's SPEED, TRAVEL TIME WILL BE 5 MINUTES
        travelTime = (long) (destination.getDistance() * BASE_DISTANCE / ship.getTravelSpeed());

        // IF SHIP's CAPACITY == SHIP's MINING SPEED, MINING TIME WILL BE 5 MINUTES
        miningTime = (long) (ship.getResourceCapacity() * BASE_MINING_TIME / ship.getMiningSpeed());

        this.schedule = new Timestamp[4];
        schedule[FROM_HOME] = startTime;
        schedule[TO_DEST] = new Timestamp(schedule[FROM_HOME].getTime() + travelTime);
        schedule[FROM_DEST] = new Timestamp(schedule[TO_DEST].getTime() + miningTime);
        schedule[TO_HOME] = new Timestamp(schedule[FROM_DEST].getTime() + travelTime);
    }

    public ShipState getCurrentState(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if(schedule[TO_HOME].before(currentTime))
            return ShipState.AT_THE_BASE;
        else if(schedule[FROM_DEST].before(currentTime))
            return ShipState.TRAVELLING_BACK;
        else if(schedule[TO_DEST].before(currentTime))
            return ShipState.MINING;
        else if(schedule[FROM_HOME].before(currentTime))
            return ShipState.TRAVELLING_OUT;
        else
            return null;
    }

    public void reset(Planet destination){
        this.destination = destination;
        setTravel(new Timestamp(System.currentTimeMillis()));
    }

    public void reset(){
        Arrays.fill(schedule, null);
        destination = null;
    }

    public Planet getDestination() {
        return destination;
    }

    public Timestamp getTimeLeft(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if(schedule[TO_HOME].before(currentTime))
            return new Timestamp(0);
        else if(schedule[FROM_DEST].before(currentTime))
            return new Timestamp(schedule[TO_HOME].getTime() - currentTime.getTime());
        else if(schedule[TO_DEST].before(currentTime))
            return new Timestamp(schedule[FROM_DEST].getTime() - currentTime.getTime());
        else if(schedule[FROM_HOME].before(currentTime))
            return new Timestamp(schedule[TO_DEST].getTime() - currentTime.getTime());
        else
            return new Timestamp(0);
    }

    public void update(float delta){
        if(getCurrentState() == ShipState.TRAVELLING_BACK && !resourceMined){
            resourceMined = true;

        }
    }

}

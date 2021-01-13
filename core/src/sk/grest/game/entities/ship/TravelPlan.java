package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import java.sql.Timestamp;
import java.util.Arrays;

import sk.grest.game.defaults.GameConstants;
import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.Resource;
import sk.grest.game.entities.enums.ShipState;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.listeners.TravelListener;

import static sk.grest.game.defaults.GameConstants.BASE_DISTANCE;
import static sk.grest.game.defaults.GameConstants.BASE_MINING_TIME;

public class TravelPlan {

    private final static int FROM_HOME = 0;
    private final static int TO_DEST = 1;
    private final static int FROM_DEST = 2;
    private final static int TO_HOME = 3;

    private Planet destination;
    private sk.grest.game.entities.ship.Ship ship;

    private Timestamp[] schedule;

    private Resource resource;
    private float resourceAmount;

    private boolean resourceMined;

    public TravelPlan(Planet destination, sk.grest.game.entities.ship.Ship ship, Resource resource, Timestamp startTime) {
        this.destination = destination;
        this.ship = ship;
        this.resource = resource;
        this.resourceAmount = 0;
        this.resourceMined = false;
        setTravel(startTime);
    }

    public TravelPlan(Planet destination, Ship ship, Resource resource){
        this.destination = destination;
        this.ship = ship;
        this.resource = resource;
        this.resourceAmount = 0;
        this.resourceMined = false;
        setTravel(new Timestamp(System.currentTimeMillis()));
    }

    private void setTravel(Timestamp startTime){

        // IF DISTANCE == SHIP's SPEED, TRAVEL TIME WILL BE 5 MINUTES
        long travelTime = (long) (destination.getDistance() * BASE_DISTANCE / ship.getTravelSpeed());

        // IF SHIP's CAPACITY == SHIP's MINING SPEED, MINING TIME WILL BE 5 MINUTES
        long miningTime = (long) (ship.getResourceCapacity() * BASE_MINING_TIME / ship.getMiningSpeed());

        this.schedule = new Timestamp[4];
        schedule[FROM_HOME] = startTime;
        schedule[TO_DEST] = new Timestamp(schedule[FROM_HOME].getTime() + travelTime);
        schedule[FROM_DEST] = new Timestamp(schedule[TO_DEST].getTime() + miningTime);
        schedule[TO_HOME] = new Timestamp(schedule[FROM_DEST].getTime() + travelTime);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String log = "\n[CURRENT] " + ScreenDeafults.timeLeftFormat.format(currentTime);
        log += "\n[FROM_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_HOME]);
        log += "\n[TO_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_DEST]);
        log += "\n[FROM_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_DEST]);
        log += "\n[TO_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_HOME]);
        Gdx.app.log("\nTIME_TRAVEL", log + "\n");

    }

    public ShipState getCurrentState(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        /*
        String log = "\n[CURRENT] " + ScreenDeafults.timeLeftFormat.format(currentTime);
        log += "\n[FROM_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_HOME]);
        log += "\n[TO_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_DEST]);
        log += "\n[FROM_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_DEST]);
        log += "\n[TO_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_HOME]);
        Gdx.app.log("TIME_TRAVEL", log + "\n");
         */
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

    public void reset(Planet destination, Resource resource){
        this.destination = destination;
        this.resource = resource;
        resource.setAmount(0);
        setTravel(new Timestamp(System.currentTimeMillis()));
    }
    public void reset(){
        Arrays.fill(schedule, null);
        destination = null;
    }

    public Planet getDestination() {
        return destination;
    }
    public Resource getResource() {
        return resource;
    }
    public float getAmount() {
        return resourceAmount;
    }
    public long getStartTime(){
        if(getTimeLeft().getTime() != 0)
            return schedule[FROM_HOME].getTime();
        else
            return 0;
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

    public void update(TravelListener listener, float delta){
        if(getCurrentState() == ShipState.TRAVELLING_BACK && !resourceMined){
            resourceMined = true;
            resourceAmount = ship.getResourceCapacity();
            Gdx.app.log("SHIP_UPDATE", "SHIP UPDATED AFTER MINING ENDED");
            listener.onShipDataChanged(ship);
        }
    }

}

package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.DatabaseChangeListener;

import static sk.grest.game.constants.GameConstants.BASE_DISTANCE;
import static sk.grest.game.constants.GameConstants.BASE_MINING_TIME;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class TravelPlan {

    DatabaseChangeListener listener;

    private final static int FROM_HOME = 0;
    private final static int TO_DEST = 1;
    private final static int FROM_DEST = 2;
    private final static int TO_HOME = 3;

    private Planet destination;
    private sk.grest.game.entities.ship.Ship ship;

    private long[] schedule;

    private Resource resource;
    private int resourceAmount;

    private boolean resourceMined;
    private boolean travelBegun;

    public TravelPlan(DatabaseChangeListener listener, Planet destination, Ship ship, Resource resource, long startTime) {
        this.listener = listener;
        this.destination = destination;
        this.ship = ship;
        this.resource = Resource.clone(resource);
        this.resourceAmount = 0;
        this.resourceMined = false;
        setTravel(startTime);
    }
    public TravelPlan(DatabaseChangeListener listener, Planet destination, Ship ship, Resource resource){
        this(listener, destination, ship, resource, System.currentTimeMillis());
    }

    private void setTravel(long startTime){

        // IF DISTANCE == SHIP's SPEED, TRAVEL TIME WILL BE 5 MINUTES
        long travelTime = (long) (destination.getDistance() * BASE_DISTANCE / ship.getAttribute(TRAVEL_SPEED));

        // IF SHIP's CAPACITY == SHIP's MINING SPEED, MINING TIME WILL BE 5 MINUTES
        long miningTime = (long) (ship.getAttribute(RESOURCE_CAPACITY) * BASE_MINING_TIME / ship.getAttribute(MINING_SPEED));

        this.schedule = new long[4];
        schedule[FROM_HOME] = startTime;
        schedule[TO_DEST] = schedule[FROM_HOME] + travelTime;
        schedule[FROM_DEST] = schedule[TO_DEST] + miningTime;
        schedule[TO_HOME] = schedule[FROM_DEST] + travelTime;

        long currentTime = System.currentTimeMillis();
        String log = "\n[CURRENT] " + ScreenConstants.timeLeftFormat.format(currentTime);
        log += "\n[FROM_HOME] " + ScreenConstants.timeLeftFormat.format(schedule[FROM_HOME]);
        log += "\n[TO_DESTINATION] " + ScreenConstants.timeLeftFormat.format(schedule[TO_DEST]);
        log += "\n[FROM_DESTINATION] " + ScreenConstants.timeLeftFormat.format(schedule[FROM_DEST]);
        log += "\n[TO_HOME] " + ScreenConstants.timeLeftFormat.format(schedule[TO_HOME]);
        Gdx.app.log("\nTIME_TRAVEL", log + "\n");

        travelBegun = true;

    }

    public boolean isTravelBegun() {
        return travelBegun;
    }
    public void setTravelBegun(boolean travelBegun) {
        this.travelBegun = travelBegun;
    }

    public ShipState getCurrentState(){
        long currentTime = System.currentTimeMillis();

        /*
        String log = "\n[CURRENT] " + ScreenDeafults.timeLeftFormat.format(currentTime);
        log += "\n[FROM_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_HOME]);
        log += "\n[TO_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_DEST]);
        log += "\n[FROM_DESTINATION] " + ScreenDeafults.timeLeftFormat.format(schedule[FROM_DEST]);
        log += "\n[TO_HOME] " + ScreenDeafults.timeLeftFormat.format(schedule[TO_HOME]);
        Gdx.app.log("TIME_TRAVEL", log + "\n");
         */

        if(schedule[TO_HOME] <= currentTime)
            return ShipState.AT_THE_BASE;
        else if(schedule[FROM_DEST] <= currentTime)
            return ShipState.TRAVELLING_BACK;
        else if(schedule[TO_DEST] <= currentTime)
            return ShipState.MINING;
        else if(schedule[FROM_HOME] <= currentTime)
            return ShipState.TRAVELLING_OUT;
        else
            return null;
    }

    public void reset(Planet destination, Resource resource){
        this.destination = destination;
        this.resource = resource;
        resource.setAmount(0);
        setTravel(System.currentTimeMillis());
    }

    public void reset(){
        Arrays.fill(schedule, 0);
        destination = null;
    }

    public Planet getDestination() {
        return destination;
    }
    public Resource getResource() {
        return resource;
    }
    public int getAmount() {
        return resourceAmount;
    }
    public long getStartTime(){
        if(getTimeLeft() != 0)
            return schedule[FROM_HOME];
        else
            return 0;
    }

    public long getTimeLeft(){
        long currentTime = System.currentTimeMillis();

        if(schedule[TO_HOME] < currentTime)
            return 0;
        else if(schedule[FROM_DEST] < currentTime)
            return schedule[TO_HOME] - currentTime;
        else if(schedule[TO_DEST] < currentTime)
            return schedule[FROM_DEST] - currentTime;
        else if(schedule[FROM_HOME] < currentTime)
            return schedule[TO_DEST] - currentTime;
        else
            return 0;
    }

    public void update(float delta){
        if(getCurrentState() == ShipState.TRAVELLING_BACK && !resourceMined){
            resourceMined = true;
            resource.setAmount(ship.getAttribute(RESOURCE_CAPACITY));
            Gdx.app.log("SHIP_UPDATE", "SHIP UPDATED AFTER MINING ENDED");
            listener.onShipDataChanged(ship);
        }
    }

    public static int getDistance(Planet p){
        return (int) (p.getDistance() * BASE_DISTANCE);
    }
    public static int getTime(Ship s, Planet p) {
        int travelTime = (int) (p.getDistance() * BASE_DISTANCE / s.getAttribute(TRAVEL_SPEED));
        int miningTime = (int) (s.getAttribute(RESOURCE_CAPACITY) * BASE_MINING_TIME / s.getAttribute(MINING_SPEED));
        return (int) 2 * travelTime + miningTime;
    }

}

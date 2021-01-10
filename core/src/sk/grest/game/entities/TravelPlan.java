package sk.grest.game.entities;

import java.sql.Time;
import java.sql.Timestamp;

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

    public TravelPlan(Planet destination, Ship ship, Timestamp startTime) {
        this.destination = destination;
        this.ship = ship;

        // IF DISTANCE == SHIP's SPEED, TRAVEL TIME WILL BE 5 MINUTES
        long travelTime = (long) (destination.getDistance() * BASE_DISTANCE / ship.getTravelSpeed());

        // IF SHIP's CAPACITY == SHIP's MINING SPEED, MINING TIME WILL BE 5 MINUTES
        long miningTime = (long) (ship.getResourceCapacity() * BASE_MINING_TIME / ship.getMiningSpeed());

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
}

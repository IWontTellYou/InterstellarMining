package sk.grest.game.entities;

import sk.grest.game.entities.enums.ShipState;

import static sk.grest.game.defaults.GameConstants.DEFAULT_MINING_TIME;
import static sk.grest.game.entities.enums.ShipState.AT_THE_BASE;
import static sk.grest.game.entities.enums.ShipState.MINING;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_BACK;
import static sk.grest.game.entities.enums.ShipState.TRAVELLING_OUT;

public class Ship {

    private String name;

    // UPGRADABLES
    private int miningSpeed;
    private int travelSpeed;
    private int resourceCapacity;

    // TRAVELING
    private Planet currentDestination;
    private ShipState state;
    private float travelTime;
    private float miningTime;

    public Ship(String name, int miningSpeed, int travelSpeed, int resourceCapacity) {
        this.name = name;
        this.miningSpeed = miningSpeed;
        this.travelSpeed = travelSpeed;
        this.resourceCapacity = resourceCapacity;
        this.state = ShipState.AT_THE_BASE;
    }

    public void update(float delta){

        switch (state){

            case AT_THE_BASE:
                break;

            case TRAVELLING_OUT:
                travelTime -= delta;
                if (travelTime <= 0){
                    state = MINING;
                    miningTime = resourceCapacity * DEFAULT_MINING_TIME / miningSpeed;
                }
                break;

            case MINING:
                miningTime -= delta;
                if(miningTime >= 0) {
                    state = TRAVELLING_BACK;
                    travelTime = currentDestination.getDistance() / travelSpeed;
                }
                break;

            case TRAVELLING_BACK:
                travelTime -= delta;
                if(travelTime <= 0){
                    state = AT_THE_BASE;
                }
                break;
        }
    }

    public void setDestination(Planet destination){
        currentDestination = destination;
        travelTime = destination.getDistance()/travelSpeed;
        state = TRAVELLING_OUT;
    }

    public void upgrade(){
        if(state == AT_THE_BASE){

        }else{
            // TOAST FOR UNABILITY TO UPGRADE WHILE AWAY FROM BASE
        }

    }
}

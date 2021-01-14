package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import javax.xml.stream.events.Attribute;

import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.listeners.DatabaseChangeListener;

import static sk.grest.game.entities.ship.Attributes.AttributeType.FUEL_EFFICIENCY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.RESOURCE_CAPACITY;

public class Attributes {

    private DatabaseChangeListener listener;

    private int miningSpeedLvl;
    private int travelSpeedLvl;
    private int resourceCapacityLvl;
    private int fuelCapacityLvl;
    private int fuelEfficiencyLvl;

    private int miningSpeedTemp;
    private int travelSpeedTemp;
    private int resourceCapacityTemp;
    private int fuelCapacityTemp;
    private int fuelEfficiencyTemp;

    public Attributes(DatabaseChangeListener listener) {
        this(listener, 1, 1, 1, 1, 1);
    }
    public Attributes(DatabaseChangeListener listener, int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl, int fuelCapacityLvl, int fuelEfficiencyLvl) {
        this.miningSpeedLvl = miningSpeedLvl;
        this.travelSpeedLvl = travelSpeedLvl;
        this.resourceCapacityLvl = resourceCapacityLvl;
        this.fuelCapacityLvl = fuelCapacityLvl;
        this.fuelEfficiencyLvl = fuelEfficiencyLvl;

        miningSpeedTemp = 0;
        travelSpeedTemp = 0;
        resourceCapacityTemp = 0;
        fuelCapacityTemp = 0;
        fuelEfficiencyTemp = 0;

        this.listener = listener;

    }

    public int getAttribute(Attributes.AttributeType type){
        switch (type){
            case MINING_SPEED:
                return miningSpeedLvl;
            case TRAVEL_SPEED:
                return travelSpeedLvl;
            case FUEL_CAPACITY:
                return fuelCapacityLvl;
            case FUEL_EFFICIENCY:
                return fuelEfficiencyLvl;
            case RESOURCE_CAPACITY:
                return resourceCapacityLvl;
            default:
                return -1;
        }
    }
    public int getTemporaryAttribute(Attributes.AttributeType type){
        switch (type){
            case MINING_SPEED:
                return miningSpeedTemp;
            case TRAVEL_SPEED:
                return travelSpeedTemp;
            case FUEL_CAPACITY:
                return fuelCapacityTemp;
            case FUEL_EFFICIENCY:
                return fuelEfficiencyTemp;
            case RESOURCE_CAPACITY:
                return resourceCapacityTemp;
            default:
                return -1;
        }
    }

    public void increaseTemporaryAttribute(AttributeType type){
        increaseTemporaryAttribute(type, 1);
    }
    public void increaseTemporaryAttribute(AttributeType type, int amount){
        switch (type){
            case MINING_SPEED:
                miningSpeedTemp += amount;
                break;
            case TRAVEL_SPEED:
                travelSpeedTemp += amount;
                break;
            case FUEL_CAPACITY:
                fuelCapacityTemp += amount;
                break;
            case FUEL_EFFICIENCY:
                fuelEfficiencyTemp += amount;
                break;
            case RESOURCE_CAPACITY:
                resourceCapacityTemp += amount;
                break;
            default:
                break;
        }
    }
    public void decreaseTemporaryAttribute(AttributeType type){
        if(getTemporaryAttribute(type) > 0)
            decreaseTemporaryAttribute(type, 1);
    }
    public void decreaseTemporaryAttribute(AttributeType type, int amount){
        switch (type){
            case MINING_SPEED:
                miningSpeedTemp -= amount;
                break;
            case TRAVEL_SPEED:
                travelSpeedTemp -= amount;
                break;
            case FUEL_CAPACITY:
                fuelCapacityTemp -= amount;
                break;
            case FUEL_EFFICIENCY:
                Gdx.app.log("ADD", FUEL_EFFICIENCY + " -");
                fuelEfficiencyTemp -= amount;
                break;
            case RESOURCE_CAPACITY:
                resourceCapacityTemp -= amount;
                break;
            default:
                break;
        }
    }
    public void saveAttributes(){
        miningSpeedLvl += miningSpeedTemp;
        travelSpeedLvl += travelSpeedTemp;
        fuelEfficiencyLvl += fuelEfficiencyTemp;
        fuelCapacityLvl += fuelCapacityTemp;
        resourceCapacityLvl += resourceCapacityTemp;

        miningSpeedTemp = 0;
        travelSpeedTemp = 0;
        fuelEfficiencyTemp = 0;
        fuelCapacityTemp = 0;
        resourceCapacityTemp = 0;
    }

    @Deprecated
    private void increaseAttribute(AttributeType type, int amount){
        switch (type){
            case MINING_SPEED:
                miningSpeedLvl += amount;
                break;
            case TRAVEL_SPEED:
                travelSpeedLvl += amount;
                break;
            case FUEL_CAPACITY:
                fuelCapacityLvl += amount;
                break;
            case FUEL_EFFICIENCY:
                fuelEfficiencyLvl += amount;
                break;
            case RESOURCE_CAPACITY:
                resourceCapacityLvl += amount;
                break;
            default:
                break;
        }
    }

    public enum AttributeType {
        MINING_SPEED,
        TRAVEL_SPEED,
        RESOURCE_CAPACITY,
        FUEL_CAPACITY,
        FUEL_EFFICIENCY;

        public static AttributeType getAttributeType(int id){

            switch (id){
                case 0:
                    return TRAVEL_SPEED;
                case 1:
                    return MINING_SPEED;
                case 2:
                    return FUEL_CAPACITY;
                case 3:
                    return FUEL_EFFICIENCY;
                case 4:
                    return RESOURCE_CAPACITY;
            }

            return null;
        }

    }

}



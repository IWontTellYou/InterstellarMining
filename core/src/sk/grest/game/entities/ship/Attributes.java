package sk.grest.game.entities.ship;

import com.badlogic.gdx.Gdx;

import javax.xml.stream.events.Attribute;

import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.listeners.DatabaseChangeListener;

import static sk.grest.game.entities.ship.Attributes.AttributeType.FUEL_EFFICIENCY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.RESOURCE_CAPACITY;

public class Attributes {

    public static final int BASE_PRICE = 50;
    public static final float MULTIPLIER = 1.01f;

    private DatabaseChangeListener listener;

    private int miningSpeedLvl;
    private int travelSpeedLvl;
    private int resourceCapacityLvl;
    private int fuelCapacityLvl;
    private int fuelEfficiencyLvl;

    public Attributes(DatabaseChangeListener listener) {
        this(listener, 1, 1, 1, 1, 1);
    }
    public Attributes(DatabaseChangeListener listener, int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl, int fuelCapacityLvl, int fuelEfficiencyLvl) {
        this.miningSpeedLvl = miningSpeedLvl;
        this.travelSpeedLvl = travelSpeedLvl;
        this.resourceCapacityLvl = resourceCapacityLvl;
        this.fuelCapacityLvl = fuelCapacityLvl;
        this.fuelEfficiencyLvl = fuelEfficiencyLvl;
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
    public void increaseAttribute(AttributeType type){
        switch (type){
            case MINING_SPEED:
                miningSpeedLvl++;
                break;
            case TRAVEL_SPEED:
                travelSpeedLvl++;
                break;
            case FUEL_CAPACITY:
                fuelCapacityLvl++;
                break;
            case FUEL_EFFICIENCY:
                fuelEfficiencyLvl++;
                break;
            case RESOURCE_CAPACITY:
                resourceCapacityLvl++;
                break;
            default:
                break;
        }
    }
    public int getAttributePrice(AttributeType type){
        switch (type){
            case MINING_SPEED:
                return (int) (BASE_PRICE * Math.pow(MULTIPLIER, miningSpeedLvl));
            case TRAVEL_SPEED:
                return (int) (BASE_PRICE * Math.pow(MULTIPLIER, travelSpeedLvl));
            case FUEL_CAPACITY:
                return (int) (BASE_PRICE * Math.pow(MULTIPLIER, fuelCapacityLvl));
            case FUEL_EFFICIENCY:
                return (int) (BASE_PRICE * Math.pow(MULTIPLIER, fuelEfficiencyLvl));
            case RESOURCE_CAPACITY:
                return (int) (BASE_PRICE * Math.pow(MULTIPLIER, resourceCapacityLvl));
            default:
                return 0;
        }
    }

    /*
    public void saveAttributes(){
        miningSpeedLvl += miningSpeedTemp;
        travelSpeedLvl += travelSpeedTemp;
        fuelEfficiencyLvl += fuelEfficiencyTemp;
        fuelCapacityLvl += fuelCapacityTemp;
        resourceCapacityLvl += resourceCapacityTemp;
    }
    */

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
        public AttributeType getType(int id){
            switch (id){
                case 0:
                    return MINING_SPEED;
                case 1:
                    return TRAVEL_SPEED;
                case 2:
                    return FUEL_EFFICIENCY;
                case 3:
                    return FUEL_CAPACITY;
                case 4:
                    return RESOURCE_CAPACITY;
                default:
                    return null;
            }
        }

    }

}



package sk.grest.game.entities.ship;

import sk.grest.game.listeners.DatabaseChangeListener;

public class Attributes {

    public static final int BASE_PRICE = 50;
    public static final float MULTIPLIER = 1.01f;

    private DatabaseChangeListener listener;

    private int miningSpeedLvl;
    private int travelSpeedLvl;
    private int resourceCapacityLvl;

    public Attributes(DatabaseChangeListener listener) {
        this(listener, 1, 1, 1);
    }
    public Attributes(DatabaseChangeListener listener, int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl) {
        this.miningSpeedLvl = miningSpeedLvl;
        this.travelSpeedLvl = travelSpeedLvl;
        this.resourceCapacityLvl = resourceCapacityLvl;
        this.listener = listener;
    }

    public int getAttribute(Attributes.AttributeType type){
        switch (type){
            case MINING_SPEED:
                return miningSpeedLvl;
            case TRAVEL_SPEED:
                return travelSpeedLvl;
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
        RESOURCE_CAPACITY;

        public static AttributeType getAttributeType(int id){

            switch (id){
                case 0:
                    return TRAVEL_SPEED;
                case 1:
                    return MINING_SPEED;
                case 2:
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
                    return RESOURCE_CAPACITY;
                default:
                    return null;
            }
        }

    }

}



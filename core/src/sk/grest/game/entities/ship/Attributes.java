package sk.grest.game.entities.ship;

public class Attributes {

    private int miningSpeedLvl;
    private int travelSpeedLvl;
    private int resourceCapacityLvl;
    private int fuelCapacityLvl;
    private int fuelEfficiencyLvl;

    Attributes(){
        miningSpeedLvl = 1;
        travelSpeedLvl = 1;
        resourceCapacityLvl = 1;
        fuelCapacityLvl = 1;
        fuelEfficiencyLvl = 1;
    }

    public Attributes(int miningSpeedLvl, int travelSpeedLvl, int resourceCapacityLvl,
                      int fuelCapacityLvl, int fuelEfficiencyLvl) {
        this.miningSpeedLvl = miningSpeedLvl;
        this.travelSpeedLvl = travelSpeedLvl;
        this.resourceCapacityLvl = resourceCapacityLvl;
        this.fuelCapacityLvl = fuelCapacityLvl;
        this.fuelEfficiencyLvl = fuelEfficiencyLvl;
    }

    public int getMiningSpeedLvl() {
        return miningSpeedLvl;
    }

    public int getTravelSpeedLvl() {
        return travelSpeedLvl;
    }

    public int getResourceCapacityLvl() {
        return resourceCapacityLvl;
    }

    public int getFuelCapacityLvl() {
        return fuelCapacityLvl;
    }

    public int getFuelEfficiencyLvl() {
        return fuelEfficiencyLvl;
    }

    // INCREASING ATRIBUTES

    public void increaseMiningSpeedLvl() {
        this.miningSpeedLvl++;
    }

    public void increaseTravelSpeedLvl() {
        this.travelSpeedLvl++;
    }

    public void increaseResourceCapacityLvl() {
        this.resourceCapacityLvl++;
    }

    public void increaseFuelCapacityLvl() {
        this.fuelCapacityLvl++;
    }

    public void increaseFuelEfficiencyLvl() {
        this.fuelEfficiencyLvl++;
    }
}

package sk.grest.game.entities;

public class Company {

    public static final float MULTIPLIER = 1.65f;

    private String name;
    private long netWorth;

    // LEVEL
    private int rank;

    // UPGRADABLES
    private int shipsOwned;
    private int shipSpeed;
    private int shipCapacity;

    public Company(String name) {
        this.name = name; // Name specified by a player
        this.netWorth = 50000000; // 50 Billion
        this.rank = 1;
        this.shipsOwned = 1;
        this.shipSpeed = 20000; // KPH
        this.shipCapacity = 5000; // tonnes
    }

    public String getName() {
        return name;
    }

    public long getNetWorth() {
        return netWorth;
    }

    public int getRank() {
        return rank;
    }

    public void addRank() {
        this.rank++;
    }

    public int getShipsOwned() {
        return shipsOwned++;
    }

    public void setShipsOwned(int shipsOwned) {
        this.shipsOwned *= MULTIPLIER;
    }

    public int getShipSpeed() {
        return shipSpeed *= MULTIPLIER;
    }

    public void setShipSpeed(int shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    public int getShipCapacity() {
        return shipCapacity;
    }

    public void setShipCapacity(int shipCapacity) {
        this.shipCapacity = shipCapacity;
    }
}

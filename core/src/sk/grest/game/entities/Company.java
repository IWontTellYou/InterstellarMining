package sk.grest.game.entities;

import java.util.ArrayList;

import sk.grest.game.defaults.GameConstants;

import static sk.grest.game.defaults.GameConstants.*;

public class Company {

    private String name;
    private long netWorth;

    // LEVEL
    private int rank;

    private ArrayList<Ship> ships;

    public Company(String name) {
        this.ships = new ArrayList<>();
        this.name = name; // Name specified by a player
        this.netWorth = (long) (50 * BILLION); // 50 Billion
        this.rank = 1;
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

    public ArrayList<Ship> getShips() {
        return ships;
    }
}

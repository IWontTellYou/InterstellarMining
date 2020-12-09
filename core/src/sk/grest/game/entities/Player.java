package sk.grest.game.entities;

import java.util.ArrayList;

import static sk.grest.game.defaults.GameConstants.*;

public class Player {

    private String name;
    private long netWorth;

    // LEVEL
    private int rank;

    private ArrayList<Ship> ships;

    public Player(String name) {
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

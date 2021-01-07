package sk.grest.game.entities;

import java.util.ArrayList;

public class PlanetSystem {

    private int id;
    private String starName;
    private String name;
    private int unlockLvl;
    private boolean unlocked;
    private ArrayList<Planet> planets;

    public PlanetSystem(int id, String starName, String name, int unlockLvl, boolean unlocked, ArrayList<Planet> planets) {
        this.id = id;
        this.starName = starName;
        this.name = name;
        this.unlockLvl = unlockLvl;
        this.unlocked = unlocked;
        this.planets = planets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStarName() {
        return starName;
    }

    public int getUnlockLvl() {
        return unlockLvl;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }
}


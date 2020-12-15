package sk.grest.game.entities;

import java.util.ArrayList;

public class PlanetSystem {

    private int id;
    private String name;
    private int unlockLvl;
    private boolean unlocked;
    private ArrayList<Planet> planets;

    public PlanetSystem(int id, String name, int unlockLvl, boolean unlocked, ArrayList<Planet> planets) {
        this.id = id;
        this.name = name;
        this.unlockLvl = unlockLvl;
        this.unlocked = unlocked;
        this.planets = planets;
    }
}


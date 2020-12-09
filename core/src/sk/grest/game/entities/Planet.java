package sk.grest.game.entities;

import java.util.ArrayList;

public class Planet {

    private int ID;
    private PlanetSystem system;
    private String name;
    private float size;
    private float distance;
    private boolean habitable;
    private String info;
    private ArrayList<Resource> resources;

    public Planet(int ID, PlanetSystem system, String name, float size, float distance, boolean habitable, String info, ArrayList<Resource> resources) {
        this.ID = ID;
        this.system = system;
        this.name = name;
        this.size = size;
        this.distance = distance;
        this.habitable = habitable;
        this.info = info;
        this.resources = resources;
    }

    public Planet(String name, float size, float distance, boolean habitable, String info, ArrayList<Resource> resources) {
        this.name = name;
        this.size = size;
        this.distance = distance;
        this.habitable = habitable;
        this.info = info;
        this.resources = resources;
    }

    public int getID() {
        return ID;
    }

    public PlanetSystem getSystem() {
        return system;
    }

    public String getName() {
        return name;
    }

    public float getSize() {
        return size;
    }

    public float getDistance() {
        return distance;
    }

    public boolean isHabitable() {
        return habitable;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
}

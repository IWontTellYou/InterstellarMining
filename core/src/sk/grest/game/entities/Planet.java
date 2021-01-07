package sk.grest.game.entities;

import java.util.ArrayList;

public class Planet {

    private int ID;
    private String name;
    private float distance;
    private boolean habitable;
    private String info;
    private ArrayList<Resource> resources;
    private String assetId;

    public Planet(int ID, String name, String assetId, float distance, boolean habitable, String info, ArrayList<Resource> resources) {
        this.ID = ID;
        this.name = name;
        this.assetId = assetId;
        this.distance = distance;
        this.habitable = habitable;
        this.info = info;
        this.resources = resources;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAssetId() {
        return assetId;
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

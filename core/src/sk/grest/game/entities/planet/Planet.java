package sk.grest.game.entities.planet;

import java.util.ArrayList;

import sk.grest.game.entities.resource.Resource;

public class Planet {

    private int ID;
    private String name;
    private int distance;
    private boolean found;
    private ArrayList<Resource> resources;
    private String assetId;

    public Planet(int ID, String name, String assetId, int distance, ArrayList<Resource> resources) {
        this.ID = ID;
        this.name = name;
        this.assetId = assetId;
        this.distance = distance;
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
    public int getDistance() {
        return distance;
    }
    public boolean isFound() {
        return found;
    }
    public void setFound(boolean found){
        this.found = found;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
}

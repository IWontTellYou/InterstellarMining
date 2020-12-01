package sk.grest.game.entities;

import java.util.ArrayList;

public class Planet {

    private int ID;
    private String name;
    private String mass;
    private float distance;
    private ArrayList<Resource> resources;

    public Planet(int ID, String name, String mass, ArrayList<Resource> resources) {
        this.ID = ID;
        this.name = name;
        this.mass = mass;
        this.resources = resources;
    }

    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Mass: " + mass + "\n" +
                "Resources: " + resources.toString();
    }

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getMass() {
        return mass;
    }
    public float getDistance() {
        return distance;
    }
    public ArrayList<Resource> getResources() {
        return resources;
    }
    public String getResourcesString(){
        StringBuilder resources = new StringBuilder();
        for (Resource r : this.resources) {
            resources.append(r.name).append(", ");
        }
        resources.delete(resources.length()-2, resources.length());

        return resources.toString();
    }

}

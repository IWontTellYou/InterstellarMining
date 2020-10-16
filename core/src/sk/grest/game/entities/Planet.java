package sk.grest.game.entities;

import java.util.ArrayList;

public class Planet {

    private int ID;
    private String name;
    private float mass;
    private float distance;
    private ArrayList<Resource> resources;

    public Planet(int ID, String name, float size, ArrayList<Resource> resources) {
        this.ID = ID;
        this.name = name;
        this.mass = size;
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
    public float getMass() {
        return mass;
    }
    public float getDistance() {
        return distance;
    }
    public ArrayList<Resource> getResources() {
        return resources;
    }



}

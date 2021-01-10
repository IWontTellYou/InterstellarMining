package sk.grest.game.entities;

import sk.grest.game.entities.enums.ResourceRarity;
import sk.grest.game.entities.enums.ResourceState;

public class Resource{

    private int ID;
    private ResourceState state;
    private ResourceRarity rarity;
    private String name;
    private float price;

    public Resource(int ID, String name, ResourceState state, ResourceRarity rarity, float price) {
        this.ID = ID;
        this.state = state;
        this.rarity = rarity;
        this.name = name;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public ResourceState getState() {
        return state;
    }

    public ResourceRarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "ID=" + ID +
                ", state=" + state +
                ", rarity=" + rarity +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

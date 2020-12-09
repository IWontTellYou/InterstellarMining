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

}

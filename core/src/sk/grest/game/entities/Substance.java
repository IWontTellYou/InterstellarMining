package sk.grest.game.entities;

import sk.grest.game.entities.enums.ResourceRarity;
import sk.grest.game.entities.enums.ResourceState;

public class Substance {

    private ResourceState state;
    private ResourceRarity rarity;
    protected int ID;
    protected String name;

    public Substance(int ID, String name, ResourceState state, ResourceRarity rarity) {
        this.ID = ID;
        this.name = name;
        this.state = state;
        this.rarity = rarity;
    }
}

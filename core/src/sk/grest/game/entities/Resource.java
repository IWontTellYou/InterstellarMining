package sk.grest.game.entities;

import sk.grest.game.entities.enums.ResourceRarity;
import sk.grest.game.entities.enums.ResourceState;

public class Resource extends Substance{

    private float value;

    public Resource(int ID, String name, ResourceState state, ResourceRarity rarity, float value) {
        super(ID, name, state, rarity);
        this.value = value;
    }

}

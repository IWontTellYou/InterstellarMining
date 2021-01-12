package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;

import sk.grest.game.entities.enums.ResourceRarity;
import sk.grest.game.entities.enums.ResourceState;

public class Resource{

    private int ID;
    private ResourceState state;
    private ResourceRarity rarity;
    private String name;
    private float price;
    private float amount;

    public Resource(int ID, String name, ResourceState state, ResourceRarity rarity, float price, float amount) {
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

    public void addAmount(Resource resource){
        if(resource.name.equals(this.name)){
            this.amount += resource.amount;
            resource.amount = 0;
        }else
            Gdx.app.log("ERROR", "Cant add up two different resources");
    }

    public void subtractAmount(float amount){
        this.amount -= amount;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    @Override
    public String toString() {

        return name + " (" + rarity.toString() + ")";

    }
}

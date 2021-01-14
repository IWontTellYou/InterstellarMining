package sk.grest.game.entities.resource;

import com.badlogic.gdx.Gdx;

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
        this.amount = amount;
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
        this.amount += resource.amount;
        resource.amount = 0;
    }

    public void subtractAmount(float amount){
        this.amount -= amount;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    @Override
    public String toString() {

        return name + " (" + rarity.toString() + ")";

    }

    public static Resource clone(Resource resource){
        return new Resource(resource.ID, resource.name, resource.state, resource.rarity, resource.price, resource.amount);
    }

}
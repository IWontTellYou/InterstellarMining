package sk.grest.game.entities.resource;

import com.badlogic.gdx.Gdx;

public class Resource{

    private int ID;
    private ResourceState state;
    private ResourceRarity rarity;
    private String name;
    private String assetName;
    private int price;
    private int amount;

    public Resource(int ID, String name, String assetName, ResourceState state, ResourceRarity rarity, int price, int amount) {
        this.ID = ID;
        this.state = state;
        this.rarity = rarity;
        this.name = name;
        this.assetName = assetName;
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

    public String getAssetName() {
        return assetName;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void addAmount(Resource resource){
        this.amount += resource.amount;
        resource.amount = 0;
    }

    public void subtractAmount(int amount){
        this.amount -= amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {

        return name + " (" + rarity.toString() + ")";

    }

    public static Resource clone(Resource resource){
        return new Resource(resource.ID, resource.name, resource.assetName, resource.state, resource.rarity, resource.price, resource.amount);
    }

}

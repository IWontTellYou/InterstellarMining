package sk.grest.game.entities.upgrade;

import java.util.ArrayList;

import sk.grest.game.entities.resource.Resource;

public class UpgradeRecipe {

    public static final int SHIP_UPGRADE = 1;
    public static final int OBSERVATORY_UPGRADE = 2;

    private int level;
    private ArrayList<Resource> resourcesNeeded;
    private int type;

    public UpgradeRecipe(int level, int type){
        this.level = level;
        this.type = type;
        resourcesNeeded = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }
    public int getType() {
        return type;
    }
    public ArrayList<Resource> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public void addResourceNeeded(Resource r, int amount){
        Resource resource = Resource.clone(r);
        resource.setAmount(amount);
        resourcesNeeded.add(resource);
    }
}

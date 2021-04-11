package sk.grest.game.entities.upgrade;

import java.util.ArrayList;

import sk.grest.game.entities.resource.Resource;

public class UpgradeRecipe implements Comparable<UpgradeRecipe> {

    public static final int SHIP_UPGRADE = 1;

    public static final int OBSERVATORY_SPEED = 2;
    public static final int OBSERVATORY_ACCURACY = 3;
    public static final int GOAL = 4;

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

    @Override
    public int compareTo(UpgradeRecipe recipe) {
        return Integer.compare(this.getLevel(), recipe.getLevel());
    }
}

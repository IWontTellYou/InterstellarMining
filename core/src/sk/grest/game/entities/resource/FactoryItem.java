package sk.grest.game.entities.resource;

import java.util.ArrayList;

import sk.grest.game.constants.ScreenConstants;

public class FactoryItem {

    public static final int PLATE = 1;
    public static final int FUEL = 2;


    private final int defaultDuration;
    private int duration;

    private long startTime;
    private Resource resource;
    private int count;
    private ArrayList<Resource> itemsNecessary;
    private int type;

    public FactoryItem(Resource resource, int count, long startTime, int defaultDuration, int type) {
        this.startTime = startTime;
        this.resource = resource;
        this.defaultDuration = defaultDuration;
        this.duration = defaultDuration*count;
        this.count = count;
        this.type = type;
        this.itemsNecessary = new ArrayList<>();
    }

    public void setStartTime(long time){
        this.startTime = time;
    }

    public void setCount(int count){
        this.count = count;
    }

    public String getTimeLeft(){
        if(System.currentTimeMillis() - startTime > duration){
            return null;
        }else
            return ScreenConstants.getTimeFormat(duration - (System.currentTimeMillis() - startTime));
    }

    public Resource getResource() {
        return resource;
    }

    public int getCount() {
        return count;
    }

    public int getType(){
        return type;
    }

    public ArrayList<Resource> getItemsNecessary(){
        return itemsNecessary;
    }

    public void addItemNecessary(Resource r, int amount) {
        Resource resource = Resource.clone(r);
        resource.setAmount(amount);
        itemsNecessary.add(resource);
    }

}

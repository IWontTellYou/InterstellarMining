package sk.grest.game.entities.resource;

import java.util.ArrayList;

import sk.grest.game.constants.ScreenConstants;

public class FactoryItem implements Cloneable {

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

    public void setCount(int count) {
        this.count = count;
        this.duration = defaultDuration * count;
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public String getDuration(){
        return ScreenConstants.getTimeFormat(duration);
    }

    public int getDurationMillis(){
        return duration;
    }

    public String getTimeLeft(){
        if(System.currentTimeMillis() - startTime > duration){
            return null;
        }else
            return ScreenConstants.getTimeFormat(duration - (System.currentTimeMillis() - startTime));
    }

    public int getTimeLeftMillis(){
        if(System.currentTimeMillis() - startTime > duration){
            return 0;
        }else
            return (int) (duration - (System.currentTimeMillis() - startTime));
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

    public long getStartTime() {
        return startTime;
    }

    public ArrayList<Resource> getItemsNecessary(){
        return itemsNecessary;
    }

    public void addItemNecessary(Resource r, int amount) {
        Resource resource = Resource.clone(r);
        resource.setAmount(amount);
        itemsNecessary.add(resource);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

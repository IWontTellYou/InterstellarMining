package sk.grest.game.entities;

import java.util.ArrayList;

public class Research {

    private int id;
    private String name;
    private String info;
    private String effect;
    private int cost;

    private ArrayList<Research> reseachesRequired;

    private boolean unlocked;

    public Research(int id, String name, String info, String effect, int cost, int level, int upgradeTime) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.effect = effect;
        this.cost = cost;

        unlocked = false;
        reseachesRequired = new ArrayList<>();
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getId() {
        return id;
    }
    public boolean isUnlocked() {
        return unlocked;
    }
    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }
    public String getEffect() {
        return effect;
    }
    public int getCost() {
        return cost;
    }

    public void addResearchRequired(Research research){
        reseachesRequired.add(research);
    }

}

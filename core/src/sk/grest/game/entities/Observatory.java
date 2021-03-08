package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Plane;

import java.util.Random;

import sk.grest.game.constants.GameConstants;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.upgrade.Upgradable;

public class Observatory implements Upgradable {

    public static final int MAX_LEVEL = 5;

    public static final int DEFAULT_SEARCH_TIME = (int) (4*GameConstants.HOUR);
    public static final int OBSERVATORY_SPEED = 0;
    public static final int OBSERVATORY_ACCURACY = 1;

    private Planet planet;
    private int speed_level;
    private int accuracy_level;
    private long endTime;

    private Random rn;
    private boolean searching;

    public Observatory(int speed_level, int accuracy_level){
        this.rn = new Random();
        this.speed_level = speed_level;
        this.accuracy_level = accuracy_level;
        this.searching = false;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
        this.endTime = (long) (System.currentTimeMillis() + GameConstants.DAY);
    }
    public void setSearch(long endTime){
        this.endTime = endTime;
        searching = endTime > System.currentTimeMillis();
    }
    public void setSearch(){
        this.endTime = System.currentTimeMillis() +
                //(int) ((DEFAULT_SEARCH_TIME * getSpeed()) + (rn.nextInt(60)-30) * GameConstants.MINUTE);
                (int) (10 * GameConstants.SECOND);
        searching = true;
        Gdx.app.log("NEW SEARCH", ScreenConstants.getTimeFormat(endTime - System.currentTimeMillis()));
    }
    public void reset(){
        searching = false;
    }

    public Planet getPlanet(){
        return planet;
    }
    public int getTimeLeftMillis(){
        return (int) (endTime - System.currentTimeMillis());
    }
    public String getTimeLeft(){
        long timeLeft = endTime - System.currentTimeMillis();
        return ((timeLeft > 0) ? ScreenConstants.getTimeFormat(timeLeft) : null);
    }

    public boolean isSearching() {
        return searching;
    }

    private void upgradeSpeed(){
        speed_level++;
    }
    private void upgradeAccuracy(){
        accuracy_level++;
    }

    public float getSpeed(){
        return 1 - (speed_level * 0.1f);
    }
    public float getAccuracy(){
        return 1 - (accuracy_level * 0.1f);
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public void upgrade(int type) {
        if(type == OBSERVATORY_ACCURACY){
            accuracy_level++;
        }else if(type == OBSERVATORY_SPEED){
            speed_level++;
        }
    }

    @Override
    public int getLevel(int type) {
        if(type == OBSERVATORY_ACCURACY){
            return accuracy_level;
        }else if(type == OBSERVATORY_SPEED){
            return speed_level;
        }
        return -1;
    }

    @Override
    public int getMaxLevel(int type) {
        return MAX_LEVEL;
    }
}

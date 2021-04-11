package sk.grest.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Plane;

import java.util.Random;

import sk.grest.game.constants.GameConstants;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.upgrade.Upgradable;

import static sk.grest.game.constants.GameConstants.*;

public class Observatory implements Upgradable {

    // TODO MAKE FURTHER TEST WHEN MORE PLANETS READY

    public static final int DEFAULT_WAITING_TIME = (int) GameConstants.DAY;

    public static final int NONE = 0;
    public static final int SEARCHING = 1;
    public static final int WAITING = 2;

    public static final int MAX_LEVEL = 5;

    public static final int DEFAULT_SEARCH_TIME = (int) (4* HOUR);
    public static final int OBSERVATORY_SPEED = 0;
    public static final int OBSERVATORY_ACCURACY = 1;

    private Planet planet;
    private int speed_level;
    private int accuracy_level;
    private long endTime;

    private Random rn;

    public Observatory(int speed_level, int accuracy_level){
        this.rn = new Random();
        this.speed_level = speed_level;
        this.accuracy_level = accuracy_level;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
        this.endTime = (long) (endTime + MINUTE);
    }
    public void setAction(long endTime){
        this.endTime = endTime;
    }
    public void setSearch(){
        this.endTime = System.currentTimeMillis() + DEFAULT_WAITING_TIME + (int) ((DEFAULT_SEARCH_TIME * getSpeed()) + (rn.nextInt(60)-30) * GameConstants.MINUTE);
        Gdx.app.log("NEW SEARCH", ScreenConstants.getTimeFormat(endTime - DEFAULT_WAITING_TIME - System.currentTimeMillis()));
    }

    public void setSearch(long endTime){
        this.endTime = endTime;
    }

    public Planet getPlanet(){
        return planet;
    }
    public int getTimeLeftMillis(){
        if(endTime - (int) DEFAULT_WAITING_TIME > System.currentTimeMillis())
            return (int) (endTime - DEFAULT_WAITING_TIME - System.currentTimeMillis());
        else {
            return  (int) (endTime - System.currentTimeMillis());
        }
    }
    public String getTimeLeft(){
        long timeLeft;
        if(endTime - DEFAULT_WAITING_TIME > System.currentTimeMillis())
            timeLeft =  endTime - DEFAULT_WAITING_TIME - System.currentTimeMillis();
        else {
            timeLeft = endTime - System.currentTimeMillis();
        }
        return ((timeLeft > 0) ? ScreenConstants.getTimeFormat(timeLeft) : null);
    }

    public boolean isSearching() {
        return (endTime - DEFAULT_WAITING_TIME - System.currentTimeMillis() > 0);
    }
    public boolean isWaiting() {
        return endTime - System.currentTimeMillis() > 0 && endTime - System.currentTimeMillis() < DEFAULT_WAITING_TIME;
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

package sk.grest.game.defaults;

import com.badlogic.gdx.Gdx;

import sk.grest.game.entities.Ship;
import sk.grest.game.entities.enums.ShipState;

public class GameConstants {

    // TIME CONSTANTS

    public static final float SECOND = 1000f;
    public static final float MINUTE = 60f * SECOND;
    public static final float HOUR = 60f * MINUTE;
    public static final float DAY = 24f * HOUR;

    // TRAVEL & MINING CONSTANTS

    private static final float BASE_TASK_TIME = 5f * MINUTE;
    public static final float BASE_SPEED = 10f;
    public static final float BASE_DISTANCE = BASE_TASK_TIME * BASE_SPEED;
    public static final float BASE_MINING_TIME = BASE_TASK_TIME;

    // NUMBER CONSTANTS

    public static final float THOUSAND = 1000;
    public static final float MILLION = 1000 * THOUSAND;
    public static final float BILLION = 1000 * MILLION;
    public static final float TRILLION = 1000 * BILLION;
    public static final float QUADRILION = 1000 * TRILLION;



}

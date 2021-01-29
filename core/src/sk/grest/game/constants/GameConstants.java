package sk.grest.game.constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import sk.grest.game.entities.Achievement;

public class GameConstants {

    // REGISTRATION DEFAULTS

    public static final int DEFAULT_SHIP_COUNT = 1;

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

    public static final String THOUSAND_SIGN = "K";
    public static final String MILLION_SIGN = "M";
    public static final String BILLION_SIGN = "B";
    public static final String TRILLION_SIGN = "T";
    public static final String QUADRILION_SIGN = "Q";

    // ACTOR COORDS TO STAGE COORDS
    public static Vector2 getStageLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }

}

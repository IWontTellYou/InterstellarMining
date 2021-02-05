package sk.grest.game.entities;

import java.util.ArrayList;

public class Achievement {

    private int id;
    private String name;
    private String info;
    private int type;

    private boolean achievementObtained;
    private int achievementLevel;

    public Achievement(int id, String name, String info, int type) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }
    private int getPoints(int level){
        switch (level){
            case 1:
                return 10;
            case 2:
                return 50;
            case 3:
                return 100;
            case 4:
                return 250;
            case 5:
                return 500;
        }
        return 0;
    }
    private int getAmount(int level){
        switch (level){
            case 1:
                return 1;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 20;
            case 5:
                return 50;
        }
        return 0;
    }
    public int getType() {
        return type;
    }
    public boolean isAchievementObtained() {
        return achievementObtained;
    }

    // CALCULATE ACHIEVEMENT POINTS
    public static ArrayList<Achievement> getAchievement(Achievement a, int currentAmount){
        ArrayList<Achievement> achievements = new ArrayList<>();

        int levelAchieved = AchievementType.getAchievementLevel(a.getType(), currentAmount);
        if(levelAchieved < 0)
            return achievements;

        for (int i = 0; i < levelAchieved; i++) {
            Achievement achievement = getAchievements(a, i);
            achievements.add(achievement);
        }
        return achievements;
    }

    private static Achievement getAchievements(Achievement a, int level){

        String name = a.getName();
        String info = a.getInfo();

        name = name.replace("%", AchievementType.getLevelSign(level));
        info = info.replace("%", String.valueOf(AchievementType.getAmountNeeded(level, a.getType())));

        return new Achievement(a.getId(), name, info, a.getType());
    }

    public static class AchievementType{

        public static int[] AMOUNT_TYPE_1 = {1, 5, 10, 20};
        public static int[] POINTS_TYPE_1 = {50, 100, 500, 1000};

        public static int[] AMOUNT_TYPE_2 = {1, 5, 10, 25, 50};
        public static int[] POINTS_TYPE_2 = {25, 75, 225, 550, 1100};

        public static int[] AMOUNT_TYPE_3 = {5, 10, 25, 50, 100};
        public static int[] POINTS_TYPE_3 = {10, 50, 100, 325, 750};

        public static String getLevelSign(int level){
            switch (level){
                case 0:
                    return "I";
                case 1:
                    return "II";
                case 2:
                    return "III";
                case 3:
                    return "IV";
                case 4:
                    return "V";
                case 5:
                    return "VI";
                default:
                    return "0";
            }
        }
        public static int getAmountNeeded(int level, int type){
            switch (type){
                case 1:
                    return AMOUNT_TYPE_1[level];
                case 2:
                    return AMOUNT_TYPE_2[level];
                case 3:
                    return AMOUNT_TYPE_3[level];
                default:
                    return -1;
            }
        }
        public static int getPoints(int level, int type){
            switch (type){
                case 1:
                    return POINTS_TYPE_1[level];
                case 2:
                    return POINTS_TYPE_2[level];
                case 3:
                    return POINTS_TYPE_3[level];
                default:
                    return -1;
            }
        }
        public static int getAchievementLevel(int type, int currentAmount){
            switch (type){
                case 1:
                    for (int i = 0; i < AMOUNT_TYPE_1.length; i++) {
                        if(currentAmount < AMOUNT_TYPE_1[i]){
                            return i-1;
                        }
                    }
                    return AMOUNT_TYPE_1.length-1;
                case 2:
                    for (int i = 0; i < AMOUNT_TYPE_2.length; i++) {
                        if(currentAmount < AMOUNT_TYPE_2[i]){
                            return i-1;
                        }
                    }
                    return AMOUNT_TYPE_2.length-1;
                case 3:
                    for (int i = 0; i < AMOUNT_TYPE_3.length; i++) {
                        if(currentAmount < AMOUNT_TYPE_3[i]){
                            return i-1;
                        }
                    }
                    return AMOUNT_TYPE_3.length-1;
                default:
                    return -1;
            }
        }

    }
}

package sk.grest.game.entities;

import java.util.ArrayList;

public class Achievement {

    private int id;
    private String name;
    private String info;
    private int currentAmount;

    private boolean achievementObtained;
    private int achievementLevel;

    public Achievement(int id, String name, String info, int currentAmount) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.currentAmount = currentAmount;
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
    public int getCurrentAmount() {
        return currentAmount;
    }
    public boolean isAchievementObtained() {
        return achievementObtained;
    }

    // CALCULATE ACHIEVEMENT POINTS
    public static ArrayList<Achievement> getAchievement(Achievement a, int levelAchieved){
        ArrayList<Achievement> achievements = new ArrayList<>();
        for (int i = 1; i < levelAchieved+1; i++) {
            Achievement achievement = getAchievemnt(a, i);

        }
        return achievements;
    }

    private static Achievement getAchievemnt(Achievement a, int level){

        String name = a.getName();
        String info = a.getInfo();

        switch (level){
            case 1:
                name = name.replace("%", "I");
                info = info.replace("%", "1");
            case 2:
                name = name.replace("%", "II");
                info = info.replace("%", "5");
            case 3:
                name = name.replace("%", "III");
                info = info.replace("%", "10");
            case 4:
                name = name.replace("%", "IV");
                info = info.replace("%", "15");
            case 5:
                name = name.replace("%", "V");
                info = info.replace("%", "25");
        }

        return new Achievement(a.getId(), name, info, a.getCurrentAmount());
    }
    private static String getName(String info, int level){
        switch (level){
            case 1:
                return info.replace("%", "I");
            case 2:
                return info.replace("%", "II");
            case 3:
                return info.replace("%", "III");
            case 4:
                return info.replace("%", "IV");
            case 5:
                return info.replace("%", "V");
        }

        return info;
    }


}

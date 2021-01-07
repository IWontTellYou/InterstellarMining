package sk.grest.game.entities.enums;

public enum ResourceRarity {
    COMMON,
    UNCOMMON,
    RARE,
    VERY_RARE,
    EXTRAORDINARY;

    public static ResourceRarity getRarity(int id){
        switch (id){
            case 1:
                return COMMON;
            case 2:
                return UNCOMMON;
            case 3:
                return RARE;
            case 4:
                return VERY_RARE;
            case 5:
                return EXTRAORDINARY;
        }

        return null;
    }

    public static int getRarity(ResourceRarity rarity){
        switch (rarity){
            case COMMON:
                return 1;
            case UNCOMMON:
                return 2;
            case RARE:
                return 3;
            case VERY_RARE:
                return 4;
            case EXTRAORDINARY:
                return 5;
        }

        return -1;
    }

}

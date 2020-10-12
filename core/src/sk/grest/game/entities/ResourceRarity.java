package sk.grest.game.entities;

public enum ResourceRarity {
    COMMON,
    UNCOMMON,
    RARE,
    VERY_RARE,
    EXTRAORDINARY;

    public static ResourceRarity getRarity(String rarity){
        return ResourceRarity.valueOf(rarity);
    }

}

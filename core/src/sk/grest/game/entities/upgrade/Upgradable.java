package sk.grest.game.entities.upgrade;

public interface Upgradable {

    void upgrade(int type);
    int getLevel(int type);
    int getMaxLevel(int type);

}

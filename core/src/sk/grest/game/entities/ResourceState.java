package sk.grest.game.entities;

public enum ResourceState {
    LIQUID,
    SOLID,
    GAS;

    public static ResourceState getState(String state){
        return ResourceState.valueOf(state);
    }

}

package sk.grest.game.entities.resource;

public enum ResourceState {
    LIQUID,
    SOLID,
    GAS;

    public static ResourceState getState(int id){
        switch (id){
            case 1:
                return LIQUID;
            case 2:
                return SOLID;
            case 3:
                return GAS;
        }
        return null;
    }

    public static int getState(ResourceState state){
        switch (state){
            case LIQUID:
                return 1;
            case SOLID:
                return 2;
            case GAS:
                return 3;
        }

        return -1;
    }

}

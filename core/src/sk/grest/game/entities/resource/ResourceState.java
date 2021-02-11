package sk.grest.game.entities.resource;

public enum ResourceState {
    LIQUID,
    SOLID,
    GAS,
    PRODUCT,
    FUEL;

    public static ResourceState getState(int id){
        switch (id){
            case 1:
                return LIQUID;
            case 2:
                return SOLID;
            case 3:
                return GAS;
            case 4:
                return PRODUCT;
            case 5:
                return FUEL;
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
            case PRODUCT:
                return 4;
            case FUEL:
                return 5;
        }

        return -1;
    }

}

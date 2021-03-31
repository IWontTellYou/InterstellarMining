package sk.grest.game.entities.resource;

public enum ResourceState {
    RESOURCE,
    PRODUCT,
    FUEL;

    public static ResourceState getState(int id){
        switch (id){
            case 1:
                return RESOURCE;
            case 2:
                return PRODUCT;
            case 3:
                return FUEL;
        }
        return null;
    }

    public static int getState(ResourceState state){
        switch (state){
            case RESOURCE:
                return 1;
            case PRODUCT:
                return 2;
            case FUEL:
                return 3;
        }
        return -1;
    }

}

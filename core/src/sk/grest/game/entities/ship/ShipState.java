package sk.grest.game.entities.ship;

public enum ShipState {
    AT_THE_BASE,
    TRAVELLING_OUT,
    TRAVELLING_BACK,
    MINING,
    UNBOUGHT;

    public static ShipState getState(int id){
        switch (id){
            case 1:
                return AT_THE_BASE;
            case 2:
                return TRAVELLING_OUT;
            case 3:
                return TRAVELLING_BACK;
            case 4:
                return MINING;
            case 5:
                return UNBOUGHT;
        }
        return null;
    }

    public static int getID(ShipState state){
        switch (state){
            case AT_THE_BASE:
                return 1;
            case TRAVELLING_OUT:
                return 2;
            case TRAVELLING_BACK:
                return 3;
            case MINING:
                return 4;
            case UNBOUGHT:
                return 5;
        }
        return -1;
    }

}

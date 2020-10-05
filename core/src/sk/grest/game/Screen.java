package sk.grest.game;

public enum Screen {
    MAIN_MENU_SCREEN,
    GAME_SCREEN;

    public Screen setScreen(Screen screen){
        if(screen.equals(MAIN_MENU_SCREEN))
            return MAIN_MENU_SCREEN;
        else if(screen.equals(GAME_SCREEN))
            return GAME_SCREEN;
        return null;
    }

}

package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import sk.grest.game.defaults.ScreenDeafults;

public class CustomDialog extends Dialog {

    public CustomDialog(String title, Skin skin) {
        super(title, skin);
    }

    public CustomDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public CustomDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        setHeight(ScreenDeafults.DEFAULT_DIALOG_HEIGHT);
        setWidth(ScreenDeafults.DEFAULT_DIALOG_WIDTH);

        getContentTable().clearChildren();
        getButtonTable().clearChildren();

    }

}

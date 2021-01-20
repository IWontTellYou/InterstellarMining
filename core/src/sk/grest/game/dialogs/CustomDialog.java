package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.defaults.ScreenDeafults;

public class CustomDialog extends Dialog {

    private Skin skin;

    public CustomDialog(String title, Skin skin) {
        super(title, skin);
        this.skin = skin;
    }

    public CustomDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public CustomDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        getContentTable().clearChildren();
        getButtonTable().clearChildren();

        setHeight(ScreenDeafults.DEFAULT_DIALOG_HEIGHT);
        setWidth(ScreenDeafults.DEFAULT_DIALOG_WIDTH);

    }

    public void update(float delta){

    }

    public void addCloseButton(final Dialog dialog){
        TextButton closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        getButtonTable().row();
        getButtonTable().add(closeBtn)
                .width(ScreenDeafults.DEFAULT_DIALOG_WIDTH/3f)
                .height(ScreenDeafults.DEFAULT_DIALOG_HEIGHT/15f)
                .align(Align.center);
    }

    @Override
    public float getPrefWidth() {
        return ScreenDeafults.DEFAULT_DIALOG_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return ScreenDeafults.DEFAULT_DIALOG_HEIGHT;
    }

}

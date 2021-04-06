package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.constants.ScreenConstants;

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

        setHeight(ScreenConstants.DEFAULT_DIALOG_HEIGHT);
        setWidth(ScreenConstants.DEFAULT_DIALOG_WIDTH);
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
        getContentTable().row();
        getContentTable().add(closeBtn)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH/3f)
                .height(ScreenConstants.DEFAULT_DIALOG_HEIGHT/15f)
                .align(Align.center);
    }

    public void addCloseButton(final Dialog dialog, int colspan){
        TextButton closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        getContentTable().row();
        getContentTable().add(closeBtn)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH/3f)
                .height(ScreenConstants.DEFAULT_DIALOG_HEIGHT/15f)
                .colspan(colspan)
                .align(Align.center);
    }

    @Override
    public float getPrefWidth() {
        return ScreenConstants.DEFAULT_DIALOG_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return ScreenConstants.DEFAULT_DIALOG_HEIGHT;
    }

}

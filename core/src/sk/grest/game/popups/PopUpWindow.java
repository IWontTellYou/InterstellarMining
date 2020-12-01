package sk.grest.game.popups;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import sk.grest.game.InterstellarMining;

public class PopUpWindow extends Dialog {

    public PopUpWindow(InterstellarMining game, String title){
        super(title, game.getUISkin());
        this.setBackground(game.getUISkin().getDrawable(""));
    }

}

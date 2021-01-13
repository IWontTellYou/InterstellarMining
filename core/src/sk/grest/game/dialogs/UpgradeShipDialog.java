package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import sk.grest.game.entities.ship.Ship;

public class UpgradeShipDialog extends CustomDialog {

    public UpgradeShipDialog(String title, Skin skin, Ship ship) {
        super(title, skin);

        float defaultActorWidth = getWidth()/2f;
        float defaultActorHeight = getHeight()/2f;

        float defaultSelectBoxWidth = getWidth()/3.5f;
        float defaultSelectBoxHeight = getHeight()/17.5f;

        Image shipImage = new Image();

    }

}

package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.dialogs.upgrade.UpgradeResourceView;
import sk.grest.game.entities.Observatory;
import sk.grest.game.entities.upgrade.UpgradeRecipe;

import static sk.grest.game.entities.Observatory.*;

public class ObservatoryDialog extends CustomDialog {

    private UpgradeRecipe[][] recipes;
    private Observatory observatory;

    public ObservatoryDialog(String title, Skin skin, InterstellarMining game) {
        super(title, skin);

        recipes = new UpgradeRecipe[2][];
        recipes[OBSERVATORY_SPEED] = game.getUpgradeRecipesByType(UpgradeRecipe.OBSERVATORY_SPEED);
        recipes[OBSERVATORY_ACCURACY] = game.getUpgradeRecipesByType(UpgradeRecipe.OBSERVATORY_ACCURACY);
        observatory = game.getPlayer().getObservatory();

        if(observatory.getTimeLeft() != null){
            Label timeLeft = new Label(observatory.getTimeLeft(), skin);
        }

        UpgradeResourceView speedView = new UpgradeResourceView(game, recipes[OBSERVATORY_SPEED], observatory, game.getPlayer(), OBSERVATORY_SPEED);
        getContentTable().add(speedView).expandX().row();

        UpgradeResourceView accuracyView = new UpgradeResourceView(game, recipes[OBSERVATORY_ACCURACY], observatory, game.getPlayer(), OBSERVATORY_ACCURACY);
        getContentTable().add(accuracyView).expandX().row();

        addCloseButton(this);

    }

    public void update(float delta) {

    }
}

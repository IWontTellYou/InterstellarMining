package sk.grest.game.dialogs.upgrade;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.CustomDialog;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.upgrade.UpgradeRecipe;

public class UpgradeShipDialog extends CustomDialog {

    // TODO ADD CONDITION SHIP.STATE == AT_THE_BASE

    private static final int MINING_SPEED_LABEL = 0;
    private static final int TRAVEL_SPEED_LABEL = 1;
    private static final int FUEL_EFFICIENCY_LABEL = 2;
    private static final int FUEL_CAPACITY_LABEL = 3;
    private static final int RESOURCE_CAPACITY_LABEL = 4;

    // InterstellarMining class will be here as temporary solution until I have regular graphics for Buttons

    // TODO FIX (IT'S RED)

    private Ship ship;

    private UpgradeTable upgradeLayout;
    private UpgradeResourceView upgradeRecipe;
    private ArrayList<UpgradeRecipe> recipes;

    public UpgradeShipDialog(String title, InterstellarMining game, final Player player, final Ship ship, ArrayList<UpgradeRecipe> recipes) {
        super(title, game.getUISkin());

        this.ship = ship;

        this.recipes = recipes;

        Image shipImage = new Image();

        // UPGRADE LAYOUT

        upgradeLayout = new UpgradeTable(getSkin(), player, ship);
        getContentTable().add(upgradeLayout)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH)
                .height(ScreenConstants.DEFAULT_DIALOG_HEIGHT/2f)
                .row();

        upgradeRecipe = new UpgradeResourceView(game, recipes, ship, player);
        getContentTable().add(upgradeRecipe)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH)
                .height(ScreenConstants.DEFAULT_ACTOR_HEIGHT)
                .row();

        addCloseButton(this);

    }

    @Override
    public float getPrefHeight() {
        return ScreenConstants.DEFAULT_DIALOG_HEIGHT;
    }

    @Override
    public float getPrefWidth() {
        return ScreenConstants.DEFAULT_DIALOG_WIDTH;
    }
}

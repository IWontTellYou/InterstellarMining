package sk.grest.game.dialogs.upgrade;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.CustomDialog;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.upgrade.UpgradeRecipe;

public class UpgradeShipDialog extends CustomDialog {

    private static final int MINING_SPEED_LABEL = 0;
    private static final int TRAVEL_SPEED_LABEL = 1;
    private static final int RESOURCE_CAPACITY_LABEL = 3;

    private Ship ship;

    private UpgradeTable upgradeLayout;
    private UpgradeResourceView upgradeRecipe;
    private UpgradeRecipe[] recipes;

    public UpgradeShipDialog(String title, InterstellarMining game, final Player player, final Ship ship, UpgradeRecipe[] recipes) {
        super(title, game.getUISkin());

        setBackground(InterstellarMining.back);

        this.ship = ship;
        this.recipes = recipes;

        Image shipImage = new Image();

        // UPGRADE LAYOUT

        Label attributesLabel = new Label("ATTRIBUTES", getSkin());
        getContentTable().add(attributesLabel)
                .expandX()
                .align(Align.center)
                .padBottom(30)
                .row();

        upgradeLayout = new UpgradeTable(getSkin(), player, ship);
        getContentTable().add(upgradeLayout)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH)
                .padBottom(30)
                .row();

        Label structureLabel = new Label("UPGRADE STRUCTURE", getSkin());
        getContentTable().add(structureLabel)
                .expandX()
                .align(Align.center)
                .padBottom(30)
                .row();

        upgradeRecipe = new UpgradeResourceView(game, recipes, ship, player, 0);
        getContentTable().add(upgradeRecipe)
                .width(ScreenConstants.DEFAULT_DIALOG_WIDTH)
                .height(ScreenConstants.DEFAULT_ACTOR_HEIGHT)
                .padBottom(30)
                .row();

        addCloseButton(this);

        //getContentTable().debug(Debug.cell);

    }

    @Override
    public float getPrefHeight() {
        return ScreenConstants.DEFAULT_DIALOG_HEIGHT/1.25f;
    }

    @Override
    public float getPrefWidth() {
        return ScreenConstants.DEFAULT_DIALOG_WIDTH/1.45f;
    }
}

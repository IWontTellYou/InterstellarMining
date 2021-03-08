package sk.grest.game.dialogs.upgrade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.controls.Button;
import sk.grest.game.dialogs.factory.FactoryQueue;
import sk.grest.game.entities.Observatory;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.upgrade.Upgradable;
import sk.grest.game.entities.upgrade.UpgradeRecipe;

import static sk.grest.game.constants.ScreenConstants.DEFAULT_PADDING;
import static sk.grest.game.entities.ship.Attributes.*;

public class UpgradeResourceView extends Table {

    public static final int DEFAULT_ITEM_SIZE = 125;

    private InterstellarMining game;
    private Upgradable upgradable;
    private Player player;
    private UpgradeRecipe[] recipes;
    private int type;

    private Button upgrade;

    public UpgradeResourceView(final InterstellarMining game, final UpgradeRecipe[] recipes,
                               final Upgradable upgradable, final Player player, int type){
        super(game.getUISkin());
        this.game = game;
        this.upgradable = upgradable;
        this.player = player;
        this.recipes = recipes;
        this.type = type;
        resetView(getRecipe());
    }

    private void resetView(final UpgradeRecipe recipe){
        clearChildren();

        if(recipe == null && upgradable.getLevel(type) == upgradable.getMaxLevel(type)){

            Image image = new Image(game.getSpriteSkin(), "max_level_sign");
            add(image)
                    .height(DEFAULT_ITEM_SIZE)
                    .width(ScreenConstants.DEFAULT_DIALOG_WIDTH/1.8f);

        }else {

            Table[] resources = new Table[recipe.getResourcesNeeded().size()];

            int i = 0;
            for (Resource r : recipe.getResourcesNeeded()) {
                Resource rOwned = player.getResource(r.getID());

                resources[i] = new Table();

                Image image;
                if (r.getAssetName().equals(""))
                    image = new Image(game.getSpriteSkin(), "iron_ingot");
                else
                    image = new Image(game.getSpriteSkin(), r.getAssetName());

                Label amount = new Label(rOwned.getAmount() + " / " + r.getAmount(), game.getUISkin());
                amount.setAlignment(Align.center);

                resources[i].add(image).center().size(FactoryQueue.DEFAULT_ITEM_SIZE / 2f).pad(DEFAULT_PADDING).row();
                resources[i].add(amount).bottom().right().row();

                add(resources[i])
                        .size(DEFAULT_ITEM_SIZE);
                i++;
            }

            upgrade = new Button(game.getSpriteSkin(), "upgrade", null);
            upgrade.getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (Resource r : recipe.getResourcesNeeded()) {
                        if (r.getAmount() > player.getResource(r.getID()).getAmount())
                            return;
                    }

                    for (Resource r : recipe.getResourcesNeeded()) {
                        player.getResource(r.getID()).subtractAmount(r.getAmount());
                        game.getHandler().updatePlayerResourceTable(r.getID());
                    }

                    upgradable.upgrade(type);

                    try{
                        Ship ship = (Ship) upgradable;
                        game.getHandler().updatePlayerShip(game.getPlayer().getID(), ship, game);
                    }catch (Exception e){
                        Observatory observatory = (Observatory) upgradable;
                        game.getHandler().updatePlayerObservatory(game.getPlayer().getID(), observatory, game);
                    }

                    game.getHandler().updatePlayer();
                    resetView(getRecipe());

                }
            });

            add(upgrade.getButton())
                    .size(DEFAULT_ITEM_SIZE);

        }

    }

    public Button getUpgradeButton() {
        return upgrade;
    }

    public UpgradeRecipe getRecipe(){
        if(upgradable.getLevel(type) < upgradable.getMaxLevel(type))
            return recipes[upgradable.getLevel(type)];
        else
            return null;
    }

}

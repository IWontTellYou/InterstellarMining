package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class TooltipBuilder {

    private static final int PADDING = 20;

    private static final int RESOURCE_TOOLTIP_WIDTH = 80;
    private static final int RESOURCE_TOOLTIP_HEIGHT = 150;

    private static final int SHIP_TOOLTIP_WIDTH = 200;
    private static final int SHIP_TOOLTIP_HEIGHT = 300;

    private static TooltipBuilder tooltipBuilder;

    private Skin uiSkin;
    private Skin spriteSkin;

    private TooltipBuilder(){
    }

    public static TooltipBuilder getInstance(){
        if(tooltipBuilder != null)
            return tooltipBuilder;
        else
            return tooltipBuilder = new TooltipBuilder();
    }

    public void setSkins(Skin uiSkin, Skin spriteSkin) {
        this.uiSkin = uiSkin;
        this.spriteSkin = spriteSkin;
    }

    public Table buildTooltipContent(Resource r){
        Table item = new Table();
        item.setBackground(ScreenConstants.getBackground(ScreenConstants.DARK_GRAY));
        Label name = new Label(r.getName(), uiSkin);
        Image img;
        if(r.getAssetName() == null || r.getAssetName().equals(""))
            img = new Image(spriteSkin, "iron_ingot");
        else
            img = new Image(spriteSkin, r.getAssetName());
        Label amount = new Label(r.getAmount()+"", uiSkin);

        item.add(name).align(Align.center).uniformX().row();
        item.add(img).align(Align.center).size(100).row();
        item.add(amount).align(Align.right).uniformX();

        item.pad(20);

        return item;
    }

    public Table buildTooltipContent(Ship ship){
        Table item = new Table();
        item.setBackground(ScreenConstants.getBackground(ScreenConstants.DARK_GRAY));
        Label name = new Label(ship.getName(), uiSkin);
        Image img = new Image(spriteSkin, ship.getAssetID());
        Label level = new Label("Upgrade level - "+ship.getLevel(0), uiSkin);

        Label travelSpeedLabel = new Label("Travel Speed", uiSkin);
        Label miningSpeedLabel = new Label("Mining Speed", uiSkin);
        Label resourceCapacityLabel = new Label("Resource Capacity", uiSkin);
        Label fuelCapacityLabel = new Label("Fuel Capacity", uiSkin);
        Label fuelEfficiencyLabel = new Label("Fuel Efficiency", uiSkin);

        Label travelSpeed = new Label(""+ship.getAttribute(TRAVEL_SPEED), uiSkin);
        Label miningSpeed = new Label(ship.getAttribute(MINING_SPEED)+"", uiSkin);
        Label resourceCapacity = new Label(ship.getAttribute(RESOURCE_CAPACITY)+"", uiSkin);

        item.add(name).align(Align.center).pad(ScreenConstants.DEFAULT_PADDING).colspan(2).row();
        item.add(img).align(Align.center).pad(ScreenConstants.DEFAULT_PADDING).width(100).height(200).colspan(2).row();

        item.add(travelSpeedLabel).align(Align.left).pad(ScreenConstants.DEFAULT_PADDING).padRight(PADDING);
        item.add(travelSpeed).align(Align.right).pad(ScreenConstants.DEFAULT_PADDING).row();

        item.add(miningSpeedLabel).align(Align.left).pad(ScreenConstants.DEFAULT_PADDING).padRight(PADDING);
        item.add(miningSpeed).align(Align.right).pad(ScreenConstants.DEFAULT_PADDING).row();

        item.add(resourceCapacityLabel).align(Align.left).pad(ScreenConstants.DEFAULT_PADDING).padRight(PADDING);
        item.add(resourceCapacity).align(Align.right).pad(ScreenConstants.DEFAULT_PADDING).row();

        item.add(level).align(Align.center).pad(ScreenConstants.DEFAULT_PADDING);

        //item.debug(Table.Debug.cell);

        item.pad(20);

        return item;
    }

}

package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;

public class TooltipBuilder {

    private static final int TOOLTIP_WIDTH = 80;
    private static final int TOOLTIP_HEIGHT = 150;

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
        Image img = new Image(spriteSkin, "ships");
        Label amount = new Label(ship.getLevel(0)+"", uiSkin);

        item.add(name).align(Align.center).uniformX().row();
        item.add(img).align(Align.center).uniformX().row();
        item.add(amount).align(Align.right).uniformX();

        item.pad(20);

        return item;
    }

}

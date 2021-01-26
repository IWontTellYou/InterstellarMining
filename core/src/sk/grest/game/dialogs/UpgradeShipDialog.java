package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.controls.Button;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

import sk.grest.game.entities.ship.Attributes.AttributeType;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class UpgradeShipDialog extends CustomDialog {

    // TODO ADD CONDINTION SHIP.STATE == AT_THE_BASE

    private static final int MINING_SPEED_LABEL = 0;
    private static final int TRAVEL_SPEED_LABEL = 1;
    private static final int FUEL_EFFICIENCY_LABEL = 2;
    private static final int FUEL_CAPACITY_LABEL = 3;
    private static final int RESOURCE_CAPACITY_LABEL = 4;

    // InterstellarMining class will be here as temporary solution until I have regular graphichs for Buttons

    // TODO FIX (IT'S RED)
    public static final String BTN_DEFAULT_DOWN = "default-round-down";
    public static final String BTN_PLUS_DEFAULT_UP = "tree-plus";
    public static final String BTN_MINUS_DEFAULT_UP = "tree-minus";

    private Skin skin;
    private Ship ship;
    final private Label[] attributeLabels;
    final private Label[] priceLabels;

    Table upgradeLayout;

    public UpgradeShipDialog(String title, Skin skin, final Player player, final Ship ship) {
        super(title, skin);

        this.skin = skin;
        this.ship = ship;

        attributeLabels = new Label[5];
        priceLabels = new Label[5];

        float defaultButtonWidth = getWidth()/8f;
        float defaultButtonHeight = getHeight()/20f;

        float defaultLabelWidth = getWidth()/8f;
        float defaultLabelHeight = getHeight()/17.5f;

        float defaultBtnSize = getHeight()/40f;

        Image shipImage = new Image();

        upgradeLayout = new Table(skin);
        //upgradeLayout.debug(Debug.all);

        final Attributes attributes = ship.getAttributes();

        for (int i = 0; i < 5; i++) {

            final int j = i;

            Label name = new Label(getLabelAtributeString(getAttributeType(i), 0), skin);

            priceLabels[i] = new Label(getLabelAtributeString(getAttributeType(i), 1), skin);
            priceLabels[i].setAlignment(Align.center);

            attributeLabels[i] = new Label(getLabelAtributeString(getAttributeType(i), 2), skin);
            attributeLabels[i].setAlignment(Align.center);

            Button travelSpeedPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
            travelSpeedPlus.getButton().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(player.getMoney() > attributes.getAttributePrice(getAttributeType(j))){
                        attributeLabels[j].setText(getLabelAtributeString(getAttributeType(j), 2));
                        priceLabels[j].setText(getLabelAtributeString(getAttributeType(j),1));
                        player.decreaseMoney(attributes.getAttributePrice(getAttributeType(j)));
                        attributes.increaseAttribute(getAttributeType(j));
                        ship.saveAttributes();
                    }
                }
            });

            upgradeLayout.add(name)
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight);
            upgradeLayout.add(attributeLabels[i])
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight);
            upgradeLayout.add(priceLabels[i])
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight);
            upgradeLayout.add(travelSpeedPlus.getButton())
                    .size(defaultBtnSize)
                    .align(Align.center)
                    .row();

        }

        final UpgradeShipDialog shipDialog = this;
        TextButton closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shipDialog.hide();
            }
        });

        upgradeLayout.add(closeBtn)
                .width(defaultButtonWidth)
                .height(defaultButtonHeight)
                .align(Align.center);

        getContentTable().add(upgradeLayout).row();

    }

    private String getLabelAtributeString(AttributeType type, int id){

        Attributes attributes = ship.getAttributes();
        String labelText = "";

        switch (type){
            case MINING_SPEED:
                if(id == 0)
                    return "MINING SPEED";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(MINING_SPEED));
                return ship.getAttribute(MINING_SPEED)+"";
            case TRAVEL_SPEED:
                if(id == 0)
                    return "TRAVEL SPEED";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(TRAVEL_SPEED));
                return ship.getAttribute(TRAVEL_SPEED)+"";
            case FUEL_CAPACITY:
                if(id == 0)
                    return "FUEL CAPACITY";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(FUEL_CAPACITY));
                return ship.getAttribute(FUEL_CAPACITY)+"";
            case FUEL_EFFICIENCY:
                if(id == 0)
                    return "FUEL EFFICIENCY";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(FUEL_EFFICIENCY));
                return ship.getAttribute(FUEL_EFFICIENCY)+"";
            case RESOURCE_CAPACITY:
                if(id == 0)
                    return "RESOURCE CAPACITY";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(RESOURCE_CAPACITY));
                return ship.getAttribute(RESOURCE_CAPACITY)+"";
        }
        return labelText;
    }

    @Override
    public float getPrefHeight() {
        return getContentTable().getPrefHeight();
    }

    @Override
    public float getPrefWidth() {
        return getContentTable().getPrefWidth();
    }
}

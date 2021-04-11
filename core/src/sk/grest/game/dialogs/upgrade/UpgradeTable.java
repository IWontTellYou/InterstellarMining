package sk.grest.game.dialogs.upgrade;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.controls.Button;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

import static sk.grest.game.entities.ship.Attributes.AttributeType.MINING_SPEED;
import static sk.grest.game.entities.ship.Attributes.AttributeType.RESOURCE_CAPACITY;
import static sk.grest.game.entities.ship.Attributes.AttributeType.TRAVEL_SPEED;
import static sk.grest.game.entities.ship.Attributes.AttributeType.getAttributeType;

public class UpgradeTable extends Table {

    public static final String BTN_DEFAULT_DOWN = "default-round-down";
    public static final String BTN_PLUS_DEFAULT_UP = "tree-plus";

    private Player player;
    private Ship ship;
    final private Label[] attributeLabels;
    final private Label[] priceLabels;

    public UpgradeTable(Skin skin, final Player player, final Ship ship) {
        super(skin);

        this.ship = ship;

        float defaultLabelWidth = ScreenConstants.DEFAULT_DIALOG_WIDTH/8f;
        float defaultLabelHeight = ScreenConstants.DEFAULT_DIALOG_HEIGHT/17.5f;

        float defaultBtnSize = ScreenConstants.DEFAULT_DIALOG_HEIGHT/20f;

        attributeLabels = new Label[3];
        priceLabels = new Label[3];

        for (int i = 0; i < 3; i++) {

            final int j = i;

            Label name = new Label(getLabelAttributeString(getAttributeType(i), 0), skin);

            priceLabels[i] = new Label(getLabelAttributeString(getAttributeType(i), 1), skin);
            priceLabels[i].setAlignment(Align.center);

            attributeLabels[i] = new Label(getLabelAttributeString(getAttributeType(i), 2), skin);
            attributeLabels[i].setAlignment(Align.center);

            Button travelSpeedPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
            travelSpeedPlus.getButton().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(increaseAttribute(ship, getAttributeType(j)) && player.getMoney() > ship.getAttributes().getAttributePrice(getAttributeType(j))){
                        attributeLabels[j].setText(getLabelAttributeString(getAttributeType(j), 2));
                        priceLabels[j].setText(getLabelAttributeString(getAttributeType(j),1));
                        player.decreaseMoney(ship.getAttributes().getAttributePrice(getAttributeType(j)));
                        ship.getAttributes().increaseAttribute(getAttributeType(j));
                        ship.saveAttributes();
                    }
                }
            });

            add(name)
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight)
                    .padRight(20);
            add(attributeLabels[i])
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight);
            add(priceLabels[i])
                    .width(defaultLabelWidth)
                    .height(defaultLabelHeight);
            add(travelSpeedPlus.getButton())
                    .size(defaultBtnSize)
                    .align(Align.center)
                    .row();

        }

    }

    private String getLabelAttributeString(Attributes.AttributeType type, int id){

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
            case RESOURCE_CAPACITY:
                if(id == 0)
                    return "CAPACITY";
                else if (id == 1)
                    return ScreenConstants.getMoneyFormat(attributes.getAttributePrice(RESOURCE_CAPACITY));
                return ship.getAttribute(RESOURCE_CAPACITY)+"";
        }
        return labelText;
    }

    public boolean increaseAttribute(Ship s, Attributes.AttributeType type){
        Attributes attributes = s.getAttributes();
        switch (s.getLevel(0)){
            case 0:
                return attributes.getAttribute(type) < 20;
            case 1:
                return attributes.getAttribute(type) < 50;
            case 2:
                return attributes.getAttribute(type) < 100;
            case 3:
                return attributes.getAttribute(type) < 200;
            case 4:
                return true;
            default:
                return false;
        }
    }

}

package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Objects;

import sk.grest.game.InterstellarMining;
import sk.grest.game.controls.Button;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

import sk.grest.game.entities.ship.Attributes.AttributeType;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class UpgradeShipDialog extends CustomDialog {

    // TODO ADD CONDINTION SHIP.STATE == AT_THE_BASE

    private static final int TRAVEL_SPEED_LABEL = 0;
    private static final int MINING_SPEED_LABEL = 1;
    private static final int FUEL_CAPACITY_LABEL = 2;
    private static final int FUEL_EFFICIENCY_LABEL = 3;
    private static final int RESOURCE_CAPACITY_LABEL = 4;

    // InterstellarMining class will be here as temporary solution until I have regular graphichs for Buttons

    public static final String BTN_PLUS_DEFAULT_UP = "tree-plus";
    public static final String BTN_MINUS_DEFAULT_UP = "tree-minus";
    // TODO FIX (IT'S RED)
    public static final String BTN_DEFAULT_DOWN = "default-round-down";

    private Skin skin;
    private Ship ship;
    final private Label[] labels;

    public UpgradeShipDialog(InterstellarMining game, String title, Skin skinTest, final Ship ship) {
        super(title, skinTest);

        this.skin = game.getUISkin();
        this.ship = ship;
        labels = new Label[5];

        float defaultButtonWidth = getWidth()/8f;
        float defaultButtonHeight = getHeight()/20f;

        float defaultLabelWidth = getWidth()/3.5f;
        float defaultLabelHeight = getHeight()/17.5f;

        float defaultBtnSize = getHeight()/40f;

        Image shipImage = new Image();

        Table upgradeLayout = new Table(skin);
        //upgradeLayout.debug(Debug.all);

        final Attributes attributes = ship.getAttributes();

    // TRAVEL_SPEED

        labels[TRAVEL_SPEED_LABEL] = new Label(getLabelAtributeString(TRAVEL_SPEED), skin);
        Button travelSpeedPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        travelSpeedPlus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.increaseTemporaryAttribute(TRAVEL_SPEED);
                labels[TRAVEL_SPEED_LABEL].setText(getLabelAtributeString(TRAVEL_SPEED));
            }
        });
        Button travelSpeedMinus = new Button(skin, BTN_MINUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        travelSpeedMinus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.decreaseTemporaryAttribute(TRAVEL_SPEED);
                labels[TRAVEL_SPEED_LABEL].setText(getLabelAtributeString(TRAVEL_SPEED));
            }
        });

        upgradeLayout.add(labels[TRAVEL_SPEED_LABEL])
                .width(defaultLabelWidth)
                .height(defaultLabelHeight);
        upgradeLayout.add(travelSpeedMinus.getButton())
                .size(defaultBtnSize);
        upgradeLayout.add(travelSpeedPlus.getButton())
                .size(defaultBtnSize)
                .row();

    // MINING_SPEED

        labels[MINING_SPEED_LABEL] = new Label(getLabelAtributeString(MINING_SPEED), skin);
        Button miningSpeedPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        miningSpeedPlus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.increaseTemporaryAttribute(MINING_SPEED);
                labels[MINING_SPEED_LABEL].setText(getLabelAtributeString(MINING_SPEED));
            }
        });
        Button miningSpeedMinus = new Button(skin, BTN_MINUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        miningSpeedMinus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.decreaseTemporaryAttribute(MINING_SPEED);
                labels[MINING_SPEED_LABEL].setText(getLabelAtributeString(MINING_SPEED));
            }
        });

        upgradeLayout.add(labels[MINING_SPEED_LABEL])
                .width(defaultLabelWidth)
                .height(defaultLabelHeight);
        upgradeLayout.add(miningSpeedMinus.getButton())
                .size(defaultBtnSize);
        upgradeLayout.add(miningSpeedPlus.getButton())
                .size(defaultBtnSize)
                .row();

    // FUEL_CAPACITY

        labels[FUEL_CAPACITY_LABEL] = new Label(getLabelAtributeString(FUEL_CAPACITY), skin);
        Button fuelCapacityPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        fuelCapacityPlus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.increaseTemporaryAttribute(FUEL_CAPACITY);
                labels[FUEL_CAPACITY_LABEL].setText(getLabelAtributeString(FUEL_CAPACITY));
            }
        });
        Button fuelCapacityMinus = new Button(skin, BTN_MINUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        fuelCapacityMinus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.decreaseTemporaryAttribute(FUEL_CAPACITY);
                labels[FUEL_CAPACITY_LABEL].setText(getLabelAtributeString(FUEL_CAPACITY));
            }
        });

        upgradeLayout.add(labels[FUEL_CAPACITY_LABEL])
                .width(defaultLabelWidth)
                .height(defaultLabelHeight);
        upgradeLayout.add(fuelCapacityMinus.getButton())
                .size(defaultBtnSize);
        upgradeLayout.add(fuelCapacityPlus.getButton())
                .size(defaultBtnSize)
                .row();

    // FUEL_EFFICIENCY

        labels[FUEL_EFFICIENCY_LABEL] = new Label(getLabelAtributeString(FUEL_EFFICIENCY), skin);

        Button fuelEfficiencyPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        fuelEfficiencyPlus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.increaseTemporaryAttribute(FUEL_EFFICIENCY);
                labels[FUEL_EFFICIENCY_LABEL].setText(getLabelAtributeString(FUEL_EFFICIENCY));
            }
        });

        Button fuelEfficiencyMinus = new Button(skin, BTN_MINUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        fuelEfficiencyMinus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.decreaseTemporaryAttribute(FUEL_EFFICIENCY);
                labels[FUEL_EFFICIENCY_LABEL].setText(getLabelAtributeString(FUEL_EFFICIENCY));
            }
        });

        upgradeLayout.add(labels[FUEL_EFFICIENCY_LABEL])
                .width(defaultLabelWidth)
                .height(defaultLabelHeight);
        upgradeLayout.add(fuelEfficiencyMinus.getButton())
                .size(defaultBtnSize);
        upgradeLayout.add(fuelEfficiencyPlus.getButton())
                .size(defaultBtnSize)
                .row();

    // RESOURCE_CAPACITY

        labels[RESOURCE_CAPACITY_LABEL] = new Label(getLabelAtributeString(RESOURCE_CAPACITY), skin);

        Button resourceCapacityPlus = new Button(skin, BTN_PLUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        resourceCapacityPlus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.increaseTemporaryAttribute(RESOURCE_CAPACITY);
                labels[RESOURCE_CAPACITY_LABEL].setText(getLabelAtributeString(RESOURCE_CAPACITY));
            }
        });

        Button resourceCapacityMinus = new Button(skin, BTN_MINUS_DEFAULT_UP, BTN_DEFAULT_DOWN);
        resourceCapacityMinus.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attributes.decreaseTemporaryAttribute(RESOURCE_CAPACITY);
                labels[RESOURCE_CAPACITY_LABEL].setText(getLabelAtributeString(RESOURCE_CAPACITY));
            }
        });

        upgradeLayout.add(labels[RESOURCE_CAPACITY_LABEL])
                .width(defaultLabelWidth)
                .height(defaultLabelHeight);
        upgradeLayout.add(resourceCapacityMinus.getButton())
                .size(defaultBtnSize);
        upgradeLayout.add(resourceCapacityPlus.getButton())
                .size(defaultBtnSize)
                .row();

        TextButton saveBtn = new TextButton("SAVE", skin);
        saveBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ship.saveAttributes();
                for (int i = 0; i < labels.length; i++)
                    labels[i].setText(getLabelAtributeString(Objects.requireNonNull(getAttributeType(i))));
            }
        });

        upgradeLayout.add(saveBtn)
                .width(defaultButtonWidth)
                .height(defaultButtonHeight)
                .align(Align.center);

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

        getContentTable().debug(Debug.all);

        getContentTable().add(upgradeLayout).row();

    }

    private String getLabelAtributeString(AttributeType type){

        Attributes attributes = ship.getAttributes();
        String labelText = "";

        switch (type){
            case MINING_SPEED:
                labelText += "Mining speed: " + ship.getAttribute(MINING_SPEED);
                if(attributes.getTemporaryAttribute(MINING_SPEED) > 0)
                    labelText += " + " + attributes.getTemporaryAttribute(MINING_SPEED);
                break;
            case TRAVEL_SPEED:
                labelText += "Travel speed: " + ship.getAttribute(TRAVEL_SPEED);
                if(attributes.getTemporaryAttribute(TRAVEL_SPEED) > 0)
                    labelText += " + " + attributes.getTemporaryAttribute(TRAVEL_SPEED);
                break;
            case FUEL_CAPACITY:
                labelText += "Fuel capacity: " + ship.getAttribute(FUEL_CAPACITY);
                if(attributes.getTemporaryAttribute(FUEL_CAPACITY) > 0)
                    labelText += " + " + attributes.getTemporaryAttribute(FUEL_CAPACITY);
                break;
            case FUEL_EFFICIENCY:
                labelText += "Fuel efficiency: " + ship.getAttribute(FUEL_EFFICIENCY);
                if(attributes.getTemporaryAttribute(FUEL_EFFICIENCY) > 0)
                    labelText += " + " + attributes.getTemporaryAttribute(FUEL_EFFICIENCY);
                break;
            case RESOURCE_CAPACITY:
                labelText += "Resource capacity: " + ship.getAttribute(RESOURCE_CAPACITY);
                if(attributes.getTemporaryAttribute(RESOURCE_CAPACITY) > 0)
                    labelText += " + " + attributes.getTemporaryAttribute(RESOURCE_CAPACITY);
                break;
        }

        return labelText;
    }

}

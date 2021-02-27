package sk.grest.game.dialogs;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.TravelPlan;

import static sk.grest.game.entities.ship.Attributes.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class TravelSettingDialog extends CustomDialog {

    public static final int TABLE_PADDING = 10;

    private TextButton closeBtn;
    private TextButton startBtn;

    private Planet planet;
    private Ship shipToTravel;
    private Resource resourceToMine;

    private Label distance;
    private Label capacity;
    private Label fuel;
    private Label estimateTime;

    public TravelSettingDialog(InterstellarMining game, Planet planet, String title, Skin skin) {
        super(title, skin);

        ArrayList<Ship> shipsAtBase = game.getPlayer().getShipsAtBase();

        this.planet = planet;
        this.shipToTravel = shipsAtBase.get(0);
        this.resourceToMine = planet.getResources().get(0);

        float defaultTableWidth = getWidth()/3f;
        float defaultTableHeight = getWidth()/3f;

        float defaultActorWidth = getWidth()/6f;
        float defaultActorHeight = getHeight()/10f;

        float defaultSelectBoxWidth = getWidth()/3.5f;
        float defaultSelectBoxHeight = getHeight()/17.5f;

        Table travelView = new Table(skin);

        travelView.add(new Label("Distance:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        distance = new Label(TravelPlan.getDistance(planet)+" km", skin);
        travelView.add(distance)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

        travelView.add(new Label("Capacity:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        capacity = new Label(shipToTravel.getAttribute(RESOURCE_CAPACITY)+"", skin);
        travelView.add(capacity)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

        // TODO FORMULA FOR CAPACITY/EFFICIENCY AND TRUE/FALSE VALUE IF POSSIBLE TO GO...

        travelView.add(new Label("Fuel:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        fuel = new Label(shipToTravel.getAttribute(FUEL_CAPACITY)+"", skin);
        travelView.add(fuel)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

        travelView.add(new Label("Estimated time:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        estimateTime = new Label(ScreenConstants.getTimeFormat(TravelPlan.getTime(shipToTravel, planet)), skin);
        travelView.add(estimateTime)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

        travelView.setBackground(ScreenConstants.getBackground(ScreenConstants.LIGHT_GRAY));

        // PLANET IMAGE

        Image planetImage = new Image(game.getSpriteSkin(), planet.getAssetId());

        getContentTable().add(planetImage)
                .expandX()
                .width(defaultTableWidth)
                .height(defaultTableHeight);

        getContentTable().add(travelView)
                .expandX()
                .row();

        // SHIP SELECT LIST

        final SelectBox<Ship> shipListSelect = new SelectBox<>(skin);
        shipListSelect.setItems(shipsAtBase.toArray(new Ship[0]));
        shipListSelect.setSelected(shipToTravel);

        shipListSelect.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shipToTravel = shipListSelect.getItems().get(shipListSelect.getSelectedIndex());
                resetView();
            }
        });

        getContentTable().add(shipListSelect)
                .expandX()
                .width(defaultSelectBoxWidth)
                .height(defaultSelectBoxHeight);

        // RESOURCE SELECT LIST

        final SelectBox<Resource> resourceListSelect = new SelectBox<>(skin);
        resourceListSelect.setItems(planet.getResources().toArray(new Resource[0]));
        resourceListSelect.setSelected(resourceToMine);

        resourceListSelect.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resourceToMine = resourceListSelect.getItems().get(resourceListSelect.getSelectedIndex());
            }
        });

        getContentTable().add(resourceListSelect)
                .expandX()
                .width(defaultSelectBoxWidth)
                .height(defaultSelectBoxHeight);

        // BUTTON TABLE

        final TravelSettingDialog travelSettingDialog = this;

        this.startBtn = new TextButton("START", skin);
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                travelSettingDialog.hide();
            }
        });
        getButtonTable().add(startBtn);

        this.closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                travelSettingDialog.hide();
            }
        });
        getButtonTable().add(closeBtn);

    }

    private void resetView(){
        distance.setText(TravelPlan.getDistance(planet) + " km");
        capacity.setText(shipToTravel.getAttribute(RESOURCE_CAPACITY));
        fuel.setText(shipToTravel.getAttribute(FUEL_CAPACITY));
        estimateTime.setText(ScreenConstants.getTimeFormat(TravelPlan.getTime(shipToTravel, planet)));
    }

    public TextButton getCloseBtn() {
        return closeBtn;
    }
    public TextButton getStartBtn() {
        return startBtn;
    }
    public Ship getShipToTravel() {
        return shipToTravel;
    }
    public Resource getResourceToMine() {
        return resourceToMine;
    }
}

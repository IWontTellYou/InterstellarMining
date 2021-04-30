package sk.grest.game.dialogs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.entities.ship.TravelPlan;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.other.selection_table.SelectionRow;
import sk.grest.game.other.selection_table.SelectionTable;

import static sk.grest.game.entities.ship.Attributes.AttributeType.*;

public class TravelSettingDialog extends CustomDialog implements ItemOpenedListener<Ship> {

    public static final int TABLE_PADDING = 10;

    private TextButton closeBtn;
    private TextButton startBtn;

    private Planet planet;
    private Ship shipToTravel;
    private Resource resourceToMine;

    private Label distance;
    private Label estimateTime;


    public TravelSettingDialog(InterstellarMining game, Planet planet, String title, Skin skin) {
        super(title, skin);

        ArrayList<Ship> shipsAtBase = game.getPlayer().getShipsAtBase();

        setBackground(InterstellarMining.back);

        this.planet = planet;

        if(shipsAtBase.size() > 0)
            this.shipToTravel = shipsAtBase.get(0);
        this.resourceToMine = planet.getResources().get(0);

        float defaultTableWidth = getWidth()/3f;
        float defaultTableHeight = getWidth()/3f;

        float defaultActorWidth = getWidth()/6f;
        float defaultActorHeight = getHeight()/10f;

        float defaultSelectBoxWidth = getWidth()/3.5f;
        float defaultSelectBoxHeight = getHeight()/17.5f;

        // -- PLANET IMAGE -- //

        Image planetImage = new Image(game.getSpriteSkin(), planet.getAssetId());

        // -- TRAVEL VIEW -- //

        Table travelView = new Table(skin);

        travelView.add(new Label("Name:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        Label planetName = new Label(planet.getName(), skin);
        travelView.add(planetName)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

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

        travelView.add(new Label("Estimated time:", skin))
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING);

        String estimateTimeText = (shipsAtBase.size() > 0) ? ScreenConstants.getTimeFormat(TravelPlan.getTime(shipToTravel, planet)) : "";
        estimateTime = new Label(estimateTimeText, skin);
        travelView.add(estimateTime)
                .expandX()
                .width(defaultActorWidth)
                .pad(TABLE_PADDING)
                .row();

        // -- RESOURCE PICKER -- //

        Table upperLine = new Table();
        ScrollPane shipSelect = null;
        SelectionTable<Ship> shipSelectionTable;

        if(shipsAtBase.size() > 0) {

            final SelectBox<Resource> resourceListSelect = new SelectBox<>(skin);
            resourceListSelect.setItems(planet.getResources().toArray(new Resource[0]));
            resourceListSelect.setSelected(resourceToMine);

            resourceListSelect.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resourceToMine = resourceListSelect.getItems().get(resourceListSelect.getSelectedIndex());
                }
            });

            travelView.add(resourceListSelect)
                    .expandX()
                    .pad(TABLE_PADDING)
                    .colspan(2)
                    .align(Align.left)
                    .row();

            //travelView.setBackground(ScreenConstants.getBackground(ScreenConstants.LIGHT_GRAY));


            // -- SHIP PICKER -- //

            Label nameLabel = new Label("Name", skin);
            upperLine.add(nameLabel)
                    .align(Align.center)
                    .pad(ScreenConstants.DEFAULT_PADDING)
                    .width(defaultActorWidth);
            Label shipSpeedLabel = new Label("Travel speed", skin);
            upperLine.add(shipSpeedLabel)
                    .align(Align.center)
                    .pad(ScreenConstants.DEFAULT_PADDING)
                    .width(defaultActorWidth);
            Label miningSpeedLabel = new Label("Mining speed", skin);
            upperLine.add(miningSpeedLabel)
                    .align(Align.center)
                    .pad(ScreenConstants.DEFAULT_PADDING)
                    .width(defaultActorWidth);
            Label resourceCapacityLabel = new Label("Capacity", skin);
            upperLine.add(resourceCapacityLabel)
                    .align(Align.center)
                    .pad(ScreenConstants.DEFAULT_PADDING)
                    .width(defaultActorWidth);


            //upperLine.debug(Debug.all);

            shipSelectionTable = new SelectionTable<>(this, true);

            for (Ship s : shipsAtBase) {

                if (s.getState() == ShipState.AT_THE_BASE) {
                    SelectionRow<Ship> row = new SelectionRow<>(skin);
                    row.setItem(s);
                    row.setColors(Color.CLEAR, ScreenConstants.LIGHT_GRAY);
                    row.addListener(shipSelectionTable);

                    //row.debug(Debug.cell);

                    Label name = new Label(s.getName(), skin);
                    row.add(name)
                            .align(Align.center)
                            .uniformX().expandX()
                            .pad(ScreenConstants.DEFAULT_PADDING)
                            .width(defaultActorWidth);

                    Label shipSpeed = new Label(String.valueOf(s.getAttribute(TRAVEL_SPEED)), skin);
                    row.add(shipSpeed)
                            .align(Align.center)
                            .uniformX().expandX()
                            .pad(ScreenConstants.DEFAULT_PADDING)
                            .width(defaultActorWidth);

                    Label miningSpeed = new Label(String.valueOf(s.getAttribute(MINING_SPEED)), skin);
                    row.add(miningSpeed)
                            .align(Align.center)
                            .uniformX().expandX()
                            .pad(ScreenConstants.DEFAULT_PADDING)
                            .width(defaultActorWidth);

                    Label resourceCapacity = new Label(String.valueOf(s.getAttribute(RESOURCE_CAPACITY)), skin);
                    row.add(resourceCapacity)
                            .align(Align.center)
                            .uniformX().expandX()
                            .pad(ScreenConstants.DEFAULT_PADDING)
                            .width(defaultActorWidth);

                    shipSelectionTable.addRow(row).expandX().row();

                }

            }

            shipSelect = new ScrollPane(shipSelectionTable);

        }

        // CONTENT TABLE

        getContentTable().add(planetImage)
                .pad(TABLE_PADDING)
                .size(400);

        getContentTable().add(travelView)
                .expandX()
                .uniformX()
                .row();

        if(shipsAtBase.size() > 0) {

            Table contentShipSelect = new Table();
            contentShipSelect.add(upperLine).expandX().row();
            contentShipSelect.add(shipSelect).expandX();

            getContentTable().add(contentShipSelect)
                    .expandX()
                    .height(ScreenConstants.DEFAULT_DIALOG_HEIGHT / 3f)
                    .colspan(2)
                    .pad(TABLE_PADDING);

        }else {

            getContentTable().add(new Label("THERE ARE NO AVAILABLE SHIPS AT THE MOMENT !", getSkin()))
                    .expandX()
                    .colspan(2)
                    .pad(TABLE_PADDING);
        }

        //getContentTable().debug(Debug.all);

        // BUTTON TABLE

        final TravelSettingDialog travelSettingDialog = this;

        if(shipsAtBase.size() > 0) {
            this.startBtn = new TextButton("START", skin);
            getButtonTable().add(startBtn);
        }

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
        estimateTime.setText(ScreenConstants.getTimeFormat(TravelPlan.getTime(shipToTravel, planet)));
    }

    public TextButton getCloseBtn() {
        return closeBtn;
    }
    public TextButton getStartBtn() {
        return startBtn;
    }

    public Planet getPlanet(){
        return planet;
    }
    public Ship getShipToTravel() {
        return shipToTravel;
    }
    public Resource getResourceToMine() {
        return resourceToMine;
    }

    @Override
    public void onItemOpenedListener(Ship item) {
        shipToTravel = item;
        resetView();
    }
}

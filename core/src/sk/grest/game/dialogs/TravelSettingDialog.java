package sk.grest.game.dialogs;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;

public class TravelSettingDialog extends CustomDialog {

    private TextButton closeBtn;
    private TextButton startBtn;

    private Ship shipToTravel;
    private Resource resourceToMine;

    public TravelSettingDialog(InterstellarMining game, Planet planet, String title, Skin skin) {
        super(title, skin);

        ArrayList<Ship> shipsAtBase = game.getPlayer().getShipsAtBase();

        this.shipToTravel = shipsAtBase.get(0);
        this.resourceToMine = planet.getResources().get(0);

        float defaultActorWidth = getWidth()/2f;
        float defaultActorHeight = getHeight()/2f;

        float defaultSelectBoxWidth = getWidth()/3.5f;
        float defaultSelectBoxHeight = getHeight()/17.5f;

        // PLANET IMAGE

        Image planetImage = new Image();
        planetImage.setDrawable(game.getSpriteSkin().getDrawable(planet.getAssetId()));

        getContentTable().add(planetImage)
            .width(defaultActorWidth)
            .height(defaultActorHeight);

        // PLANET INFO

        Table planetInfo = new Table(skin);
        getContentTable().add(planetInfo)
                .width(defaultActorWidth)
                .height(defaultActorHeight)
                .row();

        // SHIP SELECT LIST

        final SelectBox<Ship> shipListSelect = new SelectBox<>(skin);
        shipListSelect.setItems(shipsAtBase.toArray(new Ship[0]));
        shipListSelect.setSelected(shipToTravel);

        shipListSelect.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shipToTravel = shipListSelect.getItems().get(shipListSelect.getSelectedIndex());
            }
        });

        getContentTable().add(shipListSelect)
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

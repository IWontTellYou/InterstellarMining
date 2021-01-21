package sk.grest.game.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;

public class ResourceInventoryDialog extends CustomDialog {

    private ScrollPane pane;

    public ResourceInventoryDialog(String title, Skin skin, Player player) {
        super(title, skin);

        float imageCellWidth = Gdx.graphics.getWidth()/20f;
        float nameCellWidth = Gdx.graphics.getWidth()/10f;
        float destinationCellWidth = Gdx.graphics.getWidth()/8f;
        float timeToArriveCellWidth = Gdx.graphics.getWidth()/8f;

        float cellHeight = Gdx.graphics.getHeight()/25f;

        // CONTENT TABLE

        //
        // HEADER
        //

        getContentTable().clearChildren();

        Table contentTable = new Table(skin);

        Label imageLabel = new Label("", skin);
        getContentTable().add(imageLabel).
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("", skin);
        contentTable.add(nameLabel).
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label destinationLabel = new Label("NAME", skin);
        contentTable.add(destinationLabel).
                align(Align.center).
                width(destinationCellWidth).height(cellHeight);

        Label timeToArriveLabel = new Label("AMOUNT", skin);
        contentTable.add(timeToArriveLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight);

        Label stateLabel = new Label("STATE", skin);
        contentTable.add(stateLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        //
        // END OF HEADER
        //


        //
        // TABLE DATA
        //

        for (Resource r : player.getResourcesAtBase()) {

            Label resourceImage = new Label("img", skin);
            contentTable.add(resourceImage).
                    align(Align.center).
                    width(imageCellWidth).height(cellHeight);

            Label resourceName = new Label(r.getName(), skin);
            contentTable.add(resourceName).
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label resourceAmount = new Label(String.format(Locale.getDefault(),"%.2f",r.getAmount()), skin);
            contentTable.add(resourceAmount).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight);

            Label resourceState = new Label(r.getState().toString(), skin);
            contentTable.add(resourceState).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();

        }

        pane = new ScrollPane(contentTable);
        pane.setWidth(ScreenDeafults.DEFAULT_DIALOG_WIDTH * 0.8f);
        pane.setHeight(ScreenDeafults.DEFAULT_DIALOG_HEIGHT * 0.75f);
        pane.layout();

        getContentTable().add(pane);

        Gdx.app.log("PARAMS", "Height: " + getHeight() + ", Width: " + getWidth());

        final ResourceInventoryDialog resourceInventoryDialog = this;
        TextButton closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resourceInventoryDialog.hide();
            }
        });
        getButtonTable().add(closeBtn);

    }

    public void update(float delta){
        pane.act(delta);
    }

}

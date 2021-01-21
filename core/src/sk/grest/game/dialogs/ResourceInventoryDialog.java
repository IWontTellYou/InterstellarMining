package sk.grest.game.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemSelectedListener;
import sk.grest.game.other.Row;
import sk.grest.game.other.SelectionTable;
import sk.grest.game.other.SellBar;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.all;

public class ResourceInventoryDialog extends CustomDialog implements ItemSelectedListener<Resource> {

    private ScrollPane pane;
    private SelectionTable<Resource> contentTable;
    private SellBar sellBar;

    public ResourceInventoryDialog(String title, Skin skin, Player player) {
        super(title, skin);

        sellBar = new SellBar(player.getResourcesAtBase().get(0), skin);

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

        Table infoPanel = new Table(getSkin());

        Label imageLabel = new Label("", skin);
        infoPanel.add(imageLabel).
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("", skin);
        infoPanel.add(nameLabel).
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label destinationLabel = new Label("NAME", skin);
        infoPanel.add(destinationLabel).
                align(Align.center).
                width(destinationCellWidth).height(cellHeight);

        Label timeToArriveLabel = new Label("AMOUNT", skin);
        infoPanel.add(timeToArriveLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight);

        Label stateLabel = new Label("STATE", skin);
        infoPanel.add(stateLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        getContentTable().add(infoPanel).row();

        contentTable = new SelectionTable<>(skin);

        for (Resource r : player.getResourcesAtBase()) {

            Row<Resource> resourceRow = new Row<>();
            resourceRow.setItem(r);
            resourceRow.setColors(Color.BLACK, Color.CYAN);

            Label resourceImage = new Label("img", skin);
            resourceRow.add(resourceImage).
                    align(Align.center).
                    width(imageCellWidth).height(cellHeight);

            Label resourceName = new Label(r.getName(), skin);
            resourceRow.add(resourceName).
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label resourceAmount = new Label(String.format(Locale.getDefault(),"%.2f",r.getAmount()), skin);
            resourceRow.add(resourceAmount).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight);

            Label resourceState = new Label(r.getState().toString(), skin);
            resourceRow.add(resourceState).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();

            contentTable.add(resourceRow).fillX().row();

        }

        pane = new ScrollPane(contentTable);
        pane.setWidth(ScreenDeafults.DEFAULT_DIALOG_WIDTH * 0.8f);
        pane.setHeight(ScreenDeafults.DEFAULT_DIALOG_HEIGHT * 0.75f);
        pane.layout();

        getContentTable().add(pane).fillX().row();
        getContentTable().add(sellBar).fillX().row();

        //sellBar.debug(Debug.table);

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

    @Override
    public void onSelectedItemClicked(Row<Resource> r) {
        sellBar.changeResource(r.getItem());
    }

    @Override
    public void onUnselectedItemClicked() {

    }
}

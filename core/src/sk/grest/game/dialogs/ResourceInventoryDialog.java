package sk.grest.game.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.listeners.ItemSoldListener;
import sk.grest.game.other.SelectionRow;
import sk.grest.game.other.SelectionTable;
import sk.grest.game.other.SellBar;


public class ResourceInventoryDialog extends CustomDialog implements ItemOpenedListener<Resource>, ItemSoldListener {

    private static final int IMG = 0;
    private static final int NAME = 1;
    private static final int AMOUNT = 2;
    private static final int STATE = 3;

    private ScrollPane pane;
    private SelectionTable<Resource> contentTable;
    private SellBar sellBar;

    private InterstellarMining game;

    public ResourceInventoryDialog(String title, Skin skin, InterstellarMining game) {
        super(title, skin);

        this.game = game;
        sellBar = new SellBar(game.getPlayer().getResourcesAtBase().get(0), skin, game, this);

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

        Label imageLabel = new Label("IMG", skin);
        infoPanel.add(imageLabel).
                uniformX().
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("NAME", skin);
        infoPanel.add(nameLabel).
                uniformX().
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label amountLabel = new Label("AMOUNT", skin);
        infoPanel.add(amountLabel).
                uniformX().
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight);

        Label stateLabel = new Label("STATE", skin);
        infoPanel.add(stateLabel).
                uniformX().
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        getContentTable().add(infoPanel).row();

        contentTable = new SelectionTable<>(this, skin, true);

        for (Resource r : game.getPlayer().getResourcesAtBase()) {

            SelectionRow<Resource> resourceRow = new SelectionRow<>();
            resourceRow.setItem(r);
            resourceRow.setColors(ScreenConstants.TRANSPARENT, ScreenConstants.DARK_GRAY);

            if(!r.getAssetName().equals("")) {
                Image resourceImg = new Image(game.getSpriteSkin(), r.getAssetName());
                resourceRow.add(resourceImg).
                        uniformX().
                        align(Align.center).
                        width(30).height(30);
            }else{

                Image resourceImg = new Image(game.getSpriteSkin(), "iron_ingot");
                resourceRow.add(resourceImg).
                        uniformX().
                        align(Align.center).
                        width(30).height(30);
                /*
                Label resourceImage = new Label("img", skin);
                resourceRow.add(resourceImage).
                        uniformX().
                        align(Align.center).
                        width(imageCellWidth).height(cellHeight);
                */
            }

            Label resourceName = new Label(r.getName(), skin);
            resourceRow.add(resourceName).
                    uniformX().
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label resourceAmount = new Label(r.getAmount()+"", skin);
            resourceRow.add(resourceAmount).
                    uniformX().
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight);

            Label resourceState = new Label(r.getState().toString(), skin);
            resourceRow.add(resourceState).
                    uniformX().
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();

            contentTable.addRow(resourceRow).fillX().row();

        }

        pane = new ScrollPane(contentTable);
        pane.setWidth(ScreenConstants.DEFAULT_DIALOG_WIDTH * 0.8f);
        pane.setHeight(ScreenConstants.DEFAULT_DIALOG_HEIGHT * 0.75f);
        pane.layout();

        getContentTable().add(pane).uniformX().fillX().row();
        getContentTable().add(sellBar).uniformX().fillX().row();

        addCloseButton(this);

    }

    public void update(float delta){
        pane.act(delta);
    }

    @Override
    public void onItemOpenedListener(Resource item) {
        sellBar.changeResource(item);
    }

    @Override
    public void onItemSold() {
        long num = Long.parseLong(sellBar.getAmount().getText());
        Gdx.app.log(contentTable.getItemSelected().getName(), contentTable.getItemSelected().getAmount() - num+"");
        if(contentTable.getItemSelected().getAmount() - num >= 0) {
            game.getPlayer().increaseMoney(num * (long) contentTable.getItemSelected().getPrice());
            contentTable.getItemSelected().subtractAmount(Integer.parseInt(sellBar.getAmount().getText()));
            sellBar.changeResource(contentTable.getItemSelected());
            game.getHandler().updatePlayer();
            game.getHandler().updatePlayerResourceTable(contentTable.getItemSelected().getID());
        }

        ((Label) contentTable.getRow(contentTable.getItemSelected()).getElement(AMOUNT)).setText((int) contentTable.getItemSelected().getAmount());
    }
}

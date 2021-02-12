package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Objects;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.CustomDialog;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.other.SelectionRow;
import sk.grest.game.other.SelectionTable;

import static sk.grest.game.constants.ScreenConstants.DEFAULT_PADDING;

public class FactoryDialog extends CustomDialog implements ItemOpenedListener<FactoryItem> {

    public static final int ICON_SIZE = 40;

    public static final int ITEM_LIST_WIDTH = 500;
    public static final int ITEM_LIST_HEIGHT = 100;

    private ArrayList<FactoryItem> factoryItems;
    private FactoryQueue queue;
    private SelectionTable<FactoryItem> itemList;

    private Table controls;
    private TextButton increase;
    private TextButton decrease;

    private InterstellarMining game;

    // onItemSelected -> CHANGE THESE

    private int count;
    private Label amount;

    private Table itemView;

    private ArrayList<FactoryRecipeRow> rows;
    private ArrayList<Resource> itemsOwned;
    private ArrayList<Resource> itemsNeeded;

    public FactoryDialog(String title, Skin skin, final InterstellarMining game) {
        super(title, skin);
        this.game = game;

        rows = new ArrayList<>();
        itemsNeeded = new ArrayList<>();
        itemsOwned = new ArrayList<>();

        factoryItems = game.getFactoryItemsByType(FactoryItem.PLATE);

        controls = new Table(skin);

        increase = new TextButton("+", getSkin());
        increase.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateView(count+1);
            }
        });

        decrease = new TextButton("-", getSkin());
        decrease.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(count > 0)
                    updateView(count-1);
            }
        });

        amount = new Label("0", skin);
        amount.setAlignment(Align.center);

        itemList = new SelectionTable<>(this, skin, true);
        for (FactoryItem item : factoryItems) {
            SelectionRow<FactoryItem> row = new SelectionRow<>(skin);
            row.setItem(item);
            row.setColors(ScreenConstants.LIGHT_GRAY, ScreenConstants.DARK_GRAY);

            if(item.getResource().getAssetName().equals(""))
                row.add(new Image(game.getSpriteSkin(), "iron_ingot"))
                        .uniformX()
                        .expandX()
                        .align(Align.left)
                        .size(ICON_SIZE,ICON_SIZE);
            else
                row.add(new Image(game.getSpriteSkin(), item.getResource().getAssetName()))
                        .uniformX()
                        .expandX()
                        .align(Align.center)
                        .size(ICON_SIZE,ICON_SIZE);

            row.add(new Label(item.getResource().getName(), skin))
                    .uniformX()
                    .expandX();
            itemList.addRow(row).size(ITEM_LIST_WIDTH, ITEM_LIST_HEIGHT).pad(DEFAULT_PADDING).row();
        }

        itemsNeeded = itemList.getItemSelected().getItemsNecessary();
        for (Resource r : itemsNeeded) {
            itemsOwned.add(game.getPlayer().getResource(r.getID()));
        }

        itemView = new Table(skin);
        itemView.background(ScreenConstants.getBackground(ScreenConstants.LIGHT_GRAY));
        onItemOpenedListener(itemList.getItemSelected());
        count = 0;
        updateView(1);

        Table helpTable = new Table(skin);

        TextButton plateFactory = new TextButton("PLATE", skin);
        TextButton fuelFactory = new TextButton("FUEL", skin);

        Table choiceBox = new Table(skin);
        choiceBox.add(plateFactory).size(ICON_SIZE).pad(DEFAULT_PADDING);
        choiceBox.add(fuelFactory).size(ICON_SIZE).pad(DEFAULT_PADDING);

        Table amountControls = new Table(skin);
        amountControls.add(decrease).size(ICON_SIZE).pad(DEFAULT_PADDING);
        amountControls.add(amount).width(ITEM_LIST_WIDTH/4f).pad(DEFAULT_PADDING);
        amountControls.add(increase).size(ICON_SIZE).pad(DEFAULT_PADDING);

        TextButton confirm = new TextButton("START", skin);
        confirm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(queue.getUsedSlotsCount() != FactoryQueue.MAX_ITEMS) {
                    try {
                        FactoryItem item = (FactoryItem) itemList.getItemSelected().clone();
                        item.setCount(Integer.parseInt(amount.getText().toString()));
                        queue.addItem(item);

                        for (FactoryRecipeRow row : rows) {
                            row.update(game.getPlayer().getResource(row.getResource().getID()));
                        }

                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        helpTable.add(choiceBox).right().row();
        helpTable.add(amountControls).row();
        helpTable.add(itemView).center().pad(DEFAULT_PADDING).row();
        helpTable.add(confirm).center().size(ITEM_LIST_WIDTH/6f, ITEM_LIST_WIDTH/12f).row();

        Table layoutTable = new Table(skin);
        layoutTable.add(itemList).width(ITEM_LIST_WIDTH);
        layoutTable.add(helpTable).width(ITEM_LIST_WIDTH);

        getContentTable().add(layoutTable).width(ITEM_LIST_WIDTH).pad(ITEM_LIST_HEIGHT).top().row();

        queue = new FactoryQueue(skin, game.getSpriteSkin(), game.getPlayer().getQueue(), game);
        getContentTable().add(queue).width(ITEM_LIST_WIDTH).bottom();

        addCloseButton(this);

    }

    private Resource getResourceById(int id){
        for (Resource r : itemsOwned) {
            if(id == r.getID())
                return r;
        }
        return null;
    }
    public void updateView(int count){
        for (FactoryRecipeRow row : rows) {
            if(!row.testCount(count))
                return;
        }

        this.count = count;
        amount.setText(count+"");

        for (FactoryRecipeRow row : rows) {
            row.setCount(count);
        }
    }
    public void resetView(){
        rows.clear();
        itemView.clearChildren();

        for (Resource r : itemsNeeded) {
            FactoryRecipeRow row = new FactoryRecipeRow(getSkin(), game.getSpriteSkin(), r, Objects.requireNonNull(getResourceById(r.getID())));
            rows.add(row);
            itemView.add(row).width(FactoryRecipeRow.WIDTH).uniformX().row();
        }

        amount.setText(count+"");
        updateView(count);

    }

    @Override
    public void onItemOpenedListener(FactoryItem item) {

        if(itemView == null)
            return;

        itemsNeeded = itemList.getItemSelected().getItemsNecessary();
        for (Resource r : itemsNeeded) {
            itemsOwned.add(game.getPlayer().getResource(r.getID()));
        }
        resetView();
    }

    @Override
    public void update(float delta) {
        queue.update(delta);
    }
}


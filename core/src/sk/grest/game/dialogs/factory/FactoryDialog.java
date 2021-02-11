package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Objects;

import javax.xml.soap.Text;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.GameConstants;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.CustomDialog;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.other.DigitFilter;
import sk.grest.game.other.Row;
import sk.grest.game.other.SelectionTable;

public class FactoryDialog extends CustomDialog implements ItemOpenedListener<FactoryItem> {

    public static final int ICON_SIZE = 40;

    public static final int ITEM_LIST_WIDTH = 500;

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

    public FactoryDialog(String title, Skin skin, InterstellarMining game) {
        super(title, skin);
        this.game = game;

        clearChildren();
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

        itemList = new SelectionTable<>(this, skin);
        for (FactoryItem item : factoryItems) {
            Row<FactoryItem> row = new Row<>(skin);
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
            itemList.addRow(row).width(ITEM_LIST_WIDTH).pad(ScreenConstants.DEFAULT_PADDING).row();
        }

        itemsNeeded = itemList.getItemSelected().getItemsNecessary();
        for (Resource r : itemsNeeded) {
            itemsOwned.add(game.getPlayer().getResource(r.getID()));
        }

        itemView = new Table(skin);
        onItemOpenedListener(itemList.getItemSelected());
        count = 0;
        updateView(1);

        Table helpTable = new Table(skin);

        TextButton plateFactory = new TextButton("PLATE", skin);
        TextButton fuelFactory = new TextButton("FUEL", skin);

        Table choiceBox = new Table(skin);
        choiceBox.add(plateFactory).size(ICON_SIZE).pad(ScreenConstants.DEFAULT_PADDING);
        choiceBox.add(fuelFactory).size(ICON_SIZE).pad(ScreenConstants.DEFAULT_PADDING);

        Table amountControls = new Table(skin);
        amountControls.add(decrease).size(ICON_SIZE).pad(ScreenConstants.DEFAULT_PADDING);
        amountControls.add(amount).width(ITEM_LIST_WIDTH/4f).pad(ScreenConstants.DEFAULT_PADDING);
        amountControls.add(increase).size(ICON_SIZE).pad(ScreenConstants.DEFAULT_PADDING);

        TextButton confirm = new TextButton("CREATE", skin);
        confirm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FactoryItem item = itemList.getItemSelected();
                item.setCount(Integer.parseInt(amount.getText().toString()));
                queue.addItem(item);
            }
        });

        helpTable.add(choiceBox).right().top().row();
        helpTable.add(amountControls).row();
        helpTable.add(itemView).center().row();
        helpTable.add(confirm).center().row();

        Table helpTable2 = new Table(skin);
        helpTable2.add(itemList).width(ITEM_LIST_WIDTH);
        helpTable2.add(helpTable).width(ITEM_LIST_WIDTH);

        add(helpTable2).size(ITEM_LIST_WIDTH, ITEM_LIST_WIDTH).row();

        queue = new FactoryQueue(skin, game.getSpriteSkin(), game);
        queue.setColor(Color.BLUE);
        add(queue).size(ITEM_LIST_WIDTH, ITEM_LIST_WIDTH);

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
            itemView.add(row).width(FactoryRecipeRow.WIDTH).row();
            Gdx.app.log(itemsNeeded.indexOf(r)+"", row.toString());
        }
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


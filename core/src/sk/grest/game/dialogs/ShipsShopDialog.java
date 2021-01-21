package sk.grest.game.dialogs;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.ship.Ship;

import static sk.grest.game.defaults.ScreenDeafults.*;
import static sk.grest.game.entities.ship.Attributes.AttributeType;

public class ShipsShopDialog extends CustomDialog {

    private static final int TRAVEL_SPEED = 0;
    private static final int MINING_SPEED = 1;
    private static final int FUEL_EFFICIENCY = 2;
    private static final int FUEL_CAPACITY = 3;
    private static final int RESOURCE_CAPACITY = 4;

    private static final float defaultActorWidth = DEFAULT_DIALOG_WIDTH/8;
    //private static final float DEFAULT_PADDING = 0;

    private final Map<Ship, Table> shipShop;
    private final InterstellarMining game;
    private Skin skin;

    private int counter;

    private ScrollPane scrollingLayout;
    private Table layoutChild;
    private Texture tableBack;

    private TextureRegionDrawable textureRegionDrawableBg;

    public ShipsShopDialog(String title, Skin skin, InterstellarMining game) {
        super(title, skin);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(13/255f, 15/255f, 67/255f, 0.7f);
        bgPixmap.fill();

        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        tableBack = blank;

        layoutChild = new Table(skin);

        shipShop = new HashMap<>();
        this.game = game;
        this.skin = skin;

        final ArrayList<Ship> shipsForSale = game.getShipsNotOwned();

        counter = 3;
        layoutChild.add(new Label("", skin)).height(50).colspan(3).row();
        for (final Ship s : shipsForSale) {
            createItem(s);
        }

        scrollingLayout = new ScrollPane(layoutChild);
        scrollingLayout.setScrollbarsVisible(true);
        scrollingLayout.setScrollBarPositions(false, true);
        getContentTable().add(scrollingLayout);
        addCloseButton(this);

    }

    private void rearangeTable(){
        layoutChild.clearChildren();

        layoutChild.add(new Label("", skin)).height(50).colspan(3).row();

        counter = 3;
        for (Ship s : shipShop.keySet()){
            counter--;
            if(counter == 0) {
                layoutChild.add(shipShop.get(s)).row();
                counter = 3;
            }else {
                layoutChild.add(shipShop.get(s));
            }
        }
    }

    private void createItem(final Ship s){

        Table table = new Table();
        table.setBackground(textureRegionDrawableBg);
        shipShop.put(s, table);

        Label name = new Label(s.getName(), skin);
        name.setAlignment(Align.center);
        table.add(name).fillX().align(Align.center).colspan(2).row();

        Image image = new Image(game.getSpriteSkin().getDrawable("ships"));
        table.add(image).align(Align.center).colspan(2).row();

        Label[][] stats = new Label[5][2];

        stats[TRAVEL_SPEED][0] = new Label("TRAVEL SPEED", skin);
        table.add(stats[TRAVEL_SPEED][0]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth);
        stats[TRAVEL_SPEED][1] = new Label(s.getAttribute(AttributeType.TRAVEL_SPEED) + "", skin);
        stats[TRAVEL_SPEED][1].setAlignment(Align.right);
        table.add(stats[TRAVEL_SPEED][1]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth).row();

        stats[MINING_SPEED][0] = new Label("MINING SPEED", skin);
        table.add(stats[MINING_SPEED][0]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth);
        stats[MINING_SPEED][1] = new Label(s.getAttribute(AttributeType.MINING_SPEED) + "", skin);
        stats[MINING_SPEED][1].setAlignment(Align.right);
        table.add(stats[MINING_SPEED][1]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth).row();

        stats[FUEL_EFFICIENCY][0] = new Label("FUEL EFFICIENCY", skin);
        table.add(stats[FUEL_EFFICIENCY][0]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth);
        stats[FUEL_EFFICIENCY][1] = new Label(s.getAttribute(AttributeType.FUEL_EFFICIENCY) + "", skin);
        stats[FUEL_EFFICIENCY][1].setAlignment(Align.right);
        table.add(stats[FUEL_EFFICIENCY][1]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth).row();

        stats[FUEL_CAPACITY][0] = new Label("FUEL CAPACITY", skin);
        table.add(stats[FUEL_CAPACITY][0]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth);
        stats[FUEL_CAPACITY][1] = new Label(s.getAttribute(AttributeType.FUEL_CAPACITY) + "", skin);
        stats[FUEL_CAPACITY][1].setAlignment(Align.right);
        table.add(stats[FUEL_CAPACITY][1]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth).row();

        stats[RESOURCE_CAPACITY][0] = new Label("RESOURCE CAPACITY", skin);
        table.add(stats[RESOURCE_CAPACITY][0]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth);
        stats[RESOURCE_CAPACITY][1] = new Label(s.getAttribute(AttributeType.RESOURCE_CAPACITY) + "", skin);
        stats[RESOURCE_CAPACITY][1].setAlignment(Align.right);
        table.add(stats[RESOURCE_CAPACITY][1]).pad(ScreenDeafults.DEFAULT_PADDING).width(defaultActorWidth).row();

        TextButton buy = new TextButton("(BUY) " + ScreenDeafults.getMoneyFormat((long) s.getPrice()), game.getUISkin());
        buy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.getPlayer().getMoney() >= s.getPrice()) {
                    game.getPlayer().decreaseMoney((long) s.getPrice());
                    game.getPlayer().getShips().add(s);
                    layoutChild.removeActor(shipShop.get(s));
                    game.getHandler().buyShip(s);
                    shipShop.remove(s);
                    rearangeTable();
                }
            }
        });

        table.add(buy)
                .fillX()
                .colspan(2)
                .height(DEFAULT_ACTOR_HEIGHT)
                .row();
        table.pad(DEFAULT_PADDING);

        counter--;
        if(counter == 0) {
            layoutChild.add(table)
                    .pad(DEFAULT_PADDING)
                    .row();
            counter = 3;
        }else {
            layoutChild.add(table)
                    .pad(DEFAULT_PADDING);
        }

    }

}

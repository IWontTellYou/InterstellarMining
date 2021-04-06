package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.ToolTipManager;

import sk.grest.game.InterstellarMining;
import sk.grest.game.dialogs.GoalDialog;
import sk.grest.game.dialogs.ObservatoryDialog;
import sk.grest.game.dialogs.factory.FactoryDialog;
import sk.grest.game.dialogs.ResourceInventoryDialog;
import sk.grest.game.dialogs.ShipListDialog;
import sk.grest.game.dialogs.ShipsShopDialog;
import sk.grest.game.dialogs.TravelSettingDialog;
import sk.grest.game.dialogs.upgrade.UpgradeShipDialog;
import sk.grest.game.listeners.OnStatsChangedListener;
import sk.grest.game.controls.Button;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.planet.PlanetStats;
import sk.grest.game.other.TooltipBuilder;

public class GameScreen implements Screen, OnStatsChangedListener {

    private static final float PANEL_WIDTH = 175;
    private static final float PANEL_HEIGHT = 620;

    private static final String BASE_PLANET_NAME = "Earth";

    private InterstellarMining game;

    // PLANET STATS INITIALIZED

    private Stage stage;

    private PlanetStats planetStats;

    private Button planetBtn;
    private Label moneyLabel;

    private Planet base;

    private TooltipManager toolTipManager;
    private TooltipBuilder tooltipBuilder;

    private ShipListDialog shipListDialog;
    private TravelSettingDialog travelSettingDialog;
    private ResourceInventoryDialog resourceInventoryDialog;
    private UpgradeShipDialog upgradeShipDialog;
    private ShipsShopDialog shipsShopDialog;
    private GoalDialog goalDialog;
    private FactoryDialog factoryDialog;
    private ObservatoryDialog observatoryDialog;

    public GameScreen(final InterstellarMining game) {

        this.game = game;
        this.travelSettingDialog = null;
        this.shipListDialog = null;
        this.shipsShopDialog = null;
        this.factoryDialog = null;

        stage = new Stage(new ScreenViewport());

        Gdx.app.log("SCREEN_CHANGE", "Screen changed to Game");

        tooltipBuilder = TooltipBuilder.getInstance();
        tooltipBuilder.setSkins(game.getUISkin(), game.getSpriteSkin());
        toolTipManager = TooltipManager.getInstance();

        // LAYOUT DEFINING

        final Table table = new Table(game.getUISkin());
        table.setFillParent(true);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        planetStats = new PlanetStats(game, this, game.getSolarSystem());
        // planetStats.getTable().setVisible(false);

        base = game.getPlanetByName(BASE_PLANET_NAME);

        final Button backToMenu = new Button(game.getSpriteSkin(), "left_top_button", "left_top_button_pressed");
        backToMenu.getButton().addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isOver(event.getStageX(), event.getStageY())){
                    game.setScreen(new MainMenuScreen(game));
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver(event.getStageX(), event.getStageY())){
                    // ACTION
                }
            }

            public boolean isOver(float x, float y){
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenConstants.TOP_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }

        });

        final Button systemsList = new Button(game.getSpriteSkin(), "right_top_button", "right_top_button_pressed");
        systemsList.getButton().addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver()){

                }
            }

            public boolean isOver(float x, float y) {
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenConstants.TOP_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }
        });

        Button leftDown = new Button(game.getSpriteSkin(), "left_bottom_button", null);
        leftDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isOver(event.getStageX(), event.getStageY())){
                    planetStats.previousPlanet();
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver(event.getStageX(), event.getStageY())){
                    // ACTION
                }
            }

            public boolean isOver(float x, float y) {
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenConstants.BOTTOM_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }

        });

        Button rightDown = new Button(game.getSpriteSkin(), "right_bottom_button", null);
        rightDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isOver(event.getStageX(), event.getStageY())){
                    planetStats.nextPlanet();
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver(event.getStageX(), event.getStageY())){
                    // ACTION
                }
            }

            public boolean isOver(float x, float y) {
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenConstants.BOTTOM_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }
        });

        Button shipsListButton = new Button(game.getSpriteSkin(), "ships", "ships_pressed");
        shipsListButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shipListDialog = new ShipListDialog("", game.getUISkin(), game);
                shipListDialog.show(stage);
            }
        });

        final Button shipsShopButton = new Button(game.getSpriteSkin(), "shop", "shop_pressed");
        shipsShopButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shipsShopDialog = new ShipsShopDialog("", game.getUISkin(), game);
                shipsShopDialog.show(stage);
            }
        });

        Button achievementsButton = new Button(game.getSpriteSkin(), "achievement", "achievement_pressed");
        achievementsButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    observatoryDialog = new ObservatoryDialog("", game.getUISkin(), game);
                    observatoryDialog.show(stage);
            }
        });

        Button inventoryButton = new Button(game.getSpriteSkin(), "home", "home_pressed");
        inventoryButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // ADD ACTION
                resourceInventoryDialog = new ResourceInventoryDialog("Inventory", game.getUISkin(), game);
                resourceInventoryDialog.show(stage);
            }
        });

        Button otherButton = new Button(game.getSpriteSkin(), "settings", "settings_pressed");
        otherButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goalDialog = new GoalDialog("", game.getUISkin(), game.getSpriteSkin(), game);
                goalDialog.show(stage);
            }
        });

        Button settingsButton = new Button(game.getSpriteSkin(), "settings", "settings_pressed");
        settingsButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                factoryDialog = new FactoryDialog("", game.getUISkin(), game);
                factoryDialog.show(stage);
            }
        });



        planetBtn = new Button(game.getSpriteSkin(), planetStats.getCurrentPlanet().getAssetId(), null);
        planetBtn.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isOver(event.getStageX(), event.getStageY())){

                    final Planet destination = planetStats.getCurrentPlanet();

                    travelSettingDialog = new TravelSettingDialog(game, destination, "TRAVEL", game.getUISkin());
                    travelSettingDialog.show(stage);

                    travelSettingDialog.getStartBtn().addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            travelSettingDialog.getShipToTravel().setDestination(
                                            game,
                                            travelSettingDialog.getPlanet(),
                                            travelSettingDialog.getResourceToMine()
                                    );
                            travelSettingDialog.hide();
                        }
                    });
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver(event.getStageX(), event.getStageY())){

                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(isOver(event.getStageX(), event.getStageY())){

                }
            }

            public boolean isOver(float x, float y){
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenConstants.MIDDLE) < planetBtn.getButton().getWidth()/2;
            }

        });
        //planet.getButton().addListener(stats);

        float planetSize = Gdx.graphics.getWidth() / 3f;

        planetBtn.getButton().align(Align.center);
        planetBtn.getButton().setWidth(planetSize);
        planetBtn.getButton().setHeight(planetSize);

        float btnWidth = Gdx.graphics.getWidth() / 12f;
        float btnHeight = Gdx.graphics.getHeight() / 6.25f;

        // ROW 0

        backToMenu.getButton().align(Align.topLeft);
        table.add(backToMenu.getButton())
                .align(Align.topLeft)
                .width(btnWidth)
                .height(btnHeight);

        moneyLabel = new Label(game.getPlayer().getMoney() + " $", game.getUISkin());
        moneyLabel.setAlignment(Align.center);
        table.add(moneyLabel)
                .align(Align.top)
                .center()
                .width(btnWidth)
                .height(btnHeight);

        systemsList.getButton().align(Align.topRight);
        table.add(systemsList.getButton())
                .align(Align.topRight)
                .width(btnWidth)
                .height(btnHeight)
                .row();

        // ROW 1

        //table.debug(Table.Debug.all);

        Table leftPanel = new Table(game.getUISkin());
        leftPanel.setFillParent(false);
        leftPanel.left();
        leftPanel.setScale(0.95f);
        leftPanel.setOrigin(leftPanel.getPrefWidth() / 2 - 40, leftPanel.getPrefHeight() / 2);
        leftPanel.add(shipsListButton.getButton()).fill().pad(15,5,40,40).row();
        leftPanel.add(shipsShopButton.getButton()).fill().padBottom(40).padRight(40).padLeft(5).row();
        leftPanel.add(achievementsButton.getButton()).fill().padRight(40).padLeft(5).row();

        //leftPanel.debug(Table.Debug.all);

        table.add(leftPanel)
                .uniformX()
                .prefHeight(Gdx.graphics.getHeight() / 3f)
                .prefWidth(Gdx.graphics.getWidth() / 16f)
                .left()
                .fill(false, false)
                .width(Gdx.graphics.getWidth() / 8f)
                .height(Gdx.graphics.getHeight() / 1.5f)
                .pad(0);

        table.add(planetBtn.getButton())
                .expandX().align(Align.center)
                .width(planetSize)
                .height(planetSize);

        Table rightPanel = new Table(game.getUISkin());
        rightPanel.setFillParent(false);
        rightPanel.right();
        rightPanel.setOrigin(leftPanel.getPrefWidth() / 2 - 40, leftPanel.getPrefHeight() / 2);
        rightPanel.add(inventoryButton.getButton()).fill().padTop(15).padBottom(40).padLeft(35).row();
        rightPanel.add(otherButton.getButton()).fill().padBottom(40).padLeft(35).row();
        rightPanel.add(settingsButton.getButton()).fill().padLeft(35).row();

        //rightPanel.debug(Table.Debug.all);

        table.add(rightPanel)
                .uniformX()
                .prefHeight(Gdx.graphics.getHeight() / 3f)
                .prefWidth(Gdx.graphics.getWidth() / 16f)
                .right()
                .fill(false, false)
                .width(Gdx.graphics.getWidth() / 8f)
                .height(Gdx.graphics.getHeight() / 1.5f)
                .pad(0)
                .row();


        // ROW 2

        leftDown.getButton().align(Align.bottomLeft);
        table.add(leftDown.getButton())
                .align(Align.bottomLeft)
                .width(btnWidth)
                .height(btnHeight);

        //table.debug(Table.Debug.all);

        //planetStats.getTable().debug(Table.Debug.all);
        //planetStats.getTable().align(Align.center);
        table.add(planetStats.getTable())
                .width(Gdx.graphics.getWidth()/4f)
                .align(Align.bottom);

        rightDown.getButton().align(Align.bottomRight);
        table.add(rightDown.getButton())
                .align(Align.bottomRight)
                .width(btnWidth)
                .height(btnHeight)
                .row();

        table.setBackground(game.getSpriteSkin().getDrawable("game_screen_background"));

        Container<Table> container = new Container<>(table);
        container.fillX();
        container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        container.setActor(table);

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenConstants.clear();

        if (game.isWholeDatabaseInitialized()){

            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))
                game.getPlayer().increaseMoney(10000);

            game.getPlayer().update(delta);

            if(shipListDialog != null){
                shipListDialog.update(delta);
            }

            if(resourceInventoryDialog != null){
                resourceInventoryDialog.update(delta);
            }

            if(factoryDialog != null){
                factoryDialog.update(delta);
            }

            if(observatoryDialog != null){
                observatoryDialog.update(delta);
            }

            // END OF SHAPE RENDERING

            for (Ship s : game.getPlayer().getShips()) {
                s.update(delta);
            }

            moneyLabel.setText(ScreenConstants.getMoneyFormat(game.getPlayer().getMoney()));

            // START OF RENDERING SPRITES
            game.getBatch().begin();

            //game.getBatch().draw(panelLeft, 0, Gdx.graphics.getHeight()/2f - PANEL_HEIGHT/2f, PANEL_WIDTH, PANEL_HEIGHT);
            //game.getBatch().draw(panelRight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2f - PANEL_HEIGHT/2f, PANEL_WIDTH, PANEL_HEIGHT);

            // STAGE RENDERING
            stage.act(delta);
            stage.draw();

            /*
            font.setColor(Color.WHITE);
            font.draw(game.getBatch(), Gdx.input.getX() + " " + Gdx.input.getY(), Gdx.graphics.getWidth()/2f, 100);
            */

            // END OF RENDERING SPRITES
            game.getBatch().end();

            // SHAPE RENDERING

            game.getRenderer().begin();



            game.getRenderer().end();

        }
    }

    //

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
        game.getBatch().dispose();

    }

    @Override
    public void onPlanetChanged(Planet planet) {
        planetBtn.setImageUp(planet.getAssetId());
    }
}

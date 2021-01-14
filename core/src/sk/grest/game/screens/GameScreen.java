package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.dialogs.ResourceInventoryDialog;
import sk.grest.game.dialogs.ShipListDialog;
import sk.grest.game.dialogs.TravelSettingDialog;
import sk.grest.game.dialogs.UpgradeShipDialog;
import sk.grest.game.entities.PlanetSystem;
import sk.grest.game.listeners.OnStatsChangedListener;
import sk.grest.game.controls.Button;
import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.other.PlanetStats;

public class GameScreen implements Screen, OnStatsChangedListener {

    // TODO REGISTER SCREEN
    // TODO SHIP UPGRADE WINDOW
    // TODO SHIP SHOP
    // TODO PLANET SYSTEM LIST

    private static final String BASE_PLANET_NAME = "Earth";

    private InterstellarMining game;
    private Stage stage;

    private PlanetStats planetStats;

    private Button planetBtn;
    private Label planetName;

    private Planet base;

    private ShipListDialog shipListDialog;
    private TravelSettingDialog travelSettingDialog;
    private ResourceInventoryDialog resourceInventoryDialog;
    private UpgradeShipDialog upgradeShipDialog;

    public GameScreen(final InterstellarMining game) {

        this.game = game;
        this.travelSettingDialog = null;
        this.shipListDialog = null;

        Gdx.app.log("SCREEN_CHANGE", "Screen changed to Game");

        // LAYOUT DEFINING

        final Table table = new Table(game.getUISkin());
        table.setFillParent(true);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        planetStats = new PlanetStats(game, this, game.getPlanetSystemByID(1));
        // planetStats.getTable().setVisible(false);

        base = game.getPlanetByName(BASE_PLANET_NAME);

        //table.setDebug(true);
        //table.debug(Table.Debug.all);

        // MENU LAYOUT INITIALIZED

        // PLANET STATS INITIALIZED

        // TooltipManager tooltipManager = new TooltipManager();
        // final Tooltip<Table> stats = new Tooltip<>(planetStats);
        // tooltipManager.enter(stats);
        // tooltipManager.instant();

        stage = new Stage(new ScreenViewport());

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
                return mouse.dst(ScreenDeafults.TOP_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }

        });

        final Button systemsList = new Button(game.getSpriteSkin(), "right_top_button", "right_top_button_pressed");
        systemsList.getButton().addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("CLICK", "Clicked");
                if(isOver(event.getStageX(), event.getStageY())){
                    Gdx.app.log("CLICK", "Clicked inside");
                    upgradeShipDialog = new UpgradeShipDialog(game, "", game.getUISkin(), game.getPlayer().getShips().get(0));
                    upgradeShipDialog.show(stage);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(isOver()){

                }
            }

            public boolean isOver(float x, float y) {
                Vector2 mouse = new Vector2(x, y);
                return mouse.dst(ScreenDeafults.TOP_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth();
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
                return mouse.dst(ScreenDeafults.BOTTOM_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth();
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
                return mouse.dst(ScreenDeafults.BOTTOM_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth();
            }
        });

        Button leftPanel = new Button(game.getSpriteSkin(), "left_panel", null);
        leftPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shipListDialog = new ShipListDialog("", game.getUISkin(), game.getPlayer().getShips());
                shipListDialog.show(stage);
            }
        });

        Button rightPanel = new Button(game.getSpriteSkin(), "right_panel", null);
        rightPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // ADD ACTION
                resourceInventoryDialog = new ResourceInventoryDialog("Inventory", game.getUISkin(), game.getPlayer());
                resourceInventoryDialog.show(stage);
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
                                            planetStats.getCurrentPlanet(),
                                            travelSettingDialog.getResourceToMine()
                                    );
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
                return mouse.dst(ScreenDeafults.MIDDLE) < planetBtn.getButton().getWidth()/2;
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
                .expandX().align(Align.topLeft)
                .width(btnWidth)
                .height(btnHeight);

        planetName = new Label(planetStats.getSystemName(), game.getUISkin());
        planetName.setAlignment(Align.center);
        table.add(planetName)
                .align(Align.top)
                .width(btnWidth)
                .height(btnHeight);

        systemsList.getButton().align(Align.topRight);
        table.add(systemsList.getButton())
                .align(Align.topRight)
                .width(btnWidth)
                .height(btnHeight)
                .row();

        // ROW 1

        leftPanel.getButton().align(Align.left);
        table.add(leftPanel.getButton())
                .expand().align(Align.left)
                .width(Gdx.graphics.getWidth() / 4f)
                .height(Gdx.graphics.getHeight() / 1.5f);

        table.add(planetBtn.getButton())
                .expand().align(Align.center)
                .width(planetSize)
                .height(planetSize);

        rightPanel.getButton().align(Align.right);
        table.add(rightPanel.getButton())
                .expand().align(Align.right)
                .width(Gdx.graphics.getWidth() / 4f)
                .height(Gdx.graphics.getHeight() / 1.5f)
                .row();

        // ROW 2

        leftDown.getButton().align(Align.bottomLeft);
        table.add(leftDown.getButton())
                .align(Align.bottomLeft)
                .width(btnWidth)
                .height(btnHeight);

        planetStats.getTable().align(Align.center);
        table.add(planetStats.getTable())
                .align(Align.bottom);

        rightDown.getButton().align(Align.bottomRight);
        table.add(rightDown.getButton())
                .align(Align.bottomRight)
                .width(btnWidth)
                .height(btnHeight)
                .row();

        table.setBackground(game.getBackground());

        Container<Table> container = new Container<>(table);
        container.fillX();
        container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        container.setActor(table);

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenDeafults.clear();

        game.getPlayer().update(delta);

        if(shipListDialog != null){
            shipListDialog.update(delta);
        }

        if(resourceInventoryDialog != null){
            resourceInventoryDialog.update(delta);
        }


        if(game.isDatabaseInitialized()) {
            for (Ship s : game.getPlayer().getShips()) {
                s.update(delta);
            }

            // START OF RENDERING SPRITES
            game.getBatch().begin();

            // STAGE RENDERING
            stage.act(delta);
            stage.draw();

            /*
            font.setColor(Color.WHITE);
            font.draw(game.getBatch(), Gdx.input.getX() + " " + Gdx.input.getY(), Gdx.graphics.getWidth()/2f, 100);
            */

            // END OF RENDERING SPRITES
            game.getBatch().end();
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

    @Override
    public void onPlanetSystemChanged(PlanetSystem system) {
        planetName.setText(system.getName());
    }
}

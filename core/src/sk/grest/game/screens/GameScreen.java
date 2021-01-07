package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.controls.Button;
import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.Resource;
import sk.grest.game.entities.Ship;
import sk.grest.game.entities.enums.ResourceRarity;
import sk.grest.game.entities.enums.ResourceState;
import sk.grest.game.other.PlanetStats;

public class GameScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    BitmapFont font;
    private PlanetStats planetStats;

    public GameScreen(final InterstellarMining game) {

        this.game = game;

        Gdx.app.log("SCREEN_CHANGE", "Screen changed to Game");

        // LAYOUT DEFINING

        final Table table = new Table();
        table.setFillParent(true);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        planetStats = new PlanetStats(game, game.getPlanetByID(1));
        planetStats.getTable().setVisible(false);


        //table.setDebug(true);
        //table.debug(Table.Debug.all);


        // MENU LAYOUT INITIALIZED

        //PLANET STATS INITIALIZED

  //      TooltipManager tooltipManager = new TooltipManager();
 //       final Tooltip<Table> stats = new Tooltip<>(planetStats);
 //       tooltipManager.enter(stats);
 //       tooltipManager.instant();


        stage = new Stage(new ScreenViewport());

        final Button backToMenu = new Button(game.getSpriteSkin(), "left_top_button", null);
        backToMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.TOP_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth())
                    game.setScreen(new MainMenuScreen(game));
            }
        });


        Button systemsList = new Button(game.getSpriteSkin(), "right_top_button", null);
        systemsList.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.TOP_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION

                }
            }
        });

        Button leftDown = new Button(game.getSpriteSkin(), "left_bottom_button", null);
        leftDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.BOTTOM_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION

                }
            }
        });

        Button rightDown = new Button(game.getSpriteSkin(), "right_bottom_button", null);
        rightDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.BOTTOM_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION

                }
            }
        });

        Button leftPanel = new Button(game.getSpriteSkin(), "left_panel", null);
        leftPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // ADD ACTION

            }
        });

        Button rightPanel = new Button(game.getSpriteSkin(), "right_panel", null);
        rightPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // ADD ACTION

            }
        });

        final Button planetBtn = new Button(game.getSpriteSkin(), "earth", null);
        planetBtn.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Vector2(event.getStageX(), event.getStageY()).dst(ScreenDeafults.MIDDLE)
                        < planetBtn.getButton().getWidth()/2){
                    /*
                    Dialog stats = new Dialog("", game.getUISkin());
                    stats.add(planetStats);

                    //stats.setDebug(true);

                    game.getUISkin().getFont("default-font").getData().setScale(1.5f);
                    game.getUISkin().getFont("default-font").getColor().set(Color.BLACK);

                    stats.show(stage, sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
                    stats.setPosition(Gdx.graphics.getWidth()/2f-stats.getWidth()/2f, 10);
                    stage.addActor(stats);
                    */
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //planetStats.getTable().setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //planetStats.getTable().setVisible(false);
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

        Label planetName = new Label(game.getPlanetByName("Earth").getName(), game.getUISkin());
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

        table.setBackground(new TextureRegionDrawable(new TextureRegion(game.getBackground())));

        Container<Table> container = new Container<>(table);
        container.fillX();
        container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        container.setActor(table);

        stage.addActor(container);

        font = new BitmapFont(Gdx.files.internal("fonts\\space.fnt"),
                Gdx.files.internal("fonts\\space.png"), false);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenDeafults.clear();

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
}

package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.controls.Button;
import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Company;
import sk.grest.game.entities.Ship;

public class GameScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    BitmapFont font;

    private Company company;

    public GameScreen(final InterstellarMining game) {

        this.game = game;
        this.company = new Company("STELLARIS");

        // LAYOUT DEFINING

        final Table table = new Table();
        table.setFillParent(true);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // table.setDebug(true);
        // table.debug(Table.Debug.all);


        // MENU LAYOUT INITIALIZED

        stage = new Stage(new ScreenViewport());

        final Button backToMenu = new Button(game.getBtnSkin(), "left_top_button", null);
        backToMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.TOP_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth())
                    game.setScreen(new MainMenuScreen(game));
            }
        });

        Button systemsList = new Button(game.getBtnSkin(), "right_top_button", null);
        systemsList.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.TOP_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });

        Button leftDown = new Button(game.getBtnSkin(), "left_bottom_button", null);
        leftDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.BOTTOM_LEFT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });

        Button rightDown = new Button(game.getBtnSkin(), "right_bottom_button", null);
        rightDown.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 mouse = new Vector2(event.getStageX(), event.getStageY());
                if(mouse.dst(ScreenDeafults.BOTTOM_RIGHT) < table.getCell(backToMenu.getButton()).getActorWidth()){
                    // ADD ACTION
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });

        Button leftPanel = new Button(game.getBtnSkin(), "left_panel", null);
        leftPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        Button rightPanel = new Button(game.getBtnSkin(), "right_panel", null);
        rightPanel.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        final Button planet = new Button(game.getBtnSkin(), "earth", null);
        planet.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Vector2(event.getStageX(), event.getStageY()).dst(ScreenDeafults.MIDDLE)
                        < planet.getButton().getWidth()/2){
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });

        float planetSize = Gdx.graphics.getWidth() / 3f;

        planet.getButton().align(Align.center);
        planet.getButton().setWidth(planetSize);
        planet.getButton().setHeight(planetSize);

        float btnWidth = Gdx.graphics.getWidth() / 12f;
        float btnHeight = Gdx.graphics.getHeight() / 6.25f;

        // ROW 0

        backToMenu.getButton().align(Align.topLeft);
        table.add(backToMenu.getButton())
                .expandX().align(Align.topLeft)
                .width(btnWidth)
                .height(btnHeight)
                .colspan(2);

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

        table.add(planet.getButton())
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
                .expand().align(Align.bottomLeft)
                .width(btnWidth)
                .height(btnHeight)
                .colspan(2);

        rightDown.getButton().align(Align.bottomRight);
        table.add(rightDown.getButton())
                .expand().align(Align.bottomRight)
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

        for (Ship s : company.getShips()) {
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

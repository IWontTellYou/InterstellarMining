package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.defaults.ScreenDeafults;

import static sk.grest.game.defaults.GameConstants.BACKGROUND;

public class MainMenuScreen implements Screen {

    private Stage stage;

    private SpriteBatch batch;

    public MainMenuScreen(InterstellarMining game){
        this.stage = new Stage(new ScreenViewport());
        this.batch = game.getBatch();

        // MENU BUTTONS INITIALIZATION

        Skin btnSkin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton playBtn = new TextButton("PLAY", btnSkin, "default");
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INPUT", "Play button clicked!");
            }
        });

        TextButton optionsBtn = new TextButton("OPTIONS", btnSkin, "default");
        optionsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INPUT", "Options button clicked!");
            }
        });

        TextButton exitBtn = new TextButton("EXIT", btnSkin, "default");
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INPUT", "Exit button clicked!");
                System.exit(0);
            }
        });

        // MENU LAYOUT INTIALIZED

        Table table = new Table();
        table.align(Align.center);
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        table.add(playBtn).height(50).width(500);
        table.row();

        table.add(optionsBtn).height(50).width(500);
        table.row();

        table.add(exitBtn).height(50).width(500);
        table.row();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenDeafults.clear();

        // START OF RENDERING SPRITES
        batch.begin();

        // STAGE RENDERING
        stage.act(delta);
        stage.getBatch().begin();

        // BACKGROUND RENDERING
        stage.getBatch().draw(BACKGROUND, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.getBatch().end();
        stage.draw();

        // END OF RENDERING SPRITES
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }

}

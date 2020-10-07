package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;

import static sk.grest.game.other.GameConstants.BACKGROUND;

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
        clear();

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

    // CLEARING SCREEN
    private void clear(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

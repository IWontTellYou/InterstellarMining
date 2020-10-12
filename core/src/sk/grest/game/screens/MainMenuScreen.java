package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.defaults.ScreenDeafults;

public class MainMenuScreen implements Screen {

    private InterstellarMining game;

    private Stage stage;

    public MainMenuScreen(final InterstellarMining game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        //SKIN AND ATLAS INITIALIZATION

        TextureAtlas area = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        Skin btnSkin = new Skin(Gdx.files.internal("uiskin.json"), area);

        // MENU BUTTONS INITIALIZATION

        TextButton playBtn = new TextButton("PLAY", btnSkin, "default");
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INPUT", "Play button clicked!");
                game.setScreen(new GameScreen(game));
                Gdx.app.log("SCREEN_CHANGE", "Screen changed from MainMenu to Game");
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

        ImageButton button = new ImageButton(btnSkin);
        button.setSize(Gdx.graphics.getWidth()/3f, Gdx.graphics.getHeight()/10f);
        button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
        button.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture("play_hover.png")));

        Table table = new Table();
        table.align(Align.center);
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        table.add(button).height(50).width(500);
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
        game.getBatch().begin();

        // STAGE RENDERING
        stage.act(delta);
        stage.getBatch().begin();

        // BACKGROUND RENDERING
        stage.getBatch().draw(game.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.getBatch().end();
        stage.draw();

        // END OF RENDERING SPRITES
        game.getBatch().end();
    }

    @Override
    public void dispose() {
        game.getBatch().dispose();
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

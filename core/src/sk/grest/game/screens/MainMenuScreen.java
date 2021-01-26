package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.controls.Button;
import sk.grest.game.constants.ScreenConstants;

public class MainMenuScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    public MainMenuScreen(final InterstellarMining game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Gdx.app.log("SCREEN_CHANGE", "Screen changed to MainMenu");

        // MENU BUTTONS INITIALIZATION

        Image title = new Image(game.getSpriteSkin(), "title");

        Button btnPlay = new Button(game.getSpriteSkin(), "play_up", "play_over");
        btnPlay.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LogInScreen(game));
            }
        });

        Button btnOptions = new Button(game.getSpriteSkin(), "options_up", "options_over");
        btnOptions.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // OPENS OPTIONS WINDOW
            }
        });

        Button btnExit = new Button(game.getSpriteSkin(), "exit_up","exit_over");
        btnExit.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        // MAIN MENU LAYOUT INTIALIZED

        Table table = new Table();
        table.align(Align.top);
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        table.setBackground(game.getBackground());

        float btnPad = Gdx.graphics.getHeight() / 30f;

        table.padTop(Gdx.graphics.getHeight() / 12f);
        table.add(title)
                .width(Gdx.graphics.getWidth() / 1.5f)
                .padBottom(btnPad * 4)
                .row();
        table.add(btnPlay.getButton())
                .width(Gdx.graphics.getWidth() / 4.5f)
                .height(Gdx.graphics.getHeight() / 8f)
                .padBottom(btnPad)
                .row();
        table.add(btnOptions.getButton())
                .width(Gdx.graphics.getWidth() / 4.5f)
                .height(Gdx.graphics.getHeight() / 8f)
                .padBottom(btnPad)
                .row();
        table.add(btnExit.getButton())
                .width(Gdx.graphics.getWidth() / 4.5f)
                .height(Gdx.graphics.getHeight() / 8f)
                .padBottom(btnPad)
                .row();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenConstants.clear();

        // START OF RENDERING SPRITES
        game.getBatch().begin();

        // STAGE RENDERING
        stage.act(delta);
        stage.getBatch().begin();

        // BACKGROUND RENDERING
        //stage.getBatch().draw(game.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

}

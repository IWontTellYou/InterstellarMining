package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.GameConstants;
import sk.grest.game.constants.ScreenConstants;

import static sk.grest.game.constants.ScreenConstants.*;

public class OptionsScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    public OptionsScreen(final InterstellarMining game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Table content = new Table();
        content.setFillParent(true);
        content.align(Align.center);
        content.setBackground(game.getBackground());

        // ADD CONTENT

        TextButton back = new TextButton("BACK TO MENU", game.getUISkin());
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        content.add(back).size(DEFAULT_ACTOR_WIDTH, DEFAULT_ACTOR_HEIGHT);


        stage.addActor(content);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clear();

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

    @Override
    public void dispose() {

    }
}

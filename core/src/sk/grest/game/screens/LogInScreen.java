package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.defaults.ScreenDeafults;

public class LogInScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    public LogInScreen(final InterstellarMining game) {
        Gdx.app.log("SCREEN_CHANGE", "Screen changed to LogIn");

        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        final TextField inputName = new TextField("admin", game.getUISkin());
        final TextField inputPassword = new TextField("cisco123", game.getUISkin());
        // inputPassword.setPasswordCharacter('*');
        inputPassword.setPasswordMode(true);

        Button submit = new Button(game.getUISkin());
        submit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getHandler().verifyPlayer(inputName.getText(), inputPassword.getText(), game);
            }
        });

        Table table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        table.align(Align.center);
        table.add(inputName).height(80).width(200).align(Align.center).row();
        table.add(inputPassword).height(80).width(200).align(Align.center).row();
        table.add(submit).height(80).width(200).align(Align.center).row();
        table.setBackground(game.getBackground());

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
        stage.draw();

        /*
        font.setColor(Color.WHITE);
        font.draw(game.getBatch(), Gdx.input.getX() + " " + Gdx.input.getY(), Gdx.graphics.getWidth()/2f, 100);
        */

        // END OF RENDERING SPRITES
        game.getBatch().end();
    }

    @Override
    public void show() {

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

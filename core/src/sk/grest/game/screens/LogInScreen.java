package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;

import static sk.grest.game.constants.ScreenConstants.*;

public class LogInScreen implements Screen {

    private InterstellarMining game;
    private Stage stage;

    final private TextField nameInput;
    final private TextField passwordInput;
    final private Label errorMessage = null;

    public LogInScreen(final InterstellarMining game) {
        Gdx.app.log("SCREEN_CHANGE", "Screen changed to LogIn");

        this.game = game;
        this.stage = new Stage(new ScreenViewport());


        final Label nameLabel = new Label("Enter name:", game.getUISkin());
        nameInput = new TextField("admin", game.getUISkin());

        final Label passwordLabel = new Label("Enter password:", game.getUISkin());
        passwordInput = new TextField("cisco123", game.getUISkin());
        //passwordInput.setPasswordCharacter('*');
        //passwordInput.setPasswordMode(true);


        TextButton submit = new TextButton("LOG IN", game.getUISkin());
        submit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getHandler().verifyPlayer(nameInput.getText(), passwordInput.getText(), game);
            }
        });

        TextButton register = new TextButton("REGISTER", game.getUISkin());
        register.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game));
            }
        });

        TextButton back = new TextButton("BACK", game.getUISkin());
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);

        table.add(nameLabel).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center);
        table.add(nameInput).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.add(passwordLabel).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center);
        table.add(passwordInput).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.add(submit).height(DEFAULT_ACTOR_HEIGHT).fillX().align(Align.center).colspan(2).padBottom(DEFAULT_PADDING).row();
        table.add(register).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padRight(DEFAULT_PADDING).padBottom(DEFAULT_PADDING);
        table.add(back).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.setBackground(game.getBackground());

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        clear();

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

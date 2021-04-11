package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import sk.grest.game.InterstellarMining;

import static sk.grest.game.constants.ScreenConstants.*;

public class LogInScreen implements Screen {

    private static final FileHandle file_path = Gdx.files.internal("remember_user.txt");

    private static final float ACTOR_WIDTH = DEFAULT_ACTOR_WIDTH * 2f;
    private static final float ACTOR_HEIGHT = DEFAULT_ACTOR_HEIGHT * 1.5f;
    private static final float PADDING = DEFAULT_PADDING * 2f;

    private InterstellarMining game;
    private Stage stage;

    final private TextField nameInput;
    final private TextField passwordInput;
    final private Label errorMessage = null;

    private boolean remember;

    public LogInScreen(final InterstellarMining game) {
        Gdx.app.log("SCREEN_CHANGE", "Screen changed to LogIn");

        remember = false;

        this.game = game;
        this.stage = new Stage(new ScreenViewport());


        final Label nameLabel = new Label("Enter name:", game.getUISkin());
        nameInput = new TextField("", game.getUISkin());

        final Label passwordLabel = new Label("Enter password:", game.getUISkin());
        passwordInput = new TextField("", game.getUISkin());
        passwordInput.setPasswordCharacter('*');
        passwordInput.setPasswordMode(true);

        final CheckBox rememberMe = new CheckBox("  Remember me?", game.getUISkin());
        rememberMe.getImage().setScaling(Scaling.fill);
        rememberMe.getImageCell().size(30);
        rememberMe.setChecked(false);

        rememberMe.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remember = rememberMe.isChecked();
            }
        });

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(String.valueOf(file_path)));

            String name = br.readLine();
            String password = br.readLine();

            if(name != null && password != null){
                nameInput.setText(name);
                passwordInput.setText(password);
                rememberMe.setChecked(true);
                remember = true;
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        TextButton submit = new TextButton("LOG IN", game.getUISkin());
        submit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getHandler().verifyPlayer(nameInput.getText(), passwordInput.getText(), game);


                BufferedWriter bw;
                try {
                    bw = new BufferedWriter(new FileWriter(String.valueOf(file_path), false));
                    if (remember) {
                        bw.write(nameInput.getText());
                        bw.newLine();
                        bw.write(passwordInput.getText());
                    }
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }



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

        table.add(nameLabel).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center);
        table.add(nameInput).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center).padBottom(PADDING).row();

        table.add(passwordLabel).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center);
        table.add(passwordInput).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center).padBottom(PADDING).row();

        table.add(rememberMe).align(Align.left).height(ACTOR_HEIGHT).row();

        table.add(submit).height(ACTOR_HEIGHT).fillX().align(Align.center).colspan(2).padBottom(PADDING).row();
        table.add(register).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center).padRight(PADDING).padBottom(PADDING);
        table.add(back).height(ACTOR_HEIGHT).width(ACTOR_WIDTH).align(Align.center).padBottom(PADDING).row();

        table.setBackground(game.getBackground());

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        clear();

        if(game.isRegularDatabaseInitialized()) {
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

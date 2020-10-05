package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sk.grest.game.InterstellarMining;
import sk.grest.game.other.Background;

public class MainMenuScreen implements Screen, InputProcessor {

    private Background background;
    private SpriteBatch batch;

    final Stage stage = new Stage(new ScreenViewport());

    private Skin btnSkin;

    public MainMenuScreen(InterstellarMining game){
        this.batch = game.getBatch();
        this.background = new Background(batch);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        btnSkin = new Skin(Gdx.files.internal("uiskin.json"));
        btnSkin.addRegions(atlas);

        final TextButton btn = new TextButton("PLAY", btnSkin, "default");
        btn.setHeight(50);
        btn.setWidth(200);
        Gdx.app.log("BTN", "Btn loaded");

        final Dialog dialog = new Dialog("The Game", btnSkin);

        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.show(stage);
            }
        });

        stage.addActor(btn);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clear();

        // START OF RENDERING SPRITES
        batch.begin();

        background.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // END OF RENDERING SPRITES
        batch.end();
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
        batch.dispose();
    }

    // CLEARING SCREEN
    private void clear(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

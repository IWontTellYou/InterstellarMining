package sk.grest.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.other.AlphabetDigitFilter;

import static sk.grest.game.constants.ScreenConstants.clear;
import static sk.grest.game.database.DatabaseConnection.*;
import static sk.grest.game.database.DatabaseConstants.*;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.NAME;
import static sk.grest.game.database.DatabaseConstants.PlayerTable.TABLE_NAME;
import static sk.grest.game.constants.GameConstants.*;
import static sk.grest.game.constants.ScreenConstants.DEFAULT_ACTOR_HEIGHT;
import static sk.grest.game.constants.ScreenConstants.DEFAULT_PADDING;

public class RegisterScreen implements Screen, ConnectorEvent {

    private static final float DEFAULT_ACTOR_WIDTH = ScreenConstants.DEFAULT_ACTOR_WIDTH*2;
    private static final float DEFAULT_PADDING = ScreenConstants.DEFAULT_PADDING*2;

    private InterstellarMining game;
    private Stage stage;

    final DatabaseHandler handler;

    final private TextField nameInput;
    final private TextField passwordInput;
    final private TextField confirmPasswordInput;
    final private Label errorMessage;

    private ArrayList<Map<String, Object>> newPlayerData;
    private ArrayList<String> names;

    private boolean tableInitialized;

    private int shipsInitialized;
    private int planetsInitialized;
    private int resourcesInitialized;

    public RegisterScreen(final InterstellarMining game){
        Gdx.app.log("SCREEN_CHANGE", "Screen changed to Register");

        shipsInitialized = 0;
        planetsInitialized = 0;
        resourcesInitialized = 0;

        names = new ArrayList<>();
        newPlayerData = new ArrayList<>();
        tableInitialized = false;

        handler = DatabaseHandler.getInstance();
        handler.getPlayersTable(this);

        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        final Label nameLabel = new Label("Enter name:", game.getUISkin());
        nameInput = new TextField("", game.getUISkin());
        nameInput.setTextFieldFilter(new AlphabetDigitFilter());

        final Label passwordLabel = new Label("Enter password:", game.getUISkin());
        passwordInput = new TextField("", game.getUISkin());
        passwordInput.setPasswordCharacter('*');
        passwordInput.setPasswordMode(true);

        final Label passwordConfirmLabel = new Label("Enter password again:", game.getUISkin());
        confirmPasswordInput = new TextField("", game.getUISkin());
        confirmPasswordInput.setPasswordCharacter('*');
        confirmPasswordInput.setPasswordMode(true);

        errorMessage = new Label("", game.getUISkin());
        errorMessage.setAlignment(Align.center);
        errorMessage.setColor(Color.RED);

        final RegisterScreen screen = this;
        TextButton submit = new TextButton("REGISTER", game.getUISkin());
        submit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(errorMessage.getText().toString().equals("")){
                    handler.addPlayer(nameInput.getText().toLowerCase(), passwordInput.getText(), screen);
                }
            }
        });

        TextButton back = new TextButton("BACK", game.getUISkin());
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LogInScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);

        table.add(nameLabel).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center);
        table.add(nameInput).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.add(passwordLabel).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center);
        table.add(passwordInput).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.add(passwordConfirmLabel).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center);
        table.add(confirmPasswordInput).height(DEFAULT_ACTOR_HEIGHT).width(DEFAULT_ACTOR_WIDTH).align(Align.center).padBottom(DEFAULT_PADDING).row();

        table.add(errorMessage).height(DEFAULT_ACTOR_HEIGHT).fillX().align(Align.center).colspan(2).padBottom(DEFAULT_PADDING).row();

        table.add(submit).height(DEFAULT_ACTOR_HEIGHT).fillX().align(Align.center).pad(DEFAULT_PADDING);
        table.add(back).height(DEFAULT_ACTOR_HEIGHT).fillX().align(Align.center).pad(DEFAULT_PADDING).row();

        table.setBackground(game.getBackground());

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean isNameInDatabase(String name){
        for (String s : names) {
            if(name.equals(s)) return true;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clear();

        if(tableInitialized) {
            // START OF RENDERING SPRITES
            game.getBatch().begin();


            if (isNameInDatabase(nameInput.getText())) {
                errorMessage.setText("Name is already in use!");
            } else if(nameInput.getText().length() < 6) {
                errorMessage.setText("Username is too short");
            } else if(passwordInput.getText().equals("")){
                errorMessage.setText("Password has to 6+ characters long");
            } else if (!passwordInput.getText().equals(confirmPasswordInput.getText())) {
                errorMessage.setText("Passwords do not match!");
            } else
                errorMessage.setText("");

            // STAGE RENDERING
            stage.act(delta);
            stage.draw();

            game.getBatch().end();
        }

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


    @Override
    public void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData) {
        if (TABLE_NAME.equals(tableName)) {
            for (Map<String, Object> data : tableData) {
                names.add((String) data.get(NAME));
            }
            tableInitialized = true;
        }
    }
    @Override
    public void onUpdateSuccess(int requestCode, String tableName) {
        Gdx.app.log("TABLE", tableName);

        // TODO FIX REGISTRATION

        Gdx.app.log("TABLE_UPDATE", "SHIPS: " + shipsInitialized + " / " + DEFAULT_SHIP_COUNT +
                                                " PLANETS: " + planetsInitialized + " / " + game.getPlanets().size()+
                                                " RESOURCES: " + resourcesInitialized + "/" + game.getResources().size());

        switch (tableName){
            case TABLE_NAME:
                handler.verifyPlayer(nameInput.getText(), passwordInput.getText(), this);
                break;
            case PlayerShipTable.TABLE_NAME:
                shipsInitialized++;
                if(shipsInitialized == DEFAULT_SHIP_COUNT &&
                    planetsInitialized == game.getPlanets().size() &&
                    resourcesInitialized == game.getResources().size()){

                    game.onFetchSuccess(0, TABLE_NAME, newPlayerData);
                }
                break;
            case PlayerPlanetTable.TABLE_NAME:
                planetsInitialized++;
                if(planetsInitialized == game.getPlanets().size() &&
                    resourcesInitialized == game.getResources().size() &&
                    shipsInitialized == DEFAULT_SHIP_COUNT){

                    game.onFetchSuccess(0, TABLE_NAME, newPlayerData);
                }
                break;
            case PlayerResourceTable.TABLE_NAME:
                resourcesInitialized++;
                if(planetsInitialized == game.getPlanets().size() &&
                    resourcesInitialized == game.getResources().size() &&
                    shipsInitialized == DEFAULT_SHIP_COUNT){

                    game.onFetchSuccess(0, TABLE_NAME, newPlayerData);
                }
                break;
        }

    }
    @Override
    public void onConnect() {

    }
    @Override
    public void onConnectionFailed() {

    }
    @Override
    public void onResultFailed(int requestCode, String message) {
        Gdx.app.log("RESULT_FAILED", message);
    }
    @Override
    public void onUserLoginSuccessful(int requestCode, Map<String, Object> tableData) {
        newPlayerData.add(tableData);
        shipsInitialized = 0;
        planetsInitialized = 0;
        resourcesInitialized = 0;
        handler.addPlayerData(tableData, this);

    }

    @Override
    public void onDeleteSuccess(int requestCode, String message) {

    }

    @Override
    public void onLeaderBoardLoaded(int requestCode, Map<String, Float> leaderBoard) {

    }

    @Override
    public void onWinnerDataLoaded(int requestCode, Map<String, Object> data) {

    }

}

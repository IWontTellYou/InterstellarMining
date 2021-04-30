package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.crypto.Data;

import sk.grest.game.InterstellarMining;
import sk.grest.game.database.DatabaseConnection;
import sk.grest.game.database.DatabaseHandler;

public class GameOverDialog extends CustomDialog implements DatabaseConnection.ConnectorEvent {

    public static final String WINNERS_COUNT = "WINNERS_COUNT";
    public static final String GAME_START = "GAME_START";

    private Label rankLabel;

    public GameOverDialog(String title, Skin spriteSkin, Skin skin) {
        super(title, skin);
        setBackground(InterstellarMining.back);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        handler.getWinnersCount(this);

        String rank = "-";

        Label label1 = new Label("CONGRATULATIONS", skin);
        Label label2 = new Label("YOUR WORK HAS PAID OFF", skin);
        rankLabel = new Label("YOU WERE THE " + rank + " TO FINISH THE GAME", getSkin());

        getContentTable().add(label1).expandX().pad(50,30,50,30).row();
        getContentTable().add(label2).expandX().pad(50,30,50,30).row();
        getContentTable().add(rankLabel).expandX().pad(50,30,50,30).row();

        addCloseButton(this);

    }

    private String getRank(int num){
        String rank = String.valueOf(num);
        if(rank.charAt(rank.length()-1) == '1' && num != 11)
            return num + "ST";
        else if(rank.charAt(rank.length()-1) == '2' && num != 12)
            return num + "ND";
        else if(rank.charAt(rank.length()-1) == '3' && num != 13)
            return num + "RD";
        else
            return num + "TH";
    }

    @Override
    public void onWinnerDataLoaded(int requestCode, Map<String, Object> data) {
        String rank = getRank((Integer) data.get(WINNERS_COUNT) + 1);
        rankLabel.setText("YOU WERE THE " + rank + " TO FINISH THE GAME");
    }

    @Override
    public void onFetchSuccess(int requestCode, String tableName, ArrayList<Map<String, Object>> tableData) {

    }

    @Override
    public void onUpdateSuccess(int requestCode, String tableName) {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onConnectionFailed() {

    }

    @Override
    public void onResultFailed(int requestCode, String message) {

    }

    @Override
    public void onUserLoginSuccessful(int requestCode, Map<String, Object> tableData) {

    }

    @Override
    public void onDeleteSuccess(int requestCode, String message) {

    }

    @Override
    public void onLeaderBoardLoaded(int requestCode, Map<String, Float> leaderBoard) {

    }

    @Override
    public float getPrefHeight() {
        return 500;
    }

    @Override
    public float getPrefWidth() {
        return 720;
    }
}

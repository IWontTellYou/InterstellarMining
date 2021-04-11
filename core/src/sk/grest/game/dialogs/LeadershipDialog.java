package sk.grest.game.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import sk.grest.game.InterstellarMining;
import sk.grest.game.database.DatabaseConnection;
import sk.grest.game.database.DatabaseHandler;
import sk.grest.game.entities.Player;
import sk.grest.game.other.Score;

public class LeadershipDialog extends CustomDialog implements DatabaseConnection.ConnectorEvent {

    public LeadershipDialog(String title, Skin skin) {
        super("", skin);
        setBackground(InterstellarMining.back);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        handler.getLeaderBoard(this);



    }

    public void addScoreTable(ArrayList<Score> highScores){
        Table scoreBoard = new Table();

        float rankWidth = 50;
        float nameWidth = 200;
        float progressWidth = 200;

        Label rankLabel = new Label("#", getSkin());
        scoreBoard.add(rankLabel)
                .align(Align.center)
                .width(rankWidth)
                .pad(5,20,5,20);

        Label nameLabel = new Label("NAME", getSkin());
        scoreBoard.add(nameLabel)
                .align(Align.center)
                .width(nameWidth)
                .pad(5,20,5,20);

        Label progressLabel = new Label("PROGRESS (%)", getSkin());
        scoreBoard.add(progressLabel)
                .align(Align.center)
                .width(progressWidth)
                .pad(5,20,5,20)
                .row();

        int counter = 1;
        for (Score score : highScores) {

            Label rank = new Label(String.valueOf(counter), getSkin());
            scoreBoard.add(rank)
                    .align(Align.center)
                    .width(rankWidth)
                    .pad(5,20,5,20);

            Label name = new Label(score.getName(), getSkin());
            scoreBoard.add(name)
                    .align(Align.center)
                    .pad(5,20,5,20)
                    .width(nameWidth);

            Label progress = new Label(String.format("%.2f", score.getProgress()), getSkin());
            scoreBoard.add(progress)
                    .align(Align.center)
                    .width(progressWidth)
                    .pad(5,20,5,20)
                    .row();

            counter++;
        }

        scoreBoard.debug(Debug.cell);

        getContentTable().add(scoreBoard).expand().pad(50).row();
        addCloseButton(this);

    }

    @Override
    public void onLeaderBoardLoaded(int requestCode, Map<String, Float> leaderBoard) {

        ArrayList<Score> highScores = new ArrayList<>();

        for (String key : leaderBoard.keySet()) {
            Score score = new Score(key, leaderBoard.get(key));
            highScores.add(score);
        }

        Collections.sort(highScores);
        addScoreTable(highScores);

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

}

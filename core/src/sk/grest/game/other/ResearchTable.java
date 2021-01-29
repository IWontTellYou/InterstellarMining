package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.Research;

public class ResearchTable extends Table {

    private ArrayList<Table> columns;
    private ArrayList<Line> lines;
    private ArrayList<ResearchActor> researchActors;
    //private ArrayList<Research> researches;

    public ResearchTable(Skin skin /*ArrayList<Research> researches*/){
        super(skin);
        //this.researches = researches;
        this.columns = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.researchActors = new ArrayList<>();

        for (ArrayList<Integer> row : testList()) {
            Table newRow = new Table(getSkin());
            boolean first = true;
            for (int i : row) {
                ResearchActor actor = new ResearchActor(testList().indexOf(row), row.indexOf(i), i+"", skin);
                researchActors.add(actor);
                newRow.add(actor)
                        .width(ScreenConstants.DEFAULT_ACTOR_WIDTH)
                        .height(ScreenConstants.DEFAULT_ACTOR_HEIGHT)
                        .pad(50)
                        .row();
            }
            columns.add(newRow);
            add(newRow).pad(0,50,0,50);
        }
    }

    public static ArrayList<ArrayList<Integer>> testList(){
        ArrayList<ArrayList<Integer>> testList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < i+1; j++) {
                row.add(j);
            }
            testList.add(row);
        }
        return testList;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public ResearchActor getActor(int row, int column){
        for (ResearchActor actor : researchActors) {
            if(actor.getColumn() == column && actor.getRow() == row)
                return actor;
        }
        return null;
    }
}

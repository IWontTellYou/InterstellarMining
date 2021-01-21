package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class SelectionTable extends Table {

    private ArrayList<Row> rows;
    int currentRow;

    public SelectionTable() {
    }

    public SelectionTable(Skin skin) {
        super(skin);
    }

    {
        rows = new ArrayList<>();
        currentRow = 0;
    }

    public void addRow(Row row){
        this.add(row);
    }

    public Table getLastRow(){
        if(rows.size() > 0)
            return rows.get(rows.size()-1);
        else
            return null;
    }

    public Table getRow(int index){
        return rows.get(index);
    }



}

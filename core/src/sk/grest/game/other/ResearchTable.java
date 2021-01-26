package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

import sk.grest.game.entities.Research;

public class ResearchTable extends Table {

    private ArrayList<Table> columns;
    //private ArrayList<Research> researches;

    public ResearchTable(/*ArrayList<Research> researches*/){
        //this.researches = researches;
        this.columns = new ArrayList<>();


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

}

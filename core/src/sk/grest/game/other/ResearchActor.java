package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import sk.grest.game.entities.Research;

public class ResearchActor extends TextButton {

    //private Research research;
    private int column;
    private int row;

    public ResearchActor(int row, int column, /*Research research*/String title, Skin skin) {
        super(title, skin);
        this.row = row;
        this.column = column;
        //this.research = research;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}

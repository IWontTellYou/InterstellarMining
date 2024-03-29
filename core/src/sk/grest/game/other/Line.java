package sk.grest.game.other;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import sk.grest.game.constants.GameConstants;
import sk.grest.game.dialogs.research.ResearchActor;


public class Line {

    private sk.grest.game.dialogs.research.ResearchActor[] buttons;
    private Vector2[] lineVectors;

    Line(sk.grest.game.dialogs.research.ResearchActor start, sk.grest.game.dialogs.research.ResearchActor end){
        buttons = new ResearchActor[]{start, end};
        setLineVectors();
    }

    public void setLineVectors() {
        Vector2 start = new Vector2(buttons[0].getX(), buttons[0].getY());
        Vector2 end = new Vector2(buttons[1].getX(), buttons[1].getY());

        if(start.y == end.y) {
            lineVectors = new Vector2[]{start, end};
        }
        else{
            lineVectors = new Vector2[4];
            lineVectors[0] = start;
            lineVectors[1] = new Vector2((end.x-start.x)/2f, start.y);
            lineVectors[2] = new Vector2((end.x-start.x)/2f, end.y);
            lineVectors[3] = end;
        }
    }

    public Vector2 getButtonVector(Button button){
        return GameConstants.getStageLocation(button);
    }

    public void draw(ShapeRenderer renderer){
        //Gdx.app.log("line", lineVectors[0].x + " " + lineVectors[0].y  + " " +  lineVectors[1].x + " " +  lineVectors[1].y);
        if(lineVectors.length == 2){
            renderer.rectLine(lineVectors[0], lineVectors[1], 5);
        }else{
            renderer.rectLine(lineVectors[0], lineVectors[1], 5);
            renderer.rectLine(lineVectors[1], lineVectors[2], 5);
            renderer.rectLine(lineVectors[2], lineVectors[3], 5);
        }
    }

}

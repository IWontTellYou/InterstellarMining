package sk.grest.game.other;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import sk.grest.game.constants.GameConstants;
import sk.grest.game.entities.Research;


public class Line {

    private ResearchActor[] buttons;
    private Vector2[] lineVectors;

    Line(ResearchActor start, ResearchActor end){
        buttons = new ResearchActor[]{start, end};
        setLineVectors();
    }

    public void setLineVectors() {
        Vector2 start = GameConstants.getStageLocation(buttons[0]);
        Vector2 end = GameConstants.getStageLocation(buttons[1]);

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
        if(lineVectors.length == 2){
            renderer.line(lineVectors[0], lineVectors[1]);
        }else{
            renderer.line(lineVectors[0], lineVectors[1]);
            renderer.line(lineVectors[1], lineVectors[2]);
            renderer.line(lineVectors[2], lineVectors[3]);
        }
    }

}

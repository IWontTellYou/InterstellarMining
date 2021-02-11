package sk.grest.game.dialogs.research;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;

import java.util.ArrayList;
import java.util.Collections;

import sk.grest.game.entities.Research;
import sk.grest.game.other.Line;

public class ResearchTable extends Table {

    private TooltipManager manager;
    private ArrayList<Table> columns;
    private ArrayList<Line> lines;
    private ArrayList<ResearchActor> researchActors;
    //private ArrayList<Research> researches;

    public ResearchTable(Skin skin, ArrayList<Research> researches){
        super(skin);

        manager = new TooltipManager();
        manager.instant();

        //this.researches = researches;
        //this.columns = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.researchActors = new ArrayList<>();

        for (Research r : researches) {
            ResearchActor actor = new ResearchActor(r, skin);
            actor.addListener(new TextTooltip(actor.getResearch().getInfo(), manager, skin));
            researchActors.add(actor);
        }

        Collections.sort(researchActors);

        StringBuilder s = new StringBuilder();
        for (ResearchActor a : researchActors) {
            s.append(a.getResearch().getColumn());
        }

        Table row = new Table();
        int index = -1;
        for (ResearchActor actor : researchActors) {
            if (index != -1 && researchActors.get(index).getResearch().getColumn() < actor.getResearch().getColumn()){
                add(row).pad(50,30,50,50).center();
                row = new Table();
                row.add(actor).pad(50,30,50,50).width(200).height(100).center().row();
            }else {
                row.add(actor).pad(50,30,50,50).width(200).height(100).center().row();
            }
            index++;
        }
        add(row).pad(50,30,50,50).center();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public ResearchActor getActor(Research r){
        for (ResearchActor actor : researchActors) {
            if(actor.getResearch().getId() == r.getId())
                return actor;
        }
        return null;
    }

    public void update(ShapeRenderer renderer){
        for (Line l : lines) {
            l.draw(renderer);
        }
    }

}

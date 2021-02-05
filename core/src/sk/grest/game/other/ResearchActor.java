package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import sk.grest.game.entities.Research;

public class ResearchActor extends TextButton implements Comparable<ResearchActor> {

    private Research research;

    public ResearchActor(Research research, Skin skin) {
        super(research.getName(), skin);
        this.research = research;
    }

    public Research getResearch() {
        return research;
    }
    public void setResearch(Research research) {
        this.research = research;
    }

    @Override
    public int compareTo(ResearchActor researchActor) {
        if(research.getColumn() == researchActor.getResearch().getColumn())
            return 0;
        else if(research.getColumn() > researchActor.getResearch().getColumn())
            return 1;
        else
            return -1;
    }
}

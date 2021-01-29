package sk.grest.game.dialogs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import sk.grest.game.entities.Research;
import sk.grest.game.other.Line;
import sk.grest.game.other.ResearchTable;

public class ResearchDialog extends CustomDialog {

    private ResearchTable table;

    public ResearchDialog(String title, Skin skin) {
        super(title, skin);
        table = new ResearchTable(skin);

        ScrollPane pane = new ScrollPane(table);
        pane.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        while (isDragging()){
                            for (Line l : table.getLines()) {
                                l.setLineVectors();
                            }
                        }
                        this.interrupt();
                    }
                };
                thread.start();
            }
        });

        getContentTable().add(pane);
        addCloseButton(this);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void render(ShapeRenderer renderer){
        for (Line line : table.getLines()) {
            line.draw(renderer);
        }
    }
}

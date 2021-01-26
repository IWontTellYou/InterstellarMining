package sk.grest.game.dialogs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import sk.grest.game.entities.Research;
import sk.grest.game.other.ResearchTable;

public class ResearchDialog extends CustomDialog {

    private ResearchTable table;

    public ResearchDialog(String title, Skin skin) {
        super(title, skin);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void render(SpriteBatch batch){

    }
}

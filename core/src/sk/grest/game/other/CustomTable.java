package sk.grest.game.other;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CustomTable extends Table {

    public CustomTable() {
    }

    public CustomTable(Skin skin) {
        super(skin);
    }

    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if (getBackground() == null) return;
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        getBackground().draw(batch, x, y, getWidth(), getHeight());
    }

    protected void drawFrameBackground(Batch batch, float parentAlpha, float x, float y) {
        if (getBackground() == null) return;
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        getBackground().draw(batch, x, y, getWidth(), getHeight());

    }
}

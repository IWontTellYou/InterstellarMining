package sk.grest.game.other;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import sk.grest.game.defaults.ScreenDeafults;

public class Row extends Table {

    private Array<Actor> elements;

    public Row(Array<Actor> elements) {
        this.elements = elements;
    }
    public Row(Array<Actor> elements, Skin skin) {
        super(skin);
        this.elements = elements;
    }

    @Override
    public void setColor(Color color) {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));
    }
}

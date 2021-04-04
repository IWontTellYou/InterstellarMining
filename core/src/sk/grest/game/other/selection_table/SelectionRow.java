package sk.grest.game.other.selection_table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import sk.grest.game.listeners.ItemSelectedListener;

public class SelectionRow<E> extends Table {

    private ArrayList<Actor> elements;
    private E item;

    private Pixmap bgPixmap;
    private Color unselectedColor;
    private Color selectedColor;

    boolean selected;
    ItemSelectedListener<E> listener;

    public SelectionRow() {
        this.elements = new ArrayList<>();
        this.selected = false;
    }

    public SelectionRow(Skin skin) {
        super(skin);
        this.elements = new ArrayList<>();
        this.selected = false;
    }

    {

        final SelectionRow<E> instance = this;
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSelected(selected);
                listener.onSelectedItemClicked(instance);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                setColor(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!selected)
                    setColor(false);
            }
        });
    }

    public void addListener(ItemSelectedListener<E> listener){
        this.listener = listener;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public E getItem(){
        return item;
    }

    public void setColors(Color unselectedColor, Color selectedColor) {
        this.unselectedColor = unselectedColor;
        this.selectedColor = selectedColor;
        bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        setColor(false);
    }

    public void setColor(boolean selected){
        bgPixmap.setColor((selected) ? this.selectedColor : this.unselectedColor);
        bgPixmap.fill();
        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));
    }

    public void setSelected(boolean selected){
        setColor(selected);
        this.selected = selected;
    }
    public boolean isSelected() {
        return selected;
    }

    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        elements.add(actor);
        return super.add(actor);
    }

    public ArrayList<Actor> getElements() {
        return elements;
    }

    public Actor getElement(int index){
        return elements.get(index);
    }
}

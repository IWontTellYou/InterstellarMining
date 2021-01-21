package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.listeners.ItemSelectedListener;

public class SelectionTable<E> extends Table implements ItemSelectedListener<E> {

    private ArrayList<Row<E>> rows;
    private int currentRow;
    private ItemOpenedListener<E> listener;

    private E itemSelected;

    public SelectionTable(ItemOpenedListener<E> listener) {
        this.listener = listener;
    }

    public SelectionTable(ItemOpenedListener<E> listener, Skin skin) {
        super(skin);
        this.listener = listener;
    }

    {
        rows = new ArrayList<>();
        currentRow = 0;
    }

    public Table getLastRow(){
        if(rows.size() > 0)
            return rows.get(rows.size()-1);
        else
            return null;
    }


    public Cell<Row<E>> addRow(Row<E> row){
        row.addListener(this);
        rows.add(row);
        return add(row);
    }

    public Row<E> getRow(E item){
        for (Row<E> row : rows) {
            if(row.getItem() == item)
                return row;
        }
        return null;
    }

    public E getItemSelected() {
        return itemSelected;
    }

    @Override
    public void onSelectedItemClicked(Row<E> r) {
        for (Row<E> row : rows) {
            row.setSelected(false);
        }
        r.setSelected(true);
        itemSelected = r.getItem();
        listener.onItemOpenedListener(r.getItem());
    }

}

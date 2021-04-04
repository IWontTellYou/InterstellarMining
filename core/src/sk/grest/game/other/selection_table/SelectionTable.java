package sk.grest.game.other.selection_table;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.listeners.ItemSelectedListener;

public class SelectionTable<E> extends Table implements ItemSelectedListener<E> {

    private ArrayList<SelectionRow<E>> rows;
    private int currentRow;
    private ItemOpenedListener<E> listener;

    private E itemSelected;

    private boolean defaultItem;

    public SelectionTable(ItemOpenedListener<E> listener, boolean defaultItem) {
        this.listener = listener;
        this.defaultItem = defaultItem;
    }

    public SelectionTable(ItemOpenedListener<E> listener, Skin skin, boolean defaultItem) {
        super(skin);
        this.listener = listener;
        this.defaultItem = defaultItem;
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

    public Cell<SelectionRow<E>> addRow(SelectionRow<E> row){
        row.addListener(this);
        rows.add(row);
        if(defaultItem && rows.size() == 1)
            onSelectedItemClicked(row);
        return add(row);
    }

    public SelectionRow<E> getRow(E item){
        for (SelectionRow<E> row : rows) {
            if(row.getItem() == item)
                return row;
        }
        return null;
    }

    public E getItemSelected() {
        return itemSelected;
    }

    @Override
    public void onSelectedItemClicked(SelectionRow<E> r) {
        for (SelectionRow<E> row : rows) {
            row.setSelected(false);
        }
        r.setSelected(true);
        itemSelected = r.getItem();
        listener.onItemOpenedListener(r.getItem());
    }

}

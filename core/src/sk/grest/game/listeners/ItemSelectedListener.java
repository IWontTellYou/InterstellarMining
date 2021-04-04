package sk.grest.game.listeners;

import sk.grest.game.other.selection_table.SelectionRow;

public interface ItemSelectedListener<E> {

    void onSelectedItemClicked(SelectionRow<E> r);
}

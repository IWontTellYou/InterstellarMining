package sk.grest.game.listeners;

import sk.grest.game.other.SelectionRow;

public interface ItemSelectedListener<E> {

    void onSelectedItemClicked(SelectionRow<E> r);
}

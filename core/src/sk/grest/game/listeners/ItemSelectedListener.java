package sk.grest.game.listeners;

import com.badlogic.gdx.scenes.scene2d.Stage;

import sk.grest.game.other.Row;

public interface ItemSelectedListener<E> {

    void onSelectedItemClicked(Row<E> r);
    void onUnselectedItemClicked();
}

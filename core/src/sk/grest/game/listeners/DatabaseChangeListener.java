package sk.grest.game.listeners;

import sk.grest.game.entities.Ship;

public interface DatabaseChangeListener {

    void onShipDataChanged(Ship ship);
}

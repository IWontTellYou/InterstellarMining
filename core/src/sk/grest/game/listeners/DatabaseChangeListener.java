package sk.grest.game.listeners;

import sk.grest.game.entities.ship.Ship;

public interface DatabaseChangeListener {

    void onShipDataChanged(Ship ship);
}

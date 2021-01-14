package sk.grest.game.listeners;

import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Attributes;
import sk.grest.game.entities.ship.Ship;

public interface DatabaseChangeListener {

    void onShipDataChanged(Ship ship);
    void onAttributesChanged(Ship ship, Attributes attributes);
    void onShipArrivedAtBase(Ship ship, Resource resource);
}

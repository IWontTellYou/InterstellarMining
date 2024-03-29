package sk.grest.game.listeners;

import sk.grest.game.entities.resource.Resource;
import sk.grest.game.entities.ship.Ship;

public interface TravelListener {

    void onMiningFinished(Resource r);
    void onShipArrivedAtHome(Resource r);
    void onShipDataChanged(Ship ship);

}

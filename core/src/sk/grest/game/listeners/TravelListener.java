package sk.grest.game.listeners;

import sk.grest.game.entities.Resource;

public interface TravelListener {

    void onMiningFinished(Resource r);
    void onShipArrivedAtHome(Resource r);

}

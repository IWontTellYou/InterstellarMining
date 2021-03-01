package sk.grest.game.listeners;

import sk.grest.game.entities.planet.Planet;

public interface OnStatsChangedListener {
    void onPlanetChanged(Planet planet);
}

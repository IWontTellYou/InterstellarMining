package sk.grest.game.listeners;

import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.planet.PlanetSystem;

public interface OnStatsChangedListener {

    void onPlanetChanged(Planet planet);

    void onPlanetSystemChanged(PlanetSystem system);

}

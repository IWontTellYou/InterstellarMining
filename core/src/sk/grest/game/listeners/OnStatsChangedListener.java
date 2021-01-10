package sk.grest.game.listeners;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import sk.grest.game.entities.Planet;
import sk.grest.game.entities.PlanetSystem;

public interface OnStatsChangedListener {

    void onPlanetChanged(Planet planet);

    void onPlanetSystemChanged(PlanetSystem system);

}

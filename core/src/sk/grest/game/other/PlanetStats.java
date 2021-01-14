package sk.grest.game.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.PlanetSystem;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.OnStatsChangedListener;
import sk.grest.game.entities.Planet;

public class PlanetStats {

    private Table planetStats;

    private Planet currentPlanet;
    private String systemName;

    private Label nameLabel;
    private Label[] resourcesLabel;

    private ArrayList<Planet> planetSystem;
    private int currentPlanetIndex;

    private InterstellarMining game;
    private OnStatsChangedListener listener;

    public PlanetStats(InterstellarMining game, OnStatsChangedListener listener, PlanetSystem system, int currentPlanetIndex) {

        this.game = game;
        this.listener = listener;

        this.currentPlanetIndex = currentPlanetIndex;
        this.planetSystem = system.getPlanets();

        this.systemName = system.getName();

        this.nameLabel = new Label("", game.getUISkin());

        game.getUISkin().getFont("default-font").getData();

        planetStats = new Table();
        resetTable();

        this.currentPlanet = planetSystem.get(currentPlanetIndex);

    }
    public PlanetStats(InterstellarMining game, OnStatsChangedListener listener, PlanetSystem system) {
        this(game, listener, system, 0);
    }

    public void changePlanetSystem(PlanetSystem system){
        changePlanetSystem(system, 0);
    }
    public void changePlanetSystem(PlanetSystem system, int currentPlanetIndex){
        this.planetSystem = system.getPlanets();
        this.systemName = system.getName();
        this.currentPlanetIndex = currentPlanetIndex;
        resetTable();
        currentPlanet = planetSystem.get(currentPlanetIndex);
    }

    public void resetTable(){

        float cellWidth = Gdx.graphics.getWidth()/5f;
        float cellHeight = Gdx.graphics.getHeight()/20f;

        planetStats.clearChildren();

        this.nameLabel.setText(planetSystem.get(currentPlanetIndex).getName());


        Label nameText = new Label("Name:", game.getUISkin());
        planetStats.add(nameText)
                .width(cellWidth).height(cellHeight);
        planetStats.add(nameLabel)
                .width(cellWidth).height(cellHeight).row();

        Label resourcesText = new Label("Resources:", game.getUISkin());
        planetStats.add(resourcesText)
                .width(cellWidth).height(cellHeight);

        ArrayList<Resource> resources = planetSystem.get(currentPlanetIndex).getResources();
        this.resourcesLabel = new Label[resources.size()];

        resourcesLabel[0] = new Label(resources.get(0).getName(), game.getUISkin());
        planetStats.add(resourcesLabel[0])
                .width(cellWidth).height(cellHeight).
                row();

        for (int i = 1; i < resourcesLabel.length; i++) {
            resourcesLabel[i] = new Label(resources.get(i).getName(), game.getUISkin());
            resourcesLabel[i].setAlignment(Align.center);
            planetStats.add(resourcesLabel[i])
                    .width(cellWidth)
                    .height(cellHeight)
                    .align(Align.right)
                    .colspan(2)
                    .row();
        }

        planetStats.setBackground(InterstellarMining.back);
        planetStats.setSize(Gdx.graphics.getWidth()/3f, Gdx.graphics.getHeight()/5f);

    }

    public Table getTable(){
        return planetStats;
    }

    public String getSystemName(){
        return systemName;
    }

    public void nextPlanet(){
        if(currentPlanetIndex+1 < planetSystem.size()){
            currentPlanetIndex++;
            currentPlanet = planetSystem.get(currentPlanetIndex);
            listener.onPlanetChanged(planetSystem.get(currentPlanetIndex));
            resetTable();
        }
    }
    public void previousPlanet(){
        if(currentPlanetIndex-1 >= 0){
            currentPlanetIndex--;
            currentPlanet = planetSystem.get(currentPlanetIndex);
            listener.onPlanetChanged(planetSystem.get(currentPlanetIndex));
            resetTable();
        }
    }

    public String getName() {
        return nameLabel.getText().toString();
    }

    public Planet getCurrentPlanet() {
        return currentPlanet;
    }
}

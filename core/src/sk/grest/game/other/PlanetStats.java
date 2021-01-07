package sk.grest.game.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.listeners.OnPlanetChangedListener;
import sk.grest.game.entities.Planet;

public class PlanetStats {

    private Table planetStats;
    private String planetImage;

    private Label nameLabel;
    private Label resourcesLabel;

    private ArrayList<Planet> planetSystem;
    private int currentPlanetIndex;

    private OnPlanetChangedListener listener;

    public PlanetStats(InterstellarMining game, OnPlanetChangedListener listener, ArrayList<Planet> planetSystem, int currentPlanetIndex) {

        this.currentPlanetIndex = currentPlanetIndex;
        this.planetSystem = planetSystem;

        this.nameLabel = new Label(planetSystem.get(currentPlanetIndex).getName(), game.getUISkin());
        this.resourcesLabel = new Label(planetSystem.get(currentPlanetIndex).toString(), game.getUISkin());
        this.planetImage = planetSystem.get(currentPlanetIndex).getAssetId();

        game.getUISkin().getFont("default-font").getData().scale(0.25f);

        this.planetStats = new Table();

        float cellWidth = Gdx.graphics.getWidth()/5f;
        float cellHeight = Gdx.graphics.getHeight()/20f;

        planetStats.add(new Label("Name:", game.getUISkin()))
                .width(cellWidth).height(cellHeight);
        planetStats.add(nameLabel)
                .width(cellWidth).height(cellHeight).row();

        planetStats.add(new Label("Resources:", game.getUISkin()))
                .width(cellWidth).height(cellHeight);
        planetStats.add(resourcesLabel)
                .width(cellWidth).height(cellHeight).row();

    }
    public PlanetStats(InterstellarMining game, OnPlanetChangedListener listener, ArrayList<Planet> planetSystem) {
        this(game, listener, planetSystem, 0);
    }

    public void changePlanetSystem(ArrayList<Planet> planetSystem){
        changePlanetSystem(planetSystem, 0);
    }
    public void changePlanetSystem(ArrayList<Planet> planetSystem, int currentPlanetIndex){
        this.currentPlanetIndex = currentPlanetIndex;
        nameLabel.setText(planetSystem.get(currentPlanetIndex).getName());
        resourcesLabel.setText(planetSystem.get(currentPlanetIndex).getResources().toString());
        planetImage = planetSystem.get(currentPlanetIndex).getAssetId();
    }

    public void nextPlanet(){
        if(currentPlanetIndex+1 < planetSystem.size()){
            currentPlanetIndex++;
            nameLabel.setText(planetSystem.get(currentPlanetIndex).getName());
            resourcesLabel.setText(planetSystem.get(currentPlanetIndex).getResources().toString());
            planetImage = planetSystem.get(currentPlanetIndex).getAssetId();
            listener.onPlanetChanged(planetSystem.get(currentPlanetIndex));

            Gdx.app.log("PLANET", planetSystem.get(currentPlanetIndex).getName());
        }
    }
    public void previousPlanet(){
        if(currentPlanetIndex-1 >= 0){
            currentPlanetIndex--;
            nameLabel.setText(planetSystem.get(currentPlanetIndex).getName());
            resourcesLabel.setText(planetSystem.get(currentPlanetIndex).getResources().toString());
            planetImage = planetSystem.get(currentPlanetIndex).getAssetId();
            listener.onPlanetChanged(planetSystem.get(currentPlanetIndex));

            Gdx.app.log("PLANET", planetSystem.get(currentPlanetIndex).getName());
        }
    }

    public String getName() {
        return nameLabel.getText().toString();
    }
    public String getResources() {
        return resourcesLabel.getText().toString();
    }
    public Table getTable(){
        return planetStats;
    };
    public String getPlanetImage() {
        return planetImage;
    }
}

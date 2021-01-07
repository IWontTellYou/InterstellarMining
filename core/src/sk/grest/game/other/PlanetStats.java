package sk.grest.game.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.Planet;

public class PlanetStats {

    private Table planetStats;

    private Label nameLabel;
    private Label massLabel;
    private Label resourcesLabel;

    public PlanetStats(InterstellarMining game, Planet planet) {

        this.nameLabel = new Label(planet.getName(), game.getUISkin());
        this.massLabel = new Label(planet.getAssetId(), game.getUISkin());
        this.resourcesLabel = new Label(planet.toString(), game.getUISkin());

        game.getUISkin().getFont("default-font").getData().scale(0.25f);

        this.planetStats = new Table();

        float cellWidth = Gdx.graphics.getWidth()/5f;
        float cellHeight = Gdx.graphics.getHeight()/20f;

        planetStats.add(new Label("Name:", game.getUISkin()))
                .width(cellWidth).height(cellHeight);
        planetStats.add(nameLabel)
                .width(cellWidth).height(cellHeight).row();

        planetStats.add(new Label("Mass:", game.getUISkin()))
                .width(cellWidth).height(cellHeight);
        planetStats.add(massLabel)
                .width(cellWidth).height(cellHeight).row();

        planetStats.add(new Label("Resources:", game.getUISkin()))
                .width(cellWidth).height(cellHeight);
        planetStats.add(resourcesLabel)
                .width(cellWidth).height(cellHeight).row();

    }

    public String getName() {
        return nameLabel.getText().toString();
    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }

    public String getSize() {
        return massLabel.getText().toString();
    }

    public void setSize(String size) {
        this.massLabel.setText(size);
    }

    public String getResources() {
        return resourcesLabel.getText().toString();
    }

    public void setResources(String resources) {
        this.resourcesLabel.setText(resources);
    }

    public Table getTable(){
        return planetStats;
    };
}

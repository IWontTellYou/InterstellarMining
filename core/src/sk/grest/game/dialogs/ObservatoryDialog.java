package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.controls.Button;
import sk.grest.game.dialogs.upgrade.UpgradeResourceView;
import sk.grest.game.entities.Observatory;
import sk.grest.game.entities.planet.Planet;
import sk.grest.game.entities.upgrade.UpgradeRecipe;

import static sk.grest.game.entities.Observatory.OBSERVATORY_ACCURACY;
import static sk.grest.game.entities.Observatory.OBSERVATORY_SPEED;

public class ObservatoryDialog extends CustomDialog {

    private InterstellarMining game;
    private Observatory observatory;

    private Label timeLeft;
    private Image planetImage;

    public ObservatoryDialog(String title, Skin skin, final InterstellarMining game) {
        super(title, skin);
        this.game = game;

        setBackground(InterstellarMining.back);

        planetImage = new Image(game.getSpriteSkin(), "earth");

        UpgradeRecipe[][] recipes = new UpgradeRecipe[2][];
        recipes[OBSERVATORY_SPEED] = game.getUpgradeRecipesByType(UpgradeRecipe.OBSERVATORY_SPEED);
        recipes[OBSERVATORY_ACCURACY] = game.getUpgradeRecipesByType(UpgradeRecipe.OBSERVATORY_ACCURACY);
        observatory = game.getPlayer().getObservatory();

        if(observatory.getTimeLeftMillis() > 0){
            timeLeft = new Label(observatory.getTimeLeft(), skin);
        }else {
            resetObservatory();
        }
        timeLeft.setAlignment(Align.center);

        final Label speedLabel = new Label("Search speed - Searching time is reduced by " + (int)((1-observatory.getSpeed())*100) + " %", skin);
        speedLabel.setAlignment(Align.center);
        UpgradeResourceView speedView = new UpgradeResourceView(game, recipes[OBSERVATORY_SPEED], observatory, game.getPlayer(), OBSERVATORY_SPEED);
        final Label accuracyLabel = new Label("Accuracy - Chance of not finding planet is reduced by " + (int)((1-observatory.getAccuracy())*100) + " %", skin);
        accuracyLabel.setAlignment(Align.center);
        UpgradeResourceView accuracyView = new UpgradeResourceView(game, recipes[OBSERVATORY_ACCURACY], observatory, game.getPlayer(), OBSERVATORY_ACCURACY);

        if(observatory.getLevel(OBSERVATORY_SPEED) < observatory.getMaxLevel(OBSERVATORY_SPEED)){
            speedView.getUpgradeButton().getButton().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    speedLabel.setText("Search speed - Searching time is reduced by " + (int)((1-observatory.getSpeed())*100) + " %");
                }
            });
        }

        if(observatory.getLevel(OBSERVATORY_ACCURACY) < observatory.getMaxLevel(OBSERVATORY_ACCURACY)) {
            accuracyView.getUpgradeButton().getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    accuracyLabel.setText("Accuracy - Chance of not finding planet is reduced by " + (int) ((1 - observatory.getAccuracy()) * 100) + " %");
                }
            });
        }

        Button searchButton = new Button(game.getSpriteSkin(), "search", null);
        searchButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!observatory.isSearching()){
                    observatory.setSearch();
                    game.getHandler().updatePlayerObservatory(game.getPlayer().getID(), observatory, game);
                }
            }
        });

        Table upgradeBars = new Table(skin);
        upgradeBars.add(speedLabel).expandX().row();
        upgradeBars.add(speedView).expandX().row();
        upgradeBars.add(accuracyLabel).expandX().row();
        upgradeBars.add(accuracyView).expandX().row();

        Table searchBar = new Table(skin);
        searchBar.add(searchButton.getButton())
                .size(ScreenConstants.DEFAULT_BUTTON_SIZE);

        searchBar.add(timeLeft)
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH);


        getContentTable().add(planetImage)
                .size(ScreenConstants.DEFAULT_DIALOG_HEIGHT/2f);

        getContentTable().add(upgradeBars)
                .expandX()
                .row();

        getContentTable().add(searchBar)
                .width(ScreenConstants.DEFAULT_DIALOG_HEIGHT/2f);

        addCloseButton(this);

    }

    public void resetObservatory(){
        observatory.reset();

        if(observatory.getPlanet() != null){
            observatory.setPlanet(null);
            planetImage.setDrawable(game.getSpriteSkin(), "moon");
        }else {
            /*
            Random rn = new Random();
            ArrayList<Planet> planetsNotFound = game.getPlanetsNotFound();
            Planet p = planetsNotFound.get(rn.nextInt(planetsNotFound.size()));
            */

            Planet p = game.getPlanets().get(8);
            observatory.setPlanet(p);
            if(!p.getAssetId().equals(""))
                planetImage.setDrawable(game.getSpriteSkin(), p.getAssetId());
        }
    }

    public void update(float delta) {
        if (observatory.isSearching() || observatory.getPlanet() != null) {
            if (observatory.getTimeLeftMillis() < 0) {
                resetObservatory();
            } else {
                timeLeft.setText(observatory.getTimeLeft());
            }
        }
    }
}

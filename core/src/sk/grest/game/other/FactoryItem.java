package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import sk.grest.game.InterstellarMining;

public class FactoryItem extends Table {

    private String time;

    public FactoryItem(Skin skin, String drawableName, InterstellarMining game) {
        super(skin);
        Image image = new Image(game.getSpriteSkin(), drawableName);
        Label time = new Label("1:00:00", skin);
        Label count = new Label("1", skin);

        add(time).center().top();
        add(image).center().size(50);
        add(count).bottom().right();
    }

    public void update(float delta){

    }

}

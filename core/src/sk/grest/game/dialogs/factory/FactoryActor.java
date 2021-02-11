package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import sk.grest.game.InterstellarMining;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.listeners.FactoryItemFinishedListener;

public class FactoryActor extends Table {

    private Label time;
    private Label count;
    private FactoryItem item;
    private FactoryItemFinishedListener listener;

    Skin spriteSkin;

    public FactoryActor(Skin skin, Skin spriteSkin, FactoryItemFinishedListener listener){
        super(skin);
        this.spriteSkin = spriteSkin;
        this.listener = listener;
    }

    public FactoryActor(Skin skin, Skin spriteSkin, FactoryItem item, FactoryItemFinishedListener listener) {
        super(skin);
        this.spriteSkin = spriteSkin;
        this.listener = listener;
        setItem(item);
    }

    public void setItem(FactoryItem item){
        if(item != null){
            this.item = item;
            Image image = new Image(spriteSkin, item.getResource().getAssetName());
            String s = item.getTimeLeft();

            time = new Label((s != null) ? s : "00:00:00", getSkin());
            count = new Label(String.valueOf(item.getCount()), getSkin());

            add(time).center().top();
            add(image).center().size(50);
            add(count).bottom().right();

        }else{
            this.item = null;
            clear();
        }
    }

    public void update(float delta) {
        if (item != null) {
            String s = item.getTimeLeft();
            if (s != null)
                time.setText(s);
            else
                listener.onFactoryItemFinished(this);
        }
    }

    public FactoryItem getItem() {
        return item;
    }
}

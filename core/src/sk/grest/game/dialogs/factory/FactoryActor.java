package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.resource.FactoryItem;
import sk.grest.game.listeners.FactoryItemFinishedListener;

import static sk.grest.game.constants.ScreenConstants.DEFAULT_PADDING;

public class FactoryActor extends Table {

    private Label time;
    private Label count;
    private FactoryItem item;
    private FactoryItemFinishedListener listener;

    private Skin spriteSkin;

    public FactoryActor(Skin skin, Skin spriteSkin, FactoryItemFinishedListener listener){
        super(skin);
        this.spriteSkin = spriteSkin;
        this.listener = listener;
        setItem(null);
    }

    public FactoryActor(Skin skin, Skin spriteSkin, FactoryItem item, FactoryItemFinishedListener listener) {
        super(skin);
        this.spriteSkin = spriteSkin;
        this.listener = listener;
        setItem(item);
    }

    public void setItem(FactoryItem item){
        clearChildren();
        setBackground(ScreenConstants.getBackground(ScreenConstants.DARK_GRAY));

        if(item != null){
            this.item = item;

            Image image;
            if(item.getResource().getAssetName().equals(""))
                image = new Image(spriteSkin, "iron_ingot");
            else
                image = new Image(spriteSkin, item.getResource().getAssetName());

            time = new Label(item.getDuration(), getSkin());
            time.setAlignment(Align.center);
            count = new Label(String.valueOf(item.getCount()), getSkin());
            time.setAlignment(Align.right);

            add(time).center().top().row();
            add(image).center().size(FactoryQueue.DEFAULT_ITEM_SIZE/2f).pad(DEFAULT_PADDING).row();
            add(count).bottom().right().row();

        }else{
            this.item = null;
            clearChildren();
        }
    }

    public void update(float delta) {

        if (item != null) {
            String s = item.getTimeLeft();
            if (s != null)
                time.setText(s);
            else
                listener.onFactoryItemFinished(item);
        }
    }

    public FactoryItem getItem() {
        return item;
    }
}

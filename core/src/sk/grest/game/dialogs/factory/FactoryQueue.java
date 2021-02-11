package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.listeners.FactoryItemFinishedListener;
import sk.grest.game.entities.resource.FactoryItem;

public class FactoryQueue extends Table implements FactoryItemFinishedListener {

    private static final int MAX_ITEMS = 5;

    private ArrayList<FactoryActor> list;
    private InterstellarMining game;

    public FactoryQueue(Skin skin, Skin spriteSkin, InterstellarMining game){
        super(skin);
        this.game = game;
        list = new ArrayList<>();
        for (int i = 0; i < MAX_ITEMS; i++) {
            FactoryActor actor = new FactoryActor(skin, spriteSkin, this);
            list.add(actor);
            add(actor).size(100,100);
        }
    }

    public void addItem(FactoryItem item){
        if(getUsedSlotsCount() < MAX_ITEMS){
            for (FactoryActor a : list) {
                if(a.getItem() == null){
                    a.setItem(item);
                }
            }
        }
    }

    public void removeFirst(){
        FactoryItem item = null;
        for (int i = getUsedSlotsCount(); i > 0; i--) {
            if(item == null){
                item = list.get(i).getItem();
                list.get(i).setItem(null);
            }else{
                FactoryItem currentItem = list.get(i).getItem();
                list.get(i).setItem(item);
                item = currentItem;
            }
        }
    }

    public int getUsedSlotsCount(){
        int count = 0;
        for (FactoryActor a : list) {
            if(a.getItem() != null)
                count++;
        }
        return count;
    }

    @Override
    public void onFactoryItemFinished(FactoryActor item) {
        removeFirst();
    }

    public void update(float delta){
        for (FactoryActor item : list) {
            item.update(delta);
        }
    }

}

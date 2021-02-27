package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.FactoryItemFinishedListener;
import sk.grest.game.entities.resource.FactoryItem;

public class FactoryQueue extends Table implements FactoryItemFinishedListener {

    public static final int DEFAULT_ITEM_SIZE = 125;
    public static final int MAX_ITEMS = 5;

    private ArrayList<FactoryActor> list;
    private InterstellarMining game;

    private Skin spriteSkin;

    public FactoryQueue(Skin skin, Skin spriteSkin, ArrayList<FactoryItem> items, InterstellarMining game){
        super(skin);
        this.game = game;
        this.spriteSkin = spriteSkin;
        list = new ArrayList<>();
        for (int i = 0; i < MAX_ITEMS; i++) {
            FactoryActor actor = getEmptyActor();
            if(i < items.size())
                actor.setItem(items.get(i));
            list.add(actor);
            add(actor).size(DEFAULT_ITEM_SIZE).pad(ScreenConstants.DEFAULT_PADDING);
        }
    }

    public void addItem(FactoryItem item){

        int time = 0;
        for (FactoryActor f : list) {
            if(f.getItem() != null) {
                if (f.getItem() == list.get(0).getItem()) {
                    time += f.getItem().getTimeLeftMillis();
                } else {
                    time += f.getItem().getDurationMillis();
                }
            }
        }

        if(getUsedSlotsCount() < MAX_ITEMS){
            for (FactoryActor a : list) {
                if(a.getItem() == null){

                    if(a == list.get(0))
                        item.setStartTime(System.currentTimeMillis());
                    else
                        item.setStartTime(list.get(0).getItem().getStartTime() + time);

                    Player player = game.getPlayer();
                    for (Resource r : item.getItemsNecessary()) {
                        player.getResource(r.getID()).subtractAmount(r.getAmount()*item.getCount());
                        game.getHandler().updatePlayerResourceTable(r.getID());
                    }
                    player.addToQueue(item);

                    game.getHandler().addPlayerFactoryRow(item.getResource().getID(), item.getStartTime(), item.getCount());

                    a.setItem(item);
                    break;
                }
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
    public void onFactoryItemFinished(FactoryItem item) {

        for (int i = 0; i < list.size()-1; i++) {
            list.get(i).setItem(list.get(i+1).getItem());
        }
        list.get(list.size()-1).setItem(null);

        Player player = game.getPlayer();
        player.getResource(item.getResource().getID()).addAmount(item.getCount());
        player.getQueue().remove(item);
        game.getHandler().updatePlayerResourceTable(item.getResource().getID());
        game.getHandler().removePlayerFactoryRow(item.getResource().getID(), item.getStartTime(), item.getCount());


    }

    public void update(float delta){
        if(list.size() > 0)
            list.get(0).update(delta);
    }

    private FactoryActor getEmptyActor(){
        return new FactoryActor(getSkin(), spriteSkin, this);
    }

}

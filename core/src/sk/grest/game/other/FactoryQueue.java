package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class FactoryQueue extends Table {

    private static final int MAX_ITEMS = 5;

    private ArrayList<FactoryItem> list;

    public FactoryQueue(Skin skin){
        super(skin);
        list = new ArrayList<>();
    }

    public void addItem(FactoryItem item){
        if(list.size() < MAX_ITEMS){
            list.add(item);
        }
        add(item);
    }

    public void removeItem(FactoryItem item){
        list.remove(item);
    }

}

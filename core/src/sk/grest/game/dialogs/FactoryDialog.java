package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import sk.grest.game.other.FactoryQueue;

public class FactoryDialog extends CustomDialog{

    private FactoryQueue queue;

    public FactoryDialog(String title, Skin skin) {
        super(title, skin);
        queue = new FactoryQueue(skin);

        add(queue);

    }



}

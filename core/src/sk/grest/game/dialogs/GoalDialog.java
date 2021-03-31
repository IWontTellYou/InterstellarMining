package sk.grest.game.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;

import sk.grest.game.InterstellarMining;
import sk.grest.game.other.TooltipBuilder;

public class GoalDialog extends CustomDialog {

    public GoalDialog(String title, Skin skin, TooltipBuilder builder, TooltipManager manager) {
        super(title, skin);

        setBackground(InterstellarMining.back);

        

    }

}

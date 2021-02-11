package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.entities.resource.Resource;

public class FactoryRecipeRow extends Table {

    public static final int WIDTH = 400;

    private Resource needed;
    private Resource owned;
    private int count;

    private Image icon;
    private Label name;
    private Label amountInfo;

    public FactoryRecipeRow(Skin skin, Skin spriteSkin, Resource needed, Resource owned) {
        super(skin);
        this.needed = needed;
        this.owned = owned;

        if(needed.getAmount() < owned.getAmount())
            count = 1;
        count = 0;

        if(needed.getAssetName().equals(""))
            icon = new Image(spriteSkin, "iron_ingot");
        else
            icon = new Image(spriteSkin, needed.getAssetName());
        name = new Label(needed.getName(), skin);
        amountInfo = new Label(count*needed.getAmount() + " / " + owned.getAmount(), skin);

        add(icon).size(FactoryDialog.ICON_SIZE);
        add(name).align(Align.center);
        add(amountInfo);
    }

    public boolean testCount(int count){
        if(count*needed.getAmount() < owned.getAmount())
            return true;
        return false;
    }

    public void setCount(int count){
        this.count = count;
        amountInfo.setText(count*needed.getAmount() + " / " + owned.getAmount());
    }

    @Override
    public String toString() {
        return needed.getName() + " " + amountInfo.getText();
    }
}

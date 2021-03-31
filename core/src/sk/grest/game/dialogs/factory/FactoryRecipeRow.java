package sk.grest.game.dialogs.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Select;

import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.resource.Resource;

import static sk.grest.game.constants.ScreenConstants.DEFAULT_PADDING;

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

        name.setFontScale(0.8f);
        amountInfo.setFontScale(0.8f);

        add(icon)
                .size(FactoryDialog.ICON_SIZE)
                .padRight(DEFAULT_PADDING).padLeft(DEFAULT_PADDING)
                .expandX().uniformX()
                .align(Align.center);
        add(name)
                .padRight(DEFAULT_PADDING).padLeft(DEFAULT_PADDING)
                .expandX().uniformX()
                .align(Align.center);
        add(amountInfo)
                .padRight(DEFAULT_PADDING).padLeft(DEFAULT_PADDING)
                .expandX().uniformX()
                .align(Align.center);
    }

    public Resource getResource() {
        return needed;
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

    public void update(Resource owned){
        this.owned = owned;
        if(testCount(1))
            setCount(1);
        else
            setCount(0);
    }

    @Override
    public String toString() {
        return needed.getName() + " " + amountInfo.getText();
    }
}

package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.resource.Resource;

public class SellBar extends Table {

    // COLUMN 0
    private Label resourceName;
    private Label resourcePrice;

    // COlUMN 1
    private TextField amount;
    private TextButton increase;
    private TextButton decrease;
    private Label amountOwned;

    private Resource resource;

    private TextButton sellButton;

    public SellBar(final Resource resource, Skin skin) {

        super(skin);
        this.resource = resource;

        // COLUMN 0
        Table col0 = new Table(getSkin());
        resourceName = new Label(resource.getName(), getSkin());
        col0.add(resourceName).align(Align.center).row();
        resourcePrice = new Label(resource.getPrice()+"", getSkin());
        col0.add(resourcePrice).align(Align.center).row();

        // COLUMN 1
        Table col1 = new Table(getSkin());

        increase = new TextButton("+", getSkin());
        increase.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentAmount = Integer.parseInt(amount.getText());
                if(currentAmount+1 < (int) resource.getAmount()) {
                    currentAmount++;
                    amount.setText(currentAmount+"");
                }
            }
        });

        decrease = new TextButton("-", getSkin());
        decrease.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentAmount = Integer.parseInt(amount.getText());
                if(currentAmount-1 > 0) {
                    currentAmount--;
                    amount.setText(currentAmount+"");
                }
            }
        });

        Table amountTable = new Table(getSkin());
        amount = new TextField("0", getSkin());
        amount.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int currentAmount = Integer.parseInt(amount.getText());
                if(currentAmount > resource.getAmount()){
                    amount.setText(resource.getAmount()+"");
                }else if(currentAmount < 0){
                    amount.setText("0");
                }
            }
        });

        amountTable.add(amount)
                .align(Align.center)
                .row();
        amountOwned = new Label((int) resource.getAmount() + "", getSkin());
        amountTable.add(amountOwned).align(Align.center);

        col1.add(decrease).size(ScreenDeafults.DEFAULT_ACTOR_WIDTH/6f).align(Align.center);
        col1.add(amountTable).align(Align.center);
        col1.add(increase).size(ScreenDeafults.DEFAULT_ACTOR_WIDTH/6f).align(Align.center);

        sellButton = new TextButton("SELL", getSkin());

        add(col0)
                .width(ScreenDeafults.DEFAULT_ACTOR_WIDTH*2)
                .pad(ScreenDeafults.DEFAULT_PADDING)
                .align(Align.center)
                .left();
        add(col1)
                .width(ScreenDeafults.DEFAULT_ACTOR_WIDTH*2)
                .pad(ScreenDeafults.DEFAULT_PADDING)
                .align(Align.center)
                .center();
        add(sellButton)
                .size(ScreenDeafults.DEFAULT_ACTOR_WIDTH/4f)
                .pad(ScreenDeafults.DEFAULT_PADDING)
                .align(Align.center)
                .right();

        //this.debug(Debug.table);
        //col0.debug(Debug.all);
    }

    public void changeResource(Resource resource){
        this.resource = resource;
        resourceName.setText(resource.getName());
        resourcePrice.setText((int) resource.getPrice());
        amountOwned.setText(resource.getAmount()+"");
    }

}

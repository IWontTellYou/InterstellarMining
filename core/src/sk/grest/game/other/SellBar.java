package sk.grest.game.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemSoldListener;

public class SellBar extends Table {

    // COLUMN 0
    private Label resourceName;
    private Label resourcePrice;

    // COlUMN 1
    private TextField amount;
    private TextButton increase;
    private TextButton decrease;
    private Label amountOwned;

    private Player player;
    private Resource resource;

    private TextButton sellButton;

    private ItemSoldListener listener;

    public SellBar(final Resource resource, Skin skin, final InterstellarMining game, final ItemSoldListener listener) {
        super(skin);
        this.player = game.getPlayer();
        this.resource = resource;
        this.listener = listener;

        // COLUMN 0
        Table col0 = new Table(getSkin());
        resourceName = new Label(resource.getName(), getSkin());
        col0.add(resourceName).align(Align.center).row();
        resourcePrice = new Label(ScreenConstants.getMoneyFormat((int) resource.getPrice()) + " per unit", getSkin());
        col0.add(resourcePrice).align(Align.center).row();

        // COLUMN 1
        Table col1 = new Table(getSkin());

        increase = new TextButton("+", getSkin());
        increase.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(amount.getText().length() == 0){
                    amount.setText("0");
                    return;
                }
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
                if(amount.getText().length() == 0){
                    amount.setText("0");
                    return;
                }
                int currentAmount = Integer.parseInt(amount.getText());
                if(currentAmount > 0) {
                    currentAmount--;
                    amount.setText(currentAmount+"");
                }
            }
        });

        final Table amountTable = new Table(getSkin());
        amount = new TextField(resource.getAmount()+"", getSkin());
        amount.setAlignment(Align.center);
        amount.setTextFieldFilter(new DigitFilter());
        amount.addListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if(character == 8 && amount.getText().length() >= 1)
                    return true;
                try {
                    long currentAmount = Long.parseLong(amount.getText());
                    if (currentAmount > resource.getAmount()) {
                        amount.setText((int) resource.getAmount() + "");
                        return false;
                    }
                }catch (NumberFormatException e){
                    return false;
                }

                return true;
            }
        });

        amountTable.add(amount)
                .align(Align.center)
                .row();
        amountOwned = new Label("max. "+(int) resource.getAmount(), getSkin());
        amountTable.add(amountOwned).align(Align.center);

        col1.add(decrease)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .size(ScreenConstants.DEFAULT_ACTOR_WIDTH/6f)
                .align(Align.center);
        col1.add(amountTable)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center);
        col1.add(increase)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .size(ScreenConstants.DEFAULT_ACTOR_WIDTH/6f)
                .align(Align.center);

        sellButton = new TextButton("SELL", getSkin());
        sellButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onItemSold();
            }
        });

        add(col0)
                .uniform()
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center)
                .left();
        add(col1)
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH*2)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center)
                .center();
        add(sellButton)
                .uniform()
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH/2f)
                .height(ScreenConstants.DEFAULT_ACTOR_HEIGHT/2f)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center)
                .right();
    }

    public TextField getAmount() {
        return amount;
    }

    public void changeResource(Resource resource){
        this.resource = resource;
        Gdx.app.log(resource.getName(), resource.getAmount()+"");
        resourceName.setText(resource.getName());
        resourcePrice.setText(ScreenConstants.getMoneyFormat((int) resource.getPrice()) + " per unit");
        amount.setText((int) resource.getAmount()+"");
        amountOwned.setText("max. "+(int) resource.getAmount());
    }

}

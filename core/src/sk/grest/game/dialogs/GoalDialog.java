package sk.grest.game.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.entities.Player;
import sk.grest.game.entities.resource.Resource;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.listeners.ItemSelectedListener;
import sk.grest.game.listeners.ItemSentListener;
import sk.grest.game.listeners.ItemSoldListener;
import sk.grest.game.other.DigitFilter;
import sk.grest.game.other.TooltipBuilder;
import sk.grest.game.other.selection_table.SelectionRow;
import sk.grest.game.other.selection_table.SelectionTable;

public class GoalDialog extends CustomDialog implements ItemOpenedListener<Resource> {

    private Table configBar;
    private TextField amount;
    private Label amountOwned;
    private Resource configResource;

    private SelectionTable<Resource> resourceSelectionTable;

    private InterstellarMining game;
    private Player player;

    private Label overallProgress;
    private Map<Integer, Label> progressLabels;
    ArrayList<Resource> resources;

    public GoalDialog(String title, Skin skin, Skin spriteSkin, InterstellarMining game) {
        super(title, skin);

        this.player = game.getPlayer();
        this.game = game;
        setBackground(InterstellarMining.back);
        progressLabels = new HashMap<>();

        // TODO REPLACE WITH GOAL RESOURCES
        resources = game.getPlayer().getGoalResources();

        this.configResource = player.getResource(resources.get(0).getID());
        configBar = getConfigBar();

        resourceSelectionTable = new SelectionTable<>(this, true);
        for (Resource r : resources) {
            SelectionRow<Resource> row = new SelectionRow<>(getSkin());
            row.setItem(r);
            row.addListener(resourceSelectionTable);
            row.setColors(ScreenConstants.LIGHT_GRAY, ScreenConstants.DARK_GRAY);

            row.add(new Image(spriteSkin, ((!r.getAssetName().equals("")) ? r.getAssetName() : "iron_ingot"))).size(40).padRight(ScreenConstants.DEFAULT_PADDING);
            row.add(new Label(r.getName(), getSkin())).width(200);
            Label completion = new Label(getLabelFormat(r), getSkin());
            row.add(completion).width(300).align(Align.right);
            progressLabels.put(r.getID(), completion);

            resourceSelectionTable.addRow(row).expandX().uniformX().row();

        }

        ScrollPane scrollPane = new ScrollPane(resourceSelectionTable);

        Table col0 = new Table();
        col0.add(scrollPane).height(200).width(600).row();
        col0.add(configBar);

        Image dysonSphere = new Image(spriteSkin, "earth");

        overallProgress = new Label(getOverallProgress(), skin);

        Table col1 = new Table();
        col1.add(dysonSphere).height(400).width(400).row();
        col1.add(overallProgress).uniformX();

        getContentTable().add(col0).expandY();
        getContentTable().add(col1).expandY();

        addCloseButton(this, 2);

    }

    private Table getConfigBar(){
        Table configBar = new Table(getSkin());
        amount = new TextField(configResource.getAmount() + "", getSkin());

        TextButton increase = new TextButton("+", getSkin());
        increase.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (amount.getText().length() == 0) {
                    amount.setText("0");
                    return;
                }
                int currentAmount = Integer.parseInt(amount.getText());
                if (currentAmount + 1 < (int) configResource.getAmount()) {
                    currentAmount++;
                    amount.setText(currentAmount + "");
                }
            }
        });

        TextButton decrease = new TextButton("-", getSkin());
        decrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (amount.getText().length() == 0) {
                    amount.setText("0");
                    return;
                }
                int currentAmount = Integer.parseInt(amount.getText());
                if (currentAmount > 0) {
                    currentAmount--;
                    amount.setText(currentAmount + "");
                }
            }
        });

        final Table amountTable = new Table(getSkin());
        amount.setAlignment(Align.center);
        amount.setTextFieldFilter(new DigitFilter());
        amount.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == 8 && amount.getText().length() >= 1)
                    return true;
                try {
                    long currentAmount = Long.parseLong(amount.getText());
                    if (currentAmount > configResource.getAmount()) {
                        amount.setText((int) configResource.getAmount() + "");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                return true;
            }
        });

        amountTable.add(amount)
                .align(Align.center)
                .row();
        amountOwned = new Label("max. " + (int) configResource.getAmount(), getSkin());
        amountOwned.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                amount.setText(String.valueOf(configResource.getAmount()));
            }
        });
        amountTable.add(amountOwned).align(Align.center);

        Table col0 = new Table(getSkin());

        col0.add(decrease)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .size(ScreenConstants.DEFAULT_ACTOR_WIDTH / 6f)
                .align(Align.center);
        col0.add(amountTable)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center);
        col0.add(increase)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .size(ScreenConstants.DEFAULT_ACTOR_WIDTH / 6f)
                .align(Align.center);

        TextButton sendButton = new TextButton("SEND", getSkin());
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int resourceAmount = Integer.parseInt(amount.getText());
                Resource r = game.getPlayer().getGoalResourceByID(configResource.getID());
                r.addAmount(resourceAmount);
                configResource.subtractAmount(resourceAmount);
                game.getHandler().updatePlayerGoal(player.getID(), r, game);
                game.getHandler().updatePlayerResourceTable(configResource.getID());
                onItemOpenedListener(configResource);
                overallProgress.setText(getOverallProgress());
                updateLabel(r);
            }
        });

        configBar.add(col0)
                .uniformX()
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center)
                .left()
                .row();
        configBar.add(sendButton)
                .width(ScreenConstants.DEFAULT_ACTOR_WIDTH)
                .pad(ScreenConstants.DEFAULT_PADDING)
                .align(Align.center)
                .uniformX()
                .center();

        return configBar;
    }

    public void updateLabel(Resource r){
        for (Label l : progressLabels.values()) {
            if(l == progressLabels.get(r.getID())){
                l.setText(getLabelFormat(r));
            }
        }
    }

    public String getOverallProgress(){
        int done = 0, needed = 0;
        for (Resource r : resources) {
            done += r.getAmount();
            needed += r.getLimit();
        }
        return String.format("Progress - (%.2f", ((float) done/needed*100)) + ")";
    }

    public String getLabelFormat(Resource r){
        return String.format("%s / %s (%s %%)", ScreenConstants.getMoneyFormatShorter(r.getAmount()), ScreenConstants.getMoneyFormatShorter(r.getLimit()), String.format("%.2f", ((float) r.getAmount() / r.getLimit() * 100)));
    }

    @Override
    public void onItemOpenedListener(Resource item) {
        configResource = player.getResource(item.getID());
        amount.setText(configResource.getAmount()+"");
        amountOwned.setText(player.getResource(item.getID()).getAmount()+"");
    }

}

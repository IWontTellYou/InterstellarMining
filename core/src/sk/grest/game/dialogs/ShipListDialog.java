package sk.grest.game.dialogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.InterstellarMining;
import sk.grest.game.constants.ScreenConstants;
import sk.grest.game.dialogs.upgrade.UpgradeShipDialog;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.ship.ShipState;
import sk.grest.game.entities.upgrade.UpgradeRecipe;
import sk.grest.game.listeners.ItemOpenedListener;
import sk.grest.game.other.ItemTooltip;
import sk.grest.game.other.selection_table.SelectionRow;
import sk.grest.game.other.selection_table.SelectionTable;
import sk.grest.game.other.TooltipBuilder;

public class ShipListDialog extends CustomDialog implements ItemOpenedListener<Ship> {

    private float timeLeft;
    private ArrayList<Label> toUpdate;
    private ArrayList<Ship> ships;

    private InterstellarMining game;

    private TooltipManager manager;
    private TooltipBuilder builder;

    public ShipListDialog(String title, Skin skin, InterstellarMining game) {
        super(title, skin);

        manager = TooltipManager.getInstance();
        manager.instant();
        builder = TooltipBuilder.getInstance();

        this.game = game;

        this.timeLeft = 1;
        this.toUpdate = new ArrayList<>();
        this.ships = game.getPlayer().getShips();

        float imageCellWidth = Gdx.graphics.getWidth()/20f;
        float nameCellWidth = Gdx.graphics.getWidth()/10f;
        float destinationCellWidth = Gdx.graphics.getWidth()/8f;
        float timeToArriveCellWidth = Gdx.graphics.getWidth()/8f;

        float cellHeight = Gdx.graphics.getHeight()/25f;

        // CONTENT TABLE

        //
        // HEADER
        //

        Table infoTable = new Table(skin);

        setBackground(InterstellarMining.back);
        setSize(ScreenConstants.DEFAULT_DIALOG_WIDTH, ScreenConstants.DEFAULT_DIALOG_HEIGHT);

        Label imageLabel = new Label("", skin);
        infoTable.add(imageLabel).
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("NAME", skin);
        infoTable.add(nameLabel).
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label destinationLabel = new Label("DESTINATION", skin);
        infoTable.add(destinationLabel).
                align(Align.center).
                width(destinationCellWidth).height(cellHeight);

        Label timeToArriveLabel = new Label("ARRIVAL TIME", skin);
        infoTable.add(timeToArriveLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight);

        Label stateLabel = new Label("STATE", skin);
        infoTable.add(stateLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        getContentTable().add(infoTable).fillX().row();

        SelectionTable<Ship> shipList = new SelectionTable<>(this, skin, false);

        for (Ship s : ships) {

            SelectionRow<Ship> tableRow = new SelectionRow<>();
            tableRow.addListener(new ItemTooltip(builder.buildTooltipContent(s)));
            tableRow.setItem(s);
            tableRow.addListener(shipList);
            tableRow.setColors(ScreenConstants.TRANSPARENT, ScreenConstants.LIGHT_GRAY);

            Label shipImage = new Label("img", skin);
            tableRow.add(shipImage).
                    align(Align.center).
                    width(imageCellWidth).height(cellHeight);

            Label shipName = new Label(s.getName(), skin);
            tableRow.add(shipName).
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label shipDestination;
            if(s.getCurrentDestination() == null)
                shipDestination = new Label("NONE", skin);
            else
                shipDestination = new Label(s.getCurrentDestination().getName(), skin);

            toUpdate.add(shipDestination);

            tableRow.add(shipDestination).
                    align(Align.center).
                    width(destinationCellWidth).height(cellHeight);

            Label shipTimeToArrive;

            long timeLeft = s.getTimeLeft();
            if(s.getTimeLeft() == 0){
                shipTimeToArrive = new Label("00:00:00", skin);
            }else{
                shipTimeToArrive = new Label(ScreenConstants.getTimeFormat(timeLeft), skin);
            }

            tableRow.add(shipTimeToArrive).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight);

            toUpdate.add(shipTimeToArrive);

            Label shipState = new Label(s.getState().toString(), skin);

            tableRow.add(shipState).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();

            toUpdate.add(shipState);

            shipList.addRow(tableRow).row();
        }

        getContentTable().add(shipList).padBottom(100).row();

        addCloseButton(this);

        // BUTTON TABLE


    }

    public void update(float delta){
        if(timeLeft < 0){
            int shipIndex = 0;
            for (int i = 0; i < toUpdate.size(); i+=3) {
                if(ships.get(shipIndex).getState() != ShipState.AT_THE_BASE) {
                    toUpdate.get(i).setText(ships.get(shipIndex).getCurrentDestination().getName());
                    toUpdate.get(i + 1).setText(ScreenConstants.getTimeFormat(ships.get(shipIndex).getTimeLeft()));
                    toUpdate.get(i + 2).setText(ships.get(shipIndex).getState().toString());
                }else {
                    toUpdate.get(i).setText("NONE");
                    toUpdate.get(i+1).setText("00:00:00");
                    toUpdate.get(i+2).setText(ShipState.AT_THE_BASE.toString());
                }
                shipIndex++;
            }
            timeLeft = 1;
        }else
            timeLeft -= delta;
    }

    @Override
    public void onItemOpenedListener(Ship item) {
        UpgradeShipDialog dialog = new UpgradeShipDialog(item.getName(), game, game.getPlayer(), item, game.getUpgradeRecipesByType(UpgradeRecipe.SHIP_UPGRADE));
        dialog.show(this.getStage());
        this.hide();
    }
}

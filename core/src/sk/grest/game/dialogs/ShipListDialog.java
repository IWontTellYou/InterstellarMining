package sk.grest.game.dialogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.ship.Ship;
import sk.grest.game.entities.enums.ShipState;

public class ShipListDialog extends Dialog {

    private float timeLeft;
    private ArrayList<Label> toUpdate;
    private ArrayList<Ship> ships;

    public ShipListDialog(String title, Skin skin, ArrayList<Ship> ships) {
        super(title, skin);

        this.timeLeft = 1;
        this.toUpdate = new ArrayList<>();
        this.ships = ships;

        float imageCellWidth = Gdx.graphics.getWidth()/20f;
        float nameCellWidth = Gdx.graphics.getWidth()/10f;
        float destinationCellWidth = Gdx.graphics.getWidth()/8f;
        float timeToArriveCellWidth = Gdx.graphics.getWidth()/8f;

        float cellHeight = Gdx.graphics.getHeight()/25f;

        // CONTENT TABLE

        //
        // HEADER
        //

        Table contentTable = new Table(skin);

        Label imageLabel = new Label("", skin);
        contentTable.add(imageLabel).
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("NAME", skin);
        contentTable.add(nameLabel).
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label destinationLabel = new Label("DESTINATION", skin);
        contentTable.add(destinationLabel).
                align(Align.center).
                width(destinationCellWidth).height(cellHeight);

        Label timeToArriveLabel = new Label("ARRIVAL TIME", skin);
        contentTable.add(timeToArriveLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight);

        Label stateLabel = new Label("STATE", skin);
        contentTable.add(stateLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        //
        // END OF HEADER
        //


        //
        // TABLE DATA
        //

        for (Ship s : ships) {

            Label shipImage = new Label("img", skin);
            contentTable.add(shipImage).
                    align(Align.center).
                    width(imageCellWidth).height(cellHeight);

            Label shipName = new Label(s.getName(), skin);
            contentTable.add(shipName).
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label shipDestination;
            if(s.getCurrentDestination() == null)
                shipDestination = new Label("NONE", skin);
            else
                shipDestination = new Label(s.getCurrentDestination().getName(), skin);

            toUpdate.add(shipDestination);

            contentTable.add(shipDestination).
                    align(Align.center).
                    width(destinationCellWidth).height(cellHeight);

            Label shipTimeToArrive;

            long timeLeft = s.getTimeLeft().getTime();
            if(s.getTimeLeft().getTime() == 0){
                shipTimeToArrive = new Label("00:00:00", skin);
            }else{
                shipTimeToArrive = new Label(ScreenDeafults.getTimeFormat(timeLeft), skin);
            }

            contentTable.add(shipTimeToArrive).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight);

            toUpdate.add(shipTimeToArrive);

            Label shipState = new Label(s.getState().toString(), skin);

            contentTable.add(shipState).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();

            toUpdate.add(shipState);
        }

        getContentTable().add(contentTable);

        // BUTTON TABLE

        final ShipListDialog listDialog = this;

        TextButton closeBtn = new TextButton("CLOSE", skin);
        closeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listDialog.hide();
            }
        });
        getButtonTable().add(closeBtn);

    }

    public void update(float delta){
        if(timeLeft < 0){
            int shipIndex = 0;
            for (int i = 0; i < toUpdate.size(); i+=3) {
                if(ships.get(shipIndex).getState() != ShipState.AT_THE_BASE) {
                    toUpdate.get(i).setText(ships.get(shipIndex).getCurrentDestination().getName());
                    toUpdate.get(i + 1).setText(ScreenDeafults.getTimeFormat(ships.get(shipIndex).getTimeLeft().getTime()));
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

}

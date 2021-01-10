package sk.grest.game.dialogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sk.grest.game.defaults.ScreenDeafults;
import sk.grest.game.entities.Planet;
import sk.grest.game.entities.Ship;

public class ShipListDialog extends Dialog {

    public ShipListDialog(String title, Skin skin, ArrayList<Ship> ships) {
        super(title, skin);

        setWidth(ScreenDeafults.DEFAULT_DIALOG_WIDTH);
        setHeight(ScreenDeafults.DEFAULT_DIALOG_HEIGHT);

        float imageCellWidth = Gdx.graphics.getWidth()/20f;
        float nameCellWidth = Gdx.graphics.getWidth()/10f;
        float destinationCellWidth = Gdx.graphics.getWidth()/8f;
        float timeToArriveCellWidth = Gdx.graphics.getWidth()/8f;

        float cellHeight = Gdx.graphics.getHeight()/25f;

        // CONTENT TABLE
        getContentTable().clearChildren();

        Label imageLabel = new Label("", skin);
        getContentTable().add(imageLabel).
                align(Align.center).
                width(imageCellWidth).height(cellHeight);

        Label nameLabel = new Label("NAME", skin);
        getContentTable().add(nameLabel).
                align(Align.center).
                width(nameCellWidth).height(cellHeight);

        Label destinationLabel = new Label("DESTINATION", skin);
        getContentTable().add(destinationLabel).
                align(Align.center).
                width(destinationCellWidth).height(cellHeight);

        Label timeToArriveLabel = new Label("ARRIVAL TIME", skin);
        getContentTable().add(timeToArriveLabel).
                align(Align.center).
                width(timeToArriveCellWidth).height(cellHeight).
                row();

        for (Ship s : ships) {

            Label shipImage = new Label("img", skin);
            getContentTable().add(shipImage).
                    align(Align.center).
                    width(imageCellWidth).height(cellHeight);

            Label shipName = new Label(s.getName(), skin);
            getContentTable().add(shipName).
                    align(Align.center).
                    width(nameCellWidth).height(cellHeight);

            Label shipDestination;
            if(s.getCurrentDestination() == null)
                shipDestination = new Label("NONE", skin);
            else
                shipDestination = new Label(s.getCurrentDestination().getName(), skin);

            getContentTable().add(shipDestination).
                    align(Align.center).
                    width(destinationCellWidth).height(cellHeight);

            Label shipTimeToArrive;
            if(s.getTaskTime() == null){
                shipTimeToArrive = new Label("00:00:00", skin);
            }else{
                long timeLeft = s.getTaskTime().getTime() - System.currentTimeMillis();
                shipTimeToArrive = new Label(ScreenDeafults.timeLeftFormat.format(new Timestamp(timeLeft)), skin);
            }

            getContentTable().add(shipTimeToArrive).
                    align(Align.center).
                    width(timeToArriveCellWidth).height(cellHeight).
                    row();
        }

        // BUTTON TABLE
        getButtonTable().clearChildren();

    }

    /* public ShipListDialog(String title, Skin skin, String windowStyleName,  ArrayList<Ship> ships) {
        super(title, skin, windowStyleName);
    }

    public ShipListDialog(String title, WindowStyle windowStyle,  ArrayList<Ship> ships) {
        super(title, windowStyle);
    }
     */

}

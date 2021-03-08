package sk.grest.game.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Button {

    private ImageButton btn;
    private ImageButtonStyle style;
    private Skin skin;

    private String imageUp;
    private String imageOver;

    public Button(Skin skin, String imageUp, String imageOver) {
        this.skin = skin;

        this.imageOver = imageOver;
        this.imageUp = imageUp;

        this.style = new ImageButtonStyle();
        style.imageUp = skin.getDrawable(imageUp);
        if(imageOver != null)
            style.imageOver = skin.getDrawable(imageOver);

        this.btn = new ImageButton(style);
        btn.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 8f);
    }

    public ImageButton getButton(){
        return btn;
    }

    public String getImageUp() {
        return imageUp;
    }
    public String getImageOver() {
        return imageOver;
    }

    public void setImageUp(String imageUp){
        style.imageUp = skin.getDrawable(imageUp);
    }
}

package ru.skhanov.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import java.util.function.Consumer;
import ru.skhanov.base.Sprite;



/**
 * Класс в разработке
 */
public class Button extends Sprite {

    private boolean isPressed;


    public Button(TextureAtlas atlas, String atlasRegionName, float height) {
        super(atlas.findRegion(atlasRegionName));
        setHeightProportion(height);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)) {
            scale = 0.9f;
            isPressed = true;
        }
        return false;
    }


    public boolean touchUp(Vector2 touch, int pointer, Consumer action) {
        scale = 1f;
        if(isMe(touch)) {
            action.accept(this);
            isPressed = false;
        }
        return false;
    }






}

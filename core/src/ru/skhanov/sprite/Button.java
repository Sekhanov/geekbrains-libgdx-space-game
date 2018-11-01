package ru.skhanov.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;


/**
 * Класс в разработке
 */
public class Button extends Sprite {



    public Button(TextureAtlas atlas, String atlasRegionName, float height) {
        super(atlas.findRegion(atlasRegionName));
        setHeightProportion(height);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)) {
            scale = 0.9f;
        }
        return false;
    }


    public boolean touchUp(Vector2 touch, int pointer, Runnable action) {
        scale = 1f;
        if(isMe(touch)) {
            action.run();
        }
        return false;
    }


}

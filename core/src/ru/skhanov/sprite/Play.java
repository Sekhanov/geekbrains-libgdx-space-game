package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;

public class Play extends Sprite {


    public Play(TextureAtlas atlas) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.3f);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)) {
            scale = 0.9f;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        scale = 1f;
        return false;
    }
}

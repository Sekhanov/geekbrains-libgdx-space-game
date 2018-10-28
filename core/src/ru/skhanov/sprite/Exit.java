package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;

public class Exit extends Sprite {

    public Exit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(worldBounds.getRight() - 0.01f);
        setTop(worldBounds.getTop() - 0.01f);
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

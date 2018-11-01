package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

}

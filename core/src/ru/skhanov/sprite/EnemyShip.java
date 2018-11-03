package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.utils.Regions;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private Vector2 v;


    public EnemyShip() {
        regions = new TextureRegion[1];
        v = new Vector2();

    }

    public void set(TextureRegion region, float height, Vector2 pos, Vector2 v0, Rect worldBounds) {
        regions = Regions.split(region, 1, 2, 2);
        setHeightProportion(height);
        this.pos.set(pos);
        this.v.set(v0);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if(isOutside(worldBounds)) {
            destroy();
        }
    }
}

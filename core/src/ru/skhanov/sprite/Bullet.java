package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 v = new Vector2();
    private int damage;
    private Ship owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public  void set(Ship owner,
                     TextureRegion region,
                     Vector2 pos0,
                     Vector2 v0,
                     float height,
                     Rect worldBounds,
                     int damage) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if(isOutside(worldBounds)) {
            destroy();
        }
    }

    public Ship getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }
}

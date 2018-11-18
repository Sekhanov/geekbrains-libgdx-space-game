package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;

public class Bonus extends Sprite {

    public enum BonusType {
        MEDIC, BULLET_POWER, BULLET_SPEED;
    }

    private Rect worldBounds;
    private Vector2 v = new Vector2();
    private BonusType bonusType;

    public Bonus() {
        regions = new TextureRegion[1];
    }

    public  void set(TextureRegion region,
                     BonusType bonusType,
                     Vector2 pos0,
                     Vector2 v0,
                     float height,
                     Rect worldBounds) {
        this.regions[0] = region;
        this.bonusType = bonusType;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if(isOutside(worldBounds)) {
            destroy();
        }
    }

    public BonusType getBonusType() {
        return bonusType;
    }
}

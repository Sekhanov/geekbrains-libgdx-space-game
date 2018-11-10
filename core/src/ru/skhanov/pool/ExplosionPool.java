package ru.skhanov.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    TextureRegion textureRegion;

    public ExplosionPool(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 9, 9, 74);
    }
}

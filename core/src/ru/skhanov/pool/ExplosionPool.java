package ru.skhanov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    TextureRegion textureRegion;
    Sound explosionSound;

    public ExplosionPool(TextureRegion textureRegion, Sound explosionSound) {
        this.textureRegion = textureRegion;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 9, 9, 74, explosionSound);
    }
}

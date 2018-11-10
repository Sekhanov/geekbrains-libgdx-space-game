package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;

public class Explosion extends Sprite {

    protected static final float ANIMATION_INTERVAL = 0.017f;
    protected float animationTimer;
    private Sound explosionSound;

    
    public Explosion(TextureRegion textureRegion, int row, int col, int frames, Sound explosionSound) {
        super(textureRegion, row, col, frames);
        this.explosionSound = explosionSound;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play();

    }

    @Override
    public void update(float delta) {
        animationTimer += delta;
        if(animationTimer > ANIMATION_INTERVAL) {
            animationTimer = 0;
            if(++frame >= regions.length) {
                frame = 0;
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

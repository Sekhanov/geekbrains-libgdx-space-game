package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.ExplosionPool;

public abstract class Ship extends Sprite {

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;
    protected Vector2 v = new Vector2();
    protected Vector2 bulletVY = new Vector2();
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected int damage;
    protected int hp;
    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer;
    private Sound shootSound;


    public Ship(Sound shootSound, BulletPool bulletPool) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
    }

    public Ship(TextureRegion textureRegion, BulletPool bulletPool, Sound shootSound) {
        super(textureRegion, 1, 2, 2);
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;

    }

    protected void shoot(TextureRegion bulletRegion, Vector2 bulletV, float height, int damage) {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, height, worldBounds, damage);
        shootSound.play();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if(damageAnimateTimer > damageAnimateInterval) {
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0;
        hp -= damage;
        if(hp <= 0) {
            destroy();
        }
    }


}

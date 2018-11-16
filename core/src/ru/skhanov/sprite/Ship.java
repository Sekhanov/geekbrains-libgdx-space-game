package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.MovingFont;
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
    protected int initialHp;
    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer;
    private Sound shootSound;
    protected ShipType shipType;
    protected MovingFont hpMoveFont;

    protected enum ShipType{
        ENEMY_SHIP, MAIN_SHIP;
    }



    public Ship(TextureRegion regions, BulletPool bulletPool, Sound shootSound, int hp, int damage,
                ExplosionPool explosionPool, MovingFont hpMoveFont) {
        super(regions, 1, 2, 2);
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.explosionPool = explosionPool;
        this.damage = damage;
        this.hp = hp;
        this.initialHp = hp;
        this.explosionPool = explosionPool;
        this.hpMoveFont = hpMoveFont;
        this.hpMoveFont.setFontSize(0.03f);

    }


    public Ship(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, MovingFont hpMoveFont) {
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hpMoveFont = hpMoveFont;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        changeHpAnimation(batch);
    }



    protected void shoot(TextureRegion bulletRegion, Vector2 bulletV, float height, int damage) {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, height, worldBounds, damage);
        shootSound.play();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hpMoveFont.update(delta);
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
        hpMoveFont.set(pos.x, pos.y);
        if(hp <= 0) {
            destroy();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !isOutside(bullet);
    }

    @Override
    public void destroy() {
        super.destroy();
        hp = 0;
    }

    public int getHp() {
        return hp;
    }

    protected void changeHpAnimation(Batch batch) {
        if(hp != initialHp) {
            String changeHp = String.valueOf(initialHp - hp);
            switch (shipType) {
                case MAIN_SHIP:
                    hpMoveFont.draw(batch, changeHp);
                    if(hpMoveFont.getAnimationFrameCount() == 0) {
                        initialHp = hp;
                    }
                    break;
                case ENEMY_SHIP:
                    hpMoveFont.draw(batch, changeHp);
                    if(hpMoveFont.getAnimationFrameCount() == 0) {
                        initialHp = hp;
                    }
                    break;
            }
        }

    }



}

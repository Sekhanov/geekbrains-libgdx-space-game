package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.MovingFont;
import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.math.Rnd;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.ExplosionPool;
import ru.skhanov.utils.Regions;

public class EnemyShip extends Ship {

    private float reloadInterval;
    private float reloadTimer;
    private Vector2 acceleration = new Vector2(0, -0.5f);





    public EnemyShip(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, MovingFont hpMoveFont) {
        super(shootSound, bulletPool, explosionPool, hpMoveFont);
        this.shipType = ShipType.ENEMY_SHIP;
    }

    public void set(TextureRegion region,
                    TextureRegion bulletRegion,
                    float height,
                    float vY,
                    float bulletVY,
                    Rect worldBounds,
                    float reloadInterval,
                    float bulletHeight,
                    int hp,
                    int damage) {
        regions = Regions.split(region, 1, 2, 2);
        setHeightProportion(height);
        pos.set(Rnd.nextFloat(worldBounds.getLeft() + halfWidth, worldBounds.getRight() - halfWidth), worldBounds.getTop() + halfHeight);
        this.v.set(0, vY);
        this.worldBounds = worldBounds;
        this.reloadInterval = reloadInterval;
        this.bulletRegion = bulletRegion;
        this.bulletVY.set(0, bulletVY);
        this.bulletHeight = bulletHeight;
        this.hp = hp;
        this.initialHp = hp;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getTop() > worldBounds.getTop()) {
        pos.mulAdd(acceleration, delta);
        } else  {
            pos.mulAdd(v, delta);
        }

        enemyShoot(delta);
        if(getBottom() < worldBounds.getBottom()) {
            destroy();
        }

    }

    public boolean isBulletCollision(Rect bullet) {
        return (super.isBulletCollision(bullet) && pos.y < bullet.getTop());
    }



    private void enemyShoot(float delta) {
        if(reloadTimer > reloadInterval) {
            shoot(bulletRegion, bulletVY, bulletHeight, damage);
            reloadTimer = 0;
        } else {
            reloadTimer += delta;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }


}

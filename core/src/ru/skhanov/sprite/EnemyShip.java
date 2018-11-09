package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.math.Rnd;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.utils.Regions;

public class EnemyShip extends Ship {

    private float reloadInterval;
    private float reloadTimer;
    private Vector2 acceleration = new Vector2(0, 0.5f);


    public EnemyShip(Sound shootSound, BulletPool bulletPool) {
        super(shootSound, bulletPool);
    }

    public void set(TextureRegion region,
                    TextureRegion bulletRegion,
                    float height,
                    float vY,
                    float bulletVY,
                    Rect worldBounds,
                    float reloadInterval,
                    float bulletHeight) {
        regions = Regions.split(region, 1, 2, 2);
        setHeightProportion(height);
        pos.set(Rnd.nextFloat(worldBounds.getLeft() + halfWidth, worldBounds.getRight() - halfWidth), worldBounds.getTop() + halfHeight);
        this.v.set(0, vY);
        this.worldBounds = worldBounds;
        this.reloadInterval = reloadInterval;
        this.bulletRegion = bulletRegion;
        this.bulletVY.set(0, bulletVY);
        this.bulletHeight = bulletHeight;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        enemyShoot(delta);
        if(getTop() < worldBounds.getBottom()) {
            destroy();
        }

    }



    private void enemyShoot(float delta) {
        if(reloadTimer > reloadInterval) {
            shoot(bulletRegion, bulletVY, bulletHeight, damage);
            reloadTimer = 0;
        } else {
            reloadTimer += delta;
        }
    }


}

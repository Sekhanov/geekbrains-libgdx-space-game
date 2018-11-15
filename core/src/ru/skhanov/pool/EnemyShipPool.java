package ru.skhanov.pool;

import com.badlogic.gdx.audio.Sound;

import ru.skhanov.base.MovingFont;
import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private Sound shootSound;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MovingFont hpMoveFont;


    public EnemyShipPool(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, MovingFont hpMoveFont) {
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hpMoveFont = hpMoveFont;
    }

    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool, explosionPool, hpMoveFont);
    }


}

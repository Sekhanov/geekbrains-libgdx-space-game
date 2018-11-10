package ru.skhanov.pool;

import com.badlogic.gdx.audio.Sound;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private Sound shootSound;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;


    public EnemyShipPool(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool, explosionPool);
    }



}

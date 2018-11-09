package ru.skhanov.pool;

import com.badlogic.gdx.audio.Sound;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private Sound shootSound;
    private BulletPool bulletPool;


    public EnemyShipPool(Sound shootSound, BulletPool bulletPool) {
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
    }

    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool);
    }

}

package ru.skhanov.pool;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}

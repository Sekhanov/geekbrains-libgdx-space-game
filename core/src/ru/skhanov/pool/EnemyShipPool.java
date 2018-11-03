package ru.skhanov.pool;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    protected EnemyShip newObject() {
        return new EnemyShip();
    }

}

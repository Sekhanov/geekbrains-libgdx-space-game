package ru.skhanov.pool;

import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {
    @Override
    protected Bonus newObject() {
        return new Bonus();
    }
}

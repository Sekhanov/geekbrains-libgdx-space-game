package ru.skhanov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.skhanov.base.MovingFont;
import ru.skhanov.base.SpritesPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private Sound shootSound;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MovingFont hpMoveFont;
    private BonusPool bonusPool;
    private TextureAtlas bonusTextureAtlas;


    public EnemyShipPool(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool,
                         MovingFont hpMoveFont, BonusPool bonusPool, TextureAtlas bonusTextureAtlas) {
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hpMoveFont = hpMoveFont;
        this.bonusPool = bonusPool;
        this.bonusTextureAtlas = bonusTextureAtlas;
    }

    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool, explosionPool, hpMoveFont, bonusPool,
                bonusTextureAtlas);
    }


}

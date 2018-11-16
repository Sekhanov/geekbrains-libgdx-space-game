package ru.skhanov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sun.prism.TextureMap;

import ru.skhanov.base.MovingFont;
import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.math.Rnd;
import ru.skhanov.pool.BonusPool;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.ExplosionPool;
import ru.skhanov.utils.Regions;

public class EnemyShip extends Ship {

    private float reloadInterval;
    private float reloadTimer;
    private Vector2 acceleration = new Vector2(0, -0.5f);
    private BonusPool bonusPool;
    private TextureAtlas bonusTextureAtlas;
    private Vector2 bonusV = new Vector2(0, -0.1f);


    public EnemyShip(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool,
                     MovingFont hpMoveFont, BonusPool bonusPool) {
        super(shootSound, bulletPool, explosionPool, hpMoveFont);
        this.shipType = ShipType.ENEMY_SHIP;
        this.bonusPool = bonusPool;
        this.bonusTextureAtlas = new TextureAtlas("bonus.atlas");
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
        pos.set(Rnd.nextFloat(worldBounds.getLeft() + halfWidth, worldBounds.getRight() - halfWidth),
                worldBounds.getTop() + halfHeight);
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
        Bonus bonus = bonusPool.obtain();
        generateBonus(bonus);

    }

    private void generateBonus(Bonus bonus) {
        double drop = Math.random();
        Bonus.BonusType bonusType = randomBonusDrop();
        if(drop > 0.1) {
            switch (bonusType) {
                case MEDIC:
                    bonus.set(bonusTextureAtlas.findRegion("medic"), bonusType, pos,
                            bonusV, 0.05f, worldBounds);
                    break;
                case BULLET_POWER:
                    bonus.set(bonusTextureAtlas.findRegion("bullet_power"), bonusType, pos,
                            bonusV, 0.05f, worldBounds);
                    break;
                case BULLET_SPEED:
                    bonus.set(bonusTextureAtlas.findRegion("bullet_speed"), bonusType, pos,
                            bonusV, 0.05f, worldBounds);
                    break;
                default:
                    break;

            }

        }
    }

    private Bonus.BonusType randomBonusDrop() {
        Bonus.BonusType bonusType = null;
        int bonusTypeRandom = (int) (Math.random() * 3);
        switch (bonusTypeRandom) {
            case 0:
                bonusType = Bonus.BonusType.MEDIC;
                break;
            case 1:
                bonusType = Bonus.BonusType.BULLET_POWER;
                break;
            case 2:
                bonusType = Bonus.BonusType.BULLET_SPEED;
            default:
                break;
        }
        return bonusType;
    }


}

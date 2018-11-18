package ru.skhanov.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.skhanov.math.Rect;
import ru.skhanov.pool.EnemyShipPool;
import ru.skhanov.sprite.EnemyShip;

public class EnemyEmmiter {

    private EnemyShipPool enemyShipPool;
    private Rect worldBounds;
    private TextureAtlas textureAtlas;

    private float generateTimer;
    private float generateInterval;
    private int level;

    public EnemyEmmiter(EnemyShipPool enemyShipPool, Rect worldBounds, TextureAtlas textureAtlas) {
        this.enemyShipPool = enemyShipPool;
        this.worldBounds = worldBounds;
        this.textureAtlas = textureAtlas;


    }

    public void generate(float delta, int frags) {
        level = frags / 10;
        generateInterval = 5 - (level * 0.5f);
        generateTimer += delta;
        if(generateTimer > generateInterval) {
            generateTimer = 0;
            EnemyShip enemyShip = enemyShipPool.obtain();
            double random = Math.random();
            if(random < 0.6) {
                enemyShip.set(textureAtlas.findRegion("enemy0"),
                        textureAtlas.findRegion("bulletEnemy"),
                        0.1f, -0.1f, -0.2f, worldBounds, 3f,0.01f, 5  + level, 5 + level);
            } else if(random > 0.6 && random < 0.8) {
                enemyShip.set(textureAtlas.findRegion("enemy1"),textureAtlas.findRegion("bulletEnemy"),
                        0.2f, -0.05f, -0.2f, worldBounds, 5f, 0.05f, 10 + level, 10 + level);
            } else if (random > 0.8) {
                enemyShip.set(textureAtlas.findRegion("enemy2"),textureAtlas.findRegion("bulletEnemy"),
                        0.3f, -0.02f, -0.2f, worldBounds, 5f, 0.08f, 20 + level, 20 + level);
            }

        }
    }

    public int getLevel() {
        return level;
    }
}

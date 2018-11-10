package ru.skhanov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.skhanov.base.Base2DScreen;
import ru.skhanov.math.Rect;
import ru.skhanov.math.Rnd;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.EnemyShipPool;
import ru.skhanov.pool.ExplosionPool;
import ru.skhanov.sprite.Background;
import ru.skhanov.sprite.Bullet;
import ru.skhanov.sprite.Button;
import ru.skhanov.sprite.EnemyShip;
import ru.skhanov.sprite.MainShip;
import ru.skhanov.sprite.Star;
import ru.skhanov.utils.EnemyEmmiter;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 256;

    private Background background;
    private Texture bgTexture;
    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;
    private Star[] stars;
    private Button exit;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private Music music;
    private Sound shootSound;
    private EnemyEmmiter enemyEmmiter;


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        menuAtlas = new TextureAtlas("menuAtlas.tpack");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
        bulletPool = new BulletPool();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));
        mainShip = new MainShip(mainAtlas.findRegion("main_ship"), mainAtlas.findRegion("bulletMainShip"),
                bulletPool, shootSound, 100, 1);
        exit = new Button(menuAtlas, "btExit", 0.05f);
        explosionPool = new ExplosionPool(mainAtlas.findRegion("explosion"), Gdx.audio.newSound(Gdx.files.internal("explosion.wav")));
        enemyShipPool = new EnemyShipPool(shootSound, bulletPool, explosionPool);
        enemyEmmiter = new EnemyEmmiter(enemyShipPool, worldBounds, mainAtlas);

        playMusic();


    }

    private void playMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("B&DDLevel5.mp3"));
//        music.play();
        music.setLooping(true);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for(int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);

        exit.setTop(worldBounds.getTop());
        exit.setLeft(worldBounds.getLeft());

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        enemyEmmiter.generate(delta);
        checkCollision();
        deleteAllDestroyed();
        draw();

    }

    private void draw() {
        Gdx.gl.glClearColor(0.01f,0.16f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for(int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }

        mainShip.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyShipPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        exit.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        for(int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        bulletPool.updateActiveObjects(delta);
        enemyShipPool.updateActiveObjects(delta);
        explosionPool.updateActiveObjects(delta);

        mainShip.update(delta);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        menuAtlas.dispose();
        mainAtlas.dispose();
        batch.dispose();
        music.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        exit.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        exit.touchUp(touch, pointer, t -> Gdx.app.exit());
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    private void checkCollision() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<Bullet>  enemyBulletList = bulletPool.getActiveObjects();
        for(EnemyShip enemyShip: enemyShipList) {
            if(enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if(enemyShip.pos.dst2(mainShip.pos) < Math.pow(minDist, 2)) {
                enemyShip.boom();
                enemyShip.destroy();
            }

            for(Bullet bullet: enemyBulletList) {
                if(bullet.isDestroyed() || !bullet.getOwner().equals(mainShip)) {
                    continue;
                }
                if(!bullet.isOutside(enemyShip)) {
                    bullet.destroy();
                    enemyShip.damage(bullet.getDamage());
                }
            }
        }



    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }


}

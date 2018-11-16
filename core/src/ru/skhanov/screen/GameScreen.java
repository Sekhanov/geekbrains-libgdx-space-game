package ru.skhanov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.function.Consumer;

import ru.skhanov.base.Base2DScreen;
import ru.skhanov.base.Font;
import ru.skhanov.base.MovingFont;
import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.pool.BonusPool;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.EnemyShipPool;
import ru.skhanov.pool.ExplosionPool;
import ru.skhanov.sprite.Background;
import ru.skhanov.sprite.Bullet;
import ru.skhanov.sprite.Button;
import ru.skhanov.sprite.EnemyShip;
import ru.skhanov.sprite.MainShip;
import ru.skhanov.sprite.Ship;
import ru.skhanov.sprite.Star;
import ru.skhanov.utils.EnemyEmmiter;

public class GameScreen extends Base2DScreen implements Consumer<Button> {

    private static final int STAR_COUNT = 256;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "hp: ";
    private static final String LEVEL = "level: ";

    private final Game myLibGdxGame;
    private Screen menuScreen;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;
    private Star[] stars;
    private Button backButton;
    private Button newGameButton;
    private Sprite gameOverMessage;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private Music music;
    private Sound shootSound;
    private EnemyEmmiter enemyEmmiter;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private int frags;
    private MovingFont hpMoveFont;
    private BonusPool bonusPool;

    public GameScreen(Screen menuScreen, Game game) {
        this.menuScreen = menuScreen;
        this.myLibGdxGame = game;
    }


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        menuAtlas = new TextureAtlas("menuAtlas.tpack");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        font = new Font("Neucha.fnt", "Neucha.png");
        font.setFontSize(0.03f);
        hpMoveFont = new MovingFont("Neucha.fnt", "Neucha.png", new Vector2(0, 0.05f));
        initGameOverMessage();
        generateStars();
        bulletPool = new BulletPool();
        bonusPool = new BonusPool();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));
        backButton = new Button(menuAtlas, "btExit", 0.05f);
        newGameButton = new Button(mainAtlas, "button_new_game", 0.05f);
        explosionPool = new ExplosionPool(mainAtlas.findRegion("explosion"), Gdx.audio.newSound(Gdx.files.internal("explosion.wav")));
        enemyShipPool = new EnemyShipPool(shootSound, bulletPool, explosionPool, hpMoveFont, bonusPool);
        enemyEmmiter = new EnemyEmmiter(enemyShipPool, worldBounds, mainAtlas);
        mainShip = new MainShip(mainAtlas.findRegion("main_ship"),
                mainAtlas.findRegion("bulletMainShip"),
                bulletPool, explosionPool, shootSound, 100, 1, hpMoveFont);
        playMusic();
        sbLevel = new StringBuilder();
        sbHP = new StringBuilder();
        sbFrags = new StringBuilder();
    }

    private void initGameOverMessage() {
        gameOverMessage = new Sprite(mainAtlas.findRegion("message_game_over"));
        gameOverMessage.setHeightProportion(0.1f);
        gameOverMessage.setBottom(0.1f);
    }

    private void generateStars() {
        stars = new Star[STAR_COUNT];
        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
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

        backButton.setBottom(worldBounds.getBottom() + 0.01f);
        backButton.setLeft(worldBounds.getLeft() + 0.01f);
        newGameButton.setBottom(- 0.1f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        enemyEmmiter.generate(delta, frags);
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

        if(!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyShipPool.drawActiveObjects(batch);
            bonusPool.drawActiveObjects(batch);
        } else {
            if(music.isPlaying()) music.stop();
             backButton.draw(batch);
             gameOverMessage.draw(batch);
             newGameButton.draw(batch);
        }
        explosionPool.drawActiveObjects(batch);
        printInfo(batch);
        batch.end();
    }

    private void printInfo(SpriteBatch batch) {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + 0.01f, worldBounds.getTop() - 0.01f);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - 0.01f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmmiter.getLevel()), worldBounds.getRight() - 0.01f, worldBounds.getTop() - 0.01f, Align.right);
    }


    private void update(float delta) {
        for(int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        if(!mainShip.isDestroyed()) {
        bulletPool.updateActiveObjects(delta);
        enemyShipPool.updateActiveObjects(delta);
        bonusPool.updateActiveObjects(delta);
        mainShip.update(delta);
        }
        explosionPool.updateActiveObjects(delta);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        menuAtlas.dispose();
        mainAtlas.dispose();
        batch.dispose();
        music.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        backButton.touchDown(touch, pointer);
        newGameButton.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        backButton.touchUp(touch, pointer, this);
        newGameButton.touchUp(touch, pointer, this);
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
            shipCollision(enemyShip);
            damageShipFromBullet(enemyBulletList, enemyShip, false);
            if(enemyShip.isDestroyed()) {
                frags++;

            }
            damageShipFromBullet(enemyBulletList, mainShip, true);
        }
    }

    private void damageShipFromBullet(List<Bullet> enemyBulletList, Ship ship, boolean isMainShip) {
        for(Bullet bullet: enemyBulletList) {
            if(bullet.isDestroyed() || bullet.getOwner().equals(mainShip) == isMainShip) {
                continue;
            }
            if(ship.isBulletCollision(bullet)) {
                bullet.destroy();
                ship.damage(bullet.getDamage());
            }
        }
    }

    private void shipCollision(EnemyShip enemyShip) {
        float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
        if(enemyShip.pos.dst2(mainShip.pos) < Math.pow(minDist, 2)) {
            enemyShip.destroy();
            mainShip.damage(40);


        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    @Override
    public void accept(Button button) {
        if(button.equals(backButton)) {
            dispose();
            myLibGdxGame.setScreen(menuScreen);
        }
        if(button.equals(newGameButton)) {
            dispose();
            myLibGdxGame.setScreen(new GameScreen(menuScreen, myLibGdxGame));
        }
    }
}

package ru.skhanov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Base2DScreen;
import ru.skhanov.math.Rect;
import ru.skhanov.sprite.Background;
import ru.skhanov.sprite.Exit;
import ru.skhanov.sprite.Play;
import ru.skhanov.sprite.Star;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 256;
    private Background background;
    private Texture bgTexture;
    private TextureAtlas textureAtlas;
    private Star[] stars;
    private Exit exit;


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("menuAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }

        exit = new Exit(textureAtlas);

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for(int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        exit.resize(worldBounds);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

    }

    public void draw() {
        Gdx.gl.glClearColor(0.01f,0.16f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for(int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        exit.draw(batch);
        batch.end();
    }

    public void update(float delta) {
        for(int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        batch.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        exit.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        exit.touchUp(touch, pointer);
        return false;
    }
}

package ru.skhanov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    private SpriteBatch batch;
    private Texture img;
    private Texture img2;
    private TextureRegion imgReg;
    private  Vector2 spaceshipPosition;
    private Vector2 newSpaceshipPosition;
    private int moveDistance;
    private Vector2 spaceshipSpeed;

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("pixelSpace.jpg");
        img2 = new Texture("pixelSpaceShip.png");
//        imgReg = new TextureRegion(img2, 50, 50, 50 ,50);
        spaceshipPosition = new Vector2(200, 0);
        newSpaceshipPosition = new Vector2(200, 0);
        spaceshipSpeed = new Vector2(0, 0);
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.01f,0.16f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(img2, spaceshipPosition.x, spaceshipPosition.y);
        batch.end();

        moveSpaceshipOnTouch();
        moveSpaceshipOnKey();
    }

    private void moveSpaceshipOnKey() {
        spaceshipPosition.add(spaceshipSpeed);
    }

    private void moveSpaceshipOnTouch() {
        if(moveDistance > 0) {
               spaceshipPosition.add(spaceshipSpeed);
               moveDistance--;
               if(moveDistance <= 0) {
                   spaceshipSpeed.set(0, 0);
               }
        }



    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        img2.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        newSpaceshipPosition = new Vector2(screenX, reverseCoordinate(screenY));
        moveDistance = (int) newSpaceshipPosition.cpy().sub(spaceshipPosition).len();
        spaceshipSpeed = newSpaceshipPosition.cpy().sub(spaceshipPosition).nor();

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 19:
                spaceshipSpeed.add(0, 1);
                break;
            case 20:
                spaceshipSpeed.add(0, -1);
                break;
            case 21:
                spaceshipSpeed.add(-1, 0);
                break;
            case 22:
                spaceshipSpeed.add(1, 0);
                break;
            default:
                spaceshipSpeed.set(0, 0);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 19:
                spaceshipSpeed.sub(0, 1);
                break;
            case 20:
                spaceshipSpeed.sub(0, -1);
                break;
            case 21:
                spaceshipSpeed.sub(-1, 0);
                break;
            case 22:
                spaceshipSpeed.sub(1, 0);
                break;
            default:
                spaceshipSpeed.set(0, 0);
        }
        return super.keyUp(keycode);
    }

    private int reverseCoordinate(int point) {
        return Math.abs(point - Gdx.graphics.getHeight());
    }
}

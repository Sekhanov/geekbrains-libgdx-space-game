package ru.skhanov.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class Base2DScreen implements Screen, InputProcessor {


    @Override
    public void show() {
        System.out.println("show");

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize w = " + width + "h=" + height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }




    @Override
    public boolean keyDown(int keycode) {
        System.out.println("key down:" + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("key up:" + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("key taped:" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown x:" + screenX + "y:" + screenY );
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown x:" + screenX + "y:" + screenY );
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDown x:" + screenX + "y:" + screenY );
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

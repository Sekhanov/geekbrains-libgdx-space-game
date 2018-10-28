package ru.skhanov.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.screen.GameScreen;

public class Play extends Sprite {


    public Play(TextureAtlas atlas) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.3f);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)) {
            scale = 0.9f;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(isMe(touch)) {
            scale = 1f;
        }

        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, Game game, Screen parentScreen) {
        if(isMe(touch)) {
            scale = 1f;
            game.setScreen(new GameScreen());
        }

        return false;
    }


}

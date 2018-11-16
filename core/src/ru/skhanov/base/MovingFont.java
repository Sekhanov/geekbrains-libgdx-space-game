package ru.skhanov.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;


public class MovingFont extends Font {

    private static final String DAMAGE = "dm: -";

    private Vector2 pos;
    private Vector2 v;
    private int animationFrameCount = 30;

    public MovingFont(String fntFile, String imageFile, Vector2 v) {
        super(fntFile, imageFile);
        this.v = v;
    }

    public void set(float posX, float posY) {
        pos = new Vector2(posX, posY);
        animationFrameCount = 30;
    }

    public void draw(Batch batch, CharSequence charSequence) {
        this.setColor(Color.RED);
        super.draw(batch, DAMAGE + charSequence, pos.x, pos.y, Align.center);
        animationFrameCount--;
    }

    public void update(float delta) {
        if(animationFrameCount > 0 && pos != null) {
            pos.mulAdd(v, delta);
        }
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getAnimationFrameCount() {
        return animationFrameCount;
    }

    public void setAnimationFrameCount(int animationFrameCount) {
        this.animationFrameCount = animationFrameCount;
    }
}

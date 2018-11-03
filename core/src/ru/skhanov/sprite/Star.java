package ru.skhanov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.Sprite;
import ru.skhanov.math.Rect;
import ru.skhanov.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f, 0.01f));
        this.v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.03f, -0.01f));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float poxY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, poxY);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if(getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}

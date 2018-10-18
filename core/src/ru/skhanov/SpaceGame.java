package ru.skhanov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img2;
	TextureRegion imgReg;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("space.jpg");
		img2 = new Texture("badlogic.jpg");
		imgReg = new TextureRegion(img2, 50, 50, 50 ,50);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.01f,0.16f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(img2, 200, 200);
		batch.setColor(0, 0, 1, 1);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		img2.dispose();
	}
}

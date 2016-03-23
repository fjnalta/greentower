package com.greentower;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.states.GameStateManager;
import com.greentower.states.MenuState;

public class GreenTowerGame extends Game {
	
	public static final int TILE_SIZE = 32;
	public static final int WIDTH = 20 * TILE_SIZE;
	public static final int HEIGHT = 15 * TILE_SIZE;
	
	public static final String TITLE = "Green Tower";

	public static GameStateManager gsm;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		
		//draw background image
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//give MenuState to GSM
		gsm.push(new MenuState(gsm));
	}
	
	@Override
	//draw the images here
	public void render () {
		//wipe the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//redraw everything
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
}
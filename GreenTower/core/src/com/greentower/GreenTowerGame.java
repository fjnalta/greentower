package com.greentower;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.data.HighscoreList;
import com.greentower.states.MenuState;

public class GreenTowerGame extends Game {
	
	
	//virtual width and height for the game
	public static final int V_WIDTH = 20*32;
	public static final int V_HEIGHT = 30*32;
	//fix box2d scaling
	public static final float PPM = 10f;
	
	public static final String TITLE = "Green Tower";

	//all screens using this batch
	public SpriteBatch batch;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		
		setScreen(new MenuState(this));
	}
	
	@Override
	//draw the images here
	public void render () {
		super.render();
	}
}